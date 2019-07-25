package com.tabeldata.auth.dao;

import com.tabeldata.auth.dto.Authority;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("sqlserver2017")
public class AuthorityDaoSqlServer2017 implements AuthorityDao {

    @Override
    public List<Authority> distinctRolesByUsername(String username) {
        return null;
    }
}
