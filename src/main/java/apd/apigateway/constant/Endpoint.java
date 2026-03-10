package apd.apigateway.constant;

public final class Endpoint {

    private Endpoint() {}

    // ==========================
    // Authentication
    // ==========================
    public static final String AUTH_LOGIN = "/auth/login";
    public static final String AUTH_LOGOUT = "/auth/logout";
    public static final String AUTH_REFRESH_ACCESS = "/auth/refreshAccessToken";
    public static final String AUTH_DETAIL = "/auth/detail";

    // ==========================
    // User Management
    // ==========================
    public static final String USER = "/user/**";
    public static final String USER_ACCOUNT = "/account/**";
    public static final String USER_ACCOUNT_STATUS_CHANGE = "/account/change-status/**";

    // ==========================
    // Roles & Permissions
    // ==========================
    public static final String ROLES = "/roles/**";
    public static final String PERMISSIONS = "/permissions/**";

    // ==========================
    // Profile Management
    // ==========================
    public static final String PROFILE_TRAINEE = "/profile-trainee/**";
    public static final String UPLOAD_PROFILE_TRAINEE = "/upload/profile-trainee";
    public static final String IDENTIFICATION_TRAINEE = "/identification-trainee/**";
    public static final String EMERGENCY_TRAINEE = "/emergency-trainee/**";
    public static final String PROFILE_MENTOR = "/profile-mentor/**";
    public static final String PROFILE_MENTOR_TRAINEES = "/profile/mentor/trainees";
    public static final String PROFILE_TALENT_DEV = "/profile-talent-dev/**";
    public static final String UPLOAD_PROFILE_TALENT_DEV = "/upload/profile-talent-dev";

    // ==========================
    // Organization Management
    // ==========================
    public static final String UNITS = "/units/**";
    public static final String SITES = "/sites/**";
    public static final String DEPARTMENTS = "/departments/**";
    public static final String DESIGNATION = "/designation/**";

    // ==========================
    // Status Management
    // ==========================
    public static final String STATUSES = "/statuses/**";

    // ==========================
    // Leave Management
    // ==========================
    public static final String LEAVE_ENTITLEMENT = "/leave-entitlement/**";
    public static final String LEAVE_TYPE = "/leave-type/**";
    public static final String LEAVE_EMPLOYEE = "/leave-employee/**";
    public static final String LEAVE_EMPLOYEE_DOWNLOAD = "/leave-employee/download-attachment/**";

    public static final String MENTOR_LEAVES = "/mentor/leaves";
    public static final String MENTOR_LEAVES_PATCH = "/mentor/leaves/**";

    public static final String TRAINEE_LEAVE = "/trainee/leaves/**";
    public static final String TRAINEE_LEAVE_UPLOAD = "/trainee/leaves/upload-attachment/**";

    // ==========================
    // Holiday Management
    // ==========================
    public static final String HOLIDAYS = "/holidays/**";

    // ==========================
    // Attendance Management
    // ==========================
    public static final String ATTENDANCES = "/attendances/**";
    public static final String WORKING_SCHEDULE = "/working-schedule/**";

    public static final String MENTOR_ATTENDANCES = "/mentor/attendances/**";
    public static final String TRAINEE_ATTENDANCES = "/trainee/attendances/**";

    // ==========================
    // Bi-Weekly Report Management
    // ==========================
    public static final String BIWEEKLY_QUESTION = "/biweekly-question/**";
    public static final String BIWEEKLY_REPORT = "/biweekly-report/**";

    public static final String MENTOR_BIWEEKLY_REPORT = "/mentor/biweekly-report/**";

    public static final String TRAINEE_BIWEEKLY_QUESTION = "/trainee/biweekly-question";
    public static final String TRAINEE_BIWEEKLY_REPORT = "/trainee/biweekly-report/**";

    // ==========================
    // Training Management
    // ==========================
    public static final String TRAINING = "/training/**";
    public static final String MENTOR_TRAINING = "/mentor/training/**";
    public static final String TRAINEE_TRAINING = "/trainee/training/**";

    // ==========================
    // Notification & Report Management
    // ==========================
    public static final String NOTIFICATION = "/notification/**";
    public static final String REPORT = "/report/**";

    // ==========================
    // E-Learning Service Endpoints
    // ==========================
    public static final String COURSES = "/courses";
    public static final String COURSE_BY_ID = "/courses/{id}";
    public static final String COURSE_VIEW_CONTENT = "/courses/viewContent/{courseId}";
    public static final String COURSE_DEPARTMENT = "/courses/department";
    public static final String COURSE_HISTORY = "/courses-history";
    public static final String COURSE_ASSESSMENT = "/courses/{courseId}/assessment";
    public static final String COURSE_ASSESSMENT_ANSWERS = "/courses/{courseId}/assessment/answers";
    public static final String COURSE_SECTIONS = "/courses/{courseId}/contents/{contentId}/sections/{sectionId}";
    public static final String COURSE_DUPLICATE = "/courses/{sourceCourseId}/duplicate";
    public static final String COURSE_SUBMIT_ASSESSMENT = "/courses/{courseId}/assessment/submit";
    public static final String COURSE_ENABLED = "/courses/{courseId}/enabled";
    public static final String COURSE_UPDATE = "/courses/{courseId}";
    public static final String COURSE_ACKNOWLEDGE = "/courses/acknowledge/{courseId}";

