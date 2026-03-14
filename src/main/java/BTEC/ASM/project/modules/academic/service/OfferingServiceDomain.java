package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.entity.Offering;
import BTEC.ASM.project.modules.academic.entity.Term;

public interface OfferingServiceDomain {
    Offering getByOfferingId(Long id);

    boolean existsByOfferingId(Long Id);
}
