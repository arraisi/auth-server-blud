package com.tabeldata.resources.controller;

import com.tabeldata.resources.entity.ComponentMenu;
import com.tabeldata.resources.service.ComponentMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/menu")
public class ComponentMenuController {

    @Autowired
    private ComponentMenuService service;

    @GetMapping("/list")
    public ResponseEntity<List<ComponentMenu>> listAll(Principal principal) {
        List<ComponentMenu> menu = service.listAll(principal);
        if (!menu.isEmpty()) {
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
    }
}
