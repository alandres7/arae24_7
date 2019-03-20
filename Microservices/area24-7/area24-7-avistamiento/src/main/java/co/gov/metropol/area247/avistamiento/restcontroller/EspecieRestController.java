package co.gov.metropol.area247.avistamiento.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.avistamiento.model.Especie;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoEspecieService;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Avistamiento")
@RequestMapping(value="/api")
public class EspecieRestController {
	
	private Logger LOGGER = LoggerFactory.getLogger(EspecieRestController.class);
	
	@Autowired
	IAvistamientoEspecieService especieService; 
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Autowired
	IContenedoraCategoriaService categoriaService;
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/especie/list/{idCategoria}", method =  RequestMethod.GET)
	public ResponseEntity<?> getListEspeciesByIdCategoria(@PathVariable("idCategoria") Long idCategoria){
		
		List<Especie> especieList = especieService.especiePorIdCategoria(idCategoria);
		if(especieList!=null) {			
			return new ResponseEntity<List<Especie>>(especieList, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra ningúna especie asociado al id Categoria", HttpStatus.NOT_FOUND);
		}
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/especie/{idEspecie}", method = RequestMethod.GET)
	public ResponseEntity<?> especieObtenerPorId(@PathVariable ("idEspecie") Long idEspecie){
		Especie especie = especieService.especiePorId(idEspecie);
		if(especie!=null) {			
			return new ResponseEntity<Especie>(especie, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra ningún avistamiento asociado al id", HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/especie", method = RequestMethod.POST)
	public ResponseEntity<String> especieCrear(
			@RequestParam(value = "icono", required = false) MultipartFile icono,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "idCategoria", required = false) Long idCategoria){
		try {
			Categoria categoria = categoriaService.categoriaObtenerPorId(idCategoria);			
			if(categoria != null) {
			    Especie especie = new Especie();
					
			    if (icono != null && !icono.isEmpty()) {
					Long idIcono = iconoService.iconoCrear(icono, null);
					if (idIcono != null) {
						especie.setIcono(iconoService.iconoObtenerPorId(idIcono));
					}
				}										
				especie.setNombre(nombre);	
				especie.setIdCategoria(idCategoria);
				if(especieService.especieCrear(especie,idCategoria)) {
					return new ResponseEntity<String>("Especie adicionada correctamente", HttpStatus.OK);	
				}else {
					return new ResponseEntity<String>("No se ha podido agregar especie", HttpStatus.OK);	
				}								
			}else {
				return new ResponseEntity<String>("No existen categoria correspondientes al idCategoria", HttpStatus.BAD_REQUEST);	
			}						
		}catch(Exception e) {
			LOGGER.error("Error al intentar guardar la especie", e);
			return new ResponseEntity<String>("Error al intentar guardar la especie", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/especie", method = RequestMethod.PUT)
	public ResponseEntity<String> especieActualizar(
			@RequestParam(value = "icono", required = false) MultipartFile icono,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "nombre", required = false) String nombre){
		try {
			Especie especie = especieService.especiePorId(id);			
			if(especie != null) {									
			    if (icono != null && !icono.isEmpty()) {
					Long idIcono = iconoService.iconoCrear(icono, null);
					if (idIcono != null) {
						especie.setIcono(iconoService.iconoObtenerPorId(idIcono));
					}
				}										
				especie.setNombre(nombre);	
				if(especieService.especieActualizar(especie)) {
					return new ResponseEntity<String>("Especie actualizada correctamente", HttpStatus.OK);	
				}else {
					return new ResponseEntity<String>("No se ha podido actualizar la especie", HttpStatus.BAD_REQUEST);	
				}								
			}else {
				return new ResponseEntity<String>("No existen especies correspondientes al id", HttpStatus.BAD_REQUEST);	
			}						
		}catch(Exception e) {
			LOGGER.error("Error al intentar actualizar la especie", e);
			return new ResponseEntity<String>("Error al intentar actualizar la especie", HttpStatus.BAD_REQUEST);
		}		
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/avistamiento/especie/{idEspecie}", method = RequestMethod.DELETE)
	public ResponseEntity<?> especieBorrar(@PathVariable("idEspecie")Long idEspecie){
		try{
			boolean especieBorrado = especieService.especieEliminar(idEspecie);
			if(especieBorrado){
				return new ResponseEntity<String>("especie borrada",HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se pudo borrar la especie"
						+ ", revisar los datos",HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error al borrar la especie"
					+ ", revisar los datos; "+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
}
