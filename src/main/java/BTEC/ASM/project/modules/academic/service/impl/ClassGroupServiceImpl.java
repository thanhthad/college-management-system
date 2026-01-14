package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.ClassGroupRequest;
import BTEC.ASM.project.modules.academic.dto.response.ClassGroupResponse;
import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import BTEC.ASM.project.modules.academic.exception.ClassGroupAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.ClassGroupNotFoundException;
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
        if(classGroupRepository.findByGroupName(request.groupName()).isPresent()) {
            throw new ClassGroupAlreadyExistsException("Class Group Already Exists");
        }
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
    public ClassGroupResponse getById(Long id) {
        ClassGroup classGroup = classGroupRepository.findById(id).orElseThrow(
                () -> new ClassGroupNotFoundException("Class Group not found")
        );
        return classGroupMapper.toResponse(classGroup);
    }

    @Override
    public ClassGroupResponse update(Long id, ClassGroupRequest request) {
        ClassGroup classGroup = classGroupRepository.findById(id).orElseThrow(
                () -> new ClassGroupNotFoundException("Class Group not found")
        );
        classGroupMapper.updateClassGroupFromRequest(request,classGroup);
        return classGroupMapper.toResponse(classGroupRepository.save(classGroup));
    }

    @Override
    public void delete(Long id) {
        ClassGroup classGroup = classGroupRepository.findById(id).orElseThrow(
                ()-> new ClassGroupNotFoundException("Class Group not found")
        );
        classGroupRepository.deleteById(id);
    }
}
