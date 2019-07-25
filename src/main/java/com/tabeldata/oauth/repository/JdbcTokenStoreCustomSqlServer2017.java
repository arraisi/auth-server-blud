package com.tabeldata.oauth.repository;

import com.maryanto.dimas.plugins.web.commons.ui.datatables.DataTablesRequest;
import com.maryanto.dimas.plugins.web.commons.ui.datatables.OrderingByColumns;
import com.tabeldata.oauth.models.OauthAccessTokenExtended;
import com.tabeldata.oauth.models.OauthAccessTokenHistory;
import com.tabeldata.utils.QueryComparator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class JdbcTokenStoreCustomSqlServer2017 extends JdbcTokenStore implements JdbcTokenStoreCustom {
    //language=TSQL
    private String insertAccessTokenSql = "insert into OAuth.AccessToken (TokenId, Token, AuthId, Username, ClientId, Authentication, RefreshToken, IpAddress)\n" +
            "values (?, ?, ?, ?, ?, ?, ?, ?)";
    //language=TSQL
    private String selectAccessTokenSql = "select TokenId, Token from OAuth.AccessToken where TokenId = ?";
    //language=TSQL
    private String selectAccessTokenAuthenticationSql = "select TokenId, Authentication from OAuth.AccessToken where TokenId = ?";
    //language=TSQL
    private String selectAccessTokenFromAuthenticationSql = "select TokenId, Token from OAuth.AccessToken where AuthId = ?";
    //language=TSQL
    private String selectAccessTokensFromUserNameAndClientIdSql = "select TokenId, Token from OAuth.AccessToken where Username = ? and ClientId = ?";
    //language=TSQL
    private String selectAccessTokensFromUserNameSql = "select TokenId, Token from OAuth.AccessToken where Username = ?";
    //language=TSQL
    private String selectAccessTokensFromClientIdSql = "select TokenId, Token from OAuth.AccessToken where ClientId = ?";
    //language=TSQL
    private String deleteAccessTokenSql = "delete from OAuth.AccessToken where TokenId = ?";
    //language=TSQL
    private String insertRefreshTokenSql = "insert into OAuth.RefreshToken (TokenId, Token, Authentication) values (?, ?, ?)";
    //language=TSQL
    private String selectRefreshTokenSql = "select TokenId, Token from OAuth.RefreshToken where TokenId = ?";
    //language=TSQL
    private String selectRefreshTokenAuthenticationSql = "select TokenId, Authentication from OAuth.RefreshToken where TokenId = ?";
    ///language=TSQL
    private String deleteRefreshTokenSql = "delete from OAuth.RefreshToken where TokenId = ?";
    //language=TSQL
    private String deleteAccessTokenFromRefreshTokenSql = "delete from OAuth.AccessToken where RefreshToken = ?";
    //language=TSQL
    private String insertHistoryAccessTokenSql = "insert into OAuth.HistoryAccessToken (Id, AccessId, ClientId, Token, IpAddress, Username, LoginAt, IsLogout, LogoutAt, LogoutBy)\n" +
            "VALUES (newid(), ?, ?, ?, ?, ?, current_timestamp, 0, null, null)";
    //language=TSQL
    private String updateHistoryAccessTokenSql = "update OAuth.HistoryAccessToken set IsLogout = 1, LogoutAt = current_timestamp, LogoutBy = ?\n" +
            "where AccessId = ? and IsLogout = 0";

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private String getRemoteAddress() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
        String ipAddress = details.getRemoteAddress();
        return ipAddress;
    }


    public JdbcTokenStoreCustomSqlServer2017(DataSource dataSource) {
        super(dataSource);
        Assert.notNull(dataSource, "DataSource required");
        setAuthenticationKeyGenerator(authenticationKeyGenerator);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;
        String key = this.authenticationKeyGenerator.extractKey(authentication);

        try {
            accessToken = this.jdbcTemplate.queryForObject(
                    this.selectAccessTokenFromAuthenticationSql,
                    (rs, rowNum) -> JdbcTokenStoreCustomSqlServer2017.this.deserializeAccessToken(rs.getBytes(2)),
                    key);
        } catch (EmptyResultDataAccessException var5) {
            if (log.isDebugEnabled()) {
                log.debug("Failed to find access token for authentication: {}", authentication);
            }
        } catch (IllegalArgumentException var6) {
            log.error("Could not extract access token for authentication : {}", authentication, var6);
        }

        if (accessToken != null && !key.equals(this.authenticationKeyGenerator.extractKey(this.readAuthentication(accessToken.getValue())))) {
            this.removeAccessToken(accessToken.getValue());
            this.storeAccessToken(accessToken, authentication);
        }

        return accessToken;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }

        if (this.readAccessToken(token.getValue()) != null) {
            this.removeAccessToken(token.getValue());
        }

        String authId = this.authenticationKeyGenerator.extractKey(authentication);
        String tokenId = this.extractTokenKey(token.getValue());
        String username = authentication.isClientOnly() ? null : authentication.getName();
        String clientId = authentication.getOAuth2Request().getClientId();

