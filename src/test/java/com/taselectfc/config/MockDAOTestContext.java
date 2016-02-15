package com.taselectfc.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.taselectfc.dao.FixtureDAO;

@Profile("mock-dao")
@Configuration
@EnableJpaRepositories
public class MockDAOTestContext {

    @Bean
    @Primary
    public FixtureDAO fixtureDAO() {
        return Mockito.mock(FixtureDAO.class);
    }
}
