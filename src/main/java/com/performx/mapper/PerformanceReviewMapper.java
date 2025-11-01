package com.performx.mapper;

import org.mapstruct.Mapper;

import com.performx.config.GlobalMapperConfig;
import com.performx.dto.PerformanceReviewDTO;
import com.performx.entity.PerformanceReview;

@Mapper(config = GlobalMapperConfig.class)
public interface PerformanceReviewMapper extends GlobalMapper<PerformanceReview, PerformanceReviewDTO> {

}
