package com.satset.mooc.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDto {

    private String name;
    private String email;
    private String password;
    private String token;

}
