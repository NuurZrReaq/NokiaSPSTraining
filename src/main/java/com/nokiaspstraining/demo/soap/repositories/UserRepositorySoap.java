package com.nokiaspstraining.demo.soap.repositories;


import io.spring.guides.gs_producing_web_service.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class UserRepositorySoap {


    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void initUsers(){
        User user = new User();
        user.setId(1);
        user.setAge(23);
        user.setEmail("noor.com");
        user.setName("Noor");

        User user1 = new User();
        user1.setId(2);
        user1.setAge(23);
        user1.setEmail("noor1.com");
        user1.setName("Noor1");

        users.add(user);
        users.add(user1);

    }


    public User findUser(String name){
        User user = users.stream().filter(u->u.getName().equals(name)).findFirst().orElse(null);
        return user;
    }


}
