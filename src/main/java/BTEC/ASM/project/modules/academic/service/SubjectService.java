package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.request.SubjectRequest;
import BTEC.ASM.project.modules.academic.dto.response.SubjectResponse;
import BTEC.ASM.project.modules.academic.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubjectService {

    SubjectResponse create(SubjectRequest request,String ip);

    Page<SubjectResponse> findAll(Pageable pageable,String ip);

    SubjectResponse findBySubjectCode(String subjectCode,String ip);

    Page<SubjectResponse> findBySubjectNameContainingIgnoreCase(
            String subjectName,
            Pageable pageable,String ip
    );

    SubjectResponse updateBySubjectCode(String subjectCode, SubjectRequest request,String ip);

    void deleteBySubjectCode(String subjectCode,String ip);
}
