package upp.delegate;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.model.Casopis;
import upp.model.User;
import upp.service.CasopisService;
import upp.service.EmailService;
import upp.service.UserService;


@Service
public class NotifikacijaAiGL implements JavaDelegate {

	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	CasopisService casSer;
	
	@Autowired
	UserService userSer;
	

	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		
		HashMap<String, Object> podaci = (HashMap<String, Object>) execution.getVariable("rad");
		HashMap<String, Object> podaciZaCasopis = (HashMap<String, Object>) execution.getVariable("casopis");
		System.out.println("Notifikacije AIGL");
		User autor = userSer.findByEmail(podaci.get("autorEmail").toString());
		Casopis cas = casSer.getCasopis(podaciZaCasopis.get("issnBroj").toString());
		User glavniUrednik = null;
		for(int i = 0 ; i < cas.getUrednici().size(); i++) {
			if(cas.getUrednici().get(i).getGlavni())
				glavniUrednik = cas.getUrednici().get(i);
		}
		
		if(glavniUrednik!=null && autor!= null){
			emailService.getMail().setTo(glavniUrednik.getEmail());
			emailService.getMail().setSubject("Novi rad u casopisu");
			emailService.getMail().setText("Postovani, \n\n Dodat je novi rad u okviru Vaseg casopisa."+
			"Naslov rada je "+podaci.get("naslov")+ ".");
			emailService.sendNotificaitionSync(glavniUrednik);
			
			
			emailService.getMail().setTo(autor.getEmail());
			emailService.getMail().setSubject("Novi rad u casopisu");
			emailService.getMail().setText("Postovani, \n\n Vas rad je dodat u okviru casopisa: \"" + cas.getIme() + "\", ciji je glavni urednik "
							+ glavniUrednik.getName() + "Autor rada"
							+ " je: " + autor.getName() + ".");
			emailService.sendNotificaitionSync(autor);
			
		}
		execution.setVariable("glUrednik",glavniUrednik.getEmail());
		execution.setVariable("autor",autor.getEmail());

	}

}
