package com.satset.mooc.repository;

import com.satset.mooc.model.DailyNewUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface DailyNewUserRepository extends JpaRepository<DailyNewUser, Long> {

    DailyNewUser findFirstByOrderByDateDesc();

    LinkedList<DailyNewUser> findAllByOrderByDateAsc();

}
