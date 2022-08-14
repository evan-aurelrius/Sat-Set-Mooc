package com.satset.mooc.model.dto;

import lombok.Data;

@Data
public class StudentDto {
    private String name;
    private String email;
    private String password;

    public StudentDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public StudentDto() {}
}
