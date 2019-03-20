package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.centrocontrol.model.Formulario;
import co.gov.metropol.area247.centrocontrol.model.Pregunta;
import co.gov.metropol.area247.centrocontrol.model.TipoPregunta;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlFormularioService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlPreguntaService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlTipoPreguntaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Preguntas Formularios")
@RequestMapping(value = "/api")
public class PreguntaRestController {
	@Autowired
	ICentroControlTipoPreguntaService tipoPreguntaService;
	@Autowired
	ICentroControlPreguntaService preguntaService;
	@Autowired
	ICentroControlFormularioService formularioService;
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/pregunta/", method = RequestMethod.POST)
	public ResponseEntity<?> preguntaGuardar(@RequestParam (value = "idPregunta", required = false) Long idPregunta,
			@RequestParam (value = "IdFormulario", required = true) Long idFormulario,
			@RequestParam (value = "IdTipoPregunta", required = true) Long idTipoPregunta,
			@RequestParam (value = "descripcion", required = true) String descripcion,
			@RequestParam (value = "orden", required = true) int orden){
		try{
			Pregunta pregunta;
			if(idPregunta!=null){
				 pregunta = preguntaService.preguntaObtenerPorId(idPregunta);
			}else{
				 pregunta = new Pregunta();
			}
			if(pregunta !=null){
				 pregunta.setDescripcion(descripcion);
				 Formulario formularioAux = formularioService.formularioObtenerPorId(idFormulario);
				 pregunta.setFormulario(formularioAux);
				 TipoPregunta tipoPreguntaAux = tipoPreguntaService
						 .tipoPreguntaObtenerPorId(idTipoPregunta);
				 pregunta.setTipoPregunta(tipoPreguntaAux);
				 pregunta.setOrden(orden);
				 Pregunta preguntaAux = preguntaService.preguntaGuardar(pregunta);
				 return new ResponseEntity<Pregunta>(preguntaAux,HttpStatus.OK);
			 }else{
				 return new ResponseEntity<String>("Hubo un error en la creacion de la "
				 		+ "pregunta, revisar los campos",HttpStatus.BAD_REQUEST);
			 }
		}catch(Exception e){
			 return new ResponseEntity<String>("Hubo un error en la creacion de la pregunta; "+e
					 ,HttpStatus.OK);
		}	
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/pregunta/", method = RequestMethod.GET)
	public ResponseEntity<?> preguntaObtenerTodos(){
		try{
			List<Pregunta> preguntas = preguntaService.preguntaObtenerTodas();
			return new ResponseEntity<List<Pregunta>>(preguntas,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error recuperando las preguntas; "+e
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/pregunta/{idPregunta}", method = RequestMethod.GET)
	public ResponseEntity<?> preguntaObtenerPorId(@PathVariable("idPregunta")Long idPregunta){
		try{
			Pregunta pregunta = preguntaService.preguntaObtenerPorId(idPregunta);
			if(pregunta!=null){
				return new ResponseEntity<Pregunta>(pregunta,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuenta la pregunta, "
						+ "revisar los datos",HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error buscando la pregunta, "
					+ "revisa los datos y vuelve a intentar; "+e,HttpStatus.BAD_REQUEST);
		}
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/pregunta/porformulario/{idFormulario}", method = RequestMethod.GET)
	public ResponseEntity<?> preguntaObtenerPorFormulario(@PathVariable("idFormulario")Long idFormulario){
		try{
			Formulario formulario = formularioService.formularioObtenerPorId(idFormulario);
			if(formulario!=null){
				List<Pregunta> preguntas = preguntaService.preguntaObtenerPorIdFormulario(idFormulario);
				return new ResponseEntity<List<Pregunta>>(preguntas,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("Id formulario no valido",HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un problema al recuperar las "
					+ "preguntas por formulario, verificar los datos; "+e,HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/pregunta/{idPregunta}", method = RequestMethod.DELETE)
	public ResponseEntity<?> preguntaBorrar(@PathVariable("idPregunta") Long idPregunta){
		try{
			boolean preguntaBorrada = preguntaService.preguntaEliminar(idPregunta);
			if(preguntaBorrada){
				return new ResponseEntity<String>("Pregunta borrada",HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se pudo borrar la pregunta,"
						+ " revisar los datos",HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error al eliminar la "
					+ "pregunta, revisar los campos; "+e,HttpStatus.BAD_REQUEST);
		}
	}
}
