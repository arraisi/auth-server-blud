package com.tabeldata.components.dao;

import com.tabeldata.auth.dto.Authority;
import com.tabeldata.components.dto.MenuDto;

import java.util.List;

public interface MenuDao {

    List<MenuDto> getMenuByRolesAndModule(String moduleId, List<Authority> roles);
}
