package com.performx.mapper;

import org.mapstruct.Mapper;

import com.performx.config.GlobalMapperConfig;
import com.performx.dto.DepartmentMasterDTO;
import com.performx.entity.DepartmentMaster;

@Mapper(config = GlobalMapperConfig.class)
public interface DepartmentMapper extends GlobalMapper<DepartmentMaster, DepartmentMasterDTO> {

}
