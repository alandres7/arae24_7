package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.centrocontrol.model.Afectacion;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAfectacionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api")
public class AfectacionRestController {

	@Autowired
	ICentroControlAfectacionService afectacionService;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/afectacion", method = RequestMethod.GET)
	public ResponseEntity<?> afectacionObtenerTodos(){
		try{
			List<Afectacion> afectaciones = afectacionService.afectacionObtenerTodos();
			return new ResponseEntity<List<Afectacion>>(afectaciones,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo retornar las afectaciones; "
		    + e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/afectacion/{idAfectacion}", method = RequestMethod.GET)
	public ResponseEntity<?> afectacionObtenerPorId(@PathVariable(name="idAfectacion")Long idAfectacion){
		try{
			Afectacion afectacion = afectacionService.afectacionObtenerPorId(idAfectacion);
			if(afectacion!=null){
				return new ResponseEntity<Afectacion>(afectacion,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentra la afectacion; revisar id"
						,HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo retornar la afectacion;"
					+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/afectacion", method = RequestMethod.POST)
	public ResponseEntity<?> afectacionGuardar(@RequestBody Afectacion afectacion){
		try{
			Afectacion afectacionAuxiliar = afectacionService.afectacionGuardar(afectacion);
			return new ResponseEntity<Afectacion>(afectacionAuxiliar,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo guardar la afectacion;"
					+e,HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/afectacion/{idAfectacion}", method = RequestMethod.DELETE)
	public ResponseEntity<?> afectacionBorrar(@PathVariable(name="idAfectacion") Long idAfectacion){
		boolean afectacionBorrado = afectacionService.afectacionBorrar(idAfectacion);
		if(afectacionBorrado){
			return new ResponseEntity<String>("Borrado exitoso de la afectacion",
					HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se pudo borrar la afectacion",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
