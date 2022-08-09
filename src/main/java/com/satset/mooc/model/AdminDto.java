package com.satset.mooc.model;

import lombok.Data;

@Data
public class AdminDto {

    private String name;
    private String email;
    private String password;

    public AdminDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public AdminDto() {}


}
