package com.ranking.hachathon;

import com.ranking.hachathon.vk.VkSearchService;
import com.ranking.hachathon.vk.VkUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class VkSearchEngine {

  @Autowired
  private VkSearchService vkSearchService;

  @GetMapping("/vk")
  public List<VkUser> getVkUserInfo() {
    return vkSearchService.getUsersByIds(Collections.emptySet());
  }

}
