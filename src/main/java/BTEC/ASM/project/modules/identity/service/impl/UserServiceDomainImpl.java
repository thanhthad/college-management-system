package BTEC.ASM.project.modules.identity.service.impl;

import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.exception.user.UserNotFoundException;
import BTEC.ASM.project.modules.identity.repository.UserRepository;
import BTEC.ASM.project.modules.identity.service.UserServiceDomain;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceDomainImpl implements UserServiceDomain {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getByUserId(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public User getByUserCode(String userCode) {
        return userRepository.findByUserCode(userCode).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserId(Long Id) {
        return userRepository.existsById(Id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUserCode(String userCode) {
        return userRepository.existsByUserCode(userCode);
    }
}
