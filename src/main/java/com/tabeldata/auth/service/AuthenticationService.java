package com.tabeldata.auth.service;

import com.tabeldata.auth.model.Authority;
import com.tabeldata.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.tabeldata.auth.repository.AuthenticationRepository;

import java.io.Serializable;
import java.util.List;

@Service
public class AuthenticationService implements Serializable {

    @Autowired
    private AuthenticationRepository repository;

    public User getUserByUsername(String user) throws EmptyResultDataAccessException {
        return repository.login(user);
    }

    public List<Authority> getRolesByUsername(String user) {
        return repository.distinctRolesByUsername(user);
    }

    public List<Authority> getRoles() {
        return repository.roles();
    }
}
