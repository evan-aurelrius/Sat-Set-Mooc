package com.satset.mooc.model;

import com.satset.mooc.model.dto.AdminDto;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name="admin")
public class Admin{
    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private final String role = "admin";

//    @Column(name = "token", unique = true)
//    private String token;

    public Admin(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
//        this.token = token;
    }

    public Admin(AdminDto adminDto) {
        this.email = adminDto.getEmail();
        this.password = adminDto.getPassword();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
