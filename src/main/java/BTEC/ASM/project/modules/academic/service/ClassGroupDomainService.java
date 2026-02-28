package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.entity.ClassGroup;

public interface ClassGroupDomainService {
    ClassGroup getByClassGroupName(String groupName);

    boolean existsByClassGroupName(String groupName);

}
