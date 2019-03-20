package co.gov.metropol.area247.contenedora.restcontroller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api")
public class IconoRestController  {
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Autowired
    private Environment env;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/icono", method = RequestMethod.GET)
	public ResponseEntity<?> iconoObtenerTodos(){
		try {
			return new ResponseEntity<List<Icono>>(iconoService.iconoObtenerTodos(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/icono", method = RequestMethod.POST)
	public ResponseEntity<?> guardarIcono(@RequestParam("icono") MultipartFile icono) throws IOException{
	
		try{
			System.out.println(icono.getContentType());
			Long iconoId = iconoService.iconoCrear(icono,null);	
			return new ResponseEntity<Icono>(iconoService.iconoObtenerPorId(iconoId), HttpStatus.CREATED);
		} catch (Exception ex) {
		    return new ResponseEntity<String>("No se ha podido procesar el ícono de la capa, revisar y reintentar", HttpStatus.BAD_REQUEST);
		}		
    }
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/icono", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarIcono(@RequestParam(value = "id") Long id,
			                                 @RequestParam(value = "icono") MultipartFile icono) throws IOException{	
		try{
			if(iconoService.iconoObtenerPorId(id)!=null) {			
			    Long iconoId = iconoService.iconoCrear(icono,id);	
			    return new ResponseEntity<Icono>(iconoService.iconoObtenerPorId(iconoId), HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("No existe ningún icono correspondiente al id",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
		    return new ResponseEntity<String>("No se ha podido procesar el ícono de la capa, revisar y reintentar",HttpStatus.BAD_REQUEST);
		}		
    }
	
	@RequestMapping(value = "/icono/{idIcono}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> obtenerImagenIcono(@PathVariable (value = "idIcono") Long idIcono) throws IOException {
		
		String rutaCarpetaImagenes = env.getProperty("iconos.folder.url");
		
		String nombreRecurso = iconoService.iconoObtenerPorId(idIcono).getNombre();
		
		File imgPath = new File(rutaCarpetaImagenes + nombreRecurso);
		byte[] image = new byte[0];
		try {
			image = Files.readAllBytes(imgPath.toPath());
		}catch(Exception e) {
			
		}
	    HttpHeaders headers = new HttpHeaders();
	    
	    System.out.println(FilenameUtils.getExtension(nombreRecurso));
	    if (FilenameUtils.getExtension(nombreRecurso).equals("svg")) {
	    	headers.setContentType(MediaType.valueOf("image/svg+xml"));
	    }else {
	    	headers.setContentType(MediaType.IMAGE_JPEG);
	    }
	    headers.setContentLength(image.length);
	    return new ResponseEntity<>(image, headers, HttpStatus.OK);
   
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/icono", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminarIcono(@RequestParam(value = "id") Long id) throws IOException{	
		try{
			if(iconoService.iconoObtenerPorId(id)!=null) {			
			    iconoService.iconoEliminar(id);	
			    return new ResponseEntity<String>("Icono eliminado satisfactoriamente", HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("No existe ningún icono correspondiente al id",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
		    return new ResponseEntity<String>("No se ha podido procesar el ícono de la capa, revisar y reintentar",HttpStatus.BAD_REQUEST);
		}		
    }

	
}


