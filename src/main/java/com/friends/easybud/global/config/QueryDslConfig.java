package com.friends.easybud.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

    private final EntityManager em;

    public QueryDslConfig(final EntityManager em) {
        this.em = em;
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}