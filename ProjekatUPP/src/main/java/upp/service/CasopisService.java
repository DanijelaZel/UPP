package upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import upp.model.Casopis;
import upp.repository.CasopisRepository;

@Service
@Transactional
public class CasopisService {

	@Autowired
	CasopisRepository casRep;
	
	public List<Casopis> sviCasopisi(){
		return casRep.findAll();
	}
	
	public Casopis getCasopis(String issnBr) {
		return casRep.findByIssnBr(issnBr);
	}
	
	public Casopis getCasopisById(long id) {
		return casRep.findById(id).get();
	}
	
	public void save(Casopis casopis) {
		casRep.save(casopis);
	}
}
