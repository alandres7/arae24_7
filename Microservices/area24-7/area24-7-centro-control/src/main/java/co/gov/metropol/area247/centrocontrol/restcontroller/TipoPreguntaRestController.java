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

import co.gov.metropol.area247.centrocontrol.model.TipoPregunta;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlTipoPreguntaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Tipos Preguntas Formularios")
@RequestMapping(value = "/api")
public class TipoPreguntaRestController {
	@Autowired
	ICentroControlTipoPreguntaService tipoPreguntaService;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/tipopregunta/", method = RequestMethod.POST)
	public ResponseEntity<?> tipoPreguntaGuardar(@RequestBody TipoPregunta tipoPregunta){
		TipoPregunta tipoPreguntaAux;
		try{
			tipoPreguntaAux = tipoPreguntaService.tipoPreguntaGuardar(tipoPregunta);
			if(tipoPreguntaAux != null){
				return new ResponseEntity<TipoPregunta>(tipoPreguntaAux,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se pudo guardar el tipo de pregunta, revisa los datos"
						,HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo guardar el tipo de pregunta, revisa los datos"
					,HttpStatus.BAD_REQUEST);
		}
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/tipopregunta/", method = RequestMethod.GET)
	public ResponseEntity<?> tipoPreguntaObtenerTodos(){
		List<TipoPregunta> tipoPreguntaTodos;
		try{
			tipoPreguntaTodos = tipoPreguntaService.tipoPreguntaObtenerTodos();
			return new ResponseEntity<List<TipoPregunta>>(tipoPreguntaTodos,HttpStatus.OK); 
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo retornar los tipos de preguntas: "+e
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/tipopregunta/{idTipoPregunta}", method = RequestMethod.GET)
	public ResponseEntity<?> tipoPreguntaObtenerPorId(@PathVariable ("idTipoPregunta") Long idTipoPregunta){
		try{
			TipoPregunta tipoPregunta = tipoPreguntaService.tipoPreguntaObtenerPorId(idTipoPregunta);
			if(tipoPregunta!=null){
				return new ResponseEntity<TipoPregunta>(tipoPregunta,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentra el tipo pregunta",HttpStatus.NOT_FOUND);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Error recuperando el tipo pregunta",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/tipopregunta/{idTipoPregunta}", method = RequestMethod.DELETE)
	public ResponseEntity<?> tipoPreguntaBorrar(@PathVariable ("idTipoPregunta") Long idTipoPregunta){
		try{
			boolean tipoPreguntaBorrado = tipoPreguntaService.tipoPreguntaEliminar(idTipoPregunta);
			if(tipoPreguntaBorrado){
				return new ResponseEntity<String>("Tipo Pregunta borrado",HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se pudo borrar el Tipo Pregunta",
						HttpStatus.BAD_REQUEST);	
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error al borrar el tipo Pregunta",
					HttpStatus.INTERNAL_SERVER_ERROR);		
		}
	}
}
