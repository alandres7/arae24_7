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

import co.gov.metropol.area247.centrocontrol.model.AutoridadCompetente;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAutoridadCompetenteService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api")
public class AutoridadCompetenteRestController {

	@Autowired
	ICentroControlAutoridadCompetenteService autoridadCompetenteService;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/autoridad", method = RequestMethod.GET)
	public ResponseEntity<?> autoridadCompetenteObtenerTodos(){
		try{
			List<AutoridadCompetente> autoridades =autoridadCompetenteService
					.autoridadCompetenteObtenerTodos();
			return new ResponseEntity<List<AutoridadCompetente>>(autoridades,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo retornar las autoridades; "
		+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/autoridad/{idAutoridad}", method = RequestMethod.GET)
	public ResponseEntity<?> autoridadCompetenteObtenerPorId(@PathVariable(name="idAutoridad")Long idAutoridad){
		try{
			AutoridadCompetente autoridad = autoridadCompetenteService
					.autoridadCompetenteObtenerPorId(idAutoridad);
			if(autoridad!=null){
				return new ResponseEntity<AutoridadCompetente>(autoridad,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentra la autoridad; revisar id"
						,HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo retornar la autoridad; "
					+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/autoridad", method = RequestMethod.POST)
	public ResponseEntity<?> autoridadCompetenteGuardar(@RequestBody AutoridadCompetente autoridadCompetente){
		try{
			AutoridadCompetente autoridadAuxiliar = autoridadCompetenteService
					.autoridadCompetenteGuardar(autoridadCompetente);
			return new ResponseEntity<AutoridadCompetente>(autoridadAuxiliar,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo guardar la autoridad; "
					+e,HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/autoridad/{idAutoridad}", method = RequestMethod.DELETE)
	public ResponseEntity<?> autoridadCompetenteBorrar(@PathVariable(name="idAutoridad") Long idAutoridadCompetente){
		boolean resultadoBorrado = autoridadCompetenteService.autoridadCompetenteBorrar(idAutoridadCompetente);
		if(resultadoBorrado){
			return new ResponseEntity<String>("Borrado exitoso de la autoridad",
					HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se pudo borrar la autoridad",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
