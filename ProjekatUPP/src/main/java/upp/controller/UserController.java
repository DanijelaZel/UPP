package upp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import upp.camunda.model.FormFieldsDto;
import upp.camunda.model.TaskDto;
import upp.dto.UserDTO;
import upp.model.User;
import upp.service.UserService;

@RestController
@RequestMapping(value = "/korisnik")
public class UserController {
	
	@Autowired
	ProcessEngine processEngine;
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	UserService usSer;

	private static ProcessInstance pi;
	
	@RequestMapping(value = "/registracija", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registracija(@RequestBody UserDTO request) {
		
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);		
		
		String email = "email";
		String password = "password";
		String ime = "ime";
		String prezime = "prezime";
		String grad = "grad";
		String drzava = "drzava";
		System.out.println(identityService);
		HashMap<String, Object> podaci = new HashMap<String, Object>();
		podaci.put(email, request.getEmail());
		podaci.put(password, request.getPassword());
		podaci.put(ime, request.getIme());
		podaci.put(prezime, request.getPrezime());
		podaci.put(grad, request.getGrad());
		podaci.put(drzava, request.getDrzava());
		
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registracija", podaci);
		formService.submitTaskForm(task.getId(), podaci);
		
		return "Uspjesno";
		
		
	}
	
	@RequestMapping(value="/sendMail/{email}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String sendMail(@PathVariable("email") String email) {
			User user = usSer.findByEmail(email);
			user.setUserStatus(true);
			usSer.save(user);
		return "verifikovan";
	}
	
	
	@RequestMapping(value = "/logovanje", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String logovanje(@RequestBody UserDTO user, HttpServletRequest request) {		
			
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);		
	
		String email = "email";
		String lozinka = "lozinka";
		String nalog = "nalog";
		HashMap<String, Object> podaci = new HashMap<String, Object>();
		podaci.put(email, user.getEmail());
		podaci.put(lozinka, user.getPassword());
		podaci.put(nalog, false);
		User k = usSer.findByEmail(user.getEmail());
		Authentication currentAuthentication = null;
		if(k != null) {
			if(user.getPassword().equals(k.getPassword())) {
				request.getSession().setAttribute("user", k);
				currentAuthentication = new Authentication(user.getEmail(),new ArrayList<String>());
				identityService.setAuthentication(currentAuthentication);
				identityService.setAuthenticatedUserId(user.getEmail());
				podaci.put(nalog, true);
				String processInstanceId = task.getProcessInstanceId();
				
				runtimeService.setVariable(processInstanceId, "logovanje", podaci);
				
				formService.submitTaskForm(task.getId(), podaci);
				
				return "Uspjesno";
			
				}
		}
				String processInstanceId = task.getProcessInstanceId();
				
				runtimeService.setVariable(processInstanceId, "logovanje", podaci);
				
				formService.submitTaskForm(task.getId(), podaci);
				return "Neuspjesno";
}

	
	@GetMapping(path = "/getLogovanje", produces = "application/json")
    public @ResponseBody FormFieldsDto get() {
		
		pi = runtimeService.startProcessInstanceByKey("mojeLogovanje");	
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }
	
	@GetMapping(path = "/getRegistracija", produces = "application/json")
    public @ResponseBody FormFieldsDto getRegistracija() {
		
		pi = runtimeService.startProcessInstanceByKey("mojaRegistracija");			
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }
	
	
	@GetMapping(path = "/getRecezenti", produces = "application/json")
    public List<User> getRecezenti() {
		return usSer.findRecezenti();
    }
	
	
	@GetMapping(path = "/logOut", produces = "application/json")
    public void logOut(HttpServletRequest request) {
		request.getSession().invalidate();
    }
	
	
	
	
	
	
	
	
}
