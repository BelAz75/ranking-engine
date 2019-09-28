package com.ranking.hachathon.account;

import com.ranking.hachathon.instagram.InstagramScraperService;
import com.ranking.hachathon.vk.VkSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AccountController {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private InstagramScraperService instagramScraper;
  @Autowired
  private VkSearchService vkSearchService;

  @PostMapping("/accounts")
  public void addAccount(@RequestParam(name = "vk", required = false) String vkUrl,
                         @RequestParam(name = "instagram", required = false) String instagramUrl) {
    UserAccount account = new UserAccount();
      if (vkUrl != null) {
          AccountInfo vkInfo = vkSearchService.getAccountInfo(vkUrl);
          account.setName(vkInfo.getFullName());
          account.setVkId(vkInfo.getId());
      } else if (instagramUrl != null) {
          AccountInfo instagramInfo = instagramScraper.getAccountInfo(instagramUrl);
          account.setName(instagramInfo.getFullName());
          account.setInstagramId(instagramInfo.getId());
          account.setAccountInfo(instagramInfo.accountInfo);
          account.setProfileIcon(instagramInfo.profileIconUrl);
      }
    account.setVkUrl(vkUrl);
    account.setInstagramUrl(instagramUrl);
    accountRepository.save(account);
    if (instagramUrl != null) {
      instagramScraper.saveLatestPosts(instagramUrl, account);
    }
  }

  @GetMapping("/accounts")
  public List<UserAccount> getAccounts() {
    return accountRepository.findAll();
  }

  @DeleteMapping("/accounts")
  public void deleteAccount(@RequestParam(name = "id") String accountId) {
    UserAccount account = accountRepository.findAccountByUuid(accountId);
    if (account != null) {
      accountRepository.deleteById(accountId);
    }
  }
}
