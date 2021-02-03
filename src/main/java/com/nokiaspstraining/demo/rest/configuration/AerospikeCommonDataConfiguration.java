package com.nokiaspstraining.demo.rest.configuration;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.aerospike.convert.AerospikeCustomConversions;
import org.springframework.data.aerospike.convert.AerospikeTypeAliasAccessor;
import org.springframework.data.aerospike.convert.MappingAerospikeConverter;
import org.springframework.data.aerospike.core.AerospikeExceptionTranslator;
import org.springframework.data.aerospike.core.DefaultAerospikeExceptionTranslator;
import org.springframework.data.aerospike.mapping.AerospikeMappingContext;
import org.springframework.data.aerospike.mapping.Document;
import org.springframework.data.aerospike.query.StatementBuilder;
import org.springframework.data.aerospike.query.cache.IndexesCache;
import org.springframework.data.aerospike.query.cache.IndexesCacheHolder;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;

import java.util.Collections;

@Configuration(proxyBeanMethods = false)
class AerospikeCommonDataConfiguration {

    @Bean(name = "aerospikeStatementBuilder")
    @ConditionalOnMissingBean(name = "aerospikeStatementBuilder")
    public StatementBuilder aerospikeStatementBuilder(IndexesCache indexesCache) {
        return new StatementBuilder(indexesCache);
    }

    @Bean(name = "aerospikeIndexCache")
    @ConditionalOnMissingBean(name = "aerospikeIndexCache")
    public IndexesCacheHolder aerospikeIndexCache() {
        return new IndexesCacheHolder();
    }


    @Bean(name = "mappingAerospikeConverter")
    @ConditionalOnMissingBean(name = "mappingAerospikeConverter")
    public MappingAerospikeConverter mappingAerospikeConverter(AerospikeMappingContext aerospikeMappingContext,
                                                               AerospikeTypeAliasAccessor aerospikeTypeAliasAccessor,
                                                               AerospikeCustomConversions aerospikeCustomConversions) {
        return new MappingAerospikeConverter(aerospikeMappingContext, aerospikeCustomConversions, aerospikeTypeAliasAccessor);
    }

    @Bean(name = "aerospikeTypeAliasAccessor")
    @ConditionalOnMissingBean(name = "aerospikeTypeAliasAccessor")
    public AerospikeTypeAliasAccessor aerospikeTypeAliasAccessor() {
       // String typeKey = aerospikeDataProperties.getTypeKey();
        return new AerospikeTypeAliasAccessor();
    }

    @Bean(name = "aerospikeCustomConversions")
    @ConditionalOnMissingBean(name = "aerospikeCustomConversions")
    public AerospikeCustomConversions aerospikeCustomConversions() {
        return new AerospikeCustomConversions(Collections.emptyList());
    }

    @Bean(name = "aerospikeMappingContext")
    @ConditionalOnMissingBean(name = "aerospikeMappingContext")
    public AerospikeMappingContext aerospikeMappingContext(ApplicationContext applicationContext,
                                                           AerospikeCustomConversions aerospikeCustomConversions
                                                           ) throws Exception {
        AerospikeMappingContext context = new AerospikeMappingContext();
        context.setInitialEntitySet(new EntityScanner(applicationContext).scan(Document.class, Persistent.class));
        context.setSimpleTypeHolder(aerospikeCustomConversions.getSimpleTypeHolder());
        context.setDefaultNameSpace("test");
        return context;
    }

    @Bean(name = "aerospikeExceptionTranslator")
    @ConditionalOnMissingBean(name = "aerospikeExceptionTranslator")
    public AerospikeExceptionTranslator aerospikeExceptionTranslator() {
        return new DefaultAerospikeExceptionTranslator();
    }
}
