package upp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import upp.model.User;
import upp.model.UserRole;
import upp.repository.UserRepository;

@Service
@Transactional
public class UserService {

	
	@Autowired
	UserRepository usRep;
	
	public User findByEmail(String email) {
		User us = usRep.findByEmail(email);
		return us;
	}
	
	public List<User> findAll() {
		return usRep.findAll();
	}
	
	public List<User> findRecezenti() {
		List<User> svi = usRep.findAll();
		List<User> recezenti = new ArrayList<User>();
		for(int i=0; i < svi.size(); i++) {
			if(svi.get(i).getUserRole().equals(UserRole.RECEZENT))
				recezenti.add(svi.get(i));
		}
		return recezenti;
	}
	
	public void save(User us) {
		usRep.save(us);
		System.out.println("Uspjesno je dodat u bazu");
	}
}