//        SqlLobValue tokenLobValue = new SqlLobValue(this.serializeAccessToken(token)); //fixme sql server not support
//        SqlLobValue authLobValue = new SqlLobValue(this.serializeAuthentication(authentication)), //fixme sql server not supported
        byte[] tokenLobValue = this.serializeAccessToken(token);
        byte[] authLobValue = this.serializeAuthentication(authentication);
        Object[] valueParameters = new Object[]{
                tokenId,
                tokenLobValue,
                authId,
                username,
                clientId,
                authLobValue,
                this.extractTokenKey(refreshToken),
                getRemoteAddress()
        };

        try {
            this.jdbcTemplate.update(
                    this.insertAccessTokenSql,
                    valueParameters,
                    new int[]{
                            Types.VARCHAR,
                            Types.VARBINARY,
                            Types.VARCHAR,
                            Types.VARCHAR,
                            Types.VARCHAR,
                            Types.VARBINARY,
                            Types.VARCHAR,
                            Types.VARCHAR}
            );

            this.jdbcTemplate.update(
                    this.insertHistoryAccessTokenSql,
                    new Object[]{
                            tokenId,
                            clientId,
                            tokenLobValue,
                            getRemoteAddress(),
                            username},
                    new int[]{
                            Types.VARCHAR,
                            Types.VARCHAR,
                            Types.VARBINARY,
                            Types.VARCHAR,
                            Types.VARCHAR
                    }
            );
        } catch (DataAccessException dae) {
            log.error("can't save access token {}", tokenId, dae);
        } catch (Exception ex) {
            log.error("can't save access token {}", tokenId, ex);
        }


    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;

        try {
            accessToken = this.jdbcTemplate.queryForObject(
                    this.selectAccessTokenSql,
                    (rs, rowNum) -> JdbcTokenStoreCustomSqlServer2017.this.deserializeAccessToken(rs.getBytes(2)),
                    this.extractTokenKey(tokenValue));
        } catch (EmptyResultDataAccessException var4) {
            if (log.isInfoEnabled()) {
                log.info("Failed to find access token for token : {}", tokenValue);
            }
        } catch (IllegalArgumentException var5) {
            log.warn("Failed to deserialize access token for: {} ", tokenValue, var5);
            this.removeAccessToken(tokenValue);
        }

        return accessToken;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        this.removeAccessToken(token.getValue());
    }

    public void removeAccessToken(OAuth2AccessToken token, String username) {
        this.removeAccessToken(token.getValue(), username);
    }

    @Override
    public void removeAccessToken(String tokenValue) {
        String tokenId = this.extractTokenKey(tokenValue);
        try {

            this.jdbcTemplate.update(
                    this.deleteAccessTokenSql,
                    tokenId
            );

            this.jdbcTemplate.update(
                    this.updateHistoryAccessTokenSql,
                    "timeout", tokenId);
        } catch (DataAccessException dae) {
            log.error("can't remove access_token with id = {}", tokenId);
        }
    }

    public void removeAccessToken(String tokenValue, String username) {
        String tokenId = this.extractTokenKey(tokenValue);

        this.jdbcTemplate.update(
                this.deleteAccessTokenSql,
                tokenId
        );

        this.jdbcTemplate.update(
                this.updateHistoryAccessTokenSql,
                username, tokenId);
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return this.readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication authentication = null;

        try {
            authentication = this.jdbcTemplate.queryForObject(
                    this.selectAccessTokenAuthenticationSql,
                    (rs, rowNum) -> JdbcTokenStoreCustomSqlServer2017.this.deserializeAuthentication(rs.getBytes(2)),
                    this.extractTokenKey(token));
        } catch (EmptyResultDataAccessException var4) {
            if (log.isInfoEnabled()) {
                log.info("Failed to find access token for token: {} ", token);
            }
        } catch (IllegalArgumentException var5) {
            log.warn("Failed to deserialize authentication for: {} ", token, var5);
            this.removeAccessToken(token);
        }

        return authentication;
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
//        SqlLobValue tokenLobValue = new SqlLobValue(this.serializeRefreshToken(refreshToken)); //TODO sql server not support
//        SqlLobValue authLobValue = new SqlLobValue(this.serializeAuthentication(authentication)), //TODO sql server not supported
        byte[] tokenLobValue = this.serializeRefreshToken(refreshToken);
        byte[] authLobValue = this.serializeAuthentication(authentication);
        String tokenKey = this.extractTokenKey(refreshToken.getValue());
        try {
            this.jdbcTemplate.update(
                    this.insertRefreshTokenSql,
                    new Object[]{tokenKey, tokenLobValue, authLobValue},
                    new int[]{Types.VARCHAR, Types.VARBINARY, Types.VARBINARY}
            );
        } catch (DataAccessException dae) {
            log.error("can't save refresh token: {}", tokenKey, dae);
        } catch (Exception ex) {
            log.error("can't save refresh token: {}", tokenKey, ex);
        }
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String token) {
        OAuth2RefreshToken refreshToken = null;

        try {
            refreshToken = this.jdbcTemplate.queryForObject(
                    this.selectRefreshTokenSql,
                    (rs, rowNum) -> JdbcTokenStoreCustomSqlServer2017.this.deserializeRefreshToken(rs.getBytes(2)),
                    this.extractTokenKey(token));
        } catch (EmptyResultDataAccessException var4) {
            if (log.isInfoEnabled()) {
                log.info("Failed to find refresh token for token: {} ", token);
            }
        } catch (IllegalArgumentException var5) {
            log.warn("Failed to deserialize refresh token for token: {} ", token, var5);
            this.removeRefreshToken(token);
        }

        return refreshToken;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        this.removeRefreshToken(token.getValue());
    }

    @Override
    public void removeRefreshToken(String token) {
        this.jdbcTemplate.update(this.deleteRefreshTokenSql, this.extractTokenKey(token));
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return this.readAuthenticationForRefreshToken(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
        OAuth2Authentication authentication = null;

        try {
            authentication = this.jdbcTemplate.queryForObject(
                    this.selectRefreshTokenAuthenticationSql,
                    (rs, rowNum) -> JdbcTokenStoreCustomSqlServer2017.this.deserializeAuthentication(rs.getBytes(2)),
                    this.extractTokenKey(value));
        } catch (EmptyResultDataAccessException var4) {
            if (log.isInfoEnabled()) {
                log.info("Failed to find access token for token: {} ", value);
            }
        } catch (IllegalArgumentException var5) {
            log.warn("Failed to deserialize access token for : {}", value, var5);
            this.removeRefreshToken(value);
        }

        return authentication;
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        this.removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
        this.jdbcTemplate.update(this.deleteAccessTokenFromRefreshTokenSql,
                new Object[]{this.extractTokenKey(refreshToken)},
                new int[]{Types.VARCHAR}
        );
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List accessTokens = new ArrayList();

        try {
            accessTokens = this.jdbcTemplate.query(
                    this.selectAccessTokensFromClientIdSql,
                    new JdbcTokenStoreCustomSqlServer2017.SafeAccessTokenRowMapper(),
                    clientId);
        } catch (EmptyResultDataAccessException var4) {
            if (log.isInfoEnabled()) {
                log.info("Failed to find access token for clientId: {} ", clientId);
            }
        }

        accessTokens = this.removeNulls(accessTokens);
        return accessTokens;
    }

    public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<>();

        try {
            accessTokens = this.jdbcTemplate.query(
                    this.selectAccessTokensFromUserNameSql,
                    new JdbcTokenStoreCustomSqlServer2017.SafeAccessTokenRowMapper(),
                    userName);
        } catch (EmptyResultDataAccessException var4) {
            if (log.isInfoEnabled()) {
                log.info("Failed to find access token for userName: {} ", userName);
            }
        }

        accessTokens = this.removeNulls(accessTokens);
        return accessTokens;
    }

    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<>();

        try {
            accessTokens = this.jdbcTemplate.query(
                    this.selectAccessTokensFromUserNameAndClientIdSql,
                    new JdbcTokenStoreCustomSqlServer2017.SafeAccessTokenRowMapper(),
                    userName, clientId);
        } catch (EmptyResultDataAccessException var5) {
            if (log.isInfoEnabled()) {
                log.info("Failed to find access token for clientId {} and userName {}", clientId, userName);
            }
        }

        accessTokens = this.removeNulls(accessTokens);
        return accessTokens;
    }

    private List<OAuth2AccessToken> removeNulls(List<OAuth2AccessToken> accessTokens) {
        List<OAuth2AccessToken> tokens = new ArrayList<>();
        Iterator var3 = accessTokens.iterator();

        while (var3.hasNext()) {
            OAuth2AccessToken token = (OAuth2AccessToken) var3.next();
            if (token != null) {
                tokens.add(token);
            }
        }

        return tokens;
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        } else {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var5) {
                throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
            }

            try {
                byte[] bytes = digest.digest(value.getBytes("UTF-8"));
                return String.format("%032x", new BigInteger(1, bytes));
            } catch (UnsupportedEncodingException var4) {
                throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
            }
        }
    }

    protected byte[] serializeAccessToken(OAuth2AccessToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }

    protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
        return (OAuth2AccessToken) SerializationUtils.deserialize(token);
    }

    protected OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
        return (OAuth2RefreshToken) SerializationUtils.deserialize(token);
    }

    protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
        return (OAuth2Authentication) SerializationUtils.deserialize(authentication);
    }

    /**
     * private String username;
     * private String clientId;
     * private String ipAddress;
     * private String accessToken;
     * private Timestamp loginAt;
     * private Timestamp expiredAt;
     *
     * @param params
     * @return
     */
    @Override
    @Deprecated
    public List<OauthAccessTokenExtended> datatables(DataTablesRequest<OauthAccessTokenExtended> params) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        //language=TSQL
        String baseQuery = "select auth_id    as authentication_id,\n" +
                "       token_id   as token_id,\n" +
                "       token      as access_token,\n" +
                "       user_name   as username,\n" +
                "       client_id  as client_id,\n" +
                "       ip_address as ip_address,\n" +
                "       login_at   as login_time\n" +
                "from oauth.access_token where 1=1 ";
        JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenExtendedQueryComparator queryComparator = new JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenExtendedQueryComparator(baseQuery, map);
        StringBuilder query = queryComparator.getQuery(params.getValue());
        map = queryComparator.getParameters();

        OrderingByColumns serviceColumn = new OrderingByColumns("user_name", "client_id", "ip_address", "login_time");
        query.append(serviceColumn.orderBy(params.getColDir(), params.getColOrder()));


        query.append(" limit :limit ").append(" offset :offset ");
        map.addValue("limit", params.getLength());
        map.addValue("offset", params.getStart());

        List<OauthAccessTokenExtended> list = this.namedJdbcTemplate.query(query.toString(), map, (resultSet, i) -> {
            try {
                OAuth2AccessToken oauth2AccessToken = JdbcTokenStoreCustomSqlServer2017.
                        this.deserializeAccessToken(resultSet.getBytes("access_token"));
                return new OauthAccessTokenExtended(
                        resultSet.getString("username"),
                        resultSet.getString("client_id"),
                        resultSet.getString("ip_address"),
                        oauth2AccessToken.getValue(),
                        resultSet.getTimestamp("login_time"),
                        new Timestamp(oauth2AccessToken.getExpiration().getTime())
                );
            } catch (IllegalArgumentException var5) {
                String token = resultSet.getString("token_id");
                JdbcTokenStoreCustomSqlServer2017.this.jdbcTemplate.update(JdbcTokenStoreCustomSqlServer2017.this.deleteAccessTokenSql, token);
                return null;
            }
        });
        return list;
    }

    @Override
    @Deprecated
    public Long datatables(OauthAccessTokenExtended value) {
        //language=TSQL
        String baseQuery = "select count(*) as rows from oauth.access_token where 1=1 ";
        MapSqlParameterSource map = new MapSqlParameterSource();

        JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenExtendedQueryComparator queryComparator = new JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenExtendedQueryComparator(baseQuery, map);
        StringBuilder query = queryComparator.getQuery(value);
        map = queryComparator.getParameters();

        return this.namedJdbcTemplate.queryForObject(
                query.toString(), map,
                (resultSet, i) -> resultSet.getLong("rows")
        );
    }

    private class OauthAccessTokenExtendedQueryComparator implements QueryComparator<OauthAccessTokenExtended> {

        private StringBuilder builder;
        private MapSqlParameterSource parameterSource;

        public OauthAccessTokenExtendedQueryComparator(String baseQuery, MapSqlParameterSource parameterSource) {
            this.builder = new StringBuilder(baseQuery);
            this.parameterSource = parameterSource;
        }

        @Override
        public StringBuilder getQuery(OauthAccessTokenExtended value) {
            if (StringUtils.isNotBlank(value.getClientId())) {
                builder.append(" and client_id like :clientId ");
                parameterSource.addValue("clientId", new StringBuilder("%").append(value.getClientId()).append("%").toString());
            }

            if (StringUtils.isNotBlank(value.getUsername())) {
                builder.append(" and user_name like :userName ");
                parameterSource.addValue("userName", new StringBuilder("%").append(value.getUsername()).append("%").toString());
            }

            if (StringUtils.isNotBlank(value.getIpAddress())) {
                builder.append(" and ip_address = :ipAddress ");
                parameterSource.addValue("ipAddress", value.getIpAddress());
            }

            return this.builder;
        }

        @Override
        public MapSqlParameterSource getParameters() {
            return this.parameterSource;
        }
    }

    /**
     * private String username;
     * private String clientId;
     * private String ipAddress;
     * private String accessToken;
     * private Timestamp loginAt;
     * private Timestamp expiredAt;
     * private boolean logout;
     * private Timestamp logoutAt;
     * private String logoutBy;
     *
     * @param username
     * @param clientId
     * @param params
     * @return
     */
    @Deprecated
    public List<OauthAccessTokenHistory> historyByUserAndClientIdDatatables(
            String username,
            String clientId,
            DataTablesRequest<OauthAccessTokenHistory> params) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        //language=TSQL
        String baseQuery = "select access_id,\n" +
                "       token,\n" +
                "       client_id,\n" +
                "       ip_address,\n" +
                "       user_name,\n" +
                "       login_at,\n" +
                "       is_logout,\n" +
                "       logout_at,\n" +
                "       logout_by\n" +
                "from oauth.history_access_token\n" +
                "where 1 = 1\n" +
                "  and client_id = :clientId\n" +
                "  and user_name = :userName ";
        map.addValue("clientId", clientId);
        map.addValue("userName", username);

        JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByUserAndClientId queryComparator = new JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByUserAndClientId(baseQuery, map);
        StringBuilder queryBuilder = queryComparator.getQuery(params.getValue());
        map = queryComparator.getParameters();

        OrderingByColumns serviceOrder = new OrderingByColumns("user_name", "client_id", "ip_address", "login_at", "is_logout", "logout_at", "logout_by", "logout_by", "");
        queryBuilder.append(serviceOrder.orderBy(params.getColDir(), params.getColOrder()));

        queryBuilder.append(" limit :limit ").append(" offset :offset ");
        map.addValue("limit", params.getLength());
        map.addValue("offset", params.getStart());

        List<OauthAccessTokenHistory> list = this.namedJdbcTemplate.query(queryBuilder.toString(), map, (resultSet, i) -> {
            try {
                OAuth2AccessToken oauth2AccessToken = JdbcTokenStoreCustomSqlServer2017.this.deserializeAccessToken(resultSet.getBytes("token"));
                return new OauthAccessTokenHistory(
                        resultSet.getString("user_name"),
                        resultSet.getString("client_id"),
                        resultSet.getString("ip_address"),
                        oauth2AccessToken.getValue(),
                        resultSet.getTimestamp("login_at"),
                        new Timestamp(oauth2AccessToken.getExpiration().getTime()),
                        resultSet.getBoolean("is_logout"),
                        resultSet.getTimestamp("logout_at"),
                        resultSet.getString("logout_by")
                );
            } catch (IllegalArgumentException var5) {
                String token = resultSet.getString("access_id");
                JdbcTokenStoreCustomSqlServer2017.this.jdbcTemplate.update(JdbcTokenStoreCustomSqlServer2017.this.deleteAccessTokenSql, token);
                return null;
            }
        });
        return list;

    }

    @Deprecated
    public Long historyByUsernameAndClientIdDatatables(String username, String clientId, OauthAccessTokenHistory param) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        //language=TSQL
        String baseQuery = "select count(*) as rows\n" +
                "from oauth.history_access_token\n" +
                "where 1 = 1 \n" +
                "  and client_id = :clientId\n " +
                "  and user_name = :userName ";
        map.addValue("clientId", clientId);
        map.addValue("userName", username);

        JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByUserAndClientId queryComparator = new JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByUserAndClientId(baseQuery, map);
        StringBuilder queryBuilder = queryComparator.getQuery(param);
        map = queryComparator.getParameters();

        return this.namedJdbcTemplate.queryForObject(queryBuilder.toString(), map, (resultSet, i) -> resultSet.getLong("rows"));
    }

    @Deprecated
    private class OauthAccessTokenHistoryByUserAndClientId implements QueryComparator<OauthAccessTokenHistory> {

        private StringBuilder builder;
        private MapSqlParameterSource parameterSource;

        public OauthAccessTokenHistoryByUserAndClientId(String baseQuery, MapSqlParameterSource parameterSource) {
            this.builder = new StringBuilder(baseQuery);
            this.parameterSource = parameterSource;
        }

        @Override
        public StringBuilder getQuery(OauthAccessTokenHistory value) {
            if (value.getLogout() != null) {
                builder.append(" and is_logout = :isLogout ");
                parameterSource.addValue("isLogout", value.getLogout());
            }

            if (StringUtils.isNotBlank(value.getIpAddress())) {
                builder.append(" and ip_address like :ipAddress ");
                parameterSource.addValue("ipAddress", new StringBuilder("%").append(value.getIpAddress()).append("%").toString());
            }
            return this.builder;
        }

        @Override
        public MapSqlParameterSource getParameters() {
            return this.parameterSource;
        }
    }

    /**
     * private String username;
     * private String clientId;
     * private String ipAddress;
     * private String accessToken;
     * private Timestamp loginAt;
     * private Timestamp expiredAt;
     * private boolean logout;
     * private Timestamp logoutAt;
     * private String logoutBy;
     *
     * @param username
     * @param params
     * @return
     */
    @Deprecated
    public List<OauthAccessTokenHistory> historyByUserDatatables(
            String username,
            DataTablesRequest<OauthAccessTokenHistory> params) {
        MapSqlParameterSource map = new MapSqlParameterSource();

        //language=TSQL
        String baseQuery = "select access_id,\n" +
                "       token,\n" +
                "       client_id,\n" +
                "       ip_address,\n" +
                "       user_name,\n" +
                "       login_at,\n" +
                "       is_logout,\n" +
                "       logout_at,\n" +
                "       logout_by\n" +
                "from oauth.history_access_token\n" +
                "where 1 = 1\n" +
                "  and user_name = :userName ";
        map.addValue("userName", username);

        JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByUser queryComparator = new JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByUser(baseQuery, map);
        StringBuilder queryBuilder = queryComparator.getQuery(params.getValue());
        map = queryComparator.getParameters();

        OrderingByColumns serviceColumns = new OrderingByColumns("user_name", "client_id", "ip_address", "login_at", "is_logout", "logout_at", "logout_by");
        queryBuilder.append(serviceColumns.orderBy(params.getColDir(), params.getColOrder()));

        queryBuilder.append(" limit :limit ").append(" offset :offset ");
        map.addValue("limit", params.getLength());
        map.addValue("offset", params.getStart());

        List<OauthAccessTokenHistory> list = this.namedJdbcTemplate.query(queryBuilder.toString(), map, (resultSet, i) -> {
            try {
                OAuth2AccessToken oauth2AccessToken = JdbcTokenStoreCustomSqlServer2017.this.deserializeAccessToken(resultSet.getBytes("token"));
                return new OauthAccessTokenHistory(
                        resultSet.getString("user_name"),
                        resultSet.getString("client_id"),
                        resultSet.getString("ip_address"),
                        oauth2AccessToken.getValue(),
                        resultSet.getTimestamp("login_at"),
                        new Timestamp(oauth2AccessToken.getExpiration().getTime()),
                        resultSet.getBoolean("is_logout"),
                        resultSet.getTimestamp("logout_at"),
                        resultSet.getString("logout_by")
                );
            } catch (IllegalArgumentException var5) {
                String token = resultSet.getString("access_id");
                JdbcTokenStoreCustomSqlServer2017.this.jdbcTemplate.update(JdbcTokenStoreCustomSqlServer2017.this.deleteAccessTokenSql, token);
                return null;
            }
        });
        return list;

    }

    @Deprecated
    public Long historyByUsernameDatatables(String username, OauthAccessTokenHistory param) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        //language=TSQL
        String baseQuery = "select count(*) as rows\n" +
                "from oauth.history_access_token\n" +
                "where 1 = 1 \n" +
                "  and user_name = :userName ";
        map.addValue("userName", username);

        JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByUser queryComparator = new JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByUser(baseQuery, map);
        StringBuilder queryBuilder = queryComparator.getQuery(param);
        map = queryComparator.getParameters();

        return this.namedJdbcTemplate.queryForObject(
                queryBuilder.toString(), map,
                (resultSet, i) -> resultSet.getLong("rows"));
    }

    @Deprecated
    private class OauthAccessTokenHistoryByUser implements QueryComparator<OauthAccessTokenHistory> {

        private StringBuilder builder;
        private MapSqlParameterSource parameterSource;

        public OauthAccessTokenHistoryByUser(String baseQuery, MapSqlParameterSource parameterSource) {
            this.builder = new StringBuilder(baseQuery);
            this.parameterSource = parameterSource;
        }

        @Override
        public StringBuilder getQuery(OauthAccessTokenHistory param) {
            if (param.getLogout() != null) {
                builder.append(" and is_logout = :isLogout ");
                parameterSource.addValue("isLogout", param.getLogout());
            }

            if (StringUtils.isNotBlank(param.getClientId())) {
                builder.append(" and client_id like :clintId ");
                parameterSource.addValue("clintId", new StringBuilder("%").append(param.getClientId()).append("%").toString());
            }

            if (StringUtils.isNotBlank(param.getIpAddress())) {
                builder.append(" and ip_address like :ipAddress ");
                parameterSource.addValue("ipAddress", new StringBuilder("%").append(param.getIpAddress()).append("%").toString());
            }
            return this.builder;
        }

        @Override
        public MapSqlParameterSource getParameters() {
            return this.parameterSource;
        }
    }


    /**
     * private String username;
     * private String clientId;
     * private String ipAddress;
     * private String accessToken;
     * private Timestamp loginAt;
     * private Timestamp expiredAt;
     * private boolean logout;
     * private Timestamp logoutAt;
     * private String logoutBy;
     *
     * @param clientId
     * @param params
     * @return
     */
    @Deprecated
    public List<OauthAccessTokenHistory> historyByClientIdDatatables(
            String clientId,
            DataTablesRequest<OauthAccessTokenHistory> params) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        //language=TSQL
        String baseQuery = "select access_id,\n" +
                "       token,\n" +
                "       client_id,\n" +
                "       ip_address,\n" +
                "       user_name,\n" +
                "       login_at,\n" +
                "       is_logout,\n" +
                "       logout_at,\n" +
                "       logout_by\n" +
                "from oauth.history_access_token\n" +
                "where 1 = 1\n" +
                "  and client_id = :clientId ";
        map.addValue("clientId", clientId);

        JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByClientId queryComparator = new JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByClientId(baseQuery, map);
        StringBuilder queryBuilder = queryComparator.getQuery(params.getValue());
        map = queryComparator.getParameters();

        OrderingByColumns serviceColumns = new OrderingByColumns("user_name", "client_id", "ip_address", "login_at", "is_logout", "is_logout", "logout_at", "logout_by");
        queryBuilder.append(serviceColumns.orderBy(params.getColDir(), params.getColOrder()));

        queryBuilder.append(" limit :limit ").append(" offset :offset ");
        map.addValue("limit", params.getLength());
        map.addValue("offset", params.getStart());

        List<OauthAccessTokenHistory> list = this.namedJdbcTemplate.query(queryBuilder.toString(), map, (resultSet, i) -> {
            try {
                OAuth2AccessToken oauth2AccessToken = JdbcTokenStoreCustomSqlServer2017.this.deserializeAccessToken(resultSet.getBytes("token"));
                return new OauthAccessTokenHistory(
                        resultSet.getString("user_name"),
                        resultSet.getString("client_id"),
                        resultSet.getString("ip_address"),
                        oauth2AccessToken.getValue(),
                        resultSet.getTimestamp("login_at"),
                        new Timestamp(oauth2AccessToken.getExpiration().getTime()),
                        resultSet.getBoolean("is_logout"),
                        resultSet.getTimestamp("logout_at"),
                        resultSet.getString("logout_by")
                );
            } catch (IllegalArgumentException var5) {
                String token = resultSet.getString("access_id");
                JdbcTokenStoreCustomSqlServer2017.this.jdbcTemplate.update(JdbcTokenStoreCustomSqlServer2017.this.deleteAccessTokenSql, token);
                return null;
            }
        });
        return list;

    }

    @Deprecated
    public Long historyByClientIdDatatables(String clientId, OauthAccessTokenHistory param) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        //language=TSQL
        String baseQuery = "select count(*) as rows\n" +
                "from oauth.history_access_token\n" +
                "where 1 = 1 \n" +
                "  and client_id = :clientId ";
        map.addValue("clientId", clientId);
        JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByClientId queryComparator = new JdbcTokenStoreCustomSqlServer2017.OauthAccessTokenHistoryByClientId(baseQuery, map);
        StringBuilder queryBuilder = queryComparator.getQuery(param);
        map = queryComparator.getParameters();

        return this.namedJdbcTemplate.queryForObject(queryBuilder.toString(), map, (resultSet, i) -> resultSet.getLong("rows"));
    }

    @Deprecated
    private class OauthAccessTokenHistoryByClientId implements QueryComparator<OauthAccessTokenHistory> {

        private StringBuilder builder;
        private MapSqlParameterSource parameterSource;

        public OauthAccessTokenHistoryByClientId(String baseQuery, MapSqlParameterSource parameterSource) {
            this.builder = new StringBuilder(baseQuery);
            this.parameterSource = parameterSource;
        }

        @Override
        public StringBuilder getQuery(OauthAccessTokenHistory param) {
            if (param.getLogout() != null) {
                builder.append(" and is_logout = :isLogout ");
                parameterSource.addValue("isLogout", param.getLogout());
            }

            if (StringUtils.isNotBlank(param.getUsername())) {
                builder.append(" and user_name like :userName ");
                parameterSource.addValue("userName", new StringBuilder("%").append(param.getUsername()).append("%").toString());
            }

            if (StringUtils.isNotBlank(param.getIpAddress())) {
                builder.append(" and ip_address like :ipAddress ");
                parameterSource.addValue("ipAddress", new StringBuilder("%").append(param.getIpAddress()).append("%").toString());
            }
            return this.builder;
        }

        @Override
        public MapSqlParameterSource getParameters() {
            return this.parameterSource;
        }
    }


    private final class SafeAccessTokenRowMapper implements RowMapper<OAuth2AccessToken> {
        private SafeAccessTokenRowMapper() {
        }

        public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                return JdbcTokenStoreCustomSqlServer2017.this.deserializeAccessToken(rs.getBytes(2));
            } catch (IllegalArgumentException var5) {
                String token = rs.getString(1);
                JdbcTokenStoreCustomSqlServer2017.this.jdbcTemplate.update(JdbcTokenStoreCustomSqlServer2017.this.deleteAccessTokenSql, token);
                return null;
            }
        }
    }
}
