package co.gov.metropol.area247.seguridad.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.service.IContenedoraMensajeService;
import co.gov.metropol.area247.seguridad.model.dto.PaisDto;
import co.gov.metropol.area247.seguridad.service.ISeguridadPaisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Contenedora")
@RestController
@RequestMapping("/api")
public class PaisRestController {

	@Autowired
	ISeguridadPaisService paisService;
	
	@Autowired
	IContenedoraMensajeService mensajeService;

	@ResponseBody
	@ApiOperation(value="/paises", notes="Obtener todos los paises")
	@GetMapping("/paises")
	public ResponseEntity<?> getPaises(){
		try {
			return new ResponseEntity<List<PaisDto>>(paisService.getPaises(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(mensajeService.crearRespuestaJson(
					"excepcion_paises", "", ""), HttpStatus.I_AM_A_TEAPOT);
		}
	}	
}
