package com.ranking.hachathon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class DummyRestController {
  @Value("${spring.datasource.url}")
  private String dbUrl;

  @GetMapping("/one")
  public String lol() {
    return dbUrl;
  }
}
