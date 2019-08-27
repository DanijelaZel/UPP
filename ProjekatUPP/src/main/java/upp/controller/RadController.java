package upp.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import upp.camunda.model.FormFieldsDto;
import upp.dto.RecenzijaDTO;
import upp.model.Casopis;
import upp.model.NaucnaOblast;
import upp.model.Rad;
import upp.model.RadStatus;
import upp.model.Recenzija;
import upp.model.UploadModel;
import upp.model.User;
import upp.service.CasopisService;
import upp.service.NaucnaOblastService;
import upp.service.RadService;
import upp.service.RecenzijaService;
import upp.service.UserService;

@RestController
@RequestMapping(value = "/rad")
public class RadController {

		private static ProcessInstance pi = CasopisController.pi;
		private static String DATA_DIR_PATH;
		private static Task task;
		
		static {
			ResourceBundle rb=ResourceBundle.getBundle("application");
			DATA_DIR_PATH=rb.getString("dataDir");
		}
	
		@Autowired
		RadService radSer;
		
		@Autowired
		CasopisService casopisSer;
		
		@Autowired
		NaucnaOblastService noSer;
		
		@Autowired
		UserService userSer;
		
		@Autowired
		RecenzijaService recSer;
		
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
		
	
		
		private File getResourceFilePath(String path) {
		    URL url = this.getClass().getClassLoader().getResource(path);
		    File file = null;
		    try {
		        file = new File(url.toURI());
		    } catch (URISyntaxException e) {
		        file = new File(url.getPath());
		    }   
		    return file;
		}
		
		
		@PostMapping("/add/{magazineId}/{scientificArea}")
		public ResponseEntity<String> multiUploadFileModel(@ModelAttribute UploadModel model,HttpServletRequest request,@PathVariable String magazineId, @PathVariable String scientificArea) {


	        try {
	        	indexUploadedFile(model,request,magazineId,scientificArea);

	        } catch (IOException e) {
	            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	        }

	        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);

	    }
		    
		    
	    //save file
	    private String saveUploadedFile(MultipartFile file) throws IOException {
	    	String retVal = null;
            if (! file.isEmpty()) {
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + file.getOriginalFilename());
	            Files.write(path, bytes);
	            retVal = path.toString();
            }
            System.out.println("PATH"+retVal);
            return retVal;
	    }
	    
	    private void indexUploadedFile(UploadModel model,HttpServletRequest request,String magazineId,String scientificArea) throws IOException{
	    	
	    	for (MultipartFile file : model.getFiles()) {

	            if (file.isEmpty()) {
	                continue; //next please
	            }
	            String fileName = saveUploadedFile(file);
	            
	            if(fileName != null){
	            	
	            	HashMap<String, Object> radovi = new HashMap<String, Object>();
	        		String naslov = "naslov";
	        		String kljucniPojmovi = "kljucniPojmovi";
	        		String apstrakt = "apstrakt";
	        		String pdf = "pdf";
	        		String naucnaOblast = "naucnaOblast";
	        		String autorEmail = "autorEmail";
	        		String radStatus = "radStatus";
	        		String koautori = "koautori";
	        		
	        		Rad rad = new Rad();
	        		
	        		rad.setKoautori(model.getKoautori());
	        		radovi.put(koautori,rad.getKoautori());
	        		
	        		rad.setRadStatus(RadStatus.U_TOKU);
	        		radovi.put(radStatus,rad.getRadStatus());
	        		
	            	rad.setNaslov(model.getNaslov());
	            	radovi.put(naslov, rad.getNaslov());
	        		
	            	rad.setPdf(fileName);
	          		radovi.put(pdf,rad.getPdf());
	            	
	            	User us = (User)request.getSession().getAttribute("user");
	            	User autor = userSer.findByEmail(us.getEmail()); 
	            	rad.setAutor(autor);
	            	radovi.put(autorEmail, autor.getEmail());
	            	
	            	NaucnaOblast sa = noSer.findByNaziv(scientificArea);
	            	rad.setNaucnaOblast(sa);
	            	radovi.put(naucnaOblast, sa);
	            	
	            	String kljucniPojam = model.getKljucniPojmovi();
	            	rad.setKljucniPojmovi(kljucniPojam);
	            	radovi.put(kljucniPojmovi, rad.getKljucniPojmovi());
	            	
	            	rad.setApstrakt(model.getApstrakt());
	            	radovi.put(apstrakt, rad.getApstrakt());

	            	radSer.save(rad);
	            	Casopis casopis = casopisSer.getCasopis(magazineId);
	            	casopis.getRadovi().add(rad);
	            	
	            	
	            	casopisSer.save(casopis);
	            	rad.setCasopisRad(casopis);
	            	radSer.save(rad);
	            	
	            	String processInstanceId = task.getProcessInstanceId();
	        		runtimeService.setVariable(processInstanceId, "rad", radovi);
	        		formService.submitTaskForm(task.getId(), radovi);       	
	            }
	    	}
	    }
	    
	    
	    @GetMapping(path = "/getRad", produces = "application/json")
	    public @ResponseBody FormFieldsDto getRegistracija() {
			if(CasopisController.pi == null) {
				pi = runtimeService.startProcessInstanceByKey("prijavaRada");			
			}
			task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
			
			TaskFormData tfd = formService.getTaskFormData(task.getId());
			List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
			
	        return new FormFieldsDto(task.getId(), CasopisController.pi.getId(), properties);
	    }
	    
	    @PostMapping("/pregledRadaDobar/{idRada}")
	    public String pregledRadaDobar(@PathVariable String idRada,HttpServletRequest request) {
	    	User us = (User) request.getSession().getAttribute("user");
	    	task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
	    	if(task.getAssignee().equals(us.getEmail())) {
		    	HashMap<String, Object> tematskiRad = new HashMap<String, Object>();
		    	tematskiRad.put("tematskiDobar", true);
		    	Rad rad = radSer.findById(idRada);
		    	String processInstanceId = task.getProcessInstanceId();
	    		runtimeService.setVariable(processInstanceId, "tematskiDobar", true);
	    		formService.submitTaskForm(task.getId(),tematskiRad);   
    		
	    		return "pregledRadaDobar";
	    	}else {
	    		return "nijedobarkorisnik";
	    	}
	    }
	    
	    @PostMapping("/pregledRadaLos/{idRada}")
	    public String pregledRadaLos(@PathVariable String idRada,HttpServletRequest request) {
	    	
	    	User us = (User) request.getSession().getAttribute("user");
	    	task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
	    	if(task.getAssignee().equals(us.getEmail())) {
		    	HashMap<String, Object> tematskiRad = new HashMap<String, Object>();
		    	tematskiRad.put("tematskiDobar", false);
		    	Rad rad = radSer.findById(idRada);
		    	rad.setRadStatus(RadStatus.ODBIJEN);
		    	radSer.save(rad);
		    	String processInstanceId = task.getProcessInstanceId();
	    		runtimeService.setVariable(processInstanceId, "tematskiDobar", false);
	    		formService.submitTaskForm(task.getId(),tematskiRad);  
	    	
	    		return "pregledRadaLos";
	    	}else {
	    		return "nijedobarkorisnik";
	    	}
	    }
	    
	    
	    @RequestMapping(value = "/download/{idRada}", method = RequestMethod.GET)
		public ResponseEntity<ByteArrayResource> download(@PathVariable String idRada) throws IOException {
			
			Rad rad = radSer.findById(idRada);
			System.out.println(rad.getPdf());
			String baseDirectory = "";
			Path bookPath = Paths.get(baseDirectory, rad.getPdf());
			System.out.println("\n\n\tbook path: " + bookPath.toString());
			
			byte[] bookContent = Files.readAllBytes(bookPath);
		    ByteArrayResource resource = new ByteArrayResource(bookContent);

		    return ResponseEntity.ok()
		            .contentLength(bookContent.length)
		            .contentType(MediaType.parseMediaType("application/pdf"))
		            .body(resource);
		}
	    
	    @PostMapping("/pregledRadaDobarPdf/{idRada}")
	    public String pregledRadaDobarPdf(@PathVariable String idRada,HttpServletRequest request) {
	    	User us = (User) request.getSession().getAttribute("user");
	    	task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
		    if(task.getAssignee().equals(us.getEmail())) {
	    		Rad rad = radSer.findById(idRada);
		    	HashMap<String, Object> radovi = new HashMap<String, Object>();
		    	String id = "id";
	    		String naslov = "naslov";
	    		String kljucniPojmovi = "kljucniPojmovi";
	    		String apstrakt = "apstrakt";
	    		String pdf = "pdf";
	    		String naucnaOblast = "naucnaOblast";
	    		String autorEmail = "autorEmail";
	    		String radStatus = "radStatus";
	    		radovi.put(id, rad.getId());
	    		radovi.put(naslov, rad.getNaslov());
	    		radovi.put(kljucniPojmovi, rad.getKljucniPojmovi());
	    		radovi.put(apstrakt,rad.getApstrakt());
	    		radovi.put(pdf, rad.getPdf());
	    		radovi.put(naucnaOblast,rad.getNaucnaOblast());
	    		radovi.put(autorEmail,rad.getAutor().getEmail());
	    		radovi.put(radStatus,rad.getRadStatus());
	    		
	    		String processInstanceId = task.getProcessInstanceId();
	    		runtimeService.setVariable(processInstanceId, "pdfDobar", true);
	    		runtimeService.setVariable(processInstanceId, "rad", radovi);
	    		formService.submitTaskForm(task.getId(),radovi);       
	    	
	    		return "pregledRadaDobarPdf";
		    }else {
		    	return "nijedobarkorisnik";
		    }
	    }
	    
	    @PostMapping("/pregledRadaLosPdf/{idRada}")
	    public String pregledRadaLosPdf(@PathVariable String idRada,HttpServletRequest request) {
	    	
	    	User us = (User) request.getSession().getAttribute("user");
	    	task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
	    	if(task.getAssignee().equals(us.getEmail())) {
		    	Rad rad = radSer.findById(idRada);
		    	rad.setRadStatus(RadStatus.KOREKCIJA_PDF);
		    	radSer.save(rad);
		    	HashMap<String, Object> pdfLos = new HashMap<String, Object>();
		    	pdfLos.put("pdfDobar", false);
		    	pdfLos.put("rad",rad);
		    	String processInstanceId = task.getProcessInstanceId();
	    		runtimeService.setVariable(processInstanceId, "pdfDobar", false);
	    		runtimeService.setVariable(processInstanceId, "pdfLos", pdfLos);
	    		formService.submitTaskForm(task.getId(),pdfLos);  
		    	
		    	return "pregledRadaLosPdf";
	    	}else {
	    		return "nijedobarkorisnik";
	    	}
	    }
		
	    @GetMapping(path = "/getKorisnikovPdf", produces = "application/json")
	    public List<Rad> getKorisnikPdf(HttpServletRequest request) {		
			
			User user = (User) request.getSession().getAttribute("user");
			List<Rad> rad = radSer.findByAutor(user);
			List<Rad> radovi = new ArrayList<Rad>();
			for(int i=0; i < rad.size();i++) {
				if(rad.get(i).getRadStatus().equals(RadStatus.KOREKCIJA_PDF))
					radovi.add(rad.get(i));
			}
			return radovi;
	    }
	    
		@PostMapping("/updatePdf/{radId}")
		public ResponseEntity<String> updatePdf(@ModelAttribute UploadModel model, @PathVariable String radId,HttpServletRequest request) throws IOException {
			
			User us = (User) request.getSession().getAttribute("user");
			task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
			if(task.getAssignee().equals(us.getEmail())) {
				HashMap<String, Object> radovi = new HashMap<String, Object>();
	    		String id = "id";
				String naslov = "naslov";
	    		String kljucniPojmovi = "kljucniPojmovi";
	    		String apstrakt = "apstrakt";
	    		String pdf = "pdf";
	    		String naucnaOblast = "naucnaOblast";
	    		String autorEmail = "autorEmail";
	    		String radStatus = "radStatus";
				
				
				for (MultipartFile file : model.getFiles()) {
	
		            if (file.isEmpty()) {
		                continue; //next please
		            }
		            String fileName = saveUploadedFile(file);
		            
		            Rad rad = radSer.findById(radId);
		            
		            radovi.put(id,rad.getId());
		            
		            rad.setRadStatus(RadStatus.U_TOKU);
		            radovi.put(radStatus,rad.getRadStatus());
	            
	            	radovi.put(naslov, rad.getNaslov());
	        		
	            	rad.setPdf(fileName);
	          		radovi.put(pdf,rad.getPdf());
	            	
	            	radovi.put(autorEmail, rad.getAutor().getEmail());
	            	
	            	radovi.put(naucnaOblast, rad.getNaucnaOblast());
	            	
	            	radovi.put(kljucniPojmovi, rad.getKljucniPojmovi());
	            	
	            	radovi.put(apstrakt, rad.getApstrakt());
	
	            	radSer.save(rad);
	            	
	            	String processInstanceId = task.getProcessInstanceId();
	            	
	        		runtimeService.setVariable(processInstanceId, "rad", radovi);
	        		formService.submitTaskForm(task.getId(), radovi);   
		            
		            
		        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);
				}
				return new ResponseEntity<String>("UnSuccessfully uploaded!", HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Nije dobar korisnik!", HttpStatus.OK);
			}
	    }
		
		@PostMapping("/izborRecezenata/{services}")
		public String izborRecezenata(@PathVariable List<String> services, HttpServletRequest request) {
			User us = (User) request.getSession().getAttribute("user");
			task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
			if(task.getAssignee().equals(us.getEmail())) {
				String processInstanceId = task.getProcessInstanceId();
				HashMap<String, Object> podaci = (HashMap<String, Object>) runtimeService.getVariable(processInstanceId, "rad");
				Rad rad = radSer.findById(podaci.get("id").toString());
				rad.setRadStatus(RadStatus.NA_RECENZIRANJU);
				ArrayList<String> listaRecezenata = new ArrayList<String>();
				if(services.size()>=2) {
					for(int i=0;i < services.size();i++) {
						User recezent = userSer.findByEmail(services.get(i));
						rad.getRecezenti().add(recezent);
						listaRecezenata.add(recezent.getEmail());
					}
					radSer.save(rad);
					runtimeService.setVariable(processInstanceId, "recezenti", listaRecezenata);
					runtimeService.setVariable(processInstanceId, "brojRecezenata", true);
					formService.submitTaskForm(task.getId(), podaci);
					
				}else {
					runtimeService.setVariable(processInstanceId, "brojRecezenata", false);
				}
				return "ZavrsioIzborRecezenata";
			}else {
				return "nijedobarkorisnik";
			}
		}
		
		

		@PostMapping("/obavljanjeRecenzije/{radId}")
		public String obavljanjeRecenzije(@RequestBody RecenzijaDTO recenzijaDTO,@PathVariable String radId,HttpServletRequest request) {
			
			User us = (User) request.getSession().getAttribute("user");
			task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
			if(task.getAssignee().equals(us.getEmail())) {
				User recezent = (User) request.getSession().getAttribute("user");
				Rad rad = radSer.findById(radId);
				Recenzija rec = new Recenzija();
				rec.setAutorRecenzije(recezent);
				rec.setRad(rad);
				rec.setKomentar(recenzijaDTO.getKomentar());
				rec.setKomentarZaUrednika(recenzijaDTO.getKomentarZaUrednika());
				recSer.save(rec);
				
				formService.submitTaskForm(task.getId(), new HashMap<String, Object>());
				
				return "ZavrsioObavljanjeRecenzije";
			}else {
				return "nijedobarkorisnik";
			}
		}
	
		@GetMapping(path = "/getRecezentovRad", produces = "application/json")
	    public List<Rad> getRecezentovRad(HttpServletRequest request) {
			User user = (User) request.getSession().getAttribute("user");
			ArrayList<Rad> radovi = new ArrayList<Rad>();
			List<Rad> radoviRecezenta = radSer.findAll();
			for(int i = 0; i < radoviRecezenta.size();i++) {
				for(int j=0;j< radoviRecezenta.get(i).getRecezenti().size();j++) {
					if(radoviRecezenta.get(i).getRecezenti().get(j).getEmail().equals(user.getEmail())
							&& radoviRecezenta.get(i).getRadStatus().equals(RadStatus.NA_RECENZIRANJU)) {
						radovi.add(radoviRecezenta.get(i));
					}
				}
			}
			return radovi;
	    }
	
	
		@GetMapping(path = "/getRecenzije", produces = "application/json")
	    public List<Recenzija> getRecenzije() {
			
			task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
			String processInstanceId = task.getProcessInstanceId();
			HashMap<String, Object> podaci = (HashMap<String, Object>) runtimeService.getVariable(processInstanceId, "rad");
	        Rad rad = radSer.findById(podaci.get("id").toString());
	        
			List<Recenzija> recenzijeRada = recSer.findByRadId(rad.getId());
	       
			return recenzijeRada;
	    }
		
		
		 @PostMapping("/urednikDobarRad")
		 public String urednikDobarRad(HttpServletRequest request) {
		    	
			 	User us = (User) request.getSession().getAttribute("user");
		    	task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
		    	if(task.getAssignee().equals(us.getEmail())) {
			    	String processInstanceId = task.getProcessInstanceId();
			    	HashMap<String, Object> podaci = (HashMap<String, Object>) runtimeService.getVariable(processInstanceId, "rad");
			    	Rad rad = radSer.findById(podaci.get("id").toString());
			    	rad.setRadStatus(RadStatus.PRIHVACEN);
			    	rad.setKonacnaVerzija(rad.getPdf());
			    	radSer.save(rad);
			    	
			    	runtimeService.setVariable(processInstanceId, "urednikovaOdluka", "dobar");
			    	formService.submitTaskForm(task.getId(),new HashMap<String, Object>());  
			    	
			    	return "urednikDobarRad";
		    	}else {
		    		return "nijedobarkorisnik";
		    	}
		    }
		
		 @PostMapping("/urednikLosRad")
		 public String urednikLosRad(HttpServletRequest request) {
		    	
			 	User us = (User) request.getSession().getAttribute("user");
		    	task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
		    	if(task.getAssignee().equals(us.getEmail())) {
			    	String processInstanceId = task.getProcessInstanceId();
			    	HashMap<String, Object> podaci = (HashMap<String, Object>) runtimeService.getVariable(processInstanceId, "rad");
			    	Rad rad = radSer.findById(podaci.get("id").toString());
			    	rad.setRadStatus(RadStatus.ODBIJEN);
			    	radSer.save(rad);
			    	
			    	runtimeService.setVariable(processInstanceId, "urednikovaOdluka", "odbijen");
			    	formService.submitTaskForm(task.getId(),new HashMap<String, Object>());  
			    	
			    	return "urednikLosRad";
		    	}else {
		    		return "nijedobarkorisnik";
		    	}
		    }
		 
		 @PostMapping("/urednikVecaPromjena")
		 public String urednikVecaPromjena(HttpServletRequest request) {
		    	
			 	User us = (User) request.getSession().getAttribute("user");
		    	task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
		    	if(task.getAssignee().equals(us.getEmail())) {
			    	String processInstanceId = task.getProcessInstanceId();
			    	HashMap<String, Object> podaci = (HashMap<String, Object>) runtimeService.getVariable(processInstanceId, "rad");
			    	Rad rad = radSer.findById(podaci.get("id").toString());
			    	
			    	runtimeService.setVariable(processInstanceId, "urednikovaOdluka", "uslovno");
			    	runtimeService.setVariable(processInstanceId, "vecaIzmjena", true);
			    	formService.submitTaskForm(task.getId(),new HashMap<String, Object>());  
			    	
			    	return "urednikVecaPromjena";
		    	}else {
		    		return "nijedobarkorisnik";
		    	}
		    }
		 
		 
		 @PostMapping("/urednikManjaPromjena")
		 public String urednikManjaPromjena(HttpServletRequest request) {
		    	
			 	User us = (User) request.getSession().getAttribute("user");
		    	
				task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
				if(task.getAssignee().equals(us.getEmail())) {
					String processInstanceId = task.getProcessInstanceId();
			    	HashMap<String, Object> podaci = (HashMap<String, Object>) runtimeService.getVariable(processInstanceId, "rad");
			    	Rad rad = radSer.findById(podaci.get("id").toString());
			    	
			    	runtimeService.setVariable(processInstanceId, "urednikovaOdluka", "uslovno");
			    	runtimeService.setVariable(processInstanceId, "vecaIzmjena", false);
			    	formService.submitTaskForm(task.getId(),new HashMap<String, Object>());  
			    	
			    	return "urednikManjaPromjena";
				}else {
					return "nijedobarkorisnik";
				}
		    }
		 
		 
		 @GetMapping(path = "/getKorisnikovRad", produces = "application/json")
		    public List<Rad> getKorisnikovRad(HttpServletRequest request) {
				User us = (User) request.getSession().getAttribute("user");
				List<Rad> rad = radSer.findByAutor(us);
			 	return rad;
		    }
		 /*
		 @GetMapping(path = "/getKorisnikovRadRecenzije", produces = "application/json")
		    public 	HashMap<Rad, List<Recenzija>> getKorisnikovRadRecenzije(HttpServletRequest request) {
				User us = (User) request.getSession().getAttribute("user");
				HashMap<Rad, List<Recenzija>> hashRecenzijeRad = new HashMap<Rad, List<Recenzija>>();
				List<Rad> rad = radSer.findByAutor(us);
				List<Recenzija> recenzijeRada = null;
			     
				for(int i=0; i < rad.size();i++) {
					recenzijeRada =  recSer.findByRadId(rad.get(i).getId());
					hashRecenzijeRad.put(rad.get(i), recenzijeRada);
				}
				  
			 	return hashRecenzijeRad;
		    }
		 */
		 @PostMapping("/korisnikNapravioIzmjene/{radId}")
		 public ResponseEntity<String> korisnikNapravioIzmjene(@ModelAttribute UploadModel model,@PathVariable String radId, HttpServletRequest request) throws IOException {
		     User us = (User) request.getSession().getAttribute("user");	
			 task = taskService.createTaskQuery().processInstanceId(CasopisController.pi.getId()).list().get(0);
			 if(task.getAssignee().equals(us.getEmail())) {	
			 	HashMap<String, Object> radovi = new HashMap<String, Object>();
	    		String id = "id";
				String naslov = "naslov";
	    		String kljucniPojmovi = "kljucniPojmovi";
	    		String apstrakt = "apstrakt";
	    		String pdf = "pdf";
	    		String naucnaOblast = "naucnaOblast";
	    		String autorEmail = "autorEmail";
	    		String radStatus = "radStatus";
				
				
				for (MultipartFile file : model.getFiles()) {

		            if (file.isEmpty()) {
		                continue; //next please
		            }
		            String fileName = saveUploadedFile(file);
		            
		            Rad rad = radSer.findById(radId);
		            
		            radovi.put(id,rad.getId());
		            
		            rad.setRadStatus(RadStatus.NA_RECENZIRANJU);
		            radovi.put(radStatus,rad.getRadStatus());
	            
	            	radovi.put(naslov, rad.getNaslov());
	        		
	            	rad.setPdf(fileName);
	          		radovi.put(pdf,rad.getPdf());
	            	
	            	radovi.put(autorEmail, rad.getAutor().getEmail());
	            	
	            	radovi.put(naucnaOblast, rad.getNaucnaOblast());
	            	
	            	radovi.put(kljucniPojmovi, rad.getKljucniPojmovi());
	            	
	            	radovi.put(apstrakt, rad.getApstrakt());

	            	radSer.save(rad);
	            	
	            	String processInstanceId = task.getProcessInstanceId();
	            	
	        		runtimeService.setVariable(processInstanceId, "rad", radovi);
	        		formService.submitTaskForm(task.getId(), radovi);   
		            
		            
		        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);
				}
				return new ResponseEntity<String>("UnSuccessfully uploaded!", HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("nijedobarkorisnik", HttpStatus.OK);
			 }

		 }
		
}


