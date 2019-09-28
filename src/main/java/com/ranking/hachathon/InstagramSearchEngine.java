package com.ranking.hachathon;

import com.ranking.hachathon.instagram.InstagramScraper;
import com.ranking.hachathon.instagram.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class InstagramSearchEngine {
  @Autowired
  private InstagramScraper instagramScraper;

  @GetMapping("/instagram")
  public List<Post> getInstagramUserInfo() {
    return instagramScraper.scanUser("kevin");
  }
}
