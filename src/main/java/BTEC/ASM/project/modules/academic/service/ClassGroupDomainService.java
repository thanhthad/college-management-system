package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.entity.ClassGroup;

public interface ClassGroupDomainService {
    ClassGroup getByClassGroupName(String groupName);

    ClassGroup getByClassGroupId(Long groupId);

    boolean existsByClassGroupName(String groupName);

    boolean existsByClassGroupId(Long groupId);

}
