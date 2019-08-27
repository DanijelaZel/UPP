package upp.delegate;

import java.util.HashMap;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.model.User;
import upp.model.UserRole;
import upp.repository.UserRepository;
import upp.service.EmailService;

@Service
public class RegistrationDelegate implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	
	@Autowired
	UserRepository usRep;
	
	@Autowired
	private EmailService emailService;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> podaci = (HashMap<String, Object>) execution.getVariable("registracija");
		
		User k = new User();
		k.setEmail(podaci.get("email").toString());
		k.setPassword(podaci.get("password").toString());
		k.setName(podaci.get("ime").toString());
		k.setSurname(podaci.get("prezime").toString());
		k.setCity(podaci.get("grad").toString());
		k.setCountry(podaci.get("drzava").toString());
		k.setUserRole(UserRole.KORISNIK);
		k.setUserStatus(false);

		emailService.getMail().setTo(k.getEmail());
		emailService.getMail().setSubject("Verifikacija naloga");
		emailService.getMail().setText("Pozdrav " + k.getName() + " molimo Vas da verifikujete svoj email klikom na link ispod,\n\n http://localhost:4444/UPP/korisnik/sendMail/"+k.getEmail());
		emailService.sendNotificaitionSync(k);
	
		org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser(k.getEmail());
		camundaUser.setEmail(k.getEmail());
		camundaUser.setPassword(k.getPassword());
		camundaUser.setFirstName(k.getName());
		camundaUser.setLastName(k.getSurname());
		
		if(usRep.save(k) != null) 
			identityService.saveUser(camundaUser);
			
		
	}
		
}
