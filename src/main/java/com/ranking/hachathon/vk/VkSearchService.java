package com.ranking.hachathon.vk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class VkSearchService {

    @Autowired
    private VkRequestExecutor vkRequestExecutor;

    public List<VkUser> getUsersByIds(Set<String> ids) {
        return vkRequestExecutor.makeGetUsersRequest(ids);
    }

}
