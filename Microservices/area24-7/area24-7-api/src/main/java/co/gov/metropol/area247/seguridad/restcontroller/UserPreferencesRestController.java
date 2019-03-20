package co.gov.metropol.area247.seguridad.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.service.IContenedoraMensajeService;
import co.gov.metropol.area247.seguridad.service.ISeguridadUserPreferencesService;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api("Rest para el CRUD de preferencias de usuario")
@RequestMapping("/api")
public class UserPreferencesRestController {
	
	@Autowired
	ISeguridadUserPreferencesService userPreferencesSvc;
	
	@Autowired
	ISeguridadUsuarioService usuarioSvc;
	
	@Autowired
	IContenedoraMensajeService mensajeService;
	
	@ResponseBody
	@ApiOperation(value="/usuario/{idUsuario}/preferencias", notes="")
	@ApiResponses({
		@ApiResponse(code=200, message="Preferencias del usuario encontradas"),
		@ApiResponse(code=505, message="Error interno del servidor")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@GetMapping("/usuario/{username}/preferencias")
	public ResponseEntity<String> getUserPreferences(@PathVariable("username") String username){
		try {
			String preferencias =  userPreferencesSvc.getUserPreferences(username);
			if(preferencias == null || preferencias.equals("")) {
				preferencias = "";
			}
			return new ResponseEntity<String>(preferencias, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@ApiOperation(value="/usuario/{username}/preferencias", notes="")
	@ApiResponses({
		@ApiResponse(code=200, message="Preferencias de usuario actualizadas"),
		@ApiResponse(code=404, message="El usuario no existe"),
		@ApiResponse(code=404, message="Error actualizando las preferencias")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@PutMapping("/usuario/{username}/preferencias")
	public ResponseEntity<String> updatePreferences(@PathVariable("username") String username, @RequestBody(required=true) String preferencias){
		String resultado = userPreferencesSvc.updatePreferences(username, preferencias);
		if(resultado.contains("ERROR")) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}else if(resultado.contains("ENCONTRADO")){
			return new ResponseEntity<String>(mensajeService.crearRespuestaJson(
					"usuario->no-existe", "", "")
					, HttpStatus.I_AM_A_TEAPOT);
		}else {
			return new ResponseEntity<String>("ACTUALIZADO!", HttpStatus.OK);
		}
	}
	
}
