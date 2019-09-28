package com.ranking.hachathon.instagram;

import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.model.Account;
import me.postaddict.instagram.scraper.model.Media;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InstagramScraper {
  private static Instagram INSTAGRAM;
  static {
    OkHttpClient httpClient = new OkHttpClient();
    INSTAGRAM = new Instagram(httpClient);
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
      Post post = new Post();
      post.timestamp = media.getTakenAtTimestamp();
      post.likesCount = media.getLikeCount();
      post.text = media.getCaption();
      post.contentType = media.getIsVideo() ? Type.VIDEO : Type.IMAGE;
      post.commentsCount = media.getCommentCount();
      Content content = new Content();
      content.height = media.getHeight();
      content.width = media.getWidth();
      content.imageUrl = media.getDisplayUrl();
      if (post.contentType == Type.VIDEO) {
        Media videoMedia = INSTAGRAM.getMediaByCode(media.getShortcode());
        content.videoUrl = videoMedia.getVideoUrl();
      }
      post.content = content;
      post.user = user;
      posts.add(post);
    }
    return posts;
  }
}
