package com.tabeldata;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Slf4j
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = OauthSSOApplication.class)
@ActiveProfiles("oracle11")
public class PasswordEncodeTesting {

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void testEncodePassword(){
        log.info(encoder.encode("password"));
    }
}
