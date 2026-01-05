package BTEC.ASM.project.academic.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "class_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_group_id")
    private Long id;

    @Column(name = "group_name", nullable = false, unique = true, length = 100)
    private String groupName;

    @Column(name = "campus_code", length = 50)
    private String campusCode;

    @Column(name = "department_code", length = 50)
    private String departmentCode;
}
