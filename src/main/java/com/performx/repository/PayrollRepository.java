package com.performx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.performx.entity.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

}
