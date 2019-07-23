package com.tabeldata.oauth.repository;

import com.maryanto.dimas.plugins.web.commons.ui.datatables.DataTablesRequest;
import com.tabeldata.oauth.models.OauthAccessTokenExtended;
import com.tabeldata.oauth.models.OauthAccessTokenHistory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@Profile("sqlserver2017")
public class JdbcTokenStoreCustomSqlServer2017 implements JdbcTokenStoreCustom {
    @Override
    public void removeAccessToken(OAuth2AccessToken token, String username) {

    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken oAuth2AccessToken) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthentication(String s) {
        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

    }

    @Override
    public OAuth2AccessToken readAccessToken(String s) {
        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {

    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken, OAuth2Authentication oAuth2Authentication) {

    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String s) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {

    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {

    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return null;
    }

    @Override
    public List<OauthAccessTokenHistory> historyByUserAndClientIdDatatables(String username, String clientId, DataTablesRequest<OauthAccessTokenHistory> params) {
        return null;
    }

    @Override
    public Long historyByUsernameAndClientIdDatatables(String username, String clientId, OauthAccessTokenHistory param) {
        return null;
    }

    @Override
    public List<OauthAccessTokenHistory> historyByUserDatatables(String username, DataTablesRequest<OauthAccessTokenHistory> params) {
        return null;
    }

    @Override
    public Long historyByUsernameDatatables(String username, OauthAccessTokenHistory param) {
        return null;
    }

    @Override
    public List<OauthAccessTokenHistory> historyByClientIdDatatables(String clientId, DataTablesRequest<OauthAccessTokenHistory> params) {
        return null;
    }

    @Override
    public Long historyByClientIdDatatables(String clientId, OauthAccessTokenHistory param) {
        return null;
    }

    @Override
    public List<OauthAccessTokenExtended> datatables(DataTablesRequest<OauthAccessTokenExtended> dataTablesRequest) {
        return null;
    }

    @Override
    public Long datatables(OauthAccessTokenExtended oauthAccessTokenExtended) {
        return null;
    }
}
