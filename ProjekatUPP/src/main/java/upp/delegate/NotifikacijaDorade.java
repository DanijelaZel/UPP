package upp.delegate;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.model.NaucnaOblast;
import upp.model.Rad;
import upp.model.Recenzija;
import upp.model.User;
import upp.service.EmailService;
import upp.service.RadService;
import upp.service.RecenzijaService;
import upp.service.UserService;

@Service
public class NotifikacijaDorade implements JavaDelegate {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	RadService radSer;
	
	@Autowired
	RecenzijaService recSer;
	
	@Autowired
	UserService userSer;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> podaci = (HashMap<String, Object>) execution.getVariable("rad");
        Rad rad = radSer.findById(podaci.get("id").toString());
    	User autor = userSer.findByEmail(podaci.get("autorEmail").toString());
		List<Recenzija> recenzijeRada = recSer.findByRadId(rad.getId());
		
		String s = "";
		for(int i=0;i<recenzijeRada.size();i++) {
			s+=recenzijeRada.get(i).getKomentar()+ " ";
		}
		
		if((boolean)execution.getVariable("vecaIzmjena")) {
			for(int i=0; i < recenzijeRada.size();i++) {
	    		recSer.remove(recenzijeRada.get(i));
	    	}
		}
		
		emailService.getMail().setTo(autor.getEmail());
		emailService.getMail().setSubject("Dorada rad");
		emailService.getMail().setText("Postovani, \n\n Potrebno je da doradite Vas rad : "+ rad.getNaslov() + 
				" u skladu sa komentarima recezenata, a to su: " + s);
		emailService.sendNotificaitionSync(autor);
	}

}
