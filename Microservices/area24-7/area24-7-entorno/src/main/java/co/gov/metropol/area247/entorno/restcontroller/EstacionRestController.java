package co.gov.metropol.area247.entorno.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.entorno.repository.EstacionRepository;
import co.gov.metropol.area247.entorno.service.IEntornoEstacionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Rest para test de los servicios del siata")
@RequestMapping("/api/entorno/estacion")
public class EstacionRestController {
	
	@Autowired
	IEntornoEstacionService estacionSvc;
	
	@Autowired
	private EstacionRepository estacionRepository;

	private static Logger LOGGER = LoggerFactory.getLogger(EstacionRestController.class);
	
	@ResponseBody
	@ApiOperation(value="/update", notes="")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@PutMapping("/update")
	public ResponseEntity<Boolean> updateStationData(@RequestParam("idCapa")Long idCapa)
	{
		try {
			return new ResponseEntity<>( estacionSvc.updateEstaciones(idCapa), HttpStatus.OK);
		} catch (Exception e) { 
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/detalle/find-By-Marker")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	public ResponseEntity<String> getDetailXIdMarcador(@RequestParam("idMarcador") Long idMarcador) {
		try {
			String data = estacionRepository.getDetailXMarker(idMarcador);

			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al intentar obtener el detalle de la estación por id marcador", idMarcador, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/detalle/find-by-location")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
	})
	public ResponseEntity<String> getDetailXLocation(@RequestParam("idCapa") Long idCapa, 
												@RequestParam("lat") double lat, @RequestParam("lng") double lng){
		return new ResponseEntity<>(estacionSvc.getDetailXLocation(idCapa, lat, lng), HttpStatus.OK);
	}
	
}
