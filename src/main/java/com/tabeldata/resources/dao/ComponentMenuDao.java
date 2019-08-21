package com.tabeldata.resources.dao;

import com.tabeldata.resources.entity.ComponentMenu;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections15.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ComponentMenuDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<ComponentMenu> listAll(String otoritasId) {
        String sql = "select" +
                " menu.ID      AS id,\n" +
                "       menu.TITLE   AS title,\n" +
                "       menu.PATH    AS path,\n" +
                "       menu.ICON_ID AS icon\n" +
                "from COMPONENT_MENU_MAP_BY_OTORITAS mapby\n" +
                "         join COMPONENT_MENUS menu on mapby.MENU_ID = menu.ID\n" +
                "where OTORITAS_ID = :otoritasId";
        Map<String, Object> param = new HashedMap<>();
        param.put("otoritasId", otoritasId);
        return this.namedParameterJdbcTemplate.query(sql, param, (resultSet, i) -> {
            ComponentMenu menu = new ComponentMenu();
            menu.setId(resultSet.getString("id"));
            menu.setTitle(resultSet.getString("title"));
            menu.setPath(resultSet.getString("path"));
            menu.setIconId(resultSet.getString("icon"));
            return menu;
        });
    }
}
