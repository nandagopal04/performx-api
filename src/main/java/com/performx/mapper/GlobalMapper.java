package com.performx.mapper;

import java.util.List;

import org.springframework.data.domain.Page;

public interface GlobalMapper<T, D> {

	D mapToDTO(T t);

	T mapToEntity(D d);

	List<D> mapToDTOList(List<T> tList);

	List<T> mapToEntityList(List<D> dList);

	default Page<D> mapToDTOPage(Page<T> tPage) {
		return tPage.map(this::mapToDTO);
	}

	default Page<T> mapToEntityPage(Page<D> dPage) {
		return dPage.map(this::mapToEntity);
	}

}
