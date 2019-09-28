package com.ranking.hachathon.instagram;

import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.model.Account;
import me.postaddict.instagram.scraper.model.Media;
import me.postaddict.instagram.scraper.model.PageObject;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstagramScraper {
  private static Instagram INSTAGRAM;
  static {
    OkHttpClient httpClient = new OkHttpClient();
    INSTAGRAM = new Instagram(httpClient);
  }

  public List<Post> scanUser(String userName) {
    Account account = null;
    try {
      account = INSTAGRAM.getAccountByUsername(userName);
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
    if (account == null) return Collections.emptyList();
    User user = new User();
    user.username = account.getUsername();
    user.fullName = account.getFullName();
    user.accountInfo = account.getBiography();
    user.profileIconUrl = account.getProfilePicUrl();
    return getLatestPosts(account.getMedia().getNodes());
  }

  private List<Post> getLatestPosts(List<Media> medias) {
    return medias.stream().map(media -> {
      Post post = new Post();
      post.timestamp = media.getTakenAtTimestamp();
      post.likesCount = media.getLikeCount();
      post.text = media.getCaption();
      post.contentType = Type.IMAGE;
      Content content = new Content();
      content.height = media.getHeight();
      content.width = media.getWidth();
      content.url = media.getDisplayUrl();
      post.content = content;
      return post;
    }).collect(Collectors.toList());
  }
}
