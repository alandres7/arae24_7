package co.gov.metropol.area247.contenedora.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.service.IContenedoraMarkerService;
import co.gov.metropol.area247.contenedora.util.NivelCapa;
import co.gov.metropol.area247.core.domain.capa.dto.CapaGeometries;
import co.gov.metropol.area247.core.domain.marker.dto.LandMessage;
import co.gov.metropol.area247.core.domain.marker.dto.Marker;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerGeometry;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerInfo;
import co.gov.metropol.area247.core.domain.marker.dto.MarkersCharacterTab;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="Rest LTE para exponer información de los marcadores")
@RestController
@RequestMapping("/api")
public class MarkerRest {
	
	private static final int NIVEL_CAPA = 2;
	
	@Autowired
	IContenedoraMarkerService markerSvc;
	
	@ResponseBody
	@ApiOperation(value="/contenedora/markers/{nivelCapa}/{idsCapas}/{query}", 
				  notes="Se obtienen los marcadores asociados a una cadena de búsqueda")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/markers/{nivelCapa}/{idsCapas}/{query}")
	public ResponseEntity<List<Marker>> getMarkersCapaN(@PathVariable("nivelCapa") NivelCapa nivelCapa,
														@PathVariable("idsCapas") String idsCapas,
														@PathVariable("query") String query){
		
		return new ResponseEntity<List<Marker>>(markerSvc.getMarkers(nivelCapa, idsCapas, query), HttpStatus.OK);
		
	}	
	
	@ResponseBody
	@ApiOperation(value="/contenedora/markers/{nivelCapa}/{idCapa}", 
				  notes="Se obtienen los puntos cercanos dentro de un radio asociado a la ubicación dada")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/markers/{nivelCapa}/{idCapa}")
	public ResponseEntity<CapaGeometries> getMarkersCapaN(@PathVariable("nivelCapa") NivelCapa nivelCapa, 
														  @PathVariable("idCapa") Long idCapa){
		return new ResponseEntity<CapaGeometries>(markerSvc.getMarkers(nivelCapa, idCapa), HttpStatus.OK);
		
	}
	
	
	@ResponseBody
	@ApiOperation(value="/contenedora/marker-info/{idMarker}", 
				  notes="Se obtiene información de un marcado por su ID")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/marker-info/{idMarker}")
	public ResponseEntity<MarkerInfo> getMarkerInfo(@PathVariable("idMarker") Long idMarker){
		return new ResponseEntity<MarkerInfo>(markerSvc.getMarkerInfo(idMarker), HttpStatus.OK);
	}
	
	@ResponseBody
	@ApiOperation(value="/contenedora/marker/{idMarker}", 
				  notes="Se obtienen las coordenadas de un marcado por su ID")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/marker/{idMarker}")
	public ResponseEntity<MarkerGeometry> getMarker(@PathVariable("idMarker") Long idMarker){
		return new ResponseEntity<MarkerGeometry>(markerSvc.getMarker(idMarker), HttpStatus.OK);
		
	}
	
	@ResponseBody
	@ApiOperation(value="/contenedora/markers/character-tab", 
				  notes="Se obtienen las coordenadas de un marcado por su ID")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/markers/character-tab")
	public ResponseEntity<MarkersCharacterTab> getFichaCaracterizacion(@RequestParam("lat") double lat,
																       @RequestParam("lng") double lng){
		return new ResponseEntity<MarkersCharacterTab>(markerSvc.getCharacterTab(lat, lng), HttpStatus.OK);
		
	}
	
	@ResponseBody
	@ApiOperation(value="/contenedora/marker/inside", 
				  notes="Se obtienen las coordenadas de un marcado por su ID")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/marker/inside")
	public ResponseEntity<Boolean> validateInsideAMVA(@RequestParam("lat") double lat,
													  @RequestParam("lng") double lng){
		return new ResponseEntity<Boolean>(markerSvc.validateInsideAMVA(lat, lng), HttpStatus.OK);
	}
	
	@ResponseBody
	@ApiOperation(value="/contenedora/marker/land-type", 
				  notes="Se obtienen las coordenadas de un marcado por su ID")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/marker/land-type")
	public ResponseEntity<LandMessage> getLandTypeAMVA(@RequestParam("lat") double lat,
													   @RequestParam("lng") double lng){
		return new ResponseEntity<LandMessage>(markerSvc.getLandMessage(lat, lng), HttpStatus.OK);
	}
	
	
	@ResponseBody
	@ApiOperation(value="/contenedora/markers/avistamiento/{idCapa}/{latitud}/{longitud}/{radioAccion}", 
				  notes="Se obtienen los puntos de avistamiento cercanos dentro de un radio asociado a la ubicación dada")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
	})
	@GetMapping("/contenedora/markers/avistamiento/{idCapa}/{latitud}/{longitud}/{radioAccion}")
	public ResponseEntity<CapaGeometries> getMarkersAvistamiento(@PathVariable("idCapa") Long idCapa,
																 @PathVariable("latitud") Double latitud,
																 @PathVariable("longitud") Double longitud,
																 @PathVariable("radioAccion") int radioAccion){		
		return new ResponseEntity<CapaGeometries>(markerSvc.getMarkersCapaByRadio(idCapa,latitud,longitud,radioAccion), HttpStatus.OK);		
	}
	
	@ResponseBody
	@ApiOperation(value="/contenedora/geometry-invert/{idMarker}", 
				  notes="Se obtiene la geometria asociada a marcador por su ID")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/geometry-invert/{idMarker}")
	public ResponseEntity<String> getGeometryXMarkerId(@PathVariable("idMarker") Long idMarker) {
		
		return new ResponseEntity<String>(markerSvc.getGeometry(idMarker),HttpStatus.OK);
		
	}
	
	@ResponseBody
	@ApiOperation(value="/contenedora/geometries-invert/{idCategory}", 
				  notes="Se obtiene la geometria asociada a marcador por su ID")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/geometries-invert/{idCategory}")
	public ResponseEntity<Boolean> getGeometryXCategoryId(@PathVariable("idCategory") Long idCategory) {
		
		return new ResponseEntity<Boolean>(markerSvc.invertirCoordenadasCategoria(idCategory),HttpStatus.OK);
		
	}
	
}
