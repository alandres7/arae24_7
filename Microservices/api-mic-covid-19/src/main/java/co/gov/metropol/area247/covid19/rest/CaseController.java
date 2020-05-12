package co.gov.metropol.area247.covid19.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.covid19.model.C19Case;
import co.gov.metropol.area247.covid19.svc.ICaseSvc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Api de Casos de COVID-19 Valle de Aburrá")
@RestController
@RequestMapping("/api")
public class CaseController {
	
	@Autowired
	private ICaseSvc caseSvc;
	
	@ApiOperation(value = "/c19cases", notes = "Listado de casos discriminados de COVID-19")
	@GetMapping("/c19cases")
	public ResponseEntity<List<C19Case>> getCovidCases() {
		return new ResponseEntity<>(caseSvc.getCasosCovid("https://www.datos.gov.co/resource/gt2j-8ykr.json"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "/update-c19case", notes = "actualización de casos COVID-19 por tipo de atención")
	@PutMapping("/update-c19case")
	public ResponseEntity<Boolean> updateCovidCases() {
		try {
			return new ResponseEntity<>(caseSvc.updateCasosCovid(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "/c19case", notes = "Número de casos discriminados de COVID-19 - Medellin")
	@GetMapping("/c19case")
	public ResponseEntity<String> getCovidCaseMedellin() {
		return new ResponseEntity<>(caseSvc.getNumCasos(), HttpStatus.OK);
	}

}
