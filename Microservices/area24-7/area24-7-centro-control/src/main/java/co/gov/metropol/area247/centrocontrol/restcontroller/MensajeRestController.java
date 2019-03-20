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

import co.gov.metropol.area247.contenedora.model.Mensaje;
import co.gov.metropol.area247.contenedora.model.dto.MensajeDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraMensajeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Mensaje Objetos")
@RequestMapping(value = "/api")
public class MensajeRestController {
	
	@Autowired 
	IContenedoraMensajeService mensajeService;
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/mensaje/", method = RequestMethod.POST)
	public ResponseEntity<?> mensajeGuardar(
			@RequestParam (value = "idMensaje", required = false) Long idMensaje,
			@RequestParam (value = "contenido", required = false) String contenido,
			@RequestParam (value = "titulo", required = false) String titulo,
			@RequestParam (value = "nombreIdentificador", required = false) String nombreIdentificador,
			@RequestParam (value = "uso", required = false) String uso,
			@RequestParam (value = "idAplicacion", required = false) Long idAplicacion)
	{	
		try{
			Mensaje mensaje = new Mensaje();			
			mensaje.setId(idMensaje);
			mensaje.setDescripcion(contenido);
			mensaje.setTitulo(titulo);
			mensaje.setNombreIdentificador(nombreIdentificador);
			mensaje.setUso(uso);
			mensaje.setIdAplicacion(idAplicacion);
			String respuesta = "";
			if(mensajeService.mensajeGuardar(mensaje)) {
				if(idMensaje==null) {
				    respuesta = "El mensaje ha sido creado exitosamente con id: " + mensaje.getId();
				}else {
					respuesta = "El mensaje ha sido actualizado exitosamente con id: " + mensaje.getId();
				}
			}else {
				if(idMensaje==null) {
				    respuesta = "El mensaje no pudo ser creado";
				}else {
					respuesta = "El mensaje no pudo ser actualizado";
				}
			}			
			return new ResponseEntity<String>(respuesta,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<String>("No se pudo guardar el mensaje, por favor "
					+ "revisar los datos",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/mensaje/", method = RequestMethod.GET)
	public ResponseEntity<?> mensajeObtenerTodos(){
		List<MensajeDto> mensajes;
		try{
			mensajes = mensajeService.mensajeDtoObtenerTodos();
			return new ResponseEntity<List<MensajeDto>>(mensajes,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error recuperando los mensajes",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/mensaje/{idMensaje}", method = RequestMethod.GET)
	public ResponseEntity<?> mensajeObtenerPorId(@PathVariable ("idMensaje") Long idMensaje){
		MensajeDto mensaje;
		try{
			mensaje = mensajeService.mensajeDtoObtenerPorId(idMensaje);
			if(mensaje!=null){
				return new ResponseEntity<MensajeDto>(mensaje,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentra el mensaje",HttpStatus.NOT_FOUND);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error recuperando el mensaje",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/mensaje/nombreIdentificador/{nombreIdentificador}", method = RequestMethod.GET)
	public ResponseEntity<?> mensajeObtenerPorNombreIdentificador(
			@PathVariable ("nombreIdentificador") String nombreIdentificador){
		Mensaje mensaje;
		try{
			mensaje = mensajeService.mensajeObtenerPorNombreIdentificador(nombreIdentificador);
			if(mensaje!=null){
				return new ResponseEntity<Mensaje>(mensaje,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentra el mensaje",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error recuperando el mensaje",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/mensaje/{idMensaje}", method = RequestMethod.DELETE)
	public ResponseEntity<?> mensajeBorrar(@PathVariable ("idMensaje") Long idMensaje){
		try{
			boolean mensajeBorrado = mensajeService.mensajeBorrar(idMensaje);
			if(mensajeBorrado){
				return new ResponseEntity<String>("Mensaje borrado",HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se pudo borrar el mensaje",HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error al borrar el mensaje",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
}
