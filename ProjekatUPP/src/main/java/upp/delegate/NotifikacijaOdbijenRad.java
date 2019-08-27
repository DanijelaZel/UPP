package upp.delegate;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.model.Rad;
import upp.model.RadStatus;
import upp.service.EmailService;
import upp.service.RadService;

@Service
public class NotifikacijaOdbijenRad implements JavaDelegate {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	RadService radSer;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ODBIJEN RAD");
		HashMap<String, Object> pdfLos = (HashMap<String, Object>) execution.getVariable("pdfLos");
		Rad rad = (Rad) pdfLos.get("rad");
		Rad r = radSer.findById(rad.getId().toString());
		r.setRadStatus(RadStatus.ODBIJEN);
		emailService.getMail().setTo(r.getAutor().getEmail());
		emailService.getMail().setSubject("Odbijen rad");
		emailService.getMail().setText("Postovani, \n\n Vas rad " + r.getNaslov() +" je odbijen. Niste na vrijeme izmijenili pdf i ono sto se ocekivalo od Vas, zao nam je.");
		emailService.sendNotificaitionSync(r.getAutor());
		
	}

}
