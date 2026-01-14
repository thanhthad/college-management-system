package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.SubjectRequest;
import BTEC.ASM.project.modules.academic.dto.response.SubjectResponse;
import BTEC.ASM.project.modules.academic.entity.Subject;
import BTEC.ASM.project.modules.academic.exception.SubjectAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.SubjectNotFoundException;
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
        if(subjectRepository.existsBySubjectCode(request.subjectCode())){
            throw new SubjectAlreadyExistsException("Subject is already exists");
        }
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
    public SubjectResponse getById(Long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(
                () -> new SubjectNotFoundException("Subject not found")
        );
        return subjectMapper.toResponse(subject);
    }

    @Override
    public SubjectResponse update(Long id, SubjectRequest request) {
        Subject subject = subjectRepository.findById(id).orElseThrow(
                () -> new SubjectNotFoundException("Subject not found")
        );
        subjectMapper.updateSubjectFromRequest(request,subject);
        return subjectMapper.toResponse(subjectRepository.save(subject));
    }

    @Override
    public void delete(Long id) {
        if(subjectRepository.existsById(id)){
            subjectRepository.deleteById(id);
        }
    }
}
