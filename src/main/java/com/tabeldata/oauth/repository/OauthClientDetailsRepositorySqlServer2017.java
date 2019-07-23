package com.tabeldata.oauth.repository;

import com.tabeldata.oauth.models.OauthApplication;
import com.tabeldata.oauth.models.OauthClientDetails;
import com.tabeldata.oauth.models.OauthGrantType;
import com.tabeldata.oauth.models.OauthScope;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("sqlserver2017")
public class OauthClientDetailsRepositorySqlServer2017 implements OauthClientDetailsRepository {

    @Override
    public List<OauthApplication> getApplicationByClientId(String clientId) throws SQLException {
        return null;
    }

    @Override
    public List<OauthGrantType> getGrantTypeByClientId(String clientId) throws SQLException {
        return null;
    }

    @Override
    public List<String> getRedirectUrlsByClientId(String clientId) throws SQLException {
        return null;
    }

    @Override
    public List<OauthScope> getScopesByClientId(String clientId) throws SQLException {
        return null;
    }

    @Override
    public OauthClientDetails getResourceByClientId(String clientId) throws EmptyResultDataAccessException, SQLException {
        return null;
    }
}
