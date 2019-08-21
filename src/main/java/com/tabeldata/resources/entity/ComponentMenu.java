package com.tabeldata.resources.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentMenu {

    private String id;
    private String title;
    private String path;
    private String iconId;
}
