package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.request.SubjectRequest;
import BTEC.ASM.project.modules.academic.dto.response.SubjectResponse;

import java.util.List;
import java.util.Optional;

public interface SubjectService {

    SubjectResponse create(SubjectRequest request);

    List<SubjectResponse> getAll();

    SubjectResponse getById(Long id);

    SubjectResponse update(Long id, SubjectRequest request);

    void delete(Long id);
}
