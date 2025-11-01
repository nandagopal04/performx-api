package com.performx.mapper;

import org.mapstruct.Mapper;

import com.performx.config.GlobalMapperConfig;
import com.performx.dto.EmployeeDTO;
import com.performx.entity.Employee;

@Mapper(config = GlobalMapperConfig.class)
public interface EmployeeMapper extends GlobalMapper<Employee, EmployeeDTO> {

}
