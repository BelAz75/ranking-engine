package com.ranking.hachathon.feed;

import com.ranking.hachathon.account.UserAccount;
import com.ranking.hachathon.posts.UnifiedAttachments;
import com.ranking.hachathon.posts.UnifiedPost;

import java.util.List;
import java.util.stream.Collectors;

public class Converter {
    static Post convert(UnifiedPost post, List<UnifiedAttachments> attachments, UserAccount account) {
        Post result = new Post();
        result.timestamp = post.getPublicationDate();
        result.likesCount = post.getLikeCount();
        result.text = post.getPostText();
        result.commentsCount = post.getCommentCount();
        result.sourceType = post.getSourceType();
        result.content = convert(attachments);
        result.user = convert(account);
        return result;
    }

    private static List<Content> convert(List<UnifiedAttachments> attachments) {
        return attachments.stream().map(attachment -> {
            Content content = new Content();
            content.height = attachment.getHeight();
            content.width = attachment.getWidth();
            content.imageUrl = attachment.getImageUrl();
            content.videoUrl = attachment.getVideoUrl();
            content.attachmentType = attachment.getAttachmentType();
            return content;
        }).collect(Collectors.toList());
    }

    private static User convert(UserAccount account) {
        User user = new User();
        user.profileIconUrl = account.getProfileIcon();
        user.fullName = account.getName();
        user.accountInfo = account.getAccountInfo();
        return user;
    }
}
