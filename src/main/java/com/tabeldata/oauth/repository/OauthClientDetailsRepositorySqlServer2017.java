package com.tabeldata.oauth.repository;

import com.tabeldata.oauth.models.OauthApplication;
import com.tabeldata.oauth.models.OauthClientDetails;
import com.tabeldata.oauth.models.OauthGrantType;
import com.tabeldata.oauth.models.OauthScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Profile("sqlserver2017")
public class OauthClientDetailsRepositorySqlServer2017 implements OauthClientDetailsRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * get application by client detail id
     *
     * @param clientId
     * @return
     */
    public List<OauthApplication> getApplicationByClientId(String clientId) throws SQLException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("clientName", clientId);
        //language=TSQL
        StringBuilder query = new StringBuilder("select client_app.id             as application_id,\n" +
                "       client_app.Name           as application_name,\n" +
                "       client_app.CreatedBy      as created_by,\n" +
                "       client_app.CreatedDate    as created_date,\n" +
                "       client_app.LastUpdateBy   as last_update_by,\n" +
                "       client_app.LastUpdateDate as last_update_date\n" +
                "from Resources.ClientDetailApplications apps\n" +
                "         join Resources.ClientDetails client_app on apps.ClientDetailId = client_app.Id\n" +
                "where apps.AppId = (\n" +
                "    select top 1 apps2.AppId\n" +
                "    from Resources.ClientDetailApplications apps2\n" +
                "             join Resources.ClientDetails client_detail on apps2.ClientDetailId = client_detail.Id\n" +
                "    where client_detail.Name = :clientName)");
        return jdbcTemplate.query(
                query.toString(),
                params, (resultSet, i) -> new OauthApplication(
                        resultSet.getString("application_id"),
                        resultSet.getString("application_name"),
                        resultSet.getString("created_by"),
                        resultSet.getTimestamp("created_date"),
                        resultSet.getString("last_update_by"),
                        resultSet.getTimestamp("last_update_date")
                )
        );
    }

    public List<OauthGrantType> getGrantTypeByClientId(String clientId) throws SQLException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("clientId", clientId);
        //language=TSQL
        StringBuilder query = new StringBuilder("select grant_type.id          as grant_id,\n" +
                "       grant_type.Name        as grant_name,\n" +
                "       grant_type.Description as grant_description\n" +
                "from OAuth.GrantTypes grant_type\n" +
                "         join Resources.ClientDetailGrantTypes res_grant_type on grant_type.Id = res_grant_type.GrantType\n" +
                "         join Resources.ClientDetails res on res_grant_type.ClientId = res.Id\n" +
                "where res.Name = :clientId");

        return jdbcTemplate.query(query.toString(), params, (resultSet, i) -> new OauthGrantType(
                resultSet.getInt("grant_id"),
                resultSet.getString("grant_name"),
                resultSet.getString("grant_description")
        ));
    }

    public List<String> getRedirectUrlsByClientId(String clientId) throws SQLException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("clientId", clientId);
        //language=TSQL
        StringBuilder query = new StringBuilder("select url.Id          as url_id,\n" +
                "       url.RedirectUri as redirect_uri\n" +
                "from Resources.ClientDetailRedirectUris url\n" +
                "         join Resources.ClientDetails app on url.ClientId = app.Id\n" +
                "where app.Name = :clientId");
        return jdbcTemplate.query(query.toString(), params, (resultSet, i) -> resultSet.getString("redirect_uri"));
    }

    public List<OauthScope> getScopesByClientId(String clientId) throws SQLException {
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("clientId", clientId);
//        //language=TSQL
//        StringBuilder query = new StringBuilder("");
//        return this.jdbcTemplate.query(query.toString(), params, (resultSet, i) -> new OauthScope(
//                resultSet.getInt("scope_id"),
//                resultSet.getString("scope_name"),
//                resultSet.getString("created_by"),
//                resultSet.getTimestamp("created_date"),
//                resultSet.getString("last_update_by"),
//                resultSet.getTimestamp("last_update_date")
//        ));
        return Arrays.asList(
                new OauthScope(1, "SCOPE_READ", "HARD_CODE", null, "", null)
        );
    }

    public OauthClientDetails getResourceByClientId(String clientId) throws EmptyResultDataAccessException, SQLException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("clientId", clientId);
        //language=TSQL
        StringBuilder query = new StringBuilder("select Id                   as id,\n" +
                "       Name                 as name,\n" +
                "       Password             as password,\n" +
                "       IsAutoApprove        as is_auto_approve,\n" +
                "       TokenExpiredInSecond as token_expired_in_second,\n" +
                "       CreatedBy            as created_by,\n" +
                "       CreatedDate          as created_date,\n" +
                "       LastUpdateBy         as last_update_by,\n" +
                "       LastUpdateDate       as last_update_date\n" +
                "from Resources.ClientDetails\n" +
                "where Name = :clientId");
        return this.jdbcTemplate.queryForObject(query.toString(), params, (resultSet, i) -> new OauthClientDetails(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("password"),
                resultSet.getBoolean("is_auto_approve"),
                resultSet.getInt("token_expired_in_second"),
                resultSet.getString("created_by"),
                resultSet.getTimestamp("created_date"),
                resultSet.getString("last_update_by"),
                resultSet.getTimestamp("last_update_date"),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }
}
