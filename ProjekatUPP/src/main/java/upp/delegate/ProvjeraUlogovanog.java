package upp.delegate;

import java.util.HashMap;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.service.UserService;

@Service
public class ProvjeraUlogovanog implements JavaDelegate {

	@Autowired
	IdentityService identityService;
	@Autowired
	UserService usSer;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
	//	HashMap<String, Object> ulogovan = (HashMap<String, Object>) execution.getVariable("ulogovaoSe");
	//	System.out.println("Provjera ulogovanog sam");
	//	if((boolean) ulogovan.get("ulogovaoSe"))
			execution.setVariable("ulogovan", true);
	//	else
	//		execution.setVariable("ulogovan", false);
	//		
	}

}
