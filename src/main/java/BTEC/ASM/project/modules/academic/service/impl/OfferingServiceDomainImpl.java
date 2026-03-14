package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.entity.Offering;
import BTEC.ASM.project.modules.academic.exception.offering.OfferingNotFoundException;
import BTEC.ASM.project.modules.academic.mapper.OfferingMapper;
import BTEC.ASM.project.modules.academic.repository.OfferingRepository;
import BTEC.ASM.project.modules.academic.service.OfferingServiceDomain;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OfferingServiceDomainImpl implements OfferingServiceDomain {

    private final OfferingRepository offeringRepository;
    private final OfferingMapper offeringMapper;


    @Override
    public Offering getByOfferingId(Long id) {
        return offeringRepository.findById(id).orElseThrow(
                () -> new OfferingNotFoundException("Offering Not Found")
        );
    }

    @Override
    public boolean existsByOfferingId(Long Id) {
        return offeringRepository.existsById(Id);
    }
}
