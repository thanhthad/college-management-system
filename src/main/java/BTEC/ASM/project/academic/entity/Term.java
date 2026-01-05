package BTEC.ASM.project.academic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "terms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Long id;

    @Column(name = "term_code", nullable = false, unique = true, length = 50)
    private String termCode;

    @Column(name = "term_name", length = 100)
    private String termName;

    private LocalDate startDate;
    private LocalDate endDate;
}