    public static final String LEARNING_HISTORY = "/learningHistory";
    public static final String OTHER_COURSES = "/otherCourses";
    public static final String ASSIGNS = "/assigns/**";
    public static final String LEARNING = "/learning/**";
    public static final String FILES = "/files/**";

    // ==========================
    // Public / Monitoring
    // ==========================
    public static final String ACTUATOR = "/actuator/**";
    public static final String HEALTH = "/health";
    public static final String INFO = "/info";

    // ==========================
    // Swagger
    // ==========================
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String API_DOCS = "/v3/api-docs/**";
    public static final String SWAGGER_RESOURCES = "/swagger-resources/**";
    public static final String WEBJARS = "/webjars/**";
}













//package apd.apigateway.constant;
//
//public final class Endpoint {
//
//    private Endpoint() {}
//
//    // ==========================
//    // API Version Prefix
//    // ==========================
//    private static final String API_PREFIX = "/api/v3.0.0/";
//
//    // ==========================
//    // Authentication
//    // ==========================
//    public static final String AUTH_LOGIN = API_PREFIX + "auth/login";
//    public static final String AUTH_LOGOUT = API_PREFIX + "auth/logout";
//    public static final String AUTH_REFRESH_ACCESS = API_PREFIX + "auth/refreshAccessToken";
//    public static final String AUTH_DETAIL = API_PREFIX + "auth/detail";
//
//    // ==========================
//    // User Management
//    // ==========================
//    public static final String USER = API_PREFIX + "users/**";
//    public static final String USER_ACCOUNT = API_PREFIX + "account/**";
//    public static final String USER_ACCOUNT_STATUS_CHANGE = API_PREFIX + "account/change-status/**";
//
//    // ==========================
//    // Roles & Permissions
//    // ==========================
//    public static final String ROLES = API_PREFIX + "roles/**";
//    public static final String PERMISSIONS = API_PREFIX + "permissions/**";
//
//    // ==========================
//    // Profile Management (TMS)
//    // ==========================
//    public static final String PROFILE_TRAINEE = API_PREFIX + "profile-trainee/**";
//    public static final String UPLOAD_PROFILE_TRAINEE = API_PREFIX + "upload/profile-trainee";
//    public static final String IDENTIFICATION_TRAINEE = API_PREFIX + "identification-trainee/**";
//    public static final String EMERGENCY_TRAINEE = API_PREFIX + "emergency-trainee/**";
//    public static final String PROFILE_MENTOR = API_PREFIX + "profile-mentor/**";
//    public static final String PROFILE_MENTOR_TRAINEES = API_PREFIX + "profile/mentor/trainees";
//    public static final String PROFILE_TALENT_DEV = API_PREFIX + "profile-talent-dev/**";
//    public static final String UPLOAD_PROFILE_TALENT_DEV = API_PREFIX + "upload/profile-talent-dev";
//
//    // ==========================
//    // Organization Management (TMS)
//    // ==========================
//    public static final String UNITS = API_PREFIX + "units/**";
//    public static final String SITES = API_PREFIX + "sites/**";
//    public static final String DEPARTMENTS = API_PREFIX + "departments/**";
//    public static final String DESIGNATION = API_PREFIX + "designation/**";
//
//    // ==========================
//    // Status Management (TMS)
//    // ==========================
//    public static final String STATUSES = API_PREFIX + "statuses/**";
//
//    // ==========================
//    // Leave Management (TMS)
//    // ==========================
//    public static final String LEAVE_ENTITLEMENT = API_PREFIX + "leave-entitlement/**";
//    public static final String LEAVE_TYPE = API_PREFIX + "leave-type/**";
//    public static final String LEAVE_EMPLOYEE = API_PREFIX + "leave-employee/**";
//    public static final String LEAVE_EMPLOYEE_DOWNLOAD = API_PREFIX + "leave-employee/download-attachment/**";
//
//    // Mentor Leave Endpoints
//    public static final String MENTOR_LEAVES = API_PREFIX + "mentor/leaves";
//    public static final String MENTOR_LEAVES_MANAGEMENT = API_PREFIX + "mentor/leaves/**";
//
//    // Trainee Leave Endpoints
//    public static final String TRAINEE_LEAVE = API_PREFIX + "trainee/leaves/**";
//    public static final String TRAINEE_LEAVE_UPLOAD = API_PREFIX + "trainee/leaves/upload-attachment/**";
//
//    // ==========================
//    // Holiday Management (TMS)
//    // ==========================
//    public static final String HOLIDAYS = API_PREFIX + "holidays/**";
//
//    // ==========================
//    // Attendance Management (TMS)
//    // ==========================
//    public static final String ATTENDANCES = API_PREFIX + "attendances/**";
//    public static final String WORKING_SCHEDULE = API_PREFIX + "working-schedule/**";
//
//    // Mentor Attendance
//    public static final String MENTOR_ATTENDANCES = API_PREFIX + "mentor/attendances/**";
//
//    // Trainee Attendance
//    public static final String TRAINEE_ATTENDANCES = API_PREFIX + "trainee/attendances/**";
//
//    // ==========================
//    // Bi-Weekly Report Management (TMS)
//    // ==========================
//    public static final String BIWEEKLY_QUESTION = API_PREFIX + "biweekly-question/**";
//    public static final String BIWEEKLY_REPORT = API_PREFIX + "biweekly-report/**";
//
//    // Mentor Bi-Weekly
//    public static final String MENTOR_BIWEEKLY_REPORT = API_PREFIX + "mentor/biweekly-report/**";
//
//    // Trainee Bi-Weekly
//    public static final String TRAINEE_BIWEEKLY_QUESTION = API_PREFIX + "trainee/biweekly-question";
//    public static final String TRAINEE_BIWEEKLY_REPORT = API_PREFIX + "trainee/biweekly-report/**";
//
//    // ==========================
//    // Training Management (TMS)
//    // ==========================
//    public static final String TRAINING = API_PREFIX + "training/**";
//
//    // Mentor Training
//    public static final String MENTOR_TRAINING = API_PREFIX + "mentor/training/**";
//
//    // Trainee Training
//    public static final String TRAINEE_TRAINING = API_PREFIX + "trainee/training/**";
//
//    // ==========================
//    // Notification Management (TMS)
//    // ==========================
//    public static final String NOTIFICATION = API_PREFIX + "notification/**";
//
//    // ==========================
//    // Report Management (TMS)
//    // ==========================
//    public static final String REPORT = API_PREFIX + "report/**";
//
//    // ==========================
//    // E-Learning Service Endpoints
//    // ==========================
//    public static final String COURSES = API_PREFIX + "courses";
//    public static final String COURSE_BY_ID = API_PREFIX + "courses/{id}";
//    public static final String COURSE_VIEW_CONTENT = API_PREFIX + "courses/viewContent/{courseId}";
//    public static final String COURSE_DEPARTMENT = API_PREFIX + "courses/department";
//    public static final String COURSE_HISTORY = API_PREFIX + "courses-history";
//    public static final String COURSE_ASSESSMENT = API_PREFIX + "courses/{courseId}/assessment";
//    public static final String COURSE_ASSESSMENT_ANSWERS = API_PREFIX + "courses/{courseId}/assessment/answers";
//    public static final String COURSE_SECTIONS = API_PREFIX + "courses/{courseId}/contents/{contentId}/sections/{sectionId}";
//    public static final String COURSE_DUPLICATE = API_PREFIX + "courses/{sourceCourseId}/duplicate";
//    public static final String COURSE_SUBMIT_ASSESSMENT = API_PREFIX + "courses/{courseId}/assessment/submit";
//    public static final String COURSE_ENABLED = API_PREFIX + "courses/{courseId}/enabled";
//    public static final String COURSE_UPDATE = API_PREFIX + "courses/{courseId}";
//    public static final String COURSE_ACKNOWLEDGE = API_PREFIX + "courses/acknowledge/{courseId}";
//
//    public static final String LEARNING_HISTORY = API_PREFIX + "learningHistory";
//    public static final String OTHER_COURSES = API_PREFIX + "otherCourses";
//    public static final String ASSIGNS = API_PREFIX + "assigns/**";
//    public static final String LEARNING = API_PREFIX + "learning/**";
//    public static final String FILES = API_PREFIX + "files/**";
//
//    // ==========================
//    // Public / Monitoring
//    // ==========================
//    public static final String ACTUATOR = "/actuator/**";
//    public static final String HEALTH = "/health";
//    public static final String INFO = "/info";
//
//    // ==========================
//    // Swagger
//    // ==========================
//    public static final String SWAGGER_UI = "/swagger-ui/**";
//    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
//    public static final String API_DOCS = "/v3/api-docs/**";
//    public static final String SWAGGER_RESOURCES = "/swagger-resources/**";
//    public static final String WEBJARS = "/webjars/**";
//
//    // Swagger Service Specific
//    public static final String SWAGGER_TMS = "/v3/api-docs/tms";
//    public static final String SWAGGER_USER = "/v3/api-docs/user";
//    public static final String SWAGGER_ELEARNING = "/v3/api-docs/elearning";
//}