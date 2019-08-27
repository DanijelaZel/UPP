package upp.delegate;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ProvjeraTipaCasopisa implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> podaci = (HashMap<String, Object>) execution.getVariable("casopis");
		System.out.println("U provjeri tipa casopisa sam");
		if((boolean)podaci.get("openAccess")) {
			execution.setVariable("openAccess", true);
		}else {
			execution.setVariable("openAccess", false);
		}
	}

}
