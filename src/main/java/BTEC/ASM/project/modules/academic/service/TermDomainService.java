package BTEC.ASM.project.modules.academic.service;


import BTEC.ASM.project.modules.academic.entity.Term;

public interface TermDomainService {
    Term getByTermCode(String termCode);

    Term getByTermId(Long termId);

    boolean existsByTermCode(String termCode);

    boolean existsByTermId(Long termId);

}
