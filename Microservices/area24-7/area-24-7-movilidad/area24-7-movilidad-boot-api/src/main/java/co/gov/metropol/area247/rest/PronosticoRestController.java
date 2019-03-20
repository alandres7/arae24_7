package co.gov.metropol.area247.rest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.mapper.response.PronosticoResponse;
import co.gov.metropol.area247.model.PronosticoDTO;
import co.gov.metropol.area247.service.IPronosticoService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.web.Coordenada;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Permite al usuario ver el pronostico del tiempo del origen y destino", produces = MediaType.APPLICATION_JSON_VALUE, tags = {
		"pronostico" })
@RestController
@RequestMapping("/pronostico")
public class PronosticoRestController {

	@Autowired
	private IPronosticoService pronosticoService;

	@ApiOperation(value = "Proporcionar pronostico del tiempo y posibles precipitaciones del origen y destino", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = PronosticoResponse.class),
			@ApiResponse(code = 204, message = "No la informacion de la ruta"),
			@ApiResponse(code = 500, message = "Intentalo mas tarde") })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/{fecha}/{fechaOrigen}/{longitudOrigen}/{latitudOrigen}/{fechaDestino}/{longitudDestino}/{latitudDestino}", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public ResponseEntity<PronosticoResponse> informacionDeRuta(
			@PathVariable(value = "fecha") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fecha,
			@PathVariable(value = "fechaOrigen") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fechaOrigen,
			@PathVariable(value = "longitudOrigen") double longitudOrigen,
			@PathVariable(value = "latitudOrigen") double latitudOrigen,
			@PathVariable(value = "fechaDestino") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fechaDestino,
			@PathVariable(value = "longitudDestino") double longitudDestino,
			@PathVariable(value = "latitudDestino") double latitudDestino) {
		try {
			PronosticoDTO pronostico = pronosticoService.obtenerPronostico(fecha, fechaOrigen,
					new Coordenada(latitudOrigen, longitudOrigen), fechaDestino,
					new Coordenada(latitudDestino, longitudDestino));

			if (Utils.isNull(pronostico)) {
				return new ResponseEntity<PronosticoResponse>(HttpStatus.NO_CONTENT);
			} else {
				PronosticoResponse pronosticoResponse = new PronosticoResponse(pronostico);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
				return new ResponseEntity<PronosticoResponse>(pronosticoResponse, headers, HttpStatus.OK);
			}
		} catch (NumberFormatException ex) {
			LoggingUtil.logException("Ocurrio un Error.", ex);
			return null;
		}
	}

}
