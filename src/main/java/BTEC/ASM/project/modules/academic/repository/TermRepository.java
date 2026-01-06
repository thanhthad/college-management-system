package BTEC.ASM.project.modules.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BTEC.ASM.project.modules.academic.entity.Term;

import java.util.Optional;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    Optional<Term> findByTermCode(String termCode);

    boolean existsByTermCode(String termCode);
}
