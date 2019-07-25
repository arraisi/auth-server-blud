package com.tabeldata.oauth;

import com.tabeldata.oauth.repository.JdbcTokenStoreCustom;
import com.tabeldata.oauth.repository.JdbcTokenStoreCustomOracle11;
import com.tabeldata.oauth.repository.JdbcTokenStoreCustomPostgreSQL;
import com.tabeldata.oauth.repository.JdbcTokenStoreCustomSqlServer2017;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class TokenStoreConfiguration {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean
    @Profile("postgresql")
    public JdbcTokenStoreCustom tokenStorePostgreSQl() {
        JdbcTokenStoreCustom tokenStore = new JdbcTokenStoreCustomPostgreSQL(dataSource);
        return tokenStore;
    }

    @Bean
    @Profile("oracle11")
    public JdbcTokenStoreCustom tokenStoreOracle11() {
        JdbcTokenStoreCustom tokenStore = new JdbcTokenStoreCustomOracle11(dataSource);
        return tokenStore;
    }

    @Bean
    @Profile("sqlserver2017")
    public JdbcTokenStoreCustom tokenStoreSqlServer2017() {
        JdbcTokenStoreCustom tokenStore = new JdbcTokenStoreCustomSqlServer2017(dataSource);
        return tokenStore;
    }

}
