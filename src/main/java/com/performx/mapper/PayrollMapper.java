package com.performx.mapper;

import org.mapstruct.Mapper;

import com.performx.config.GlobalMapperConfig;
import com.performx.dto.PayrollDTO;
import com.performx.entity.Payroll;

@Mapper(config = GlobalMapperConfig.class)
public interface PayrollMapper extends GlobalMapper<Payroll, PayrollDTO> {

}
