package BTEC.ASM.project.modules.enrollment.dto.response;

import java.time.LocalDateTime;

public class EnrollmentResponse {
    private Long id;

    private String studentCode;
    private String studentName;

    private String subjectCode;
    private String subjectName;

    private String termCode;
    private String termName;

    private String classGroupName;
    private String classGroupCode;

    private String enrollStatus;

    private LocalDateTime createdAt;

}