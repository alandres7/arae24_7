package co.gov.metropol.area247.contenedora.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.util.Actualizador;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Contenedora")
@RequestMapping(value = "/contenedora")
public class ActualizadorRestController {
	
	@Autowired
	Actualizador actualizador;

	@ResponseBody
	@ApiOperation(value = "/actualizar", notes = "Permite la actualización de la información base de una de las capas, categorías o subcategorías del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá, ingresa tipo recurso 1 para actualizar una capa, 2 para una categoría/convención, 3 para una subcategoría")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Actualización exitosa"),
            @ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"),
			@ApiResponse(code = 500, message = "Error interno en el procesamiento, contactar administrador del sistema")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/actualizar", method = RequestMethod.PUT)
	public ResponseEntity<String> actualizarInformacion(Long idRecurso, Long tipoRecurso, boolean poligono){
		try {
			actualizador.actualizarData(tipoRecurso, idRecurso, poligono);
			return new ResponseEntity<String>("Actualización realizada con éxito", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Ha ocurrido un error durante la actualización, reintentar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
    
	@ResponseBody
	@ApiOperation(value = "/actualizar/job", notes = "Permite la actualización de la información base de una de las capas, categorías o subcategorías del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá, ingresa tipo recurso 1 para actualizar una capa, 2 para una categoría/convención, 3 para una subcategoría")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Actualización exitosa"),
            @ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"),
			@ApiResponse(code = 500, message = "Error interno en el procesamiento, contactar administrador del sistema")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/actualizar/job", method = RequestMethod.POST)
	public ResponseEntity<String> actualizarInformacionPorJob(@RequestParam (value = "idRecurso") Long idRecurso,
			                                                  @RequestParam (value = "tipoRecurso",required = false) Long tipoRecurso, 			                                            
			                                                  @RequestParam (value = "poligono",required = false) boolean poligono){
		try {
			actualizador.actualizarData(tipoRecurso, idRecurso, poligono);
			return new ResponseEntity<String>("Actualización realizada con éxito", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Ha ocurrido un error durante la actualización, reintentar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
		
}
