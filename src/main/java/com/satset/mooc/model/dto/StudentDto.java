package com.satset.mooc.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDto {
    private long id;
    private String name;
    private String gender;
    private String image;
    private String email;
    private String role;
    private String createdAt;
    private String password;

}
