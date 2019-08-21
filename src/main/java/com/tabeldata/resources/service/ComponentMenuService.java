package com.tabeldata.resources.service;

import com.tabeldata.resources.dao.ComponentMenuDao;
import com.tabeldata.resources.dao.DataPenggunaLoginDao;
import com.tabeldata.resources.dto.DataPenggunaLogin;
import com.tabeldata.resources.entity.ComponentMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
public class ComponentMenuService {

    @Autowired
    private ComponentMenuDao dao;

    @Autowired
    private DataPenggunaLoginDao penggunaLoginDao;

    public List<ComponentMenu> listAll(Principal principal) {
        DataPenggunaLogin data = penggunaLoginDao.getDataPenggunaLogin(principal.getName());
        return dao.listAll(data.getOtor());
    }
}
