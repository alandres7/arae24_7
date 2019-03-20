package co.gov.metropol.area247.core.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.core.domain.context.web.Aplicacion;
import co.gov.metropol.area247.core.repository.AplicacionRepository;
import co.gov.metropol.area247.jdbc.util.Utils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api/core/app")
public class AppRestController {

	private static Logger LOGGER = LoggerFactory.getLogger(AppRestController.class);

	@Autowired
	private AplicacionRepository aplicacionRepository;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/findbycapa/{idCapa}")
	public ResponseEntity<Aplicacion> obtenerAplicacionPorIdCapa(@PathVariable Long idCapa) {
		try {
			Aplicacion aplicacion = aplicacionRepository.consultarAplicacionPorIdCapa(idCapa);

			return new ResponseEntity<Aplicacion>(!Utils.isNull.apply(aplicacion) ? aplicacion : new Aplicacion(),
					HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al obtener la aplicacion por la capa --{}{}", idCapa, e);
			return new ResponseEntity<Aplicacion>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
