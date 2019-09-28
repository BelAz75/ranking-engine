package com.ranking.hachathon.instagram;

import com.ranking.hachathon.account.AccountInfo;
import com.ranking.hachathon.account.UserAccount;
import com.ranking.hachathon.posts.*;
import me.postaddict.instagram.scraper.Endpoint;
import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.model.Account;
import me.postaddict.instagram.scraper.model.Media;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InstagramScraperService {
  private static Instagram INSTAGRAM;
  static {
    OkHttpClient httpClient = new OkHttpClient();
    INSTAGRAM = new Instagram(httpClient);
  }

  @Autowired
  private UnifiedPostRepository unifiedPostRepository;

  @Autowired
  private UnifiedAttachmentRepository unifiedAttachmentRepository;

  public AccountInfo getAccountInfo(String link) {
    try {
      Account account = INSTAGRAM.getAccountByLink(link);
      AccountInfo accountInfo = new AccountInfo();
      accountInfo.setId(account.getId());
      accountInfo.setFullName(account.getFullName());
      accountInfo.accountInfo = account.getBiography();
      accountInfo.profileIconUrl = account.getProfilePicUrl();
      return accountInfo;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void saveLatestPosts(String link, UserAccount userAccount) {
    try {
      Account account = INSTAGRAM.getAccountByUsername(link);
      if (account == null) return;
      for (Media media : account.getMedia().getNodes()) {
        UnifiedPost post = new UnifiedPost();
        post.setUserId(userAccount.getUuid());
        post.setCommentCount(media.getCommentCount());
        post.setLikeCount(media.getLikeCount());
        post.setPublicationDate(media.getTakenAtTimestamp());
        post.setPostText(handleText(media.getCaption()));
        post.setSourceType(SourceType.INSTAGRAM);
        post.setPostIdFromSource(media.getShortcode());
        unifiedPostRepository.save(post);

        UnifiedAttachments attachments = new UnifiedAttachments();
        attachments.setPostId(media.getShortcode());
        attachments.setHeight(media.getHeight());
        attachments.setWidth(media.getWidth());
        attachments.setAttachmentType(media.getIsVideo() ? AttachmentType.VIDEO : AttachmentType.PHOTO);
        attachments.setImageUrl(media.getDisplayUrl());

        if (attachments.getAttachmentType() == AttachmentType.VIDEO) {
          Media videoMedia = INSTAGRAM.getMediaByCode(media.getShortcode());
          attachments.setVideoUrl(videoMedia.getVideoUrl());
        }
        unifiedAttachmentRepository.save(attachments);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String handleText(String content) {
    Map<String, String> replacementMap = new HashMap<>();
    Pattern mentionPattern = Pattern.compile("@([a-zA-Z0-9_.])+");
    Matcher matcher = mentionPattern.matcher(content);
    while (matcher.find()) {
      String mention = matcher.group();
      String accountLink = Endpoint.getAccountLink(mention.substring(1));
      replacementMap.put(mention, "<a href=\"" + accountLink + "\" target=\"_blank\">" + mention + "</a>");
    }
    Pattern hashTagPattern = Pattern.compile("#([a-zA-Z0-9_.])+");
    matcher = hashTagPattern.matcher(content);
    while (matcher.find()) {
      String hashTag = matcher.group();
      String tagInfo = Endpoint.getTagByTagName(hashTag.substring(1));
      replacementMap.put(hashTag, "<a href=\"" + tagInfo + "\" target=\"_blank\">" + hashTag + "</a>");
    }
    String resultString = content;
    for (Map.Entry<String, String> entry : replacementMap.entrySet()) {
      resultString = resultString.replace(entry.getKey(), entry.getValue());
    }
    return resultString;
  }
}
