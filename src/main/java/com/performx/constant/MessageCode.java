package com.performx.constant;

/**
 * Defines standardized message codes and message templates used across the
 * PerformX application for logging, exception handling, and API responses.
 *
 * <p>
 * Each enum constant includes:
 * <ul>
 * <li>A unique message code (for tracking / monitoring)</li>
 * <li>A severity level (INFO, WARN, ERROR)</li>
 * <li>A message template (parameterized for runtime data)</li>
 * </ul>
 */
public enum MessageCode {

	// ============================================================
	// ========== SAVE OPERATIONS =================================
	// ============================================================

	ENTITY_SAVE_ATTEMPT("MSG_SAVE_001", Severity.INFO, "Attempting to save entity: {}"),
	ENTITY_SAVE_ALL_ATTEMPT("MSG_SAVE_002", Severity.INFO, "Attempting to save multiple entities: {}"),
	ENTITY_SAVE_SUCCESS("MSG_SAVE_003", Severity.INFO, "Entity saved successfully."),
	ENTITY_SAVE_ERROR("MSG_SAVE_004", Severity.ERROR, "Error occurred while saving entity: {}"),
	ENTITY_SAVE_ERROR_REASON("MSG_SAVE_005", Severity.ERROR, "Failed to save entity. Reason: %s"),

	// ============================================================
	// ========== UPDATE OPERATIONS ===============================
	// ============================================================

	ENTITY_UPDATE_ATTEMPT("MSG_UPD_001", Severity.INFO, "Attempting to update entity with ID: {} | Payload: {}"),
	ENTITY_UPDATE_SUCCESS("MSG_UPD_002", Severity.INFO, "Entity updated successfully with ID: {}"),
	ENTITY_UPDATE_ALL_ATTEMPT("MSG_UPD_003", Severity.INFO, "Attempting bulk update for {} entities"),
	ENTITY_UPDATE_ALL_SUCCESS("MSG_UPD_004", Severity.INFO, "Successfully updated {} entities."),
	ENTITY_UPDATE_ERROR("MSG_UPD_005", Severity.ERROR, "Error occurred while updating entity: {}"),
	ENTITY_UPDATE_ERROR_REASON("MSG_UPD_006", Severity.ERROR, "Failed to update operation. Reason: %s"),
	ENTITY_ID_REQUIRED("MSG_UPD_007", Severity.WARN, "ID cannot be null for update operation."),
	ENTITY_IDS_TO_UPDATE("MSG_UPD_008", Severity.INFO, "IDs to be updated: {}"),
	ENTITY_SET_ID_FAIL("MSG_UPD_009", Severity.WARN, "Unable to set ID field via reflection for entity: {}"),
	ENTITY_EXTRACT_ID_FAIL("MSG_UPD_010", Severity.ERROR, "Failed to extract ID: {}"),

	// ============================================================
	// ========== FIND OPERATIONS =================================
	// ============================================================

	ENTITY_FIND_ALL_ATTEMPT("MSG_FIND_004", Severity.INFO, "Attempting to find all entities"),
	ENTITY_FIND_ALL_SUCCESS("MSG_FIND_005", Severity.INFO, "Find all successful — {} records returned"),
	ENTITIES_NOT_FOUND("MSG_FIND_006", Severity.WARN, "No entities found in database"),

	ENTITY_FIND_BY_ID_ATTEMPT("MSG_FIND_001", Severity.INFO, "Attempting to find entity with ID: {}"),
	ENTITY_FIND_BY_ID_SUCCESS("MSG_FIND_002", Severity.INFO, "Entity found successfully with ID: {}"),
	ENTITY_NOT_FOUND("MSG_FIND_003", Severity.WARN, "Entity not found with ID(s): {}"),

	ENTITY_FIND_MULTI_ATTEMPT("MSG_FIND_007", Severity.INFO, "Attempting to find entities with IDs: {}"),
	ENTITY_FIND_MULTI_SUCCESS("MSG_FIND_008", Severity.INFO, "Successfully found {} entities for given IDs"),
	ENTITY_FIND_MULTI_NOT_FOUND("MSG_FIND_009", Severity.WARN, "No entities found for provided IDs: {}"),
	ENTITY_FIND_NO_IDS_PROVIDED("MSG_FIND_010", Severity.WARN, "No IDs provided for multi-entity retrieval operation"),

	/** ✅ Pagination Support */
	ATTEMPT_FIND_PAGE_OPERATION("MSG_FIND_011", Severity.INFO, "Attempting to find entities as page"),
	FIND_PAGE_SUCCESS("MSG_FIND_012", Severity.INFO, "Find as page successful — {} records returned"),

	// ============================================================
	// ========== ERROR HANDLING ==================================
	// ============================================================

	ENTITY_FETCH_ERROR("MSG_ERR_001", Severity.ERROR, "Error occurred while fetching entities. Reason: {}"),
	ENTITY_FETCH_ALL_FAIL("MSG_ERR_002", Severity.ERROR, "Failed to fetch all entities. Reason: %s"),
	ENTITY_FETCH_MULTI_FAIL("MSG_ERR_003", Severity.ERROR, "Failed to fetch entities by IDs. Reason: %s"),

	// ============================================================
	// ========== DELETE OPERATIONS ===============================
	// ============================================================

	ENTITY_DELETE_BY_ID_ATTEMPT("MSG_DELETE_001", Severity.INFO, "Attempting to delete the entity with ID: {}"),
	ENTITY_DELETE_MULTI_ATTEMPT("MSG_DELETE_002", Severity.INFO, "Attempting to delete the entities with IDs: {}"),
	ENTITY_DELETE_ALL_ATTEMPT("MSG_DELETE_003", Severity.INFO, "Attempting to delete all entities"),

	ENTITY_DELETE_MULTI_FAIL("MSG_DELETE_004", Severity.ERROR, "Failed to delete entities with IDs: {}"),
	ENTITY_DELETE_BY_ID_FAIL("MSG_DELETE_005", Severity.ERROR, "Failed to delete entity with ID: {}"),
	ENTITY_DELETE_ALL_FAIL("MSG_DELETE_006", Severity.ERROR, "Failed to delete all entities"),

	ENTITY_DELETE_MULTI_SUCCESS("MSG_DELETE_007", Severity.INFO, "Successfully deleted entities with IDs: {}"),
	ENTITY_DELETE_BY_ID_SUCCESS("MSG_DELETE_008", Severity.INFO, "Successfully deleted entity with ID: {}"),
	ENTITY_DELETE_ALL_SUCCESS("MSG_DELETE_009", Severity.INFO, "Successfully deleted all entities");

	// ============================================================
	// ========== ENUM STRUCTURE ==================================
	// ============================================================

	private final String code;
	private final Severity severity;
	private final String message;

	MessageCode(String code, Severity severity, String message) {
		this.code = code;
		this.severity = severity;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public Severity getSeverity() {
		return severity;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "[" + code + " | " + severity + "] " + message;
	}

	// Sub-enum for severity levels
	public enum Severity {
		INFO, WARN, ERROR
	}
}
