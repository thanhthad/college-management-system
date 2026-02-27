package BTEC.ASM.project.modules.academic.entity;

import BTEC.ASM.project.modules.academic.enums.OfferingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "offerings",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_offering_subject_term_classgroup",
                    columnNames = {
                            "subject_id",
                            "term_id",
                            "class_group_id"
                    }
            )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offering_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;

    @ManyToOne
    @JoinColumn(name = "class_group_id")
    private ClassGroup classGroup;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private OfferingStatus status;
}
