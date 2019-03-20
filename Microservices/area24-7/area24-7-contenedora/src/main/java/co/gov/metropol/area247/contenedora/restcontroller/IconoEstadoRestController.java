package co.gov.metropol.area247.contenedora.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.model.IconoEstado;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoEstadoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "IconoEstado")
@RequestMapping(value = "/api")
public class IconoEstadoRestController {

	@Autowired
	IContenedoraIconoEstadoService iconoEstadoService;

	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/iconoEstado", method = RequestMethod.POST)
	public ResponseEntity<String> iconoEstadoCrear(@RequestParam(value = "nivelCapa", required = true) String nivelCapa,
			                                       @RequestParam(value = "idCapaCategoria", required = true) Long idCapaCategoria,
			                                       @RequestParam(value = "idEstado", required = false) int idEstado,
			                                       @RequestParam(value = "icono") MultipartFile icono){
		
		if(iconoEstadoService.iconoEstadoRegistrar(nivelCapa,idCapaCategoria,idEstado,icono)) {
			return new ResponseEntity<String>("Registro adicionado correctamente", HttpStatus.OK);	
		}else {
			return new ResponseEntity<String>("No se ha podido agregar el Registro", HttpStatus.BAD_REQUEST);	
		}		
	}
			
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/iconoEstado/capa/{idCapa}", method =  RequestMethod.GET)
	public ResponseEntity<?> iconoEstadoPorIdCapa(@PathVariable ("idCapa") Long idCapa){
		
		List<IconoEstado> iconoEstadoList = iconoEstadoService.iconoEstadoPorIdCapa(idCapa);
		if(iconoEstadoList != null) {			
			return new ResponseEntity<List<IconoEstado>>(iconoEstadoList, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra ningún icono asociado al id", HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/iconoEstado/capa/estado/{idCapa}/{idEstado}", method =  RequestMethod.GET)
	public ResponseEntity<?> iconoEstadoPorCapaEstado(@PathVariable("idCapa") Long idCapa,
			                                          @PathVariable("idEstado") int idEstado){
		
		IconoEstado iconoEstadoList = iconoEstadoService.iconoEstadoPorCapaEstado(idCapa,idEstado);
		if(iconoEstadoList != null) {			
			return new ResponseEntity<IconoEstado>(iconoEstadoList, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra ningún icono asociado al id", HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/iconoEstado/categoria/{idCategoria}", method =  RequestMethod.GET)
	public ResponseEntity<?> historiaObtenerPorIdCategoria(@PathVariable ("idCategoria") Long idCategoria){
		
		List<IconoEstado> iconoEstadoList = iconoEstadoService.iconoEstadoPorIdCategoria(idCategoria);
		if(iconoEstadoList != null) {			
			return new ResponseEntity<List<IconoEstado>>(iconoEstadoList, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra ningún icono asociado al id", HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/iconoEstado/categoria/estado/{idCategoria}/{idEstado}", method =  RequestMethod.GET)
	public ResponseEntity<?> iconoEstadoPorCategoriaEstado(@PathVariable("idCategoria") Long idCategoria,
                                                           @PathVariable("idEstado") int idEstado){
		
		IconoEstado iconoEstadoList = iconoEstadoService.iconoEstadoPorCategoriaEstado(idCategoria,idEstado);
		if(iconoEstadoList != null) {			
			return new ResponseEntity<IconoEstado>(iconoEstadoList, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra ningún icono asociado al id", HttpStatus.NOT_FOUND);
		}
	}	
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/iconoEstado/categoria/estado/{idCategoria}/{idEstado}", method =  RequestMethod.DELETE)
	public ResponseEntity<?> iconoEstadoEliminarByCategoria(@PathVariable("idCategoria") Long idCategoria,
                                                 @PathVariable("idEstado") int idEstado){					
	    if(iconoEstadoService.iconoEstadoEliminarByCategoria(idCategoria,idEstado)) {
		    return new ResponseEntity<String>("El icono ha sido eliminado exitosamente", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("El icono no pudo ser eliminado o no existe", HttpStatus.OK);
		}

	}	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/iconoEstado/capa/estado/{idCapa}/{idEstado}", method =  RequestMethod.DELETE)
	public ResponseEntity<?> iconoEstadoEliminarByCapa(@PathVariable("idCapa") Long idCapa,
                                                 @PathVariable("idEstado") int idEstado){					
	    if(iconoEstadoService.iconoEstadoEliminarByCapa(idCapa,idEstado)) {
		    return new ResponseEntity<String>("El icono ha sido eliminado exitosamente", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("El icono no pudo ser eliminado o no existe", HttpStatus.OK);
		}

	}	
}
