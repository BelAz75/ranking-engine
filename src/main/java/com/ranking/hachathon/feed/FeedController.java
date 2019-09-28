package com.ranking.hachathon.feed;

import com.ranking.hachathon.account.UserAccount;
import com.ranking.hachathon.account.AccountRepository;
import com.ranking.hachathon.posts.UnifiedAttachmentRepository;
import com.ranking.hachathon.posts.UnifiedAttachments;
import com.ranking.hachathon.posts.UnifiedPost;
import com.ranking.hachathon.posts.UnifiedPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class FeedController {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private UnifiedPostRepository unifiedPostRepository;
  @Autowired
  private UnifiedAttachmentRepository unifiedAttachmentRepository;

  @GetMapping("/feed")
  public List<Post> getFeed() {
    Map<String, UserAccount> accountMap = accountRepository.findAll().stream()
                                                        .collect(Collectors.toMap(UserAccount::getUuid, Function.identity()));
    List<UnifiedPost> posts = unifiedPostRepository.findAll(Sort.by("publication_date"));
    List<Post> result = new ArrayList<>();
    for (UnifiedPost post : posts) {
      List<UnifiedAttachments> attachments = unifiedAttachmentRepository.findAllByPostId(post.getPostIdFromSource());
      result.add(Converter.convert(post, attachments, accountMap.get(post.getUserId())));
    }
    return result;
  }
}
