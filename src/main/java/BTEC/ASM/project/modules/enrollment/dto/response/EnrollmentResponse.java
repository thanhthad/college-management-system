package BTEC.ASM.project.modules.enrollment.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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