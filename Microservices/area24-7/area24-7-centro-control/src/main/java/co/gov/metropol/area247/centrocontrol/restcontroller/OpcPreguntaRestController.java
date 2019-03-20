package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.centrocontrol.model.OpcPregunta;
import co.gov.metropol.area247.centrocontrol.model.dto.OpcPreguntaDto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlOpcPreguntaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "centro-control")
@RestController
@RequestMapping(value="/api")
public class OpcPreguntaRestController {
	
	@Autowired
	private ICentroControlOpcPreguntaService opcPreguntaSvc;
	
	@ResponseBody
	@ApiOperation(value="/opcion-pregunta",notes="Crear una nueva opción de pregunta")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Creada. creación exitosa."),
			@ApiResponse(code = 400, message = "Fallo en la creación."),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
			@ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
			@ApiResponse(code = 404, message = "Not Found. La url que se intenta acceder no se encuetra disponible o no existe"),
			@ApiResponse(code = 409, message = "Conflicto. La opción de que se intenta crear ya existe"),
			@ApiResponse(code = 500, message = "Error interno.")
	})
	@RequestMapping(value = "/opcion-pregunta", method = RequestMethod.POST)
	public ResponseEntity<?> guardarOpcPregunta(@RequestBody OpcPreguntaDto opcPregunta) {
		try {
			if(opcPreguntaSvc.getOpcPregunta(opcPregunta.getClave(), opcPregunta.getPreguntaId())){
				return new ResponseEntity<>("Opción de pregunta ya existe.", HttpStatus.CONFLICT);
			}else{
			if (opcPreguntaSvc.guardarOpcPregunta(opcPregunta)) {
				return new ResponseEntity<>("Opción de pregunta creada exitosamente.", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Los datos de la opción contienen un error.", HttpStatus.BAD_REQUEST);
			}}
		} catch (Exception e) {
			return new ResponseEntity<>("Error procesando la petición",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@ApiOperation(value="/opcion-pregunta",notes="Modificar una opción de pregunta")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Actualización exitosa."),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
			@ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
			@ApiResponse(code = 404, message = "Not Found. La url que se intenta acceder no se encuetra disponible o no existe"),
			@ApiResponse(code = 409, message = "Conflicto. La opción de que se intenta crear ya existe"),
			@ApiResponse(code = 500, message = "Error interno.")
	})
	@RequestMapping(value = "/opcion-pregunta", method = RequestMethod.PUT)
	public ResponseEntity<?> editarOpcPregunta(@RequestBody OpcPreguntaDto opcPregunta){
		try{
			OpcPregunta aux = opcPreguntaSvc.getById(opcPregunta.getId());
			if(aux != null){
				if(opcPreguntaSvc.editarOpcPregunta(opcPregunta)){
					return new ResponseEntity<>("Opción de pregunta modificada con éxito.", HttpStatus.OK);
				}else{
					return new ResponseEntity<>("Clave ya existe en la pregunta.", HttpStatus.CONFLICT); 
				}
			}else{
				return new ResponseEntity<>("Opción de pregunta no existe.", HttpStatus.NOT_FOUND);
			}
		}catch(Exception e){
			return new ResponseEntity<>("Error procesando la petición",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@ApiOperation(value = "/opcion-pregunta/{idOpcPregunta}", notes = "Obtiene una opción de pregunta a partir del id")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@ApiResponses(value = {
			@ApiResponse(code = 302, message = "Recuperación exitosa"),
			@ApiResponse(code = 500, message = "Existe un error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
            @ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
            @ApiResponse(code = 404, message = "Not Found. No se encontró la opción"),
		    @ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto")
	})
	@RequestMapping(value = "/opcion-pregunta/{idOpcPregunta}", method = RequestMethod.GET)
	public ResponseEntity<?> obtenerOpcPregunta(@PathVariable("idOpcPregunta") Long idOpcPregunta) {
		try {
			OpcPreguntaDto auxDto = opcPreguntaSvc.getDtoById(idOpcPregunta);
			if (auxDto != null) {
				return new ResponseEntity<OpcPreguntaDto>(auxDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró la opción de pregunta.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error recuperando la opción.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ResponseBody
	@ApiOperation(value = "/opcions-pregunta/{idPregunta}", notes = "Obtiene una opción de pregunta a partir del id de la pregunta")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@ApiResponses(value = {
			@ApiResponse(code = 302, message = "Recuperación exitosa"),
			@ApiResponse(code = 500, message = "Existe un error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
            @ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
            @ApiResponse(code = 404, message = "Not Found. No se encontraron las opciones"),
		    @ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto")
	})
	@RequestMapping(value = "/opcions-pregunta/{idPregunta}", method = RequestMethod.GET)
	public ResponseEntity<?> obtenerOpcsPregunta(@PathVariable("idPregunta") Long idPregunta) {
		try {
			List<OpcPreguntaDto> listOpcsPregunta = opcPreguntaSvc.listarOpcsPregunta(idPregunta);
			if (listOpcsPregunta.isEmpty()) {
				return new ResponseEntity<String>("No existen opciones para la pregunta respectiva.",
						HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<List<OpcPreguntaDto>>(listOpcsPregunta, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error obteniendo el listado de opciones.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
