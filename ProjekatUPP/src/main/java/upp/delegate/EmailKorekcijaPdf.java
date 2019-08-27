package upp.delegate;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.model.Rad;
import upp.service.EmailService;
import upp.service.RadService;

@Service
public class EmailKorekcijaPdf implements JavaDelegate {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	RadService radSer;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Email korekcija pdf-a");
		
		HashMap<String, Object> pdfLos = (HashMap<String, Object>) execution.getVariable("pdfLos");
		Rad rad = (Rad) pdfLos.get("rad");
		Rad r = radSer.findById(rad.getId().toString());
		emailService.getMail().setTo(r.getAutor().getEmail());
		emailService.getMail().setSubject("Los format pdf-a");
		emailService.getMail().setText("Postovani, \n\n Vas pdf nije dobro formatiran u radu: " + r.getNaslov() +". \n\n Molimo vas na u sto kracem roku izmjenite pdf!");
		emailService.sendNotificaitionSync(r.getAutor());
		
	}

}
