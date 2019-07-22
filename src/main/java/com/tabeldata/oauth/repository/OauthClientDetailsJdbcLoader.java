package com.tabeldata.oauth.repository;

import com.tabeldata.oauth.models.OauthApplication;
import com.tabeldata.oauth.models.OauthClientDetails;
import com.tabeldata.oauth.models.OauthGrantType;
import com.tabeldata.oauth.models.OauthScope;
import com.tabeldata.oauth.service.OauthClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OauthClientDetailsJdbcLoader implements ClientDetailsService {

    @Autowired
    private OauthClientDetailsService service;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetails client;
        try {
            log.info("clientId: {}", clientId);
            client = this.service.findByClientId(clientId);
            log.info("client detail 1: {}", client);
            client.setApplications(service.findApplicationByClientId(clientId));
            log.info("client detail 2: {}", client);
            client.setOauthGrantTypes(service.findGrantTypeByClientId(clientId));
            log.info("client detail 3: {}", client);
            client.setRedirectUrls(service.findRedirectUrlsByClientId(clientId));
            log.info("client detail 3: {}", client);
            client.setOauthScopes(service.findScopeByClientId(clientId));
            log.info("client detail: {}", client);
        }catch (SQLException sqle){
            log.error("something wrong!", sqle);
            throw new UsernameNotFoundException("client_id not found!", sqle);
        } catch (EmptyResultDataAccessException erde) {
            log.error("username not found", erde);
            throw new UsernameNotFoundException("client_id not found!", erde);
        }

        return new OauthClientDetailsModel(client);
    }

    private class OauthClientDetailsModel implements ClientDetails {
        private final OauthClientDetails client;

        public OauthClientDetailsModel(OauthClientDetails client) {
            this.client = client;
            log.info("client: {}", this.client);
        }

        @Override
        public String getClientId() {
            return this.client.getName();
        }

        @Override
        public Set<String> getResourceIds() {
            Set<String> collect = this.client.getApplications().stream()
                    .map(OauthApplication::getName)
                    .collect(Collectors.toSet());
            collect.add("oauth2-resource");
            return collect;
        }

        @Override
        public boolean isSecretRequired() {
            return true;
        }

        @Override
        public String getClientSecret() {
            return this.client.getPassword();
        }

        @Override
        public boolean isScoped() {
            return !this.client.getOauthScopes().isEmpty();
        }

        @Override
        public Set<String> getScope() {
            Set<String> collect = this.client.getOauthScopes().stream()
                    .map(OauthScope::getName)
                    .collect(Collectors.toSet());
            collect.add("read");
            return collect;
        }

        @Override
        public Set<String> getAuthorizedGrantTypes() {
            return this.client.getOauthGrantTypes().stream()
                    .map(OauthGrantType::getName)
                    .collect(Collectors.toSet());
        }

        @Override
        public Set<String> getRegisteredRedirectUri() {
            return this.client.getRedirectUrls().stream()
                    .map(String::toString)
                    .collect(Collectors.toSet());
        }

        @Override
        public Collection<GrantedAuthority> getAuthorities() {
            return new ArrayList<>();
        }

        @Override
        public Integer getAccessTokenValiditySeconds() {
            return this.client.getExpiredInSecond();
        }

        @Override
        public Integer getRefreshTokenValiditySeconds() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isAutoApprove(String s) {
            return this.client.isAutoApprove();
        }

        @Override
        public Map<String, Object> getAdditionalInformation() {
            return new HashMap<>();
        }
    }
}
