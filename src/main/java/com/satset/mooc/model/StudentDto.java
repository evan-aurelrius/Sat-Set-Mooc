package com.satset.mooc.model;

import lombok.Data;

@Data
public class StudentDto {
    private long id;
    private String name;
    private String gender;
    private String image;
    private String email;
    private String role;
    private String createdAt;
    private String password;

    public StudentDto(long id, String name, String gender, String image, String email, String role, String createdAt, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.password = password;
    }

    public StudentDto() {}
}
