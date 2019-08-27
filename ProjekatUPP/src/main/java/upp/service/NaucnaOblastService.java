package upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import upp.model.NaucnaOblast;
import upp.repository.NaucnaOblastRepository;

@Service
@Transactional
public class NaucnaOblastService {

	@Autowired
	NaucnaOblastRepository noRep;
	
	public NaucnaOblast findByNaziv(String naziv) {
		return noRep.findByNaziv(naziv);
	}
	
	public List<NaucnaOblast> findAll(){
		return noRep.findAll();
	}
	
	public NaucnaOblast save(NaucnaOblast no) {
		return noRep.save(no);
	}
	
	//public NaucnaOblast findById(Long id) {
	//	return noRep.finby
	//}
}
