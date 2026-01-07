package BTEC.ASM.project.modules.identity.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String userCode;
    private String fullName;
    private String email;
    private String status;
    private Set<String> roles;
}

