package BTEC.ASM.project.modules.academic.service;


import BTEC.ASM.project.modules.academic.entity.Term;

public interface TermDomainService {
    Term getByTermCode(String termCode);

    boolean existsByTermCode(String termCode);
}
