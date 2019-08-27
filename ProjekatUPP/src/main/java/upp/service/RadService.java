package upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import upp.model.Rad;
import upp.model.User;
import upp.repository.RadRepository;

@Service
@Transactional
public class RadService {

	@Autowired
	RadRepository radRep;
	
	public Rad findById(String id) {
		return radRep.findById(Long.parseLong(id)).get();
	}
	
	public void save(Rad rad) {
		radRep.save(rad);
		System.out.println("Uspjesno je dodat u bazu");
	}
	
	public List<Rad> findByAutor(User autor) {
		return radRep.findByAutorId(autor.getId());
	}
	
	public List<Rad> findAll() {
		return radRep.findAll();
	}
}
