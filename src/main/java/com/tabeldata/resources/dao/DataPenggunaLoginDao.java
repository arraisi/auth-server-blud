package com.tabeldata.resources.dao;

import com.tabeldata.resources.dto.DataPenggunaLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DataPenggunaLoginDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DataPenggunaLogin getDataPenggunaLogin(String penggunaNrk) throws EmptyResultDataAccessException {
        String sql = "SELECT\n" +
                "    I_ID,\n" +
                "    C_PGUN_GROUP,\n" +
                "    C_PGUN_OTOR,\n" +
                "    I_PEG_NRK,\n" +
                "    I_SANDI,\n" +
                "    N_EMAIL,\n" +
                "    N_HPNO,\n" +
                "    C_HPNO_AKTIF,\n" +
                "    I_PEG_NIP,\n" +
                "    N_PEG,\n" +
                "    N_PEG_JABATAN,\n" +
                "    I_IDSKPD\n" +
                "FROM\n" +
                "    TRRBAPENGGUNA\n" +
                "WHERE\n" +
                "        C_AKTIF = '1' AND C_LOCK = '0'\n" +
                "  AND I_PEG_NRK = :vpegnrk";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("vpegnrk", penggunaNrk);
        return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new RowMapper<DataPenggunaLogin>() {

            @Override
            public DataPenggunaLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new DataPenggunaLogin(
                        rs.getInt("I_ID"),
                        rs.getString("C_PGUN_GROUP"),
                        rs.getString("C_PGUN_OTOR"),
                        rs.getString("I_PEG_NRK"),
                        rs.getString("N_EMAIL"),
                        rs.getString("N_HPNO"),
                        rs.getString("C_HPNO_AKTIF"),
                        rs.getString("I_PEG_NIP"),
                        rs.getString("N_PEG"),
                        rs.getString("N_PEG_JABATAN"),
                        rs.getString("I_IDSKPD")
                );
            }
        });
    }
}
