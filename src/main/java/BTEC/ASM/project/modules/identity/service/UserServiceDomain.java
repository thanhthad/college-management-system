package BTEC.ASM.project.modules.identity.service;

import BTEC.ASM.project.modules.identity.entity.User;

public interface UserServiceDomain {

    User getByUserId(Long id);

    User getByUserCode(String userCode);

    boolean existsByUserId(Long Id);

    boolean existsByUserCode(String userCode );

}
