package com.ranking.hachathon.vk;

import com.ranking.hachathon.user.User;
import com.ranking.hachathon.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

@Service
public class VkPostsMonitor {

    // Replace with repository
    private List<VkPost> result = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VkRequestExecutor vkRequestExecutor;

    // 6 hours delay
    @Scheduled(fixedDelayString = "21600000")
    public void monitor() {
        List<User> users = userRepository.getUserList();

        // Get top 20 latest posts for users Map<UserId, Set<PostId>
        Map<String, Set<Integer>> userIdToTopPostIds = new HashMap<>();

        for (User user : users) {
            Set<Integer> processedPostIds = userIdToTopPostIds.computeIfAbsent(user.getId(), k -> emptySet());

            List<VkPost> posts = vkRequestExecutor.getPosts(user.getVkId()).stream()
                    .filter(vkPost -> !processedPostIds.contains(vkPost.getPostId()))
                    .collect(Collectors.toList());

            result.addAll(posts);
        }
    }

}
