package com.performx.mapper;

import org.mapstruct.Mapper;

import com.performx.config.GlobalMapperConfig;
import com.performx.dto.ProjectDTO;
import com.performx.entity.Project;

@Mapper(config = GlobalMapperConfig.class)
public interface ProjectMapper extends GlobalMapper<Project, ProjectDTO> {

}
