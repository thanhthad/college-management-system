package BTEC.ASM.project.modules.academic.service;

public interface OfferingService {

    void validateTermNotInUse(Long termId);

    void validateSubjectNotInUse(Long subjectId);

    void validateClassGroupNotInUse(Long classGroupId);
}
