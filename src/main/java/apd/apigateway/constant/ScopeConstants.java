package apd.apigateway.constant;

/**
 * Defines all scope constants for the application
 * These match the scopes in your JWT token example
 */
public final class ScopeConstants {

    private ScopeConstants() {}

    // ==========================
    // User Management Scopes
    // ==========================
    public static final String USER_READ = "user.read";
    public static final String USER_WRITE = "user.write";
    public static final String USER_UPDATE = "user.update";
    public static final String USER_DELETE = "user.delete";

    // ==========================
    // Role Management Scopes
    // ==========================
    public static final String ROLE_READ = "role.read";
    public static final String ROLE_WRITE = "role.write";
    public static final String ROLE_UPDATE = "role.update";
    public static final String ROLE_DELETE = "role.delete";

    // ==========================
    // Profile Management Scopes
    // ==========================
    public static final String PROFILE_READ = "profile.read";
    public static final String PROFILE_WRITE = "profile.write";
    public static final String PROFILE_UPDATE = "profile.update";
    public static final String PROFILE_DELETE = "profile.delete";
    public static final String PROFILE_TRAINEE_READ = "profile-trainee.read";

    // ==========================
    // Change Status Scopes
    // ==========================
    public static final String CHANGE_STATUS_READ = "change-status.read";
    public static final String CHANGE_STATUS_WRITE = "change-status.write";
    public static final String CHANGE_STATUS_UPDATE = "change-status.update";
    public static final String CHANGE_STATUS_DELETE = "change-status.delete";

    // ==========================
    // Attendance Log Scopes
    // ==========================
    public static final String ATTENDANCE_LOG_READ = "attendance-log.read";
    public static final String ATTENDANCE_LOG_WRITE = "attendance-log.write";
    public static final String ATTENDANCE_LOG_UPDATE = "attendance-log.update";
    public static final String ATTENDANCE_LOG_APPROVE = "attendance-log.approve";
    public static final String ATTENDANCE_LOG_DELETE = "attendance-log.delete";

    public static final String ATTENDANCE_LOG_MENTOR_READ = "attendance-log-mentor.read";
    public static final String ATTENDANCE_LOG_MENTOR_APPROVE = "attendance-log-mentor.approve";

    public static final String ATTENDANCE_LOG_TRAINEE_READ = "attendance-log-trainee.read";
    public static final String ATTENDANCE_LOG_TRAINEE_WRITE = "attendance-log-trainee.write";

    // ==========================
    // Leave Management Scopes
    // ==========================
    public static final String LEAVE_MANAGEMENT_READ = "leave-management.read";
    public static final String LEAVE_MANAGEMENT_WRITE = "leave-management.write";
    public static final String LEAVE_MANAGEMENT_UPDATE = "leave-management.update";
    public static final String LEAVE_MANAGEMENT_APPROVE = "leave-management.approve";
    public static final String LEAVE_MANAGEMENT_DELETE = "leave-management.delete";
    public static final String LEAVE_MANAGEMENT_VOID = "leave-management.void";

    public static final String LEAVE_MENTOR_READ = "leave-mentor.read";
    public static final String LEAVE_MENTOR_APPROVE = "leave-mentor.approve";

    public static final String LEAVE_TRAINEE_READ = "leave-trainee.read";
    public static final String LEAVE_TRAINEE_WRITE = "leave-trainee.write";
    public static final String LEAVE_TRAINEE_VOID = "leave-trainee.void";

    public static final String LEAVE_TYPE_READ = "leave-type.read";

    // ==========================
    // Bi-Weekly Report Scopes
    // ==========================
    public static final String BI_WEEKLY_READ = "bi-weekly.read";
    public static final String BI_WEEKLY_WRITE = "bi-weekly.write";
    public static final String BI_WEEKLY_UPDATE = "bi-weekly.update";
    public static final String BI_WEEKLY_APPROVE = "bi-weekly.approve";
    public static final String BI_WEEKLY_DELETE = "bi-weekly.delete";

    public static final String BI_WEEKLY_QUESTION_READ = "bi-weekly-question.read";

    public static final String BI_WEEKLY_MENTOR_READ = "bi-weekly-mentor.read";
    public static final String BI_WEEKLY_MENTOR_APPROVE = "bi-weekly-mentor.approve";

    public static final String BI_WEEKLY_TRAINEE_READ = "bi-weekly-trainee.read";
    public static final String BI_WEEKLY_TRAINEE_WRITE = "bi-weekly-trainee.write";
    public static final String BI_WEEKLY_TRAINEE_UPDATE = "bi-weekly-trainee.update";

    // ==========================
    // Training Scopes
    // ==========================
    public static final String TRAINING_READ = "training.read";
    public static final String TRAINING_WRITE = "training.write";
    public static final String TRAINING_UPDATE = "training.update";
    public static final String TRAINING_DELETE = "training.delete";

    public static final String TRAINING_MENTOR_READ = "training-mentor.read";
    public static final String TRAINING_MENTOR_APPROVE = "training-mentor.approve";

    public static final String TRAINING_TRAINEE_READ = "training-trainee.read";
    public static final String TRAINING_TRAINEE_APPROVE = "training-trainee.approve";

    // ==========================
    // Report Scopes
    // ==========================
    public static final String REPORT_READ = "report.read";

    // ==========================
    // Holiday Scopes
    // ==========================
    public static final String HOLIDAY_READ = "holiday.read";
    public static final String HOLIDAY_WRITE = "holiday.write";
    public static final String HOLIDAY_UPDATE = "holiday.update";
    public static final String HOLIDAY_DELETE = "holiday.delete";

    // ==========================
    // Notification Scopes
    // ==========================
    public static final String NOTIFICATION_READ = "notification.read";
    public static final String NOTIFICATION_WRITE = "notification.write";

    // ==========================
    // Account Scopes
    // ==========================
    public static final String ACCOUNT_READ = "account.read";
    public static final String ACCOUNT_WRITE = "account.write";
    public static final String ACCOUNT_UPDATE = "account.update";

    // ==========================
    // Permission Scopes
    // ==========================
    public static final String PERMISSION_READ = "permission.read";

    // ==========================
    // Setting Scopes
    // ==========================
    public static final String SETTING_READ = "setting.read";
    public static final String SETTING_WRITE = "setting.write";
    public static final String SETTING_UPDATE = "setting.update";
    public static final String SETTING_DELETE = "setting.delete";


    // ==========================
    // E-Learning Scopes
    // ==========================
    public static final String COURSE_READ = "course.read";
    public static final String COURSE_WRITE = "course.write";
    public static final String COURSE_UPDATE = "course.update";
    public static final String COURSE_DELETE = "course.delete";
    public static final String COURSE_VIEW = "course.view";

    public static final String LEARNING_HISTORY_READ = "learning-history.read";
    public static final String OTHER_COURSES_READ = "other-courses.read";

    public static final String LEARNING_WRITE = "learning.write";
    public static final String LEARNING_UPDATE = "learning.update";

    public static final String ASSESSMENT_READ = "assessment.read";
    public static final String ASSESSMENT_SUBMIT = "assessment.submit";

    public static final String ASSIGN_READ = "assign.read";
    public static final String ASSIGN_WRITE = "assign.write";
    public static final String ASSIGN_DELETE = "assign.delete";
}