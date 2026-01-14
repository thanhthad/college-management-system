package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.TermRequest;
import BTEC.ASM.project.modules.academic.dto.response.TermResponse;
import BTEC.ASM.project.modules.academic.entity.Term;
import BTEC.ASM.project.modules.academic.exception.TermAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.TermNotFoundException;
import BTEC.ASM.project.modules.academic.mapper.TermMapper;
import BTEC.ASM.project.modules.academic.repository.TermRepository;
import BTEC.ASM.project.modules.academic.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;
    private final TermMapper termMapper;

    @Override
    public TermResponse create(TermRequest request) {
        if(termRepository.existsByTermCode(request.termCode())) {
            throw new TermAlreadyExistsException("Term already exist");
        }
        Term entity = termMapper.toEntity(request);
        termRepository.save(entity);
        return termMapper.toResponse(entity);
    }

    @Override
    public List<TermResponse> getAll() {
        return termRepository.findAll().stream()
                .map(termMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TermResponse getById(Long id) {
        Term term = termRepository.findById(id).orElseThrow(
                () -> new TermNotFoundException("Term not found")
        );
        return termMapper.toResponse(term);
    }

    @Override
    public TermResponse update(Long id, TermRequest request) {
        Term term = termRepository.findById(id).orElseThrow(
                () -> new TermNotFoundException("Term not found")
        );
        termMapper.updateTermFromRequest(request,term);
        return termMapper.toResponse(termRepository.save(term));
    }

    @Override
    public void delete(Long id) {
        if(termRepository.existsById(id)){
            termRepository.deleteById(id);
        }
    }
}
