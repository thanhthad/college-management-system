package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.OfferingRequest;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import BTEC.ASM.project.modules.academic.entity.Offering;
import BTEC.ASM.project.modules.academic.entity.Subject;
import BTEC.ASM.project.modules.academic.entity.Term;
import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import BTEC.ASM.project.modules.academic.mapper.OfferingMapper;
import BTEC.ASM.project.modules.academic.repository.OfferingRepository;
import BTEC.ASM.project.modules.academic.repository.SubjectRepository;
import BTEC.ASM.project.modules.academic.repository.TermRepository;
import BTEC.ASM.project.modules.academic.repository.ClassGroupRepository;
import BTEC.ASM.project.modules.academic.service.OfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OfferingServiceImpl implements OfferingService {

    private final OfferingRepository offeringRepository;
    private final OfferingMapper offeringMapper;
    private final SubjectRepository subjectRepository;
    private final TermRepository termRepository;
    private final ClassGroupRepository classGroupRepository;

    @Override
    public Optional<OfferingResponse> create(OfferingRequest request) {
        Optional<Subject> subjectOpt = subjectRepository.findById(request.subjectId());
        Optional<Term> termOpt = termRepository.findById(request.termId());
        Optional<ClassGroup> groupOpt = classGroupRepository.findById(request.classGroupId());

        if (subjectOpt.isEmpty() || termOpt.isEmpty() || groupOpt.isEmpty()) return Optional.empty();

        Offering entity = offeringMapper.toEntity(request);
        entity.setSubject(subjectOpt.get());
        entity.setTerm(termOpt.get());
        entity.setClassGroup(groupOpt.get());

        offeringRepository.save(entity);
        return Optional.of(offeringMapper.toResponse(entity));
    }

    @Override
    public List<OfferingResponse> getAll() {
        return offeringRepository.findAll().stream()
                .map(offeringMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OfferingResponse> getById(Long id) {
        return offeringRepository.findById(id)
                .map(offeringMapper::toResponse);
    }

    @Override
    public Optional<OfferingResponse> update(Long id, OfferingRequest request) {
        return offeringRepository.findById(id).flatMap(existing -> {
            Optional<Subject> subjectOpt = subjectRepository.findById(request.subjectId());
            Optional<Term> termOpt = termRepository.findById(request.termId());
            Optional<ClassGroup> groupOpt = classGroupRepository.findById(request.classGroupId());

            if(subjectOpt.isEmpty() || termOpt.isEmpty() || groupOpt.isEmpty()) return Optional.empty();

            offeringMapper.updateOfferingFromRequest(request, existing);

            existing.setSubject(subjectOpt.get());
            existing.setTerm(termOpt.get());
            existing.setClassGroup(groupOpt.get());

            offeringRepository.save(existing);
            return Optional.of(offeringMapper.toResponse(existing));
        });
    }

    @Override
    public boolean delete(Long id) {
        if(offeringRepository.existsById(id)){
            offeringRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
