package com.tabeldata.components.dao;

import com.tabeldata.auth.dto.Authority;
import com.tabeldata.components.dto.MenuDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("sqlserver2017")
public class MenuDaoSqlServer2017 implements MenuDao {

    @Override
    public List<MenuDto> getMenuByRolesAndModule(String moduleId, List<Authority> roles) {
        return null;
    }
}
