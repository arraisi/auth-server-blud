package com.tabeldata.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.tabeldata.oauth.models.OauthApplication;
import com.tabeldata.oauth.models.OauthClientDetails;
import com.tabeldata.oauth.models.OauthGrantType;
import com.tabeldata.oauth.models.OauthScope;
import com.tabeldata.oauth.repository.OauthClientDetailsRepository;

import java.io.Serializable;
import java.util.List;

@Service
public class OauthClientDetailsService implements Serializable {

    @Autowired
    private OauthClientDetailsRepository repository;

    public OauthClientDetails findByClientId(String clientId) throws EmptyResultDataAccessException {
        return repository.getResourceByClientId(clientId);
    }

    public List<OauthScope> findScopeByClientId(String clientId) {
        return repository.getScopesByClientId(clientId);
    }

    public List<OauthGrantType> findGrantTypeByClientId(String clientId) {
        return repository.getGrantTypeByClientId(clientId);
    }

    public List<OauthApplication> findApplicationByClientId(String clientId) {
        return repository.getApplicationByClientId(clientId);
    }

    public List<String> findRedirectUrlsByClientId(String clientId) {
        return repository.getRedirectUrlsByClientId(clientId);
    }
}
