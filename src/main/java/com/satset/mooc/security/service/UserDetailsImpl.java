package com.satset.mooc.security.service;

import com.satset.mooc.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserDetailsImpl implements UserDetails {
    private long id;
    private String name;
    private String email;
    private String gender;
    private String image;
    private String roles;
    private Timestamp createdAt;
    @JsonIgnore
    private String password;

    public UserDetailsImpl(long id, String name, String gender, String image, String email, String roles, Timestamp createdAt, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.email = email;
        this.roles = roles;
        this.createdAt = createdAt;
        this.password = password;
    }

    public static UserDetailsImpl build(Student student) {
        return new UserDetailsImpl(student.getId(),
                                student.getName(),
                                student.getGender(),
                                student.getImage(),
                                student.getEmail(),
                                "student",
                                student.getCreatedAt(),
                                student.getPassword()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.hasText(roles)) {
            String[] splits = roles.replaceAll(" ", "").split(",");
            for (String string : splits) {
                authorities.add(new SimpleGrantedAuthority(string));
            }
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
