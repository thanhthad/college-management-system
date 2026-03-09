package BTEC.ASM.project.modules.enrollment.entity;

import BTEC.ASM.project.modules.academic.entity.Offering;
import BTEC.ASM.project.modules.enrollment.enums.StaffRole;
import BTEC.ASM.project.modules.identity.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "offering_staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferingStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offering_staff_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offering_id", nullable = false)
    private Offering offering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "staff_role", length = 50)
    private StaffRole staffRole;

    @Column(name = "active_flag")
    private Boolean activeFlag;

    @CreationTimestamp
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
}
