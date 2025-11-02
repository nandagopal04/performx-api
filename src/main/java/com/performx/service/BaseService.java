package com.performx.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.performx.exception.GlobalException;
import com.performx.request.AggregateRequest;
import com.performx.request.FilterRequest;
import com.performx.request.GroupByRequest;

/**
 * BaseService is a generic service interface that defines a set of
 * reusable CRUD and query operations applicable across any domain or module.
 *
 * <p>This interface can serve as the foundation for any service layer
 * implementation, promoting consistency, reusability, and scalability.</p>
 *
 * <p>Type Parameters:</p>
 * <ul>
 *   <li><b>T</b> - The entity type (e.g., Employee, Product)</li>
 *   <li><b>D</b> - The corresponding DTO (Data Transfer Object) type</li>
 *   <li><b>ID</b> - The identifier type (e.g., Long, UUID, String)</li>
 * </ul>
 *
 * <p>Key Features:</p>
 * <ul>
 *   <li>Supports CRUD operations and bulk processing</li>
 *   <li>Enables dynamic filtering and pagination for flexible querying</li>
 *   <li>Provides aggregation and group-by capabilities for analytics</li>
 *   <li>Extensible to any project regardless of domain or database</li>
 * </ul>
 *
 * @author Nanda Gopal Ikkurthi
 * @version 1.0
 */
public interface BaseService<T, D, ID> {

    // ------------------------------------------------------------------------
    // CRUD OPERATIONS
    // ------------------------------------------------------------------------

    /**
     * Persists a new record.
     *
     * @param d The DTO object to save.
     * @return The saved DTO.
     * @throws GlobalException
     */
    D save(D d) throws GlobalException;

    /**
     * Persists a list of new records in bulk.
     *
     * @param ds The list of DTOs to save.
     * @return List of saved DTOs.
     */
    List<D> saveAll(List<D> ds);

    /**
     * Updates an existing record identified by ID.
     *
     * @param id The unique identifier.
     * @param d  The DTO with updated fields.
     * @return The updated DTO.
     */
    D update(ID id, D d);

    /**
     * Updates multiple records in bulk.
     *
     * @param ds List of DTOs containing updated data.
     * @return List of updated DTOs.
     */
    List<D> updateAll(List<D> ds);

    /**
     * Retrieves a record by its unique identifier.
     *
     * @param id The unique identifier.
     * @return The corresponding DTO.
     */
    D findById(ID id);

    /**
     * Retrieves all records.
     *
     * @return List of all DTOs.
     */
    List<D> findAll();

    /**
     * Retrieves multiple records by a list of IDs.
     *
     * @param ids List of unique identifiers.
     * @return List of corresponding DTOs.
     */
    List<D> findMulti(List<ID> ids);

    /**
     * Deletes a record by its ID.
     *
     * @param id The unique identifier.
     * @return The deleted DTO.
     */
    D deleteById(ID id);

    /**
     * Deletes multiple records by their IDs.
     *
     * @param ids List of unique identifiers.
     * @return List of deleted DTOs.
     */
    List<D> deleteMulti(List<ID> ids);

    /**
     * Deletes all records in the repository.
     *
     * @return {@code true} if all records are deleted successfully, otherwise {@code false}.
     */
    Boolean deleteAll();

    // ------------------------------------------------------------------------
    // PAGINATION
    // ------------------------------------------------------------------------

    /**
     * Retrieves paginated records.
     *
     * @param pageable Pagination and sorting information.
     * @return A page of DTOs.
     */
    Page<D> findAsPage(Pageable pageable);

    // ------------------------------------------------------------------------
    // QUERY & SEARCH OPERATIONS (Criteria API Friendly)
    // ------------------------------------------------------------------------

    /**
     * Retrieves records dynamically based on provided filter conditions.
     *
     * @param filterRequest The filter and search criteria.
     * @return List of matching DTOs.
     */
    List<D> findAll(FilterRequest filterRequest);

    /**
     * Retrieves paginated and dynamically filtered records.
     *
     * @param filterRequest The filter and search criteria.
     * @param pageable      Pagination and sorting information.
     * @return Page of matching DTOs.
     */
    Page<D> findAll(FilterRequest filterRequest, Pageable pageable);

    /**
     * Counts records matching the provided filters.
     *
     * @param filterRequest The filter and search criteria.
     * @return Count of matching records.
     */
    Long count(FilterRequest filterRequest);

    /**
     * Checks whether any record exists that matches the provided filters.
     *
     * @param filterRequest The filter and search criteria.
     * @return {@code true} if any record matches, otherwise {@code false}.
     */
    Boolean exists(FilterRequest filterRequest);

    // ------------------------------------------------------------------------
    // UTILITY METHODS
    // ------------------------------------------------------------------------

    /**
     * Retrieves a single record by a field name and its value.
     *
     * @param fieldName The name of the field.
     * @param value     The value to match.
     * @return Optional containing the matching DTO, if found.
     */
    Optional<D> findOneByField(String fieldName, Object value);

    /**
     * Retrieves all records by a field name and its value.
     *
     * @param fieldName The name of the field.
     * @param value     The value to match.
     * @return List of matching DTOs.
     */
    List<D> findAllByField(String fieldName, Object value);

    /**
     * Counts all records in the repository.
     *
     * @return Total count of records.
     */
    Long count();

    /**
     * Checks whether a record exists by its unique identifier.
     *
     * @param id The unique identifier.
     * @return {@code true} if the record exists, otherwise {@code false}.
     */
    Boolean existsById(ID id);

    // ------------------------------------------------------------------------
    // ANALYTICS & AGGREGATIONS
    // ------------------------------------------------------------------------

    /**
     * Performs aggregation operations (SUM, AVG, COUNT, MAX, MIN)
     * based on provided aggregation request.
     *
     * @param aggregateRequest The aggregation configuration.
     * @return A map containing aggregate results (e.g., {"totalSalary": 120000.0}).
     */
	Map<String, Object> aggregate(AggregateRequest aggregateRequest);

    /**
     * Performs group-by operations and returns summarized data.
     *
     * @param groupByRequest The grouping configuration.
     * @return List of grouped and aggregated results.
     */
    List<Map<String, Object>> groupBy(GroupByRequest groupByRequest);

    // ------------------------------------------------------------------------
    // LOGICAL DELETION (Optional)
    // ------------------------------------------------------------------------

    /**
     * Marks a record as inactive or logically deleted.
     *
     * @param id The unique identifier.
     * @return The updated DTO after deactivation.
     */
    D deactivate(ID id);

    /**
     * Marks multiple records as inactive or logically deleted.
     *
     * @param ids List of unique identifiers.
     * @return List of updated DTOs after deactivation.
     */
    List<D> deactivateMulti(List<ID> ids);
}
