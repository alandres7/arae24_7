package co.gov.metropol.area247.entorno.restcontroller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.entorno.model.Medicion;
import co.gov.metropol.area247.entorno.model.RecomendacionAire;
import co.gov.metropol.area247.entorno.service.IEntornoMedicionService;
import co.gov.metropol.area247.entorno.service.IEntornoRecomendacionAireService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@ControllerAdvice
@RequestMapping(value = "/api")
public class RecomendacioAireRestController {
	
	private Logger LOGGER = LoggerFactory.getLogger(RecomendacioAireRestController.class);
	
	@Autowired
	IEntornoRecomendacionAireService recomendacionService; 
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Autowired
	IEntornoMedicionService medicionService;
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/entorno/recomendacion/list", method = RequestMethod.GET)
	public ResponseEntity<?> recomendacionObtenerTodas(){
		try{	
			return new ResponseEntity<List<RecomendacionAire>>(recomendacionService.recomendacionObtenerTodas(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
		
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/entorno/recomendacionPorCodigo/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<?> recomendacionObtenerPorCodigo(@PathVariable ("codigo") String codigo){
		RecomendacionAire recomedacion = recomendacionService.recomendacionPorCodigo(codigo);
		if(recomedacion!=null) {			
			return new ResponseEntity<RecomendacionAire>(recomedacion,HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra ningúna recomendacion asociada al codigo", HttpStatus.NOT_FOUND);
		}
	}	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/entorno/recomendacionPorId/{idRecomendacion}", method = RequestMethod.GET)
	public ResponseEntity<?> recomendacionObtenerPorId(@PathVariable ("idRecomendacion") Long idRecomendacion){
		RecomendacionAire recomedacion = recomendacionService.recomendacionPorId(idRecomendacion);
		if(recomedacion!=null) {			
			return new ResponseEntity<RecomendacionAire>(recomedacion,HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra ningúna recomendacion asociada al codigo", HttpStatus.NOT_FOUND);
		}
	}	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/entorno/recomendacion", method = RequestMethod.POST)
	public ResponseEntity<String> recomendacionCrear(
			@RequestParam(value = "icono", required = false) MultipartFile icono,
			@RequestParam(value = "texto", required = false) String texto,
			@RequestParam(value = "codigo", required = false) String codigo){
		try {
		    RecomendacionAire recomendacion = new RecomendacionAire();
					
			if (icono != null && !icono.isEmpty()) {
				Long idIcono = iconoService.iconoCrear(icono, null);
				if (idIcono != null) {
					recomendacion.setIcono(iconoService.iconoObtenerPorId(idIcono));
				}
			}										
			recomendacion.setTexto(texto);	
			recomendacion.setCodigo(codigo);
			if(recomendacionService.recomendacionCrear(recomendacion)) {
				return new ResponseEntity<String>("Recomendacion adicionada correctamente", HttpStatus.OK);	
			}else {
				return new ResponseEntity<String>("No se ha podido agregar la recomendacion", HttpStatus.OK);	
			}														
		}catch(Exception e) {
			LOGGER.error("Error al intentar guardar la recomendacion", e);
			return new ResponseEntity<String>("Error al intentar guardar la recomendacion", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/entorno/recomendacion", method = RequestMethod.PUT)
	public ResponseEntity<String> recomendacionActualizar(
			@RequestParam(value = "icono", required = false) MultipartFile icono,
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "texto", required = false) String texto){
		try {
			RecomendacionAire recomendacion = recomendacionService.recomendacionPorCodigo(codigo);			
			if(recomendacion != null) {									
			    if (icono != null && !icono.isEmpty()) {
					Long idIcono = iconoService.iconoCrear(icono, null);
					if (idIcono != null) {
						recomendacion.setIcono(iconoService.iconoObtenerPorId(idIcono));
					}
				}										
			    recomendacion.setTexto(texto);
			    recomendacion.setCodigo(codigo);
				if(recomendacionService.recomendacionActualizar(recomendacion)) {
					return new ResponseEntity<String>("Recomendacion actualizada correctamente", HttpStatus.OK);	
				}else {
					return new ResponseEntity<String>("No se ha podido actualizar la recomendacion", HttpStatus.BAD_REQUEST);	
				}								
			}else {
				return new ResponseEntity<String>("No existen recomendaciones correspondientes al codigo", HttpStatus.BAD_REQUEST);	
			}						
		}catch(Exception e) {
			LOGGER.error("Error al intentar actualizar la recomendacion", e);
			return new ResponseEntity<String>("Error al intentar actualizar la recomendacion", HttpStatus.BAD_REQUEST);
		}		
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/entorno/recomendacion/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<?> recomendacionBorrar(@PathVariable("codigo")String codigo){
		try{
			boolean recomendacionBorrado = recomendacionService.recomendacionEliminar(codigo);
			if(recomendacionBorrado){
				return new ResponseEntity<String>("recomendacion borrada",HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se pudo borrar la recomendacion"
						+ ", revisar los datos",HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error al borrar la recomendacion"
					+ ", revisar los datos; "+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
}
