package com.performx.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.performx.constant.GlobalConstants;
import com.performx.exception.GlobalException;
import com.performx.mapper.GlobalMapper;
import com.performx.request.AggregateRequest;
import com.performx.request.FilterRequest;
import com.performx.request.GroupByRequest;
import com.performx.service.BaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public abstract class BaseServiceImpl<T, D, ID> implements BaseService<T, D, ID> {

	protected JpaRepository<T, ID> jpaRepository;
	protected GlobalMapper<T, D> globalMapper;

	public BaseServiceImpl(JpaRepository<T, ID> jpaRepository, GlobalMapper<T, D> globalMapper) {
		this.jpaRepository = jpaRepository;
		this.globalMapper = globalMapper;
	}

	@Override
	public D save(D d) throws GlobalException {
		try {
			log.info(GlobalConstants.ENTITY_SAVE_ATTEMPT, d);
			T entity = globalMapper.mapToEntity(d);
			T savedEntity = jpaRepository.save(entity);
			log.info(GlobalConstants.SAVE_SUCCESSFUL);
			return globalMapper.mapToDTO(savedEntity);
		} catch (Exception e) {
			log.error(GlobalConstants.ERROR_SAVE, d, e);
			throw new GlobalException(String.format(GlobalConstants.SAVE_ERROR_REASON, e.getMessage()), e);
		}
	}

	@Override
	public List<D> saveAll(List<D> ds) throws GlobalException {
		try {
			log.info(GlobalConstants.ENTITY_SAVE_ALL_ATTEMPT, ds);
			List<T> entities = globalMapper.mapToEntityList(ds);
			List<T> savedEntities = jpaRepository.saveAll(entities);
			log.info(GlobalConstants.SAVE_SUCCESSFUL);
			return globalMapper.mapToDTOList(savedEntities);
		} catch (Exception e) {
			log.error(GlobalConstants.ERROR_SAVE, ds, e);
			throw new GlobalException(String.format(GlobalConstants.SAVE_ERROR_REASON, e.getMessage()), e);
		}
	}

	@Override
	public D update(ID id, D d) {
		try {
			log.info(GlobalConstants.ENTITY_UPDATE_ATTEMPT, id, d);
			findEntityById(id);
			T updatedEntity = globalMapper.mapToEntity(d);
			setEntityId(updatedEntity, id);
			T savedEntity = jpaRepository.save(updatedEntity);
			log.info("Entity updated successfully with ID: {}", id);
			return globalMapper.mapToDTO(savedEntity);
		} catch (Exception e) {
			log.error(GlobalConstants.ERROR_UPDATE, d, e);
			throw new GlobalException(String.format(GlobalConstants.UPDATE_ERROR_REASON, e.getMessage()), e);
		}
	}

	/**
	 * Utility method to ensure ID is preserved during update.
	 */
	private void setEntityId(T entity, ID id) {
		try {
			Field idField = entity.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			idField.set(entity, id);
		} catch (Exception e) {
			log.warn("Unable to set ID field via reflection for entity: {}", entity.getClass().getSimpleName());
		}
	}

	private T findEntityById(ID id) throws GlobalException {
		Optional<T> optEntity = jpaRepository.findById(id);
		if (optEntity.isEmpty()) {
			throw new GlobalException(String.format(GlobalConstants.ENTITY_NOT_FOUND, id));
		}
		return optEntity.get();
	}

	@Override
	public List<D> updateAll(List<D> ds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public D findById(ID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<D> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<D> findMulti(List<ID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public D deleteById(ID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<D> deleteMulti(List<ID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<D> findAsPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<D> findAll(FilterRequest filterRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<D> findAll(FilterRequest filterRequest, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count(FilterRequest filterRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean exists(FilterRequest filterRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<D> findOneByField(String fieldName, Object value) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<D> findAllByField(String fieldName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean existsById(ID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> aggregate(AggregateRequest aggregateRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> groupBy(GroupByRequest groupByRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public D deactivate(ID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<D> deactivateMulti(List<ID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

}
