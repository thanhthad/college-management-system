package BTEC.ASM.project.modules.identity.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserRoleId implements Serializable {

    private Long userId;
    private Integer roleId;
}
