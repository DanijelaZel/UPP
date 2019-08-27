package upp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import upp.model.Casopis;
@Repository
public interface CasopisRepository extends JpaRepository<Casopis, Long> {

	Casopis findByIssnBr(String issnBr);
	
}
