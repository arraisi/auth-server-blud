package com.tabeldata.auth.dao;

import com.tabeldata.auth.dto.Authority;

import java.util.List;

public interface AuthorityDao {

    List<Authority> distinctRolesByUsername(String username);
}
