package com.tabeldata.auth.dao;

import com.tabeldata.auth.dto.Authority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("oracle11")
@Slf4j
public class AuthorityDaoOracle11 implements AuthorityDao {

    @Override
    public List<Authority> distinctRolesByUsername(String username) {
        return null;
    }
}
