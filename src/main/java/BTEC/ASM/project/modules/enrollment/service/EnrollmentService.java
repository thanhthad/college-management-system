package BTEC.ASM.project.modules.enrollment.service;

import BTEC.ASM.project.modules.enrollment.dto.request.EnrollmentRequest;
import BTEC.ASM.project.modules.enrollment.dto.request.EnrollmentUpdateRequest;
import BTEC.ASM.project.modules.enrollment.dto.response.EnrollmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnrollmentService {
    EnrollmentResponse create(EnrollmentRequest enrollmentRequest, String ip);

    EnrollmentResponse getById(Long id,String ip);

    Page<EnrollmentResponse> getAll(Pageable pageable, String ip);

    EnrollmentResponse update(Long id, EnrollmentUpdateRequest enrollmentUpdateRequest, String ip);

    void delete(Long id, String ip);
}
