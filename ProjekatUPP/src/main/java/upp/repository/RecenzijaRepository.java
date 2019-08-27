package upp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import upp.model.Recenzija;
@Repository
public interface RecenzijaRepository extends JpaRepository<Recenzija,Long>{

	List<Recenzija> findByRadId(Long id);
}

