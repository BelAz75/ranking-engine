package com.ranking.hachathon;

import com.ranking.hachathon.instagram.InstagramScraperService;
import com.ranking.hachathon.instagram.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class FeedController {
  @Autowired
  private InstagramScraperService instagramScraper;

  @GetMapping("/feed")
  public List<Post> getFeed() {
    return instagramScraper.scanUser("aleksandrovechkinofficial");
  }
}
