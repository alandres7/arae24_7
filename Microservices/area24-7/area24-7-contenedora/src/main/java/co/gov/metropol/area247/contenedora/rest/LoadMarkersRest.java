package co.gov.metropol.area247.contenedora.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.service.IContenedoraLoadMarkersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Api para carga de datos desde el AMVA")
@RequestMapping("/api")
public class LoadMarkersRest {
	
	
	@Autowired
	@Qualifier("loadLayersSvc")
	IContenedoraLoadMarkersService loadLayersSvc;
	
	@Autowired
	@Qualifier("MunicipiosSvc")
	IContenedoraLoadMarkersService municipiosSvc;
	
	
	
	
	@ResponseBody
	@ApiOperation(value = "/contenedora/load-markers-categoria", notes = "Rest TEST para cargar info de los marcadores por CATEGORIA desde el AMVA")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto") })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@PutMapping("/contenedora/load-markers-categoria")
	public ResponseEntity<?> getMarkerXCategoria(@RequestParam("idCategoria") Long idCategoria) {
		boolean cargadoComercio = loadLayersSvc.updateMarkersCategoria(idCategoria);
		return new ResponseEntity<Boolean>(cargadoComercio, HttpStatus.OK);
	}
	
	@ResponseBody
	@ApiOperation(value = "/contenedora/load-markers-capa", notes = "Rest para cargar info de los marcadores por CAPA desde el AMVA")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto") })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@PutMapping("/contenedora/load-markers-capa")
	public ResponseEntity<?> getMarkerXCapa(@RequestParam("idCapa") Long idCapa) {
		boolean cargadoComercio = loadLayersSvc.updateMarkersCapa(idCapa);
		return new ResponseEntity<Boolean>(cargadoComercio, HttpStatus.OK);
	}
	
	@ResponseBody
	@ApiOperation(value = "/contenedora/load-markers-municipios", notes = "Rest para cargar info de los polígonos asociados a cada Municipio desde el AMVA")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto") })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@PutMapping("/contenedora/load-markers-municipios")
	public ResponseEntity<?> getMarkerMunicipios() {
		boolean cargadoComercio = municipiosSvc.updateMarkers();
		return new ResponseEntity<Boolean>(cargadoComercio, HttpStatus.OK);
	}
	
	

}
