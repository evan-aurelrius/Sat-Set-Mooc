package com.satset.mooc.service;

import com.satset.mooc.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminRepository adminRepository;
    @Override
    public Boolean authenticate(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password).isPresent() ? true : false;
    }
}
