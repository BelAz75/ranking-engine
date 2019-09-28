package com.ranking.hachathon.posts;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "unified_post")
public class UnifiedPost {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "source_type")
    private SourceType sourceType;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "post_id_from_source")
    private String postIdFromSource;

    @Column(name = "post_text")
    private String postText;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "comment_count")
    private int commentCount;

    @Column(name = "post_score")
    private int postScore;

    @Column(name = "publication_date")
    private long publicationDate;

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostIdFromSource() {
        return postIdFromSource;
    }

    public void setPostIdFromSource(String postIdFromSource) {
        this.postIdFromSource = postIdFromSource;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getPostScore() {
        return postScore;
    }

    public void setPostScore(int postScore) {
        this.postScore = postScore;
    }

    public long getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(long publicationDate) {
        this.publicationDate = publicationDate;
    }

}
