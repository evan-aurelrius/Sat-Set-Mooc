package com.satset.mooc.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignupRequest implements Serializable {
    private String name;
    private String gender;
    private String image;
    private String email;
    private String password;
}
