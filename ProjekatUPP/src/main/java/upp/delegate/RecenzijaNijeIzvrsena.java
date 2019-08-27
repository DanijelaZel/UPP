package upp.delegate;

import java.util.ArrayList;
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
import upp.service.NaucnaOblastService;
import upp.service.RadService;
import upp.service.RecenzijaService;
import upp.service.UserService;

@Service
public class RecenzijaNijeIzvrsena implements JavaDelegate {

	@Autowired
	RadService radSer;
	
	@Autowired
	RecenzijaService recSer;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	NaucnaOblastService noSer;
	
	@Autowired
	UserService userSer;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		HashMap<String, Object> podaci = (HashMap<String, Object>) execution.getVariable("rad");
		Rad rad = radSer.findById(podaci.get("id").toString());		
		List<Recenzija> recenzije = recSer.findByRadId(rad.getId());
		
		List<User> nisuRecenzirali = rad.getRecezenti();
		List<User> suRecenzirali = new ArrayList<User>();
		
		NaucnaOblast naucnaOblast = noSer.findByNaziv(rad.getNaucnaOblast().getNaziv());
		User urednik = userSer.findByEmail(naucnaOblast.getUrednikNaucneOblasti().getEmail());

		for(int i = 0; i < recenzije.size();i++) {
			for(int j = 0; j< rad.getRecezenti().size();j++) {
				if(recenzije.get(i).getAutorRecenzije().getEmail().equals(rad.getRecezenti().get(j).getEmail()))
					suRecenzirali.add(rad.getRecezenti().get(j));
			}
		}
		
		nisuRecenzirali.removeAll(suRecenzirali);
		String s = "";
		for(int i=0;i<nisuRecenzirali.size();i++) {
			s+=nisuRecenzirali.get(i).getEmail()+ " ";
		}
		
		emailService.getMail().setTo(urednik.getEmail());
		emailService.getMail().setSubject("Recenzija nije obavljena");
		emailService.getMail().setText("Postovani, \n\n za rad : "+ rad.getNaslov() + " nije obavljena u potpunosti recenzija. Korisnici koji nisu recenzirali su:"+ s +".Molimo Vas da izaberete nove clanove recenzije!");
		emailService.sendNotificaitionSync(urednik);
	}

}
