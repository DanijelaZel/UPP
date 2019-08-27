package upp.delegate;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.model.Casopis;
import upp.model.NaucnaOblast;
import upp.model.Rad;
import upp.service.CasopisService;
import upp.service.NaucnaOblastService;
import upp.service.RadService;

@Service
public class BiranjeUrednikaNoviRad implements JavaDelegate {

	@Autowired
	RadService radSer;
	
	@Autowired
	CasopisService casSer;
	
	@Autowired
	NaucnaOblastService noSer;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Biranje Urednika");
		HashMap<String, Object> podaci = (HashMap<String, Object>) execution.getVariable("rad");
		System.out.println(podaci.get("naslov"));
		Rad rad = radSer.findById(podaci.get("id").toString());
		Casopis casopis = casSer.getCasopis(rad.getCasopisRad().getIssnBr());
		NaucnaOblast naucnaOblast = noSer.findByNaziv(rad.getNaucnaOblast().getNaziv());
		
		
			for(int i = 0; i < casopis.getUrednici().size();i++) {
				if(!casopis.getUrednici().get(i).getGlavni()) {
					naucnaOblast.setUrednikNaucneOblasti(casopis.getUrednici().get(i));
					break;
				}
			}
			noSer.save(naucnaOblast);
			if(naucnaOblast.getUrednikNaucneOblasti()!=null) {
				execution.setVariable("urednik", true);
				execution.setVariable("urednikNO", naucnaOblast.getUrednikNaucneOblasti().getEmail());
			}else {
				execution.setVariable("urednik", false);
			}
			
			
			
		
	}

}
