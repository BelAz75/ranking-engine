package com.ranking.hachathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class RankingEngineApplication {

  public static void main(String[] args) {
    SpringApplication.run(RankingEngineApplication.class, args);
  }
}
