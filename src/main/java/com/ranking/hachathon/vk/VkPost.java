package com.ranking.hachathon.vk;

import java.time.LocalDateTime;
import java.util.List;

public class VkPost {

    // Database id
    private String id;
    // VK post id
    private Integer postId;
    private String userId;
    private String text;
    private int likeCount;
    private int repostCount;
    private int viewCount;
    private int commentCount;
    private LocalDateTime createdDate;
    private List<VkContent> content;
    private List<VkComment> comments;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getRepostCount() {
        return repostCount;
    }

    public void setRepostCount(int repostCount) {
        this.repostCount = repostCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<VkContent> getContent() {
        return content;
    }

    public void setContent(List<VkContent> content) {
        this.content = content;
    }
}
