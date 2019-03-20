package co.gov.metropol.area247.contenedora.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.model.TipoMultimedia;
import co.gov.metropol.area247.contenedora.service.IContenedoraTipoMultimediaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api")
public class TipoMultimediaRestController {
	
	@Autowired
	IContenedoraTipoMultimediaService tipoMultimediaService;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/tipo", method = RequestMethod.POST)
	public ResponseEntity<String> tipoMedicionCrear(@RequestBody TipoMultimedia  tipoMultimedia){
		try {
			if(tipoMultimediaService.tipoMultimediaObtenerPorSubtipoMultimedia(tipoMultimedia.getSubtipo())!=null) {
				return new ResponseEntity<String>("Conflicto. El tipo multimedia que intenta crear, ya existe", HttpStatus.CONFLICT);
			}
			if(tipoMultimediaService.tipoMultimediaCrear(tipoMultimedia)) {
				return new ResponseEntity<String>("Tipo multimedia creado exitosamente", HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("No se ha podido crear el tipo de multimedia, reintentar", HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e){
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/tipo", method = RequestMethod.GET)
	public ResponseEntity<?> tipoMedicionObtenerTodos(){
		try {
			return new ResponseEntity<List<TipoMultimedia>>(tipoMultimediaService.tipoMultimediaObtenerTodos(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
