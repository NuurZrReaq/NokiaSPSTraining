package com.nokiaspstraining.demo.soap.controllers;

import com.nokiaspstraining.demo.soap.repositories.UserRepositorySoap;
import io.spring.guides.gs_producing_web_service.UserRequest;
import io.spring.guides.gs_producing_web_service.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class UserControllerSoap {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private UserRepositorySoap userRepository;
    @Autowired
    public UserControllerSoap(UserRepositorySoap userRepository) {
        this.userRepository = userRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "userRequest")
    @ResponsePayload
    public UserResponse getUser(@RequestPayload UserRequest request) {
        UserResponse response = new UserResponse();
        response.setUser(userRepository.findUser(request.getName()));
        System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

        return response;

    }


}
