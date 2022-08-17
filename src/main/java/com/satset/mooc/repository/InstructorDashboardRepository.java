package com.satset.mooc.repository;

import com.satset.mooc.model.InstructorDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorDashboardRepository extends JpaRepository<InstructorDashboard, Long> {



}
