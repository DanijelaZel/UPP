package upp.delegate;

import java.util.ArrayList;
import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.model.Casopis;
import upp.model.NaucnaOblast;
import upp.model.Rad;
import upp.model.RadStatus;
import upp.model.User;
import upp.service.CasopisService;
import upp.service.NaucnaOblastService;
import upp.service.RadService;

@Service
public class PostavljanjeGL implements JavaDelegate {

	@Autowired
	RadService radSer;
	
	@Autowired
	CasopisService casSer;
	
	@Autowired
	NaucnaOblastService noSer;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Postavljanje gl");
		HashMap<String, Object> podaci = (HashMap<String, Object>) execution.getVariable("rad");
		Rad rad = radSer.findById(podaci.get("id").toString());
		Casopis casopis = casSer.getCasopis(rad.getCasopisRad().getIssnBr());
		NaucnaOblast naucnaOblast = noSer.findByNaziv(rad.getNaucnaOblast().getNaziv());
		rad.setRadStatus(RadStatus.NA_RECENZIRANJU);
		
		ArrayList<String> listaRecezenata = new ArrayList<String>();
		
		
		for(int i = 0; i < casopis.getUrednici().size();i++) {
			if(casopis.getUrednici().get(i).getGlavni()) {
					naucnaOblast.setUrednikNaucneOblasti(casopis.getUrednici().get(i));
					rad.getRecezenti().add(casopis.getUrednici().get(i));
					listaRecezenata.add(casopis.getUrednici().get(i).getEmail());
					
					break;
				}
			}
		execution.setVariable("recezenti", listaRecezenata);
		execution.setVariable("urednikNO", naucnaOblast.getUrednikNaucneOblasti().getEmail());
		radSer.save(rad);
		noSer.save(naucnaOblast);
			
	}

}
