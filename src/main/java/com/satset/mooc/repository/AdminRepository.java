package com.satset.mooc.repository;

import com.satset.mooc.model.Admin;
import com.satset.mooc.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmailAndPassword(String email, String password);

    Optional<Admin> findByEmail(String email);
}