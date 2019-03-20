package co.gov.metropol.area247.core.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.model.LatLng;

import co.gov.metropol.area247.core.domain.capa.dto.CapaPointMarkerList;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.repository.MarcadorRepository;
import co.gov.metropol.area247.core.svc.ICoreMarkerSvc;
import co.gov.metropol.area247.jdbc.util.Utils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(value = "/api/core/marker")
@RestController
public class MarkerRestController {

	private static Logger LOGGER = LoggerFactory.getLogger(MarkerRestController.class);

	@Autowired
	private MarcadorRepository marcadorRepository;

	@Autowired
	@Qualifier("markerConverter")
	private ICoreMarkerSvc markerConverter;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/find/{nivelCapa}/{idCapa}")
	public ResponseEntity<CapaPointMarkerList> obtenerAvistamientosPorLatLonYRadio(@PathVariable String nivelCapa,
			@PathVariable Long idCapa, @RequestParam Double latitud, @RequestParam Double longitud,
			@RequestParam int radioAccion) {
		try {
			CapaPointMarkerList avistamientoDTO = new CapaPointMarkerList();
			avistamientoDTO.setIdCapa(idCapa);
			List<MarkerPoint> markersPoint = marcadorRepository.obtenerMarcadoresPorLatYLonYRadioYCapa(latitud,
					longitud, radioAccion, idCapa, nivelCapa);
			System.out.println(markersPoint.size());
			if (!Utils.isNull.apply(markersPoint)) {
				avistamientoDTO = new CapaPointMarkerList();
				avistamientoDTO.setMarkersPoint(markersPoint);
				return new ResponseEntity<CapaPointMarkerList>(avistamientoDTO, HttpStatus.OK);
			} else {
				avistamientoDTO.setMarkersPoint(new ArrayList<>());
				return new ResponseEntity<CapaPointMarkerList>(avistamientoDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener los avistamientos por capa {}{}", idCapa, e);
			return new ResponseEntity<CapaPointMarkerList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/findbycapa/{nivelCapa}/{idCapa}")
	public ResponseEntity<CapaPointMarkerList> consultarMarcadorPorCapaYNivelCapa(@PathVariable String nivelCapa,
			@PathVariable Long idCapa) {
		try {
			CapaPointMarkerList avistamientoDTO = new CapaPointMarkerList();
			avistamientoDTO.setIdCapa(idCapa);
			List<MarkerPoint> markersPoint = marcadorRepository.obtenerMarcadorPorCapaYNivelCapa(nivelCapa, idCapa);
			if (!Utils.isNull.apply(markersPoint)) {
				avistamientoDTO = new CapaPointMarkerList();
				avistamientoDTO.setMarkersPoint(markersPoint);
				return new ResponseEntity<CapaPointMarkerList>(avistamientoDTO, HttpStatus.OK);
			} else {
				avistamientoDTO.setMarkersPoint(new ArrayList<>());
				return new ResponseEntity<CapaPointMarkerList>(avistamientoDTO, HttpStatus.OK);
			}

		} catch (Exception e) {
			LOGGER.error("Error al obtener los avistamientos por capa {}{}", idCapa, e);
			return new ResponseEntity<CapaPointMarkerList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/findbycapa")
	public ResponseEntity<CapaPointMarkerList> obtenerMarcadoresPorIdCapa(@RequestParam Long idCapa) {
		try {
			CapaPointMarkerList avistamientoDTO = new CapaPointMarkerList();
			avistamientoDTO.setIdCapa(idCapa);
			List<MarkerPoint> markersPoint = marcadorRepository.obtenerMarcadoresPorIdCapa(idCapa);
			if (!Utils.isNull.apply(markersPoint)) {
				avistamientoDTO = new CapaPointMarkerList();
				avistamientoDTO.setMarkersPoint(markersPoint);
				return new ResponseEntity<CapaPointMarkerList>(avistamientoDTO, HttpStatus.OK);
			} else {
				avistamientoDTO.setMarkersPoint(new ArrayList<>());
				return new ResponseEntity<CapaPointMarkerList>(avistamientoDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener los avistamientos por capa {}{}", idCapa, e);
			return new ResponseEntity<CapaPointMarkerList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/convertSpatialReference", notes = "Se obtienen los puntos de avistamiento cercanos dentro de un radio asociado a la ubicación dada")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto") })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/convertSpatialReference")
	public ResponseEntity<LatLng> pointConvertSpatialReference(@RequestParam("lat") double lat,
			@RequestParam("lng") double lng) {
		try {
			return new ResponseEntity<LatLng>(markerConverter.markerConvertSpatialReference(lat, lng), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<LatLng>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
