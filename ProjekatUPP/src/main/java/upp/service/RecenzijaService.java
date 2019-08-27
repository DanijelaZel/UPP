package upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import upp.model.Recenzija;
import upp.repository.RecenzijaRepository;

@Service
@Transactional
public class RecenzijaService {

	@Autowired
	RecenzijaRepository recRep;
	
	
	public void save(Recenzija rec) {
		recRep.save(rec);
	}
	
	public List<Recenzija> findAll() {
		return recRep.findAll();
	}
	
	public List<Recenzija> findByRadId(Long id){
		return recRep.findByRadId(id);
	}
	
	public void remove(Recenzija recenzija){
		recRep.delete(recenzija);
	}
}
