package com.satset.mooc.service;

import com.satset.mooc.model.Admin;

public interface AdminService {

    Boolean authenticate(String email, String password);

    void save(Admin admin);

}
