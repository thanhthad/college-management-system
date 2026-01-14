package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.request.TermRequest;
import BTEC.ASM.project.modules.academic.dto.response.TermResponse;

import java.util.List;
import java.util.Optional;

public interface TermService {

    TermResponse create(TermRequest request);

    List<TermResponse> getAll();

    TermResponse getById(Long id);

    TermResponse update(Long id, TermRequest request);

    void delete(Long id);
}
