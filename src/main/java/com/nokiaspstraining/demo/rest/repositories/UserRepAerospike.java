package com.nokiaspstraining.demo.rest.repositories;

import io.spring.guides.gs_producing_web_service.User;
import org.springframework.data.aerospike.repository.AerospikeRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserRepAerospike extends AerospikeRepository<User,Integer> {






}
