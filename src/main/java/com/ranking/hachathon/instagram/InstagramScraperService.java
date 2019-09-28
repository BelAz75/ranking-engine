package com.ranking.hachathon.instagram;

import com.ranking.hachathon.posts.AttachmentType;
import com.ranking.hachathon.posts.SourceType;
import com.ranking.hachathon.posts.UnifiedAttachments;
import com.ranking.hachathon.posts.UnifiedPost;
import me.postaddict.instagram.scraper.Endpoint;
import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.model.Account;
import me.postaddict.instagram.scraper.model.Media;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InstagramScraperService {
  private static Instagram INSTAGRAM;
  static {
    OkHttpClient httpClient = new OkHttpClient();
    INSTAGRAM = new Instagram(httpClient);
  }

  public String getFullName(String link) {
    try {
      Account account = INSTAGRAM.getAccountByLink(link);
      return account.getFullName();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  public List<Post> scanUser(String userName) {
    try {
      Account account = INSTAGRAM.getAccountByUsername(userName);
      if (account == null) return Collections.emptyList();
      User user = new User();
      user.username = account.getUsername();
      user.fullName = account.getFullName();
      user.accountInfo = account.getBiography();
      user.profileIconUrl = account.getProfilePicUrl();
      return getLatestPosts(account.getMedia().getNodes(), user);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  private List<Post> getLatestPosts(List<Media> medias, User user) throws IOException {
    List<Post> posts = new ArrayList<>();
    for (Media media : medias) {
      UnifiedPost post = new UnifiedPost();
      post.setCommentCount(media.getCommentCount());
      post.setLikeCount(media.getLikeCount());
      post.setPublicationDate(media.getTakenAtTimestamp());
      post.setPostText(handleText(media.getCaption()));
      post.setSourceType(SourceType.INSTAGRAM);
      post.setPostIdFromSource(media.getShortcode());

      UnifiedAttachments attachments = new UnifiedAttachments();
      attachments.setHeight(media.getHeight());
      attachments.setWidth(media.getWidth());
      attachments.setAttachmentType(media.getIsVideo() ? AttachmentType.VIDEO : AttachmentType.PHOTO);
      attachments.setImageUrl(media.getDisplayUrl());

      if (attachments.getAttachmentType() == AttachmentType.VIDEO) {
        Media videoMedia = INSTAGRAM.getMediaByCode(media.getShortcode());
        attachments.setVideoUrl(videoMedia.getVideoUrl());
      }
    }
    return posts;
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
