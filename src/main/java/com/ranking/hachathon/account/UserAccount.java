package com.ranking.hachathon.account;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class UserAccount {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "uuid", unique = true)
  private String uuid;

  @Column(name = "name")
  private String name;

  @Column(name = "account_info")
  private String accountInfo;

  @Column(name = "profile_icon")
  private String profileIcon;

  @Column(name = "vk_url")
  private String vkUrl;

  @Column(name = "vk_id")
  private Long vkId;

  @Column(name = "instagram_url")
  private String instagramUrl;

  @Column(name = "instagram_id")
  private Long instagramId;

  public Long getVkId() {
    return vkId;
  }

  public void setVkId(Long vkId) {
    this.vkId = vkId;
  }

  public Long getInstagramId() {
    return instagramId;
  }

  public void setInstagramId(Long instagramId) {
    this.instagramId = instagramId;
  }

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

  public String getAccountInfo() {
    return accountInfo;
  }

  public void setAccountInfo(String accountInfo) {
    this.accountInfo = accountInfo;
  }

  public String getProfileIcon() {
    return profileIcon;
  }

  public void setProfileIcon(String profileIcon) {
    this.profileIcon = profileIcon;
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
