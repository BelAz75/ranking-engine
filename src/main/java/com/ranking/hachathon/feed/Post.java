package com.ranking.hachathon.feed;

import com.ranking.hachathon.posts.SourceType;

import java.util.List;

public class Post {
  public long timestamp;
  public int likesCount;
  public String text;
  public SourceType sourceType;
  public List<Content> content;
  public int commentsCount;
  public User user;
}
