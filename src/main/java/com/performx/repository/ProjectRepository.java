package com.performx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.performx.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
