package co.gov.metropol.area247.contenedora.restcontroller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

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
import co.gov.metropol.area247.contenedora.model.Multimedia;
import co.gov.metropol.area247.contenedora.service.IContenedoraMultimediaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api")
public class MultimediaRestController {
	
	@Autowired
	private IContenedoraMultimediaService multimediaService;
	
	@Autowired
    private Environment env;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/multimedia", method = RequestMethod.POST)
	public ResponseEntity<?> multimediaGuardar( @RequestParam ("multimedia") MultipartFile multimedia){
		try {
		    Multimedia multimediaAuxiliar = multimediaService.multimediaCrear(null,multimedia);
			return new ResponseEntity<Multimedia>(multimediaAuxiliar,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No ha sido posible almacenar el archivo multimedia, reintentar", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/multimedia", method = RequestMethod.PUT)
	public ResponseEntity<?> multimediaActualizar(@RequestParam(value = "id") Long id,
			                                      @RequestParam ("multimedia") MultipartFile multimedia){
		try {
			if(multimediaService.multimediaObtenerPorId(id)!=null) {			
				Multimedia multimediaAuxiliar = multimediaService.multimediaCrear(id,multimedia);
				return new ResponseEntity<Multimedia>(multimediaAuxiliar,HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("No existe ninguna multimedia correspondiente al id",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No ha sido posible almacenar el archivo multimedia, reintentar",HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/multimedia/{idMultimedia}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> obtenerMultimedia(@PathVariable(value = "idMultimedia") Long idMultimedia)
			throws IOException {

		String rutaCarpetaMultimedia = env.getProperty("media.folder.url");

		Multimedia multimediaFile = multimediaService.multimediaObtenerPorId(idMultimedia);
		String nombreRecurso = multimediaFile.getNombre() == null ? "" : multimediaFile.getNombre();
		byte[] multimedia = new byte[0];
		if (!"".equals(nombreRecurso)) {
			try {
				File multimediaPath = new File(rutaCarpetaMultimedia + nombreRecurso);
				multimedia = Files.readAllBytes(multimediaPath.toPath());
			} catch (Exception e) {

			}
		} else {
			try {
				URL url = new URL(multimediaFile.getRuta());
				InputStream in = new BufferedInputStream(url.openStream());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int n = 0;
				while (-1 != (n = in.read(buffer))) {
					out.write(buffer, 0, n);
				}
				out.close();
				in.close();
				multimedia = out.toByteArray();
			} catch (Exception e) {

			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(multimediaFile.getTipoMultimedia().getRenderizador()));
		headers.setContentLength(multimedia.length);
		return new ResponseEntity<>(multimedia, headers, HttpStatus.OK);

	}

}
