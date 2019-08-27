package upp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import upp.model.Casopis;
import upp.model.Rad;
import upp.model.RadStatus;
import upp.model.User;
import upp.model.UserRole;
import upp.service.CasopisService;
import upp.service.NaucnaOblastService;

@RestController
@RequestMapping(value = "/casopis")
public class CasopisController {

	@Autowired
	CasopisService casSer;
	
	@Autowired
	NaucnaOblastService noSer;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	IdentityService identityService;
	
	public static ProcessInstance pi;
	public static Task task;
	
	@GetMapping(path = "/getOne/{issnBr}", produces = "application/json")
    public Casopis getOne(@PathVariable String issnBr) {		
		return casSer.getCasopis(issnBr);
    }
	
	@GetMapping(path = "/getAll", produces = "application/json")
    public List<Casopis> getAll() {
		pi = runtimeService.startProcessInstanceByKey("prijavaRada");
		List<Casopis> casopisi = casSer.sviCasopisi();
		return casopisi;
    }
	
	
	@RequestMapping(value = "/biranjeCasopisa/{issnBr}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String biranjeCasopisa(@PathVariable String issnBr) {		
				
		 task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);		
			
		
		Casopis casopis = casSer.getCasopis(issnBr);
		String ime = "ime";
		String issnBroj = "issnBroj";
		String openAccess = "openAccess";
		
		HashMap<String, Object> listCasopis = new HashMap<String, Object>();
		listCasopis.put(ime, casopis.getIme());
		listCasopis.put(issnBroj, casopis.getIssnBr());
		listCasopis.put(openAccess, casopis.isOpenAccess());
		
		
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "casopis", listCasopis);
		formService.submitTaskForm(task.getId(), listCasopis);
		return "Uspjesno";
		
	}

	
	@GetMapping(path = "/getUrednikovCasopis", produces = "application/json")
    public List<Rad> getUrednikovCasopis(HttpServletRequest request) {
	
		User urednik = (User) request.getSession().getAttribute("user");
		Casopis cas = casSer.getCasopis(urednik.getCasopis().getIssnBr());
		if(urednik.getUserRole().equals(UserRole.UREDNIK)) {
			ArrayList<Rad> radovi = new ArrayList<Rad>();
			if(urednik.getGlavni()) {
				for(int i=0;i<cas.getRadovi().size(); i++) {
					if(cas.getRadovi().get(i).getRadStatus().equals(RadStatus.U_TOKU)) {
						radovi.add(cas.getRadovi().get(i));
					}
				}
			}else{
				return null;
				}
			return radovi;
			
		}else {
			return null;
		}
	}
	
	@GetMapping(path = "/getUser", produces = "application/json")
    public String getUser(HttpServletRequest request) {
	
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		String processInstanceId = task.getProcessInstanceId();
		String ulogovaoSe = "ulogovaoSe";
		HashMap<String, Object> login = new HashMap<String, Object>();
		login.put(ulogovaoSe, false);
		User onSession = (User) request.getSession().getAttribute("user");
			if(onSession.getGlavni()) {
				login.put(ulogovaoSe,true);
				runtimeService.setVariable(processInstanceId, "ulogovan", true);
				return "glavni";
			}else{
				login.put(ulogovaoSe,true);
				runtimeService.setVariable(processInstanceId, "ulogovan", true);
				return onSession.getUserRole().toString();
			}
    }
	
	
}
