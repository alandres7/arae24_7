package co.gov.metropol.area247.entorno.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.entorno.model.dto.ClimaDetail;
import co.gov.metropol.area247.entorno.service.IEntornoClimaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api/entorno/clima")
public class ClimaRestController {

	private static Logger LOGGER = LoggerFactory.getLogger(ClimaRestController.class);

	@Autowired
	private IEntornoClimaService climaSvc;

	@GetMapping(value = "/detalle/find-By-Marker")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	public ResponseEntity<ClimaDetail> obtenerClimaPorCapaMarcador(@RequestParam Long idMarcador) {
		try {
			return new ResponseEntity<ClimaDetail>(climaSvc.getDetailClima(idMarcador), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al consultar el climar por {}{}",
					(" id marcador : " + idMarcador), e);
			return new ResponseEntity<ClimaDetail>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/detalle/find-by-location")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
	})
	public ResponseEntity<ClimaDetail> getDetailXLocation(@RequestParam("lat") double lat, 
														@RequestParam("lng") double lng){
		try {
			return new ResponseEntity<>(climaSvc.getDetailClimaXLocation(lat, lng), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al consultar el climar por {}{}",
					(" ubicación => lat:" + lat+", Lng:"+lng), e);
			return new ResponseEntity<ClimaDetail>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
