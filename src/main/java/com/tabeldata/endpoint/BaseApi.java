package com.tabeldata.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BaseApi {

    @GetMapping("/logs")
    public Map<String, Object> index() {
        Map<String, Object> params = new HashMap<>();
        params.put("date", new Date());
        params.put("user", "Dimas Maryanto");
        return params;
    }
}
