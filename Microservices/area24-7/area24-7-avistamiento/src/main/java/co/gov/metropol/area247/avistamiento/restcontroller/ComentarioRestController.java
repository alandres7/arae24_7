package co.gov.metropol.area247.avistamiento.restcontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.avistamiento.model.Avistamiento;
import co.gov.metropol.area247.avistamiento.model.Comentario;
import co.gov.metropol.area247.avistamiento.model.dto.ComentarioDto;
import co.gov.metropol.area247.avistamiento.model.enums.Estados;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoAvistamientoService;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoComentarioService;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Avistamiento")
@RequestMapping(value="/api")
public class ComentarioRestController {
	
	@Autowired
	IAvistamientoComentarioService comentarioService; 
	
	@Autowired
	IAvistamientoAvistamientoService avistamientoService;
	
	@Autowired
	ISeguridadUsuarioService usuarioService;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/comentario/avistamiento/{idAvistamiento}", method =  RequestMethod.GET)
	public ResponseEntity<?> comentarioObtenerPorIdAvistamiento(@PathVariable ("idAvistamiento") Long idAvistamiento, @RequestParam String nickname){
		return new ResponseEntity<List<ComentarioDto>> (comentarioService.comentarioObtenerPorIdAvistamiento(idAvistamiento, nickname),HttpStatus.OK);
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/comentario/list/{idAvistamiento}", method =  RequestMethod.GET)
	public ResponseEntity<?> getListaComentariosPorAvistamiento(@PathVariable ("idAvistamiento") Long idAvistamiento){
		return new ResponseEntity<List<ComentarioDto>> (comentarioService.comentarioObtenerPorIdAvistamiento(idAvistamiento),HttpStatus.OK);
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/comentario/{idComentario}", method = RequestMethod.GET)
	public ResponseEntity<?> comentarioObtenerPorId(@PathVariable ("idComentario") Long idComentario){
		ComentarioDto comentario = comentarioService.comentarioDtoPorId(idComentario);
		if(comentario!=null) {			
			return new ResponseEntity<ComentarioDto>(comentario, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra ningúna historia asociada al id", HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/comentario", method = RequestMethod.POST)
	public ResponseEntity<String> comentarioCrear(@RequestParam(value = "idAvistamiento") Long idAvistamiento,
				                                  @RequestParam(value = "descripcion", required = false) String descripcion,
				                                  @RequestParam(value = "username", required = false) String username){
		try {
			Avistamiento avistamiento = avistamientoService.avistamientoPorId(idAvistamiento);			
			if(avistamiento != null) {
				Marcador marcador = avistamiento.getMarcador();
				boolean tieneHistoria = false;
				if(marcador.getCategoria() != null) {
					tieneHistoria = marcador.getCategoria().getTieneHistoria();
				}else {
					if(marcador.getCapa() != null) {
					    tieneHistoria = marcador.getCapa().getContieneHistoria();
					}
				}
				if(!tieneHistoria) {
					Comentario comentario = new Comentario();
					comentario.setDescripcion(descripcion);
					comentario.setFechaPublicacion(new Date());
					comentario.setEstado(2);
					comentario.setIdUsuario(usuarioService.obtenerUsuarioPorUsername(username).getId());
					if(comentarioService.comentarioCrear(comentario, idAvistamiento)) {
					    return new ResponseEntity<String>("Comentario adicionado correctamente", HttpStatus.OK);
					}else {
						return new ResponseEntity<String>("No se ha podido agregar comentario", HttpStatus.INTERNAL_SERVER_ERROR);	
					}
				}else {
					return new ResponseEntity<String>("No se pueden asociar comentarios a este avistamiento", HttpStatus.INTERNAL_SERVER_ERROR);
				}				
			}else {
				return new ResponseEntity<String>("No existen avistamientos correspondientes al idAvistamiento", HttpStatus.INTERNAL_SERVER_ERROR);	
			}						
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido agregar comentario", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/comentario/{idAvistamiento}", method = RequestMethod.PUT)
	public ResponseEntity<String> comentarioActualizar(@RequestBody Comentario comentario){
		try {
			comentarioService.comentarioActualizar(comentario);
			return new ResponseEntity<String>("comentario actualizado correctamente", HttpStatus.OK);		
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido actualizar el comentario", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/comentario/{idComentario}", method = RequestMethod.DELETE)
	public ResponseEntity<?> comentarioBorrar(@PathVariable("idComentario")Long idComentario){
		try{
			boolean comentarioBorrado = comentarioService.comentarioEliminar(idComentario);
			if(comentarioBorrado){
				return new ResponseEntity<String>("Comentario borrado",HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se pudo borrar el comentario"
						+ ", revisar los datos",HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error al borrar el comentario"
					+ ", revisar los datos; "+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
	})
	@RequestMapping(value = "/avistamiento/comentario/estado", method = RequestMethod.PUT)
	public ResponseEntity<String> cambiarEstadoAvistamientoa(
			@RequestParam(value = "id") Long id, 
			@RequestParam(value = "estado", required = false) String estado) {
		
		Comentario comentario = comentarioService.comentarioPorId(id);
		
		if (comentario == null) {
			return new ResponseEntity<String>("No fue posible cambiar el estado del comentario", HttpStatus.NOT_FOUND);
		} else {
			try {
				if(estado.equals("aprobado")) {
					comentario.setEstado(Estados.APROBADO.getEstado());						
				}else {
					if(estado.equals("rechazado")) {
						comentario.setEstado(Estados.RECHAZADO.getEstado());
					}else {
						if(estado.equals("pendiente")) {
							comentario.setEstado(Estados.PENDIENTE.getEstado());
						}else {
							return new ResponseEntity<String>("No se pudo realizar actualización, el estado que coloco no existe",
									HttpStatus.BAD_REQUEST);
						}	
					}
				}
				comentarioService.comentarioActualizar(comentario);
				return new ResponseEntity<String>("Actualización exitosa", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("No se pudo realizar actualización, revisar datos y reintentar ",
						HttpStatus.BAD_REQUEST);
			}
		}
	}	
	
	
}
