package upp.delegate;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ProvjeraNaloga implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		HashMap<String, Object> podaci = (HashMap<String, Object>) execution.getVariable("logovanje");
		System.out.println("Stugai sam u provjera Naloga" + podaci.get("nalog").toString());
		if((boolean) podaci.get("nalog"))
			execution.setVariable("nalog", true);
		else
			execution.setVariable("nalog", false);
			
	}

}
