package com.performx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.performx.entity.DepartmentMaster;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentMaster, Long> {

}
