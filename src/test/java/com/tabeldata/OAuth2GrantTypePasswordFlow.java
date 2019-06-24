package com.tabeldata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = OauthSSOApplication.class)
public class OAuth2GrantTypePasswordFlow {

    private final static Logger console = LoggerFactory.getLogger(OAuth2GrantTypePasswordFlow.class);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "client-web");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("client-web", "123456"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void givenNoToken() throws Exception {
        mockMvc.perform(get("/api/roles/list"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenTokenWithoutRoles() throws Exception {
        String accessToken = obtainAccessToken("user", "password");
        console.info("access token for user: {}", accessToken);
        mockMvc.perform(
                get("/api/roles/list")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .header("Authorization", "Bearer " + accessToken)
        ).andExpect(status().isForbidden());
    }

    @Test
    public void givenTokenWithRightAuthorization() throws Exception {
        String accessToken = obtainAccessToken("admin", "password");
        console.info("access token for admin: {}", accessToken);
        mockMvc.perform(
                get("/api/roles/list")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .header("Authorization", "Bearer " + accessToken)
        ).andExpect(status().isOk());
    }

}
