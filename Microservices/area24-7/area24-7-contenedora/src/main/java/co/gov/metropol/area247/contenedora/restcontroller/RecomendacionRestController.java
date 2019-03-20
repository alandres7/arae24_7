package co.gov.metropol.area247.contenedora.restcontroller;

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

import co.gov.metropol.area247.contenedora.model.Recomendacion;
import co.gov.metropol.area247.contenedora.model.dto.RecomendacionDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraRecomendacionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Contenedora")
@RequestMapping(value = "/api")
public class RecomendacionRestController {
	
	@Autowired
	IContenedoraRecomendacionService recomendacionService;
	
	@ResponseBody
	@ApiOperation(value = "/recomendacion/{aplicacionId}", notes = "Permite la creación de una recomendación a usuario (ayudas, mensajes aclaratorios) frente al uso de una de las aplicaciones del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ceración exitosa"),
            @ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"),
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/recomendacion/{aplicacionId}", method = RequestMethod.POST)
	public ResponseEntity<String> recomendacionCrear(@RequestBody Recomendacion recomendacion, @PathVariable Long aplicacionId){		
		try{
			if(recomendacionService.recomendacionCrear(recomendacion, aplicacionId)){
				return new ResponseEntity<String>("Recomendación creada con éxito", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se pudo crear la recomendación, revisar datos y reintentar", HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
				
	}
	
	@ResponseBody
	@ApiOperation(value = "/recomendacion/{aplicacionId}", notes = "Permite la actualización de una recomendación a usuario (ayudas, mensajes aclaratorios) sobre el uso de una aplicación del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ceración exitosa"),
            @ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"),
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/recomendacion/{aplicacionId}", method = RequestMethod.PUT)
	public ResponseEntity<String> recomendacionActualizar(@RequestBody Recomendacion recomendacion, @PathVariable Long aplicacionId){
		try{
			if(recomendacionService.recomendacionActualizar(recomendacion,aplicacionId)){
				return new ResponseEntity<String>("Recomendación actualizada con éxito", HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("No se ha podido realizar la actualización, revisar datos y reintentar", HttpStatus.BAD_REQUEST);
			}					
		}catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar", HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
	}
	
	@ResponseBody
	@ApiOperation(value = "/recomendacion", notes = "Permite la consulta de las diferentes recomendaciones a usuario (ayudas, mensajes aclaratorios) frente al uso de las aplicaciones del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ceración exitosa"),
            @ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"),
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/recomendacion", method = RequestMethod.GET)
	public ResponseEntity<?> recomendacionObtenerTodas(){
		try{	
			return new ResponseEntity<List<Recomendacion>>(recomendacionService.recomendacionObtenerTodas(), HttpStatus.OK);	
		} catch(Exception e) {
			return new ResponseEntity<String>("No se han podido obtener las recomendaciones, reintentar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/recomendacion/aplicacion/{idAplicacion}", method = RequestMethod.GET)
	public ResponseEntity<?> recomendacionObtenerPorIdAplicacion(@PathVariable ("idAplicacion") Long idAplicacion){
		List<RecomendacionDto> listRecomendacion = recomendacionService.recomendacionObtenerPorAplicacionId(idAplicacion);
		if(!listRecomendacion.isEmpty()) {			
			return new ResponseEntity<RecomendacionDto>(listRecomendacion.get(0),HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra ningúna recomendacion asociada al id", HttpStatus.NOT_FOUND);
		}
	}
}
