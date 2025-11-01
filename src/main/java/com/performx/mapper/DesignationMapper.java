package com.performx.mapper;

import org.mapstruct.Mapper;

import com.performx.config.GlobalMapperConfig;
import com.performx.dto.DesignationMasterDTO;
import com.performx.entity.DesignationMaster;

@Mapper(config = GlobalMapperConfig.class)
public interface DesignationMapper extends GlobalMapper<DesignationMaster, DesignationMasterDTO> {

}
