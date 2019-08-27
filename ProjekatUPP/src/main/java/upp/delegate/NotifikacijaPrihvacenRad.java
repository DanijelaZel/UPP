package upp.delegate;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.model.Rad;
import upp.model.User;
import upp.service.EmailService;
import upp.service.RadService;
import upp.service.UserService;

@Service
public class NotifikacijaPrihvacenRad implements JavaDelegate{

	@Autowired
	private EmailService emailService;
	
	@Autowired
	RadService radSer;
	
	@Autowired
	UserService userSer;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> podaci = (HashMap<String, Object>) execution.getVariable("rad");
		Rad rad = radSer.findById(podaci.get("id").toString());
		User autor = userSer.findByEmail(podaci.get("autorEmail").toString());
		
		emailService.getMail().setTo(autor.getEmail());
		emailService.getMail().setSubject("Prihvacen rad");
		emailService.getMail().setText("Postovani, \n\n Vas rad : "+ rad.getNaslov() + " je prihvacen!");
		emailService.sendNotificaitionSync(autor);
		
	}

}
