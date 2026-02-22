package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.request.SubjectRequest;
import BTEC.ASM.project.modules.academic.dto.response.SubjectResponse;
import BTEC.ASM.project.modules.academic.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubjectService {

    SubjectResponse create(SubjectRequest request);

    Page<SubjectResponse> findAll(Pageable pageable);

    SubjectResponse findBySubjectCode(String subjectCode);

    Page<Subject> findBySubjectNameContainingIgnoreCase(
            String subjectName,
            Pageable pageable
    );

    SubjectResponse updateBySubjectCode(Long id, SubjectRequest request);

    void deleteBySubjectCode(Long id);
}
