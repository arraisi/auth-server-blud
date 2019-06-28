package com.tabeldata.auth.dao;

import com.tabeldata.auth.dto.Authority;
import com.tabeldata.auth.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Deprecated
public class AuthenticationDao implements Serializable {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public User login(final String username) throws EmptyResultDataAccessException {
        String query = "select id,\n" +
                "       username,\n" +
                "       email,\n" +
                "       password,\n" +
                "       is_active,\n" +
                "       is_locked,\n" +
                "       is_keep_active,\n" +
                "       is_sudo,\n" +
                "       login_failed_counter,\n" +
                "       created_by,\n" +
                "       created_date,\n" +
                "       last_update_by,\n" +
                "       last_update_date\n" +
                "from auth.users\n" +
                "where username = :username;";
        final Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        final User availableUser = jdbcTemplate.queryForObject(query, params, (resultSet, i) ->
                new User(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("is_active"),
                        resultSet.getBoolean("is_locked"),
                        resultSet.getBoolean("is_sudo"),
                        resultSet.getBoolean("is_keep_active"),
                        resultSet.getInt("login_failed_counter"),
                        resultSet.getString("created_by"),
                        resultSet.getTimestamp("created_date"),
                        resultSet.getString("last_update_by"),
                        resultSet.getTimestamp("last_update_date")
                )
        );
        return availableUser;
    }

    public List<Authority> distinctRolesByUsername(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", username);
        StringBuilder query = new StringBuilder("select distinct role.id as role_id, role.name as role_name, role.description as role_description\n" +
                "from auth.users u\n" +
                "       join auth.user_privileges granted on u.id = granted.user_id\n" +
                "       join auth.privileges privilege on granted.privilege_id = privilege.id\n" +
                "       join auth.authorities authority on authority.privilege_id = privilege.id\n" +
                "       join auth.roles role on authority.role_id = role.id\n" +
                "where u.username = :userId");
        return this.jdbcTemplate.query(
                query.toString(), params,
                (resultSet, i) -> new Authority(
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name"),
                        resultSet.getString("role_description"))
        );
    }

    public List<Authority> roles() {
        StringBuilder query = new StringBuilder("select id as role_id, description as role_description, name as role_name\n" +
                "from auth.roles");
        return this.jdbcTemplate.query(query.toString(), (resultSet, i) -> new Authority(
                resultSet.getInt("role_id"),
                resultSet.getString("role_name"),
                resultSet.getString("role_description")));
    }
}
