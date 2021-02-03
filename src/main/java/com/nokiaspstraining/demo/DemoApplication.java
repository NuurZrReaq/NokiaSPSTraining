package com.nokiaspstraining.demo;

import com.nokiaspstraining.demo.rest.repositories.UserRepAerospike;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;


@Configuration
@SpringBootApplication
@EnableAerospikeRepositories(repositoryBaseClass = UserRepAerospike.class)


public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
