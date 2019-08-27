package upp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upp.model.NaucnaOblast;
import upp.service.NaucnaOblastService;

@RestController
@RequestMapping(value = "/naucnaOblast")
public class NaucnaOblastKController {

	
	@Autowired 
	NaucnaOblastService noSer;
	
	@GetMapping(value="getAll", consumes="application/json")
	public ResponseEntity<List<NaucnaOblast>> getAll() {
		List<NaucnaOblast> all = noSer.findAll();
    	return new ResponseEntity<List<NaucnaOblast>>(all, HttpStatus.OK);
	}
}
