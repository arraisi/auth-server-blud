package com.tabeldata.components.dao;

import com.tabeldata.auth.dto.Authority;
import com.tabeldata.components.dto.MenuDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class MenuDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<MenuDto> getMenuByRolesAndModule(String moduleId, List<Authority> roles) {
        log.info("roles -> {}", roles);
        //language=PostgreSQL
        String baseQuery = "select id           as id,\n" +
                "       title        as title,\n" +
                "       path         as path,\n" +
                "       icon_id      as icon,\n" +
                "       is_menu      as is_menu,\n" +
                "       created_by   as created_by,\n" +
                "       created_date as created_date,\n" +
                "       resource_id  as resource_id,\n" +
                "       parent_id    as parent_id\n" +
                "from component.menu";
        StringBuilder menuQuery = new StringBuilder(baseQuery)
                .append(" where resource_id = :moduleId and parent_id is null");
        MapSqlParameterSource menuParameter = new MapSqlParameterSource();
        menuParameter.addValue("moduleId", moduleId);
        return this.jdbcTemplate.query(
                menuQuery.toString(),
                menuParameter,
                new MenuDtoRowMapper(baseQuery, null)
        );

    }

    private class MenuDtoRowMapper implements RowMapper<MenuDto> {

        private final StringBuilder queryMenuByParentid;
        private final String baseQuery;
        private final MenuDto superClass;

        MenuDtoRowMapper(String baseQuery, MenuDto superClass) {
            this.baseQuery = baseQuery;
            this.queryMenuByParentid = new StringBuilder(baseQuery);
            this.queryMenuByParentid.append(" where parent_id = :parentId");
            this.superClass = superClass;
        }

        @Override
        public MenuDto mapRow(ResultSet resultSet, int i) throws SQLException {
            MenuDto menu = new MenuDto();
            menu.setId(resultSet.getString("id"));
            if (this.superClass != null)
                menu.setUrl(
                        new StringBuilder(
                                this.superClass.getUrl())
                                .append(resultSet.getString("path")).toString()
                );
            else
                menu.setUrl(resultSet.getString("path"));
            menu.setIcon(resultSet.getString("icon"));
            menu.setTitle(resultSet.getString("title"));

            MapSqlParameterSource parametersMenuByParentId = new MapSqlParameterSource();
            parametersMenuByParentId.addValue("parentId", menu.getId());
            menu.setChildren(
                    jdbcTemplate.query(
                            this.queryMenuByParentid.toString(),
                            parametersMenuByParentId,
                            new MenuDtoRowMapper(this.baseQuery, menu)
                    )
            );
            return menu;
        }
    }
}
