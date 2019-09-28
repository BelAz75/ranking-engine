package com.ranking.hachathon.account;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "uuid", unique = true)
  private String uuid;

  @Column(name = "name")
  private String name;

  @Column(name = "vk_url")
  private String vkUrl;

  @Column(name = "instagram_url")
  private String instagramUrl;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVkUrl() {
    return vkUrl;
  }

  public void setVkUrl(String vkUrl) {
    this.vkUrl = vkUrl;
  }

  public String getInstagramUrl() {
    return instagramUrl;
  }

  public void setInstagramUrl(String instagramUrl) {
    this.instagramUrl = instagramUrl;
  }
}
