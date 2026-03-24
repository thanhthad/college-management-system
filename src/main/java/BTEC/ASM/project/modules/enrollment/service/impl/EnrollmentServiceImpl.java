package BTEC.ASM.project.modules.enrollment.service.impl;

import BTEC.ASM.project.modules.academic.entity.Offering;
import BTEC.ASM.project.modules.academic.service.OfferingServiceDomain;
import BTEC.ASM.project.modules.enrollment.dto.request.EnrollmentRequest;
import BTEC.ASM.project.modules.enrollment.dto.request.EnrollmentUpdateRequest;
import BTEC.ASM.project.modules.enrollment.dto.response.EnrollmentResponse;
import BTEC.ASM.project.modules.enrollment.entity.Enrollment;
import BTEC.ASM.project.modules.enrollment.enums.EnrollmentStatus;
import BTEC.ASM.project.modules.enrollment.exception.EnrollmentAlreadyExistsException;
import BTEC.ASM.project.modules.enrollment.exception.EnrollmentNotFoundException;
import BTEC.ASM.project.modules.enrollment.mapper.EnrollmentMapper;
import BTEC.ASM.project.modules.enrollment.repository.EnrollmentRepository;
import BTEC.ASM.project.modules.enrollment.service.EnrollmentService;
import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.security.userdetails.CustomUserDetails;
import BTEC.ASM.project.modules.identity.service.UserServiceDomain;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Log4j2
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final UserServiceDomain userServiceDomain;
    private final OfferingServiceDomain offeringServiceDomain;

    private Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return null;
        }
        return userDetails.getId();
    }

    @Override
    @Transactional
    public EnrollmentResponse create(EnrollmentRequest request, String ip) {
        if (request.studentUserId() == null && request.userCode() == null) {
            throw new IllegalArgumentException("studentUserId or userCode must be provided");
        }
        User student;
        if (request.studentUserId() != null) {
            student = userServiceDomain.getByUserId(request.studentUserId());
        } else {
            student = userServiceDomain.getByUserCode(request.userCode());
        }
        Offering offering = offeringServiceDomain.getByOfferingId(request.offeringId());
        Enrollment enrollment = Enrollment.builder()
                .offering(offering)
                .student(student)
                .enrollStatus(EnrollmentStatus.ENROLLED)
                .createdAt(LocalDateTime.now())
                .build();

        if(enrollmentRepository.existsByStudentAndOffering(student,offering)){
            throw new EnrollmentAlreadyExistsException("Enrollment already exists");
        }
        Enrollment saved = enrollmentRepository.save(enrollment);

        log.info(
                "ENROLLMENT_EVENT | action=ENROLLMENT_CREATED | userId={} | studentId={} | offeringId={} | enrollmentId={} | ip={}",
                getUserId(),
                student.getId(),
                offering.getId(),
                saved.getId(),
                ip
        );
        return enrollmentMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public EnrollmentResponse getById(Long id, String ip) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(
                () -> new EnrollmentNotFoundException("Enrollment Not Found")
        );
        log.info(
                "ENROLLMENT_EVENT | action=ENROLLMENT_FETCHED | userId={} | enrollmentId={} | ip={}",
                getUserId(),
                id,
                ip
        );

        return enrollmentMapper.toResponse(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnrollmentResponse> getAll(Pageable pageable, String ip) {
        Page<Enrollment> page = enrollmentRepository.findAll(pageable);

        log.info(
                "ENROLLMENT_EVENT | action=ENROLLMENT_LIST_ALL | userId={}  | ip={}",
                getUserId(),
                ip
        );

        return page.map(enrollmentMapper::toResponse);
    }

    @Transactional
    @Override
    public EnrollmentResponse update(Long id , EnrollmentUpdateRequest request , String ip) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid enrollment id");
        }
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(
                () -> new EnrollmentNotFoundException("Enrollment Not Found")
        );
        if(enrollment.getEnrollStatus().equals(request.enrollStatus())){
            throw new IllegalArgumentException("Enrollment status is unchanged");
        }
        enrollment.setEnrollStatus(request.enrollStatus());
        log.info(
                "ENROLLMENT_EVENT | action=ENROLLMENT_UPDATE | userId={}  | ip={}",
                getUserId(),
                ip
        );
        Enrollment updated = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponse(updated);
    }

    @Transactional
    @Override
    public void delete(Long id, String ip) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid enrollment id");
        }
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(
                () -> new EnrollmentNotFoundException("Enrollment Not Found")
        );
        log.info(
                "ENROLLMENT_EVENT | action=ENROLLMENT_DELETE | userId={}  | ip={}",
                getUserId(),
                ip
        );
        if (enrollment.getEnrollStatus() == EnrollmentStatus.CANCELLED) {
            throw new IllegalStateException("Enrollment already cancelled");
        }
        enrollment.setEnrollStatus(EnrollmentStatus.CANCELLED);
    }
}
