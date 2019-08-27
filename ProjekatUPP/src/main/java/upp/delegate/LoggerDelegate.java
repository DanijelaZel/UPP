package upp.delegate;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class LoggerDelegate implements JavaDelegate {

	 private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	 
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		 
		System.out.println("Stigao do registracije");
		LOGGER.info("\n\n Proces: " + execution.getProcessDefinitionId() +" \n\n");
	     LOGGER.info("Bussines Key: " + execution.getProcessBusinessKey());
	     
	}

}
