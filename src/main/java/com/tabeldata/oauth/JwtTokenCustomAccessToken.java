package com.tabeldata.oauth;

import com.tabeldata.resources.dto.DataPenggunaLogin;
import com.tabeldata.resources.service.DataPenggunaLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class JwtTokenCustomAccessToken implements TokenEnhancer {

    @Autowired
    private DataPenggunaLoginService dataPenggunaLoginService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        try {
            String username = oAuth2Authentication.getName();
            DataPenggunaLogin dataPenggunaLogin = dataPenggunaLoginService.getDataPenggunaLogin(username);
            log.info("Data Pengguna : {}", dataPenggunaLogin);
            Map<String, Object> addValue = new HashMap<>();
            addValue.put("nrk", dataPenggunaLogin.getNrk());
            addValue.put("nip", dataPenggunaLogin.getNip());
            addValue.put("nama", dataPenggunaLogin.getNama());
            addValue.put("jabatan", dataPenggunaLogin.getJabatan());
            addValue.put("noHp", dataPenggunaLogin.getNoHp());
            addValue.put("noHpAktif", dataPenggunaLogin.getNoHpAktif());
            addValue.put("skpdId", dataPenggunaLogin.getIdSkpd());
            addValue.put("group", dataPenggunaLogin.getGroup());
            addValue.put("otor", dataPenggunaLogin.getOtor());
            addValue.put("email", dataPenggunaLogin.getEmail());
            additionalInfo.put("pengguna", addValue);
        } catch (EmptyResultDataAccessException erdae) {
            log.error("error catch", erdae);
        } catch (DataAccessException dae) {
            log.error("error sql exception", dae);
        }
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
