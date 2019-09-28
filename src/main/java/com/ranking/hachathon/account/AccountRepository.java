package com.ranking.hachathon.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<UserAccount, String> {
  UserAccount findAccountByUuid(String id);
}
