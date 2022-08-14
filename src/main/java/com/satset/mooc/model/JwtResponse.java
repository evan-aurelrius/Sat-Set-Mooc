package com.satset.mooc.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class JwtResponse implements Serializable {
    private long id;
    private String name;
    private String gender;
    private String image;
    private String email;
    private String role;
    private Timestamp created_at;
    private String token;

    public JwtResponse(long id, String name, String gender, String image, String email, String role, Timestamp created_at, String token) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.email = email;
        this.role = role;
        this.created_at = created_at;
        this.token = token;
    }
}
