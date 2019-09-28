package com.ranking.hachathon.account;

import com.ranking.hachathon.instagram.InstagramScraper;
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
  private InstagramScraper instagramScraper;
  @Autowired
  private VkSearchService vkSearchService;

  @PostMapping("/accounts")
  public void addAccount(@RequestParam(name = "vk", required = false) String vkUrl,
                         @RequestParam(name = "instagram", required = false) String instagramUrl) {
    Account account = new Account();
    if (vkUrl != null) {
      account.setName(vkSearchService.getFullName(vkUrl));
    }
    if (instagramUrl != null) {
      account.setName(instagramScraper.getFullName(instagramUrl));
    }
    account.setVkUrl(vkUrl);
    account.setInstagramUrl(instagramUrl);
    accountRepository.save(account);
  }

  @GetMapping("/accounts")
  public List<Account> getAccounts() {
    return accountRepository.findAll();
  }

  @DeleteMapping("/accounts")
  public void deleteAccount(@RequestParam(name = "id") String accountId) {
    Account account = accountRepository.findAccountByUuid(accountId);
    if (account != null) {
      accountRepository.deleteById(accountId);
    }
  }
}
