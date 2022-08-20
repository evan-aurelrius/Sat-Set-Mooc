package com.satset.mooc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminTest {

    private final Admin admin = new Admin("Admin1", "admin@localhost.com", "pass123");

    @Test
    void setAccountClassFromAccountDTO(){
        assertEquals("admin@localhost.com", admin.getEmail());
        assertEquals("pass123", admin.getPassword());
    }
}
