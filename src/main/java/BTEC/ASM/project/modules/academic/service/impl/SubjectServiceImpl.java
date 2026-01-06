package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.SubjectRequest;
import BTEC.ASM.project.modules.academic.dto.response.SubjectResponse;
import BTEC.ASM.project.modules.academic.entity.Subject;
import BTEC.ASM.project.modules.academic.mapper.SubjectMapper;
import BTEC.ASM.project.modules.academic.repository.SubjectRepository;
import BTEC.ASM.project.modules.academic.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public SubjectResponse create(SubjectRequest request) {
        Subject entity = subjectMapper.toEntity(request);
        subjectRepository.save(entity);
        return subjectMapper.toResponse(entity);
    }

    @Override
    public List<SubjectResponse> getAll() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SubjectResponse> getById(Long id) {
        return subjectRepository.findById(id)
                .map(subjectMapper::toResponse);
    }

    @Override
    public Optional<SubjectResponse> update(Long id, SubjectRequest request) {
        return subjectRepository.findById(id)
                .map(existing -> {
                    subjectMapper.updateSubjectFromRequest(request, existing);
                    subjectRepository.save(existing);
                    return subjectMapper.toResponse(existing);
                });
    }

    @Override
    public boolean delete(Long id) {
        if(subjectRepository.existsById(id)){
            subjectRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
