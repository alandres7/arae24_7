package co.gov.metropol.area247.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.service.IPosibilidadViajeService;
import co.gov.metropol.area247.services.rest.opt.PosibilidadViajeWSDTO;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Funciona como puente entre el aplicativo movil y el servicio de otp (opentripplanner), el otp calcula los posibles viajes que se pueden realizar desde un origen hasta un destino y modo de transporte seleccionado")
@RestController
@RequestMapping("/viajes/")
public class PosibilidadesViajeController {

	@Autowired
	private IPosibilidadViajeService posibilidadViajeService;

	@ApiOperation(value = "Obtener las posibilidades de viajes desde un origen a un destino", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK", response = PosibilidadViajeWSDTO.class),
			@ApiResponse(code = 204, message = "No la informacion de la ubicaion"),
			@ApiResponse(code = 500, message = "Intentalo mas tarde") })
	@RequestMapping(value = "/{longitudOrigen}/{latitudOrigen}/{longitudDestino}/{latitudDestino}/{modoTransporte}", method = RequestMethod.GET)
	public ResponseEntity<PosibilidadViajeWSDTO> obtenerPosiblesViajes(
			@PathVariable(value = "longitudOrigen") double longitudOrigen,
			@PathVariable(value = "latitudOrigen") double latitudOrigen,
			@PathVariable(value = "longitudDestino") double longitudDestino,
			@PathVariable(value = "latitudDestino") double latitudDestino,
			@PathVariable(value = "modoTransporte") List<Long> modosTransporte) {

		Coordenada origen = new Coordenada(latitudOrigen, longitudOrigen);
		Coordenada destino = new Coordenada(latitudDestino, longitudDestino);
		Date fecha = Calendar.getInstance().getTime();

		try {
			PosibilidadViajeWSDTO posibilidadViajeWSDTO = posibilidadViajeService.consultarPosiblesViajes(origen, destino, fecha, modosTransporte);
			return new ResponseEntity<>(posibilidadViajeWSDTO, HttpStatus.OK);
		} catch (Area247Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

}
