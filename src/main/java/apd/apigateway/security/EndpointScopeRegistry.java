package apd.apigateway.security;

import apd.apigateway.constant.Endpoint;
import apd.apigateway.constant.ScopeConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps endpoints to required scopes
 */
@Slf4j
@Component
public class EndpointScopeRegistry {

    @Getter
    private final Map<String, Map<String, String>> endpointScopes = new HashMap<>();
    private final String apiBasePath;

    public EndpointScopeRegistry(@Value("/api/${api.ems_version}") String apiBasePath) {
        this.apiBasePath = apiBasePath;
        initializeScopeMappings();
    }

    private void initializeScopeMappings() {
        // Authentication endpoints - register with full path
        registerEndpoint(apiBasePath + Endpoint.AUTH_LOGOUT, "GET", ScopeConstants.USER_READ);
        registerEndpoint(apiBasePath + Endpoint.AUTH_REFRESH_ACCESS, "POST", ScopeConstants.USER_READ);
        registerEndpoint(apiBasePath + Endpoint.AUTH_DETAIL, "GET", ScopeConstants.USER_READ);

        // User Management - register with full path
        registerEndpoint(apiBasePath + Endpoint.USER, "GET", ScopeConstants.USER_READ);
        registerEndpoint(apiBasePath + Endpoint.USER, "POST", ScopeConstants.USER_WRITE);
        registerEndpoint(apiBasePath + Endpoint.USER, "PUT", ScopeConstants.USER_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.USER, "DELETE", ScopeConstants.USER_DELETE);

        // Role Management
        registerEndpoint(apiBasePath + Endpoint.ROLES, "GET", ScopeConstants.ROLE_READ);
        registerEndpoint(apiBasePath + Endpoint.ROLES, "POST", ScopeConstants.ROLE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.ROLES, "PUT", ScopeConstants.ROLE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.ROLES, "DELETE", ScopeConstants.ROLE_DELETE);

        // Permission Management
        registerEndpoint(apiBasePath + Endpoint.PERMISSIONS, "GET", ScopeConstants.PERMISSION_READ);
        registerEndpoint(apiBasePath + Endpoint.PERMISSIONS, "POST", ScopeConstants.ROLE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.PERMISSIONS, "PUT", ScopeConstants.ROLE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.PERMISSIONS, "DELETE", ScopeConstants.ROLE_DELETE);

        // Profile Management
        registerEndpoint(apiBasePath + Endpoint.PROFILE_TRAINEE, "GET", ScopeConstants.PROFILE_TRAINEE_READ);
        registerEndpoint(apiBasePath + Endpoint.PROFILE_TRAINEE, "POST", ScopeConstants.PROFILE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.PROFILE_TRAINEE, "PUT", ScopeConstants.PROFILE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.PROFILE_TRAINEE, "DELETE", ScopeConstants.PROFILE_DELETE);

        registerEndpoint(apiBasePath + Endpoint.PROFILE_MENTOR, "GET", ScopeConstants.PROFILE_READ);
        registerEndpoint(apiBasePath + Endpoint.PROFILE_MENTOR, "POST", ScopeConstants.PROFILE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.PROFILE_MENTOR, "PUT", ScopeConstants.PROFILE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.PROFILE_MENTOR, "DELETE", ScopeConstants.PROFILE_DELETE);

        registerEndpoint(apiBasePath + Endpoint.PROFILE_TALENT_DEV, "GET", ScopeConstants.PROFILE_READ);
        registerEndpoint(apiBasePath + Endpoint.PROFILE_TALENT_DEV, "POST", ScopeConstants.PROFILE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.PROFILE_TALENT_DEV, "PUT", ScopeConstants.PROFILE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.PROFILE_TALENT_DEV, "DELETE", ScopeConstants.PROFILE_DELETE);

        // Organization Management
        registerEndpoint(apiBasePath + Endpoint.UNITS, "GET", ScopeConstants.PROFILE_READ);
        registerEndpoint(apiBasePath + Endpoint.UNITS, "POST", ScopeConstants.PROFILE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.UNITS, "PUT", ScopeConstants.PROFILE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.UNITS, "DELETE", ScopeConstants.PROFILE_DELETE);

        registerEndpoint(apiBasePath + Endpoint.SITES, "GET", ScopeConstants.PROFILE_READ);
        registerEndpoint(apiBasePath + Endpoint.SITES, "POST", ScopeConstants.PROFILE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.SITES, "PUT", ScopeConstants.PROFILE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.SITES, "DELETE", ScopeConstants.PROFILE_DELETE);

        registerEndpoint(apiBasePath + Endpoint.DEPARTMENTS, "GET", ScopeConstants.PROFILE_READ);
        registerEndpoint(apiBasePath + Endpoint.DEPARTMENTS, "POST", ScopeConstants.PROFILE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.DEPARTMENTS, "PUT", ScopeConstants.PROFILE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.DEPARTMENTS, "DELETE", ScopeConstants.PROFILE_DELETE);

        registerEndpoint(apiBasePath + Endpoint.DESIGNATION, "GET", ScopeConstants.PROFILE_READ);
        registerEndpoint(apiBasePath + Endpoint.DESIGNATION, "POST", ScopeConstants.PROFILE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.DESIGNATION, "PUT", ScopeConstants.PROFILE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.DESIGNATION, "DELETE", ScopeConstants.PROFILE_DELETE);

        // Leave Management
        registerEndpoint(apiBasePath + Endpoint.LEAVE_ENTITLEMENT, "GET", ScopeConstants.LEAVE_MANAGEMENT_READ);
        registerEndpoint(apiBasePath + Endpoint.LEAVE_ENTITLEMENT, "POST", ScopeConstants.LEAVE_MANAGEMENT_WRITE);

        registerEndpoint(apiBasePath + Endpoint.LEAVE_TYPE, "GET", ScopeConstants.LEAVE_TYPE_READ);
        registerEndpoint(apiBasePath + Endpoint.LEAVE_TYPE, "POST", ScopeConstants.LEAVE_MANAGEMENT_WRITE);
        registerEndpoint(apiBasePath + Endpoint.LEAVE_TYPE, "PUT", ScopeConstants.LEAVE_MANAGEMENT_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.LEAVE_TYPE, "DELETE", ScopeConstants.LEAVE_MANAGEMENT_DELETE);

        // Mentor Leaves
        registerEndpoint(apiBasePath + Endpoint.MENTOR_LEAVES, "GET", ScopeConstants.LEAVE_MENTOR_READ);
        registerEndpoint(apiBasePath + Endpoint.MENTOR_LEAVES_PATCH, "PATCH", ScopeConstants.LEAVE_MENTOR_APPROVE);

        // Bi-Weekly Questions
        registerEndpoint(apiBasePath + Endpoint.BIWEEKLY_QUESTION, "GET", ScopeConstants.BI_WEEKLY_READ);
        registerEndpoint(apiBasePath + Endpoint.BIWEEKLY_QUESTION, "POST", ScopeConstants.BI_WEEKLY_WRITE);
        registerEndpoint(apiBasePath + Endpoint.BIWEEKLY_QUESTION, "PUT", ScopeConstants.BI_WEEKLY_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.BIWEEKLY_QUESTION, "DELETE", ScopeConstants.BI_WEEKLY_DELETE);
        registerEndpoint(apiBasePath + Endpoint.BIWEEKLY_QUESTION, "PATCH", ScopeConstants.BI_WEEKLY_APPROVE);

        // Trainee Bi-Weekly Questions
        registerEndpoint(apiBasePath + Endpoint.TRAINEE_BIWEEKLY_QUESTION, "GET", ScopeConstants.BI_WEEKLY_TRAINEE_READ);

        // Mentor Bi-Weekly Reports
        registerEndpoint(apiBasePath + Endpoint.MENTOR_BIWEEKLY_REPORT, "GET", ScopeConstants.BI_WEEKLY_MENTOR_READ);
        registerEndpoint(apiBasePath + Endpoint.MENTOR_BIWEEKLY_REPORT, "PATCH", ScopeConstants.BI_WEEKLY_MENTOR_APPROVE);

        // Trainee Bi-Weekly Reports
        registerEndpoint(apiBasePath + Endpoint.TRAINEE_BIWEEKLY_REPORT, "GET", ScopeConstants.BI_WEEKLY_TRAINEE_READ);
        registerEndpoint(apiBasePath + Endpoint.TRAINEE_BIWEEKLY_REPORT, "POST", ScopeConstants.BI_WEEKLY_TRAINEE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.TRAINEE_BIWEEKLY_REPORT, "PUT", ScopeConstants.BI_WEEKLY_TRAINEE_UPDATE);

        // Emergency Trainee
        registerEndpoint(apiBasePath + Endpoint.EMERGENCY_TRAINEE, "GET", ScopeConstants.PROFILE_READ);
        registerEndpoint(apiBasePath + Endpoint.EMERGENCY_TRAINEE, "POST", ScopeConstants.PROFILE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.EMERGENCY_TRAINEE, "PUT", ScopeConstants.PROFILE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.EMERGENCY_TRAINEE, "DELETE", ScopeConstants.PROFILE_DELETE);

        // Holidays
        registerEndpoint(apiBasePath + Endpoint.HOLIDAYS, "GET", ScopeConstants.HOLIDAY_READ);
        registerEndpoint(apiBasePath + Endpoint.HOLIDAYS, "POST", ScopeConstants.HOLIDAY_WRITE);
        registerEndpoint(apiBasePath + Endpoint.HOLIDAYS, "PUT", ScopeConstants.HOLIDAY_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.HOLIDAYS, "DELETE", ScopeConstants.HOLIDAY_DELETE);

        // Identification Trainee
        registerEndpoint(apiBasePath + Endpoint.IDENTIFICATION_TRAINEE, "GET", ScopeConstants.PROFILE_READ);
        registerEndpoint(apiBasePath + Endpoint.IDENTIFICATION_TRAINEE, "POST", ScopeConstants.PROFILE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.IDENTIFICATION_TRAINEE, "PUT", ScopeConstants.PROFILE_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.IDENTIFICATION_TRAINEE, "DELETE", ScopeConstants.PROFILE_DELETE);

        // Leave Employee
        registerEndpoint(apiBasePath + Endpoint.LEAVE_EMPLOYEE, "GET", ScopeConstants.LEAVE_MANAGEMENT_READ);
        registerEndpoint(apiBasePath + Endpoint.LEAVE_EMPLOYEE, "POST", ScopeConstants.LEAVE_MANAGEMENT_WRITE);
        registerEndpoint(apiBasePath + Endpoint.LEAVE_EMPLOYEE, "DELETE", ScopeConstants.LEAVE_MANAGEMENT_VOID);

        // Mentor Leaves (alternate endpoint)
        registerEndpoint(apiBasePath + Endpoint.MENTOR_LEAVES, "GET", ScopeConstants.LEAVE_MENTOR_READ);
        registerEndpoint(apiBasePath + Endpoint.MENTOR_LEAVES, "PATCH", ScopeConstants.LEAVE_MENTOR_APPROVE);

        // Notification
        registerEndpoint(apiBasePath + Endpoint.NOTIFICATION, "GET", ScopeConstants.NOTIFICATION_READ);
        registerEndpoint(apiBasePath + Endpoint.NOTIFICATION, "PUT", ScopeConstants.NOTIFICATION_WRITE);
        registerEndpoint(apiBasePath + Endpoint.NOTIFICATION, "PATCH", ScopeConstants.NOTIFICATION_WRITE);

        // Report
        registerEndpoint(apiBasePath + Endpoint.REPORT, "GET", ScopeConstants.REPORT_READ);

        // Statuses
        registerEndpoint(apiBasePath + Endpoint.STATUSES, "GET", ScopeConstants.CHANGE_STATUS_READ);
        registerEndpoint(apiBasePath + Endpoint.STATUSES, "POST", ScopeConstants.CHANGE_STATUS_WRITE);
        registerEndpoint(apiBasePath + Endpoint.STATUSES, "PUT", ScopeConstants.CHANGE_STATUS_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.STATUSES, "DELETE", ScopeConstants.CHANGE_STATUS_DELETE);

        // Trainee Leave
        registerEndpoint(apiBasePath + Endpoint.TRAINEE_LEAVE, "GET", ScopeConstants.LEAVE_TRAINEE_READ);
        registerEndpoint(apiBasePath + Endpoint.TRAINEE_LEAVE, "POST", ScopeConstants.LEAVE_TRAINEE_WRITE);
        registerEndpoint(apiBasePath + Endpoint.TRAINEE_LEAVE, "DELETE", ScopeConstants.LEAVE_TRAINEE_VOID);

        // Training
        registerEndpoint(apiBasePath + Endpoint.TRAINING, "GET", ScopeConstants.TRAINING_READ);
        registerEndpoint(apiBasePath + Endpoint.TRAINING, "POST", ScopeConstants.TRAINING_WRITE);
        registerEndpoint(apiBasePath + Endpoint.TRAINING, "PUT", ScopeConstants.TRAINING_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.TRAINING, "PATCH", ScopeConstants.TRAINING_TRAINEE_APPROVE);

        // Mentor Training
        registerEndpoint(apiBasePath + Endpoint.MENTOR_TRAINING, "GET", ScopeConstants.TRAINING_MENTOR_READ);

        // Working Schedule
        registerEndpoint(apiBasePath + Endpoint.WORKING_SCHEDULE, "GET", ScopeConstants.ATTENDANCE_LOG_READ);
        registerEndpoint(apiBasePath + Endpoint.WORKING_SCHEDULE, "POST", ScopeConstants.ATTENDANCE_LOG_WRITE);
        registerEndpoint(apiBasePath + Endpoint.WORKING_SCHEDULE, "PUT", ScopeConstants.ATTENDANCE_LOG_UPDATE);
        registerEndpoint(apiBasePath + Endpoint.WORKING_SCHEDULE, "DELETE", ScopeConstants.ATTENDANCE_LOG_DELETE);

        // Attendances
        registerEndpoint(apiBasePath + Endpoint.ATTENDANCES, "GET", ScopeConstants.ATTENDANCE_LOG_READ);
        registerEndpoint(apiBasePath + Endpoint.ATTENDANCES, "PATCH", ScopeConstants.ATTENDANCE_LOG_APPROVE);

        // Mentor Attendances
        registerEndpoint(apiBasePath + Endpoint.MENTOR_ATTENDANCES, "GET", ScopeConstants.ATTENDANCE_LOG_MENTOR_READ);
        registerEndpoint(apiBasePath + Endpoint.MENTOR_ATTENDANCES, "PATCH", ScopeConstants.ATTENDANCE_LOG_MENTOR_APPROVE);

        // Trainee Attendances
        registerEndpoint(apiBasePath + Endpoint.TRAINEE_ATTENDANCES, "GET", ScopeConstants.ATTENDANCE_LOG_TRAINEE_READ);
        registerEndpoint(apiBasePath + Endpoint.TRAINEE_ATTENDANCES, "POST", ScopeConstants.ATTENDANCE_LOG_TRAINEE_WRITE);

        // E-Learning Endpoints
        registerEndpoint(apiBasePath + Endpoint.COURSES, "GET", ScopeConstants.USER_READ);
        registerEndpoint(apiBasePath + Endpoint.COURSES, "POST", ScopeConstants.USER_WRITE);

        registerEndpoint(apiBasePath + Endpoint.COURSE_VIEW_CONTENT, "GET", ScopeConstants.USER_READ);

        registerEndpoint(apiBasePath + Endpoint.COURSE_ASSESSMENT, "GET", ScopeConstants.USER_READ);
        registerEndpoint(apiBasePath + Endpoint.COURSE_SUBMIT_ASSESSMENT, "POST", ScopeConstants.USER_WRITE);

        registerEndpoint(apiBasePath + Endpoint.LEARNING_HISTORY, "GET", ScopeConstants.LEARNING_HISTORY_READ);
        registerEndpoint(apiBasePath + Endpoint.OTHER_COURSES, "GET", ScopeConstants.OTHER_COURSES_READ);

        registerEndpoint(apiBasePath + Endpoint.ASSIGNS, "GET", ScopeConstants.USER_READ);
        registerEndpoint(apiBasePath + Endpoint.ASSIGNS, "POST", ScopeConstants.USER_WRITE);
        registerEndpoint(apiBasePath + Endpoint.ASSIGNS, "DELETE", ScopeConstants.USER_DELETE);

        registerEndpoint(apiBasePath + Endpoint.LEARNING, "GET", ScopeConstants.USER_READ);
        registerEndpoint(apiBasePath + Endpoint.LEARNING, "POST", ScopeConstants.USER_WRITE);
        registerEndpoint(apiBasePath + Endpoint.LEARNING, "PATCH", ScopeConstants.USER_UPDATE);

        log.info("Endpoint scope registry initialized with {} endpoint mappings", endpointScopes.size());
    }

