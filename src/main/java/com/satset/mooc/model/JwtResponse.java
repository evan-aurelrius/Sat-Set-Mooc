package com.satset.mooc.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {
    private String token;
    private String type = "Bearer";
    private String email;

    public JwtResponse(
            String accessToken,
            String email) {
        this.email = email;
        this.token = accessToken;
    }

}
