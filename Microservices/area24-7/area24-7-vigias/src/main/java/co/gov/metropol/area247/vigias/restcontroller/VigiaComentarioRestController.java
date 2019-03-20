package co.gov.metropol.area247.vigias.restcontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;
import co.gov.metropol.area247.vigias.model.ComentarioVigia;
import co.gov.metropol.area247.vigias.model.Vigia;
import co.gov.metropol.area247.vigias.model.dto.ComentarioVigiaDto;
import co.gov.metropol.area247.vigias.model.dto.VigiaDto;
import co.gov.metropol.area247.vigias.model.enums.Estados;
import co.gov.metropol.area247.vigias.service.IVigiasComentarioService;
import co.gov.metropol.area247.vigias.service.IVigiasVigiaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Vigia")
@RequestMapping(value="/api")
public class VigiaComentarioRestController {
	
	@Autowired
	IVigiasComentarioService comentarioService; 
	
	@Autowired
	IVigiasVigiaService vigiaService;
	
	@Autowired
	ISeguridadUsuarioService usuarioService;
	
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/vigia/comentarioVigia", method = RequestMethod.POST)
	public ResponseEntity<String> comentarioVigiaCrear(@RequestParam(value = "idVigia") Long idVigia,
				                                       @RequestParam(value = "descripcion", required = false) String descripcion,
				                                       @RequestParam(value = "username", required = false) String username){
		try {
			Vigia vigia = vigiaService.vigiaConsultarPorId(idVigia);			
			if(vigia != null) {
				ComentarioVigia comentario = new ComentarioVigia();
					comentario.setDescripcion(descripcion);
					comentario.setFechaCreacion(new Date());
					comentario.setIdReporteVigia(idVigia);
					comentario.setEstado(Estados.PENDIENTE.name().toString());
					comentario.setIdUsuario(usuarioService.obtenerUsuarioPorUsername(username).getId());
					if(comentarioService.comentarioVigiaCrear(comentario)) {
					    return new ResponseEntity<String>(
					    		"Comentario adicionado correctamente con id: " + comentario.getId(), HttpStatus.OK);
					}else {
						return new ResponseEntity<String>("No se ha podido agregar comentario", HttpStatus.BAD_REQUEST);	
					}				
			}else {
				return new ResponseEntity<String>("El idVigia que intenta agregar no corresponde a ninguno existente", HttpStatus.BAD_REQUEST);	
			}						
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido agregar comentario", HttpStatus.BAD_REQUEST);
		}
	}	
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/vigia/comentarioVigia/{idVigia}", method =  RequestMethod.GET)
	public ResponseEntity<?> comentarioVigiaObtenerPorIdVigia(@PathVariable ("idVigia") Long idVigia){
		return new ResponseEntity<List<ComentarioVigiaDto>> (
				comentarioService.comentarioVigiaDtoObtenerPorReporteVigia(idVigia), HttpStatus.OK);
	}
	
	
}
