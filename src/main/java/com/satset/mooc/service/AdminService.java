package com.satset.mooc.service;

import com.satset.mooc.model.Admin;
import com.satset.mooc.model.Instructor;

public interface AdminService {

    Boolean authenticate(String email, String password);

    Admin getAdminByEmail(String email);

    void save(Admin admin);

}
