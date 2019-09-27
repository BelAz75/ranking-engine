package com.ranking.hachathon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class DummyRestController {
  @Value("${spring.datasource.url}")
  private String dbUrl;

  @GetMapping("/one")
  public String lol() {
    return dbUrl;
  }
}
