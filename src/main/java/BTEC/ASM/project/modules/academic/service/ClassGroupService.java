package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.request.ClassGroupRequest;
import BTEC.ASM.project.modules.academic.dto.response.ClassGroupResponse;

import java.util.List;
import java.util.Optional;

public interface ClassGroupService {

    ClassGroupResponse create(ClassGroupRequest request);

    List<ClassGroupResponse> getAll();

    ClassGroupResponse getById(Long id);

    ClassGroupResponse update(Long id, ClassGroupRequest request);

    void delete(Long id);
}
