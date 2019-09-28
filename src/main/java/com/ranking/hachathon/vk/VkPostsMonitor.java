package com.ranking.hachathon.vk;

import com.ranking.hachathon.account.AccountRepository;
import com.ranking.hachathon.account.UserAccount;
import com.ranking.hachathon.posts.SourceType;
import com.ranking.hachathon.posts.UnifiedPost;
import com.ranking.hachathon.posts.UnifiedPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

@Service
public class VkPostsMonitor {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UnifiedPostRepository unifiedPostRepository;

    @Autowired
    private VkRequestExecutor vkRequestExecutor;

    // 6 hours delay
    @Scheduled(fixedDelayString = "21600000")
    public void monitor() {
        List<UserAccount> accounts = accountRepository.findAll();

        Set<String> vkUserIds = accounts.stream().map(UserAccount::getUuid).collect(Collectors.toSet());

        Map<String, Set<String>> userIdToPostIds;
        if (vkUserIds.isEmpty()) {
            userIdToPostIds = new HashMap<>();
        } else {
            userIdToPostIds = unifiedPostRepository.findByUserIds(vkUserIds)
                    .stream()
                    .collect(Collectors.groupingBy(UnifiedPost::getUserId, Collectors.mapping(UnifiedPost::getPostIdFromSource, Collectors.toSet())));
        }

        List<UnifiedPost> result = new ArrayList<>();
        for (UserAccount account : accounts) {
            Set<String> processedPostIds = userIdToPostIds.computeIfAbsent(account.getUuid(), k -> emptySet());

            List<VkPost> posts = vkRequestExecutor.getPosts(account.getVkId().intValue()).stream()
                    .filter(vkPost -> !processedPostIds.contains(vkPost.getPostId().toString()))
                    .collect(Collectors.toList());

            result.addAll(posts.stream().map(vkPost -> {
                UnifiedPost unifiedPost = new UnifiedPost();
                unifiedPost.setUserId(account.getUuid());
                unifiedPost.setCommentCount(vkPost.getCommentCount());
                unifiedPost.setLikeCount(vkPost.getCommentCount());
                unifiedPost.setSourceType(SourceType.VK);
                unifiedPost.setPostText(vkPost.getText());
                unifiedPost.setPublicationDate(vkPost.getCreatedDate());
                unifiedPost.setPostScore(vkPost.getLikeCount() + vkPost.getCommentCount());
                unifiedPost.setPostIdFromSource(vkPost.getPostId().toString());
                return unifiedPost;
            }).collect(Collectors.toList()));
        }

        unifiedPostRepository.saveAll(result);
    }

}
