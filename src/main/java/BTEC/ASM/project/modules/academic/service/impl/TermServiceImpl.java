package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.TermRequest;
import BTEC.ASM.project.modules.academic.dto.response.TermResponse;
import BTEC.ASM.project.modules.academic.entity.Term;
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
    public Optional<TermResponse> getById(Long id) {
        return termRepository.findById(id)
                .map(termMapper::toResponse);
    }

    @Override
    public Optional<TermResponse> update(Long id, TermRequest request) {
        return termRepository.findById(id)
                .map(existing -> {
                    termMapper.updateTermFromRequest(request, existing);
                    termRepository.save(existing);
                    return termMapper.toResponse(existing);
                });
    }

    @Override
    public boolean delete(Long id) {
        if(termRepository.existsById(id)){
            termRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
