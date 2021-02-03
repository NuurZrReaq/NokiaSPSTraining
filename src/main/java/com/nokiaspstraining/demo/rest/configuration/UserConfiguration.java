package com.nokiaspstraining.demo.rest.configuration;


import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;
import com.nokiaspstraining.demo.rest.repositories.UserRepAerospike;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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


@Configuration()
@EnableTransactionManagement
@EnableAerospikeRepositories(repositoryBaseClass = UserRepAerospike.class)
public class UserConfiguration {


    public @Bean(destroyMethod = "close") AerospikeClient aerospikeClient() {

        ClientPolicy policy = new ClientPolicy();
        policy.failIfNotConnected = true;
        policy.timeout = 2000;

        return new AerospikeClient(policy, "127.0.0.1", 3000);
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


    @Bean(name = "aerospikeQueryEngine")
    @ConditionalOnMissingBean(name = "aerospikeQueryEngine")
    public QueryEngine aerospikeQueryEngine(AerospikeClient aerospikeClient,
                                            StatementBuilder statementBuilder) {
        QueryEngine queryEngine = new QueryEngine(aerospikeClient, statementBuilder, aerospikeClient.getQueryPolicyDefault());
        return queryEngine;
    }

    @Bean(name = "aerospikeIndexRefresher")
    @ConditionalOnMissingBean(name = "aerospikeIndexRefresher")
    public IndexRefresher aerospikeIndexRefresher(AerospikeClient aerospikeClient, IndexesCacheUpdater indexesCacheUpdater) {
        IndexRefresher refresher = new IndexRefresher(aerospikeClient, aerospikeClient.getInfoPolicyDefault(), new InternalIndexOperations(new IndexInfoParser()), indexesCacheUpdater);
        refresher.refreshIndexes();
        return refresher;
    }





}


