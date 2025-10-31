package com.performx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.performx.entity.DesignationMaster;

@Repository
public interface DesignationRepository extends JpaRepository<DesignationMaster, Long> {

}
