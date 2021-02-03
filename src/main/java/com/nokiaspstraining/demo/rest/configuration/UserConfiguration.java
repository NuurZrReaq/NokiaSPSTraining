package com.nokiaspstraining.demo.rest.configuration;


import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.aerospike.convert.MappingAerospikeConverter;
import org.springframework.data.aerospike.core.AerospikeExceptionTranslator;
import org.springframework.data.aerospike.core.AerospikeTemplate;
import org.springframework.data.aerospike.mapping.AerospikeMappingContext;
import org.springframework.data.aerospike.query.QueryEngine;
import org.springframework.data.aerospike.query.StatementBuilder;
import org.springframework.data.aerospike.query.cache.IndexInfoParser;
import org.springframework.data.aerospike.query.cache.IndexRefresher;
import org.springframework.data.aerospike.query.cache.IndexesCacheUpdater;
import org.springframework.data.aerospike.query.cache.InternalIndexOperations;
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement

public class UserConfiguration {


    public @Bean AerospikeClient aerospikeClient() {

        ClientPolicy policy = new ClientPolicy();
        policy.failIfNotConnected = true;

        return new AerospikeClient(policy, "172.28.128.4", 3000);
    }

    @Bean(name = "aerospikeTemplate")
    @ConditionalOnMissingBean(name = "aerospikeTemplate")
    public AerospikeTemplate aerospikeTemplate(AerospikeClient aerospikeClient,
                                               MappingAerospikeConverter mappingAerospikeConverter,
                                               AerospikeMappingContext aerospikeMappingContext,
                                               AerospikeExceptionTranslator aerospikeExceptionTranslator,
                                               QueryEngine queryEngine, IndexRefresher indexRefresher) {
        return new AerospikeTemplate(aerospikeClient,
                "test",
                mappingAerospikeConverter,
                aerospikeMappingContext,
                aerospikeExceptionTranslator, queryEngine, indexRefresher);
    }








}


