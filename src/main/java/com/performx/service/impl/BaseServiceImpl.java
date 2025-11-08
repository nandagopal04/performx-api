package com.performx.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.performx.constant.LogicalOperator;
import com.performx.constant.MessageCode;
import com.performx.constant.SortDirection;
import com.performx.exception.GlobalException;
import com.performx.mapper.GlobalMapper;
import com.performx.request.AggregateRequest;
import com.performx.request.FilterCondition;
import com.performx.request.FilterRequest;
import com.performx.request.GroupByRequest;
import com.performx.request.SortOrder;
import com.performx.service.BaseService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public abstract class BaseServiceImpl<T, D, ID> implements BaseService<T, D, ID> {

	protected JpaRepository<T, ID> jpaRepository;
	protected GlobalMapper<T, D> globalMapper;

	@Autowired
	private EntityManager entityManager;

	private final Class<T> entityClass;

	protected Class<T> getEntityClass() {
		return entityClass;
	}

	public BaseServiceImpl(JpaRepository<T, ID> jpaRepository, GlobalMapper<T, D> globalMapper, Class<T> entityClass) {
		this.jpaRepository = jpaRepository;
		this.globalMapper = globalMapper;
		this.entityClass = entityClass;
	}

	@Override
	public D save(D d) throws GlobalException {
		try {
			log.info(MessageCode.ENTITY_SAVE_ATTEMPT.getMessage(), d);
			T entity = globalMapper.mapToEntity(d);
			T savedEntity = jpaRepository.save(entity);
			log.info(MessageCode.ENTITY_SAVE_SUCCESS.getMessage());
			return globalMapper.mapToDTO(savedEntity);
		} catch (Exception e) {
			log.error(MessageCode.ENTITY_SAVE_ERROR.getMessage(), d, e);
			throw new GlobalException(String.format(MessageCode.ENTITY_SAVE_ERROR_REASON.getMessage(), e.getMessage()),
					e);
		}
	}

	@Override
	public List<D> saveAll(List<D> ds) throws GlobalException {
		try {
			log.info(MessageCode.ENTITY_SAVE_ALL_ATTEMPT.getMessage(), ds);
			List<T> entities = globalMapper.mapToEntityList(ds);
			List<T> savedEntities = jpaRepository.saveAll(entities);
			log.info(MessageCode.ENTITY_SAVE_SUCCESS.getMessage());
			return globalMapper.mapToDTOList(savedEntities);
		} catch (Exception e) {
			log.error(MessageCode.ENTITY_SAVE_ERROR.getMessage(), ds, e);
			throw new GlobalException(String.format(MessageCode.ENTITY_SAVE_ERROR_REASON.getMessage(), e.getMessage()),
					e);
		}
	}

	@Override
	public D update(ID id, D d) {
		return performUpdate(id, d, false);
	}

	@Override
	public D update(D d) {
		return performUpdate(null, d, true);
	}

	/**
	 * Common reusable method for single-entity update operations.
	 */
	private D performUpdate(ID pathId, D d, boolean extractFromDto) {
		try {
			ID effectiveId = extractFromDto ? extractIdFromDto(d) : pathId;
			if (effectiveId == null) {
				throw new GlobalException(MessageCode.ENTITY_ID_REQUIRED.getMessage());
			}
			log.info(MessageCode.ENTITY_UPDATE_ATTEMPT.getMessage(), effectiveId, d);
			findEntityById(effectiveId);
			T updatedEntity = globalMapper.mapToEntity(d);
			setEntityId(updatedEntity, effectiveId);
			T savedEntity = jpaRepository.save(updatedEntity);
			log.info(MessageCode.ENTITY_UPDATE_SUCCESS.getMessage(), effectiveId);
			return globalMapper.mapToDTO(savedEntity);

		} catch (Exception e) {
			log.error(MessageCode.ENTITY_UPDATE_ERROR.getMessage(), d, e);
			throw new GlobalException(
					String.format(MessageCode.ENTITY_UPDATE_ERROR_REASON.getMessage(), e.getMessage()), e);
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
			log.warn(MessageCode.ENTITY_SET_ID_FAIL.getMessage(), entity.getClass().getSimpleName());
		}
	}

	private T findEntityById(ID id) throws GlobalException {
		log.info(String.format(MessageCode.ENTITY_FIND_BY_ID_ATTEMPT.getMessage(), id));
		Optional<T> optEntity = jpaRepository.findById(id);
		if (optEntity.isEmpty()) {
			throw new GlobalException(String.format(MessageCode.ENTITY_NOT_FOUND.getMessage(), id));
		}
		return optEntity.get();
	}

	@Override
	public List<D> updateAll(List<D> ds) {
		try {
			log.info(MessageCode.ENTITY_UPDATE_ALL_ATTEMPT.getMessage(), ds.size());
			List<ID> ids = ds.stream().map(this::extractIdFromDto).filter(Objects::nonNull).toList();
			log.debug(MessageCode.ENTITY_IDS_TO_UPDATE.getMessage(), ids);
			List<T> existingEntities = jpaRepository.findAllById(ids);
			if (existingEntities.isEmpty()) {
				throw new GlobalException(String.format(MessageCode.ENTITY_NOT_FOUND.getMessage(), ids));
			}
			Map<ID, T> entityMap = existingEntities.stream()
					.collect(Collectors.toMap(this::extractIdFromEntity, e -> e));
			List<T> updatedEntities = new ArrayList<>();
			for (D dto : ds) {
				ID id = extractIdFromDto(dto);
				T existingEntity = entityMap.get(id);
				if (existingEntity == null) {
					log.warn(MessageCode.ENTITY_NOT_FOUND.getMessage(), id);
					continue;
				}
				T updatedEntity = globalMapper.mapToEntity(dto);
				setEntityId(updatedEntity, id);
				updatedEntities.add(updatedEntity);
			}
			List<T> savedEntities = jpaRepository.saveAll(updatedEntities);
			log.info(MessageCode.ENTITY_UPDATE_ALL_SUCCESS.getMessage(), savedEntities.size());
			return globalMapper.mapToDTOList(savedEntities);
		} catch (Exception e) {
			log.error(MessageCode.ENTITY_UPDATE_ERROR_REASON.getMessage(), e.getMessage(), e);
			throw new GlobalException(
					String.format(MessageCode.ENTITY_UPDATE_ERROR_REASON.getMessage(), e.getMessage()), e);
		}
	}

	@SuppressWarnings("unchecked")
	private ID extractIdFromDto(D dto) {
		try {
			Field idField = dto.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			return (ID) idField.get(dto);
		} catch (Exception e) {
			log.warn(MessageCode.ENTITY_EXTRACT_ID_FAIL.getMessage(), dto.getClass().getSimpleName());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private ID extractIdFromEntity(T entity) {
		try {
			Field idField = entity.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			return (ID) idField.get(entity);
		} catch (Exception e) {
			log.warn(MessageCode.ENTITY_EXTRACT_ID_FAIL.getMessage(), entity.getClass().getSimpleName());
			return null;
		}
	}

	@Override
	public D findById(ID id) {
		T entity = findEntityById(id);
		log.info(MessageCode.ENTITY_FIND_BY_ID_SUCCESS.getMessage(), id);
		return globalMapper.mapToDTO(entity);
	}

	@Override
	public List<D> findAll() {
		try {
			log.info(MessageCode.ENTITY_FIND_ALL_ATTEMPT.getMessage());
			List<T> entities = jpaRepository.findAll();
			if (entities.isEmpty()) {
				log.info(MessageCode.ENTITIES_NOT_FOUND.getMessage());
				return Collections.emptyList();
			}
			log.info(MessageCode.ENTITY_FIND_ALL_SUCCESS.getMessage(), entities.size());
			return globalMapper.mapToDTOList(entities);
		} catch (Exception e) {
			log.error(MessageCode.ENTITY_FETCH_ERROR.getMessage(), e.getMessage(), e);
			throw new GlobalException(String.format(MessageCode.ENTITY_FETCH_ALL_FAIL.getMessage(), e.getMessage()), e);
		}
	}

	@Override
	public List<D> findMulti(List<ID> ids) {
		try {
			log.info(MessageCode.ENTITY_FIND_MULTI_ATTEMPT.getMessage(), ids);
			if (ids == null || ids.isEmpty()) {
				log.warn(MessageCode.ENTITY_FIND_NO_IDS_PROVIDED.getMessage());
				return Collections.emptyList();
			}
			List<T> entities = jpaRepository.findAllById(ids);
			if (entities.isEmpty()) {
				log.info(MessageCode.ENTITY_FIND_MULTI_NOT_FOUND.getMessage(), ids);
			} else {
				log.info(MessageCode.ENTITY_FIND_MULTI_SUCCESS.getMessage(), entities.size());
			}
			return globalMapper.mapToDTOList(entities);
		} catch (Exception e) {
			log.error(MessageCode.ENTITY_FETCH_ERROR.getMessage(), e.getMessage(), e);
			throw new GlobalException(String.format(MessageCode.ENTITY_FETCH_MULTI_FAIL.getMessage(), e.getMessage()),
					e);
		}
	}

	@Override
	public D deleteById(ID id) {
		try {
			log.info(MessageCode.ENTITY_DELETE_BY_ID_ATTEMPT.getMessage(), id);
			T entity = findEntityById(id);
			jpaRepository.delete(entity);
			log.info(MessageCode.ENTITY_DELETE_BY_ID_SUCCESS.getMessage(), id);
			return globalMapper.mapToDTO(entity);
		} catch (Exception e) {
			log.error(MessageCode.ENTITY_DELETE_BY_ID_FAIL.getMessage(), id, e);
			throw new GlobalException(String.format(MessageCode.ENTITY_DELETE_BY_ID_FAIL.getMessage(), id), e);
		}
	}

	@Override
	public List<D> deleteMulti(List<ID> ids) {
		try {
			log.info(MessageCode.ENTITY_DELETE_MULTI_ATTEMPT.getMessage(), ids);
			if (ids == null || ids.isEmpty()) {
				log.warn(MessageCode.ENTITY_FIND_NO_IDS_PROVIDED.getMessage());
				return Collections.emptyList();
			}
			List<T> entities = jpaRepository.findAllById(ids);
			if (entities.isEmpty()) {
				log.warn(MessageCode.ENTITY_DELETE_MULTI_FAIL.getMessage(), ids);
				return Collections.emptyList();
			}
			jpaRepository.deleteAll(entities);
			log.info(MessageCode.ENTITY_DELETE_MULTI_SUCCESS.getMessage(), ids);
			return globalMapper.mapToDTOList(entities);
		} catch (Exception e) {
			log.error(MessageCode.ENTITY_DELETE_MULTI_FAIL.getMessage(), ids, e);
			throw new GlobalException(String.format(MessageCode.ENTITY_DELETE_MULTI_FAIL.getMessage(), ids), e);
		}
	}

	@Override
	public Boolean deleteAll() {
		try {
			log.info(MessageCode.ENTITY_DELETE_ALL_ATTEMPT.getMessage());
			jpaRepository.deleteAll();
			log.info(MessageCode.ENTITY_DELETE_ALL_SUCCESS.getMessage());
			return true;
		} catch (Exception e) {
			log.error(MessageCode.ENTITY_DELETE_ALL_FAIL.getMessage(), e);
			throw new GlobalException(MessageCode.ENTITY_DELETE_ALL_FAIL.getMessage(), e);
		}
	}

	@Override
	public Page<D> findAsPage(Pageable pageable) {
		try {
			log.info(MessageCode.ATTEMPT_FIND_PAGE_OPERATION.getMessage());
			Page<T> entityPage = jpaRepository.findAll(pageable);
			if (entityPage.isEmpty()) {
				log.info(MessageCode.ENTITIES_NOT_FOUND.getMessage());
				return Page.empty(pageable);
			}
			log.info(MessageCode.FIND_PAGE_SUCCESS.getMessage(), entityPage.getTotalElements());
			return entityPage.map(globalMapper::mapToDTO);
		} catch (Exception e) {
			log.error(MessageCode.ENTITY_FETCH_ERROR.getMessage(), e.getMessage(), e);
			throw new GlobalException(String.format(MessageCode.ENTITY_FETCH_ALL_FAIL.getMessage(), e.getMessage()), e);
		}
	}

	@Override
	public List<D> findAll(FilterRequest filterRequest) {
		try {
			log.info(MessageCode.ENTITY_FIND_ALL_ATTEMPT.getMessage());
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(getEntityClass());
			Root<T> root = cq.from(getEntityClass());
			List<Predicate> predicates = new ArrayList<>();
			if (filterRequest != null && filterRequest.getConditions() != null) {
				for (FilterCondition fc : filterRequest.getConditions()) {
					Predicate p = buildPredicate(cb, root, fc);
					if (p != null)
						predicates.add(p);
				}
			}
			// Combine with AND/OR (default AND)
			Predicate where = predicates.isEmpty() ? cb.conjunction()
					: (filterRequest != null && filterRequest.getLogicalOperator() == LogicalOperator.OR)
							? cb.or(predicates.toArray(new Predicate[0]))
							: cb.and(predicates.toArray(new Predicate[0]));
			cq.select(root).where(where);
			// Sorting (optional)
			if (filterRequest != null && filterRequest.getSortOrders() != null) {
				List<Order> orders = new ArrayList<>();
				for (SortOrder so : filterRequest.getSortOrders()) {
					Path<?> path = root.get(so.getField());
					orders.add(so.getDirection() == SortDirection.DESC ? cb.desc(path) : cb.asc(path));
				}
				if (!orders.isEmpty())
					cq.orderBy(orders);
			}
			List<T> entities = entityManager.createQuery(cq).getResultList();
			if (entities.isEmpty()) {
				log.info(MessageCode.ENTITIES_NOT_FOUND.getMessage());
				return Collections.emptyList();
			}
			log.info(MessageCode.ENTITY_FIND_ALL_SUCCESS.getMessage(), entities.size());
			return globalMapper.mapToDTOList(entities);

		} catch (Exception e) {
			log.error(MessageCode.ENTITY_FETCH_ERROR.getMessage(), e.getMessage(), e);
			throw new GlobalException(String.format(MessageCode.ENTITY_FETCH_ALL_FAIL.getMessage(), e.getMessage()), e);
		}
	}

	/** Build a single Predicate for a condition, with basic type handling. */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Predicate buildPredicate(CriteriaBuilder cb, Root<T> root, FilterCondition fc) {
		String field = fc.getField();
		Object value = fc.getValue();
		Path<?> path = root.get(field);
		Class<?> javaType = path.getJavaType();

		switch (fc.getOperator()) {
		case EQUALS:
			return cb.equal(path, convert(value, javaType));
		case NOT_EQUALS:
			return cb.notEqual(path, convert(value, javaType));
		case GREATER_THAN:
			return cb.greaterThan((Expression<? extends Comparable>) path, (Comparable) convert(value, javaType));
		case GREATER_THAN_EQUAL:
			return cb.greaterThanOrEqualTo((Expression<? extends Comparable>) path,
					(Comparable) convert(value, javaType));
		case LESS_THAN:
			return cb.lessThan((Expression<? extends Comparable>) path, (Comparable) convert(value, javaType));
		case LESS_THAN_EQUAL:
			return cb.lessThanOrEqualTo((Expression<? extends Comparable>) path, (Comparable) convert(value, javaType));
		case BETWEEN: {
			Object v1 = convert(value, javaType);
			Object v2 = convert(fc.getAdditionalValue(), javaType);
			return cb.between((Expression<? extends Comparable>) path, (Comparable) v1, (Comparable) v2);
		}
		case LIKE:
			if (javaType != String.class) {
				throw new GlobalException("LIKE is only valid for String fields: " + field);
			}
			return cb.like((Expression<String>) path, "%" + value + "%");
		case IN:
			if (!(value instanceof Collection<?> col)) {
				throw new GlobalException("Value for IN must be a collection: " + field);
			}
			CriteriaBuilder.In<Object> in = cb.in(path);
			for (Object v : col)
				in.value(convert(v, javaType));
			return in;
		case NOT_IN:
			if (!(value instanceof Collection<?> col2)) {
				throw new GlobalException("Value for NOT_IN must be a collection: " + field);
			}
			CriteriaBuilder.In<Object> in2 = cb.in(path);
			for (Object v : col2)
				in2.value(convert(v, javaType));
			return cb.not(in2);
		case IS_NULL:
			return cb.isNull(path);
		case IS_NOT_NULL:
			return cb.isNotNull(path);
		default:
			throw new GlobalException("Unsupported operator: " + fc.getOperator());
		}
	}

	/**
	 * Minimal converter to coerce values to the field's Java type. Extend as
	 * needed.
	 */
	private Object convert(Object value, Class<?> targetType) {
		if (value == null || targetType.isInstance(value))
			return value;

		if (targetType == String.class)
			return String.valueOf(value);
		if (Number.class.isAssignableFrom(targetType)) {
			String s = String.valueOf(value);
			if (targetType == Integer.class)
				return Integer.valueOf(s);
			if (targetType == Long.class)
				return Long.valueOf(s);
			if (targetType == Double.class)
				return Double.valueOf(s);
			if (targetType == Float.class)
				return Float.valueOf(s);
			if (targetType == Short.class)
				return Short.valueOf(s);
			if (targetType == BigDecimal.class)
				return new BigDecimal(s);
		}
		if (targetType == Boolean.class)
			return Boolean.valueOf(String.valueOf(value));
		if (Comparable.class.isAssignableFrom(targetType) && value instanceof String str) {
			// dates/times/uuids — plug in your parsers here
			if (targetType == LocalDate.class)
				return LocalDate.parse(str);
			if (targetType == LocalDateTime.class)
				return LocalDateTime.parse(str);
			if (targetType == UUID.class)
				return UUID.fromString(str);
		}
		return value; // last resort
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
