package com.tabeldata.resources.service;

import com.tabeldata.resources.dao.DataPenggunaLoginDao;
import com.tabeldata.resources.dto.DataPenggunaLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class DataPenggunaLoginService {

    @Autowired
    private DataPenggunaLoginDao dao;

    public DataPenggunaLogin getDataPenggunaLogin(String penggunaNrk) throws EmptyResultDataAccessException {
        DataPenggunaLogin value = dao.getDataPenggunaLogin(penggunaNrk);
        log.info("Data Pengguna Pada DAO: {}", value);
        return value;
    }
}
