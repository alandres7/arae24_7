package co.gov.metropol.area247.seguridad.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.model.LatLng;

import co.gov.metropol.area247.core.domain.marker.dto.Town;
import co.gov.metropol.area247.core.domain.marker.dto.TownPolygon;
import co.gov.metropol.area247.seguridad.service.ISeguridadMunicipioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api("Api para Queries sobre los municipios de la app")
@RequestMapping("/api")
public class MunicipioRestController {
	
	@Autowired
	ISeguridadMunicipioService municipioSvc;
	
	@ResponseBody
	@ApiOperation(value="/seguridad/municipo/{idMunicipio}", notes="")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/seguridad/municipo/{idMunicipio}")
	public ResponseEntity<TownPolygon> getTownPolygonById(@PathVariable("idMunicipio") Long idMunicipio) {
		return new ResponseEntity<TownPolygon>(municipioSvc.getPolygonMunicipio(idMunicipio), HttpStatus.OK);
	}
	
	@ResponseBody
	@ApiOperation(value="/seguridad/subregion-AMVA", notes="")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/seguridad/subregion")
	public ResponseEntity<List<LatLng>> getSubRegionValleAburra() {
		return new ResponseEntity<List<LatLng>>(municipioSvc.getPolygonAMVA(), HttpStatus.OK);
	}
	
	@ResponseBody
	@ApiOperation(value="/seguridad/municipios-AMVA", notes="")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/seguridad/municipios-AMVA")
	public ResponseEntity<List<Town>> getMunicipiosValleAburra() {
		return new ResponseEntity<List<Town>>(municipioSvc.getTowns(), HttpStatus.OK);
	}
	
}
