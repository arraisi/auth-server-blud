package com.tabeldata.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataPenggunaLogin {
    private Integer id;
    private String group;
    private String otor;
    private String nrk;
    private String email;
    private String noHp;
    private String noHpAktif;
    private String nip;
    private String nama;
    private String jabatan;
    private String idSkpd;
}
