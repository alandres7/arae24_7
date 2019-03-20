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

import co.gov.metropol.area247.centrocontrol.model.RecursoVigia;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlRecursoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api")
public class RecursoRestController {

	@Autowired
	ICentroControlRecursoService recursoService;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/recurso", method = RequestMethod.GET)
	public ResponseEntity<?> recursoObtenerTodos(){
		try{
			List<RecursoVigia> recursos = recursoService.recursoObtenerTodos();
			return new ResponseEntity<List<RecursoVigia>>(recursos,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo retornar los recursos; "
		    + e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/recurso/{idRecurso}", method = RequestMethod.GET)
	public ResponseEntity<?> recursoObtenerPorId(@PathVariable(name="idRecurso")Long idRecurso){
		try{
			RecursoVigia recurso = recursoService.recursoObtenerPorId(idRecurso);
			if(recurso!=null){
				return new ResponseEntity<RecursoVigia>(recurso,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentra el recurso; revisar id"
						,HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo retornar el recurso; "
					+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/recurso", method = RequestMethod.POST)
	public ResponseEntity<?> recursoGuardar(@RequestBody RecursoVigia recurso){
		try{
			RecursoVigia recursoAuxiliar = recursoService.recursoGuardar(recurso);
			return new ResponseEntity<RecursoVigia>(recursoAuxiliar,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo guardar el recurso; "
					+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/recurso/{idRecurso}", method = RequestMethod.DELETE)
	public ResponseEntity<?> recursoBorrar(@PathVariable(name="idRecurso") Long idRecurso){
		boolean resultadoBorrado = recursoService.recursoBorrar(idRecurso);
		if(resultadoBorrado){
			return new ResponseEntity<String>("Borrado exitoso del recurso",
					HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se pudo borrar el recurso",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
