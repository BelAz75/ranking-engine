package com.ranking.hachathon.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserRepository {

    public List<User> getUserList() {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFullName("Olga Buzova");
        user.setVkId(32707600);

        users.add(user);

        return users;
    }

}
