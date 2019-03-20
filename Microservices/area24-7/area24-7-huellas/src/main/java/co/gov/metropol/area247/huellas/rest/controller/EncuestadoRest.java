package co.gov.metropol.area247.huellas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.huellas.model.EncuestadoDto;
import co.gov.metropol.area247.huellas.svc.IHuellasEncuestadoSvc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@Api(value = "Huellas")
//@RestController
//@RequestMapping("/api")
public class EncuestadoRest {
	
	@Autowired
	@Qualifier("encuestadoSvc")
	IHuellasEncuestadoSvc encuestadoSvc;
	
	@ResponseBody
	@ApiOperation(value="/huellas/encuestado", notes="Registrar un encuestado en un tipo de huella")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value= {@ApiResponse(code=404,message="Not provided")})
	@PostMapping(value="/huellas/encuestado")
	public ResponseEntity<?> registrarEncuestado(@RequestBody EncuestadoDto encuestado){
		System.out.println("");
		encuestado = encuestadoSvc.registro(encuestado);
		return new ResponseEntity<EncuestadoDto>(encuestado, HttpStatus.CREATED);
		
	}
	
	@ResponseBody
	@ApiOperation(value="/huellas/encuestado/{idEncuestado}", notes="Obtener un encuestado especifíco")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value= {
			@ApiResponse(code=302,message="Se encontró el encuestado específico con éxito"),
			@ApiResponse(code=400,message="Error en la petición"),
			@ApiResponse(code=403,message="Acceso no permitido"),
			@ApiResponse(code=404,message="No se encontró el enccuestado especificado")
			})
	@GetMapping(value="/huellas/encuestado/{idEncuestado}")
	public ResponseEntity<?> obtenerEncuestado(@PathVariable Long idEncuestado){
		try {
			return new ResponseEntity<EncuestadoDto>(encuestadoSvc.getEncuestado(idEncuestado), HttpStatus.FOUND);
		}catch(Exception e) {
			return new ResponseEntity<String>("Error en los parámetros de la petición", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseBody
	@ApiOperation(value="/huellas/encuestados", notes="Registrar un encuestado en un tipo de huella")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value= {@ApiResponse(code=404,message="Not provided")})
	@GetMapping(value="/huellas/encuestados")
	public ResponseEntity<?> obtenerEncuestados(){
		return new ResponseEntity<EncuestadoDto>(encuestadoSvc.getEncuestado(null), HttpStatus.FOUND);		
	}
	
	
	
	
	
}
