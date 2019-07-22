package com.tabeldata.oauth.repository;

import com.tabeldata.oauth.models.OauthApplication;
import com.tabeldata.oauth.models.OauthClientDetails;
import com.tabeldata.oauth.models.OauthGrantType;
import com.tabeldata.oauth.models.OauthScope;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface OauthClientDetailsRepository extends Serializable {

    List<OauthApplication> getApplicationByClientId(String clientId) throws SQLException;

    List<OauthGrantType> getGrantTypeByClientId(String clientId) throws SQLException;

    List<String> getRedirectUrlsByClientId(String clientId) throws SQLException;

    List<OauthScope> getScopesByClientId(String clientId) throws SQLException;

    OauthClientDetails getResourceByClientId(String clientId) throws EmptyResultDataAccessException, SQLException;
}
