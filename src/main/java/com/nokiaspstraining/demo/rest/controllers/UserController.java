package com.nokiaspstraining.demo.rest.controllers;


import com.nokiaspstraining.demo.rest.repositories.UserRepAerospike;
import com.nokiaspstraining.demo.rest.repositories.UserRepositoryRest;
import io.spring.guides.gs_producing_web_service.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepositoryRest userRepository;

    @Autowired
    private UserRepAerospike userRepAerospike;




    @RequestMapping("/{name}")
    @ResponseBody
    public io.spring.guides.gs_producing_web_service.User getUser (@PathVariable String name) {
        io.spring.guides.gs_producing_web_service.User  user = userRepository.findUser(name);
        return user;


    }


    @RequestMapping(method = RequestMethod.POST,value = "/addUser")
    public void addUser(@RequestBody User user){
        if(user != null)
            userRepAerospike.save(user);

    }



}
