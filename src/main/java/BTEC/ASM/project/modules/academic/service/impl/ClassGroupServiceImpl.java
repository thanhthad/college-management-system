package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.ClassGroupRequest;
import BTEC.ASM.project.modules.academic.dto.response.ClassGroupResponse;
import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import BTEC.ASM.project.modules.academic.mapper.ClassGroupMapper;
import BTEC.ASM.project.modules.academic.repository.ClassGroupRepository;
import BTEC.ASM.project.modules.academic.service.ClassGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassGroupServiceImpl implements ClassGroupService {

    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupMapper classGroupMapper;

    @Override
    public ClassGroupResponse create(ClassGroupRequest request) {
        ClassGroup entity = classGroupMapper.toEntity(request);
        classGroupRepository.save(entity);
        return classGroupMapper.toResponse(entity);
    }

    @Override
    public List<ClassGroupResponse> getAll() {
        return classGroupRepository.findAll().stream()
                .map(classGroupMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClassGroupResponse> getById(Long id) {
        return classGroupRepository.findById(id)
                .map(classGroupMapper::toResponse);
    }

    @Override
    public Optional<ClassGroupResponse> update(Long id, ClassGroupRequest request) {
        return classGroupRepository.findById(id)
                .map(existing -> {
                    classGroupMapper.updateClassGroupFromRequest(request, existing);
                    classGroupRepository.save(existing);
                    return classGroupMapper.toResponse(existing);
                });
    }

    @Override
    public boolean delete(Long id) {
        if(classGroupRepository.existsById(id)){
            classGroupRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