    private void registerEndpoint(String endpoint, String httpMethod, String requiredScope) {
        endpointScopes.computeIfAbsent(endpoint, k -> new HashMap<>())
                .put(httpMethod, requiredScope);
    }

    public String getRequiredScope(String endpoint, String httpMethod) {
        log.debug("Looking up scope for {} {}", httpMethod, endpoint);

        // First try exact match
        Map<String, String> methodScopes = endpointScopes.get(endpoint);
        if (methodScopes != null) {
            String scope = methodScopes.get(httpMethod);
            if (scope != null) {
                log.debug("Found exact scope match: {}", scope);
                return scope;
            }
        }

        // Try pattern matching for wildcard endpoints
        for (Map.Entry<String, Map<String, String>> entry : endpointScopes.entrySet()) {
            if (matchesPattern(endpoint, entry.getKey())) {
                String scope = entry.getValue().get(httpMethod);
                if (scope != null) {
                    log.debug("Found pattern scope match: {}", scope);
                    return scope;
                }
            }
        }

        log.debug("No scope found for {} {}", httpMethod, endpoint);
        return null;
    }

    public boolean isEndpointRegistered(String endpoint) {
        if (endpointScopes.containsKey(endpoint)) {
            return true;
        }

        // Check pattern matching for wildcard endpoints
        return endpointScopes.keySet().stream()
                .anyMatch(pattern -> matchesPattern(endpoint, pattern));
    }

    private boolean matchesPattern(String actualPath, String pattern) {
        if (pattern.endsWith("/**")) {
            String base = pattern.substring(0, pattern.length() - 3);
            return actualPath.startsWith(base);
        }
        return actualPath.equals(pattern);
    }
}