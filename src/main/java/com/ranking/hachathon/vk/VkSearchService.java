package com.ranking.hachathon.vk;

import com.ranking.hachathon.account.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VkSearchService {

    @Autowired
    private VkRequestExecutor vkRequestExecutor;

    public AccountInfo getAccountInfo(String link) {
        return vkRequestExecutor.getAccountInfo(link);
    }
}
