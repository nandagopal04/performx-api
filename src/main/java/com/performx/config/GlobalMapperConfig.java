package com.performx.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring",
			  unmappedTargetPolicy = ReportingPolicy.IGNORE,
			  unmappedSourcePolicy = ReportingPolicy.IGNORE
			  )
public interface GlobalMapperConfig {

}
