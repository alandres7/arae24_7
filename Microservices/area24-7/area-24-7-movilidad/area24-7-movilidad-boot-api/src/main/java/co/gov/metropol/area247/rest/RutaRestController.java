package co.gov.metropol.area247.rest;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.mapper.response.CicloviaResponse;
import co.gov.metropol.area247.mapper.response.DisponibilidadCiclaResponse;
import co.gov.metropol.area247.mapper.response.LineaResponse;
import co.gov.metropol.area247.mapper.response.PosibilidadViajeResponse;
import co.gov.metropol.area247.mapper.response.RutaAndLineaResponse;
import co.gov.metropol.area247.mapper.response.RutaGtpcResponse;
import co.gov.metropol.area247.mapper.response.RutaOrLineaInfoResponse;
import co.gov.metropol.area247.mapper.response.TareviaEstacionEnCiclaResponse;
import co.gov.metropol.area247.model.DisponibilidadCiclaDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.RutaConcretaDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ClasificacionInformacion;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoRespuestaWS;
import co.gov.metropol.area247.service.IEnciclaService;
import co.gov.metropol.area247.service.ILineaMetroService;
import co.gov.metropol.area247.service.IRutaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.web.Coordenada;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Permite al usuario validar las rutas que se pueden estar cerca a su ubicacion", produces = MediaType.APPLICATION_JSON_VALUE, tags = {
		"Rutas" })
@RestController
@RequestMapping("/rutas")
public class RutaRestController {

	@Autowired
	private IRutaService rutaService;

	@Autowired
	private IEnciclaService enciclaService;

	@Autowired
	private ILineaMetroService lineaMetroService;

	@ApiOperation(value = "Obtener la informacion de la estación", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = TareviaEstacionEnCiclaResponse.class),
			@ApiResponse(code = 204, message = "No la informacion de la ruta"),
			@ApiResponse(code = 500, message = "Intentalo mas tarde") })
	@RequestMapping(value = "/encicla/{id-estacion}", method = RequestMethod.GET)
	public ResponseEntity<TareviaEstacionEnCiclaResponse> informacionDeEstacionEncicla(
			@PathVariable(value = "id-estacion") Long id) {

		TareviaEstacionEnciclaDTO tareviaEstacionEncicla = enciclaService.findByEstacionEnciclaId(id);

		if (!Utils.isNull(tareviaEstacionEncicla)) {
			return new ResponseEntity<TareviaEstacionEnCiclaResponse>(
					new TareviaEstacionEnCiclaResponse(tareviaEstacionEncicla), HttpStatus.OK);
		}
		return new ResponseEntity<TareviaEstacionEnCiclaResponse>(HttpStatus.NO_CONTENT);

	}

	@ApiOperation(value = "Obtener la informacion de disponibilidad de bicicletas en la estación", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = TareviaEstacionEnCiclaResponse.class),
			@ApiResponse(code = 204, message = "No la informacion de la ruta"),
			@ApiResponse(code = 500, message = "Intentalo mas tarde") })
	@RequestMapping(value = "/encicla/disponibilidad/{id-estacion}", method = RequestMethod.GET)
	public ResponseEntity<DisponibilidadCiclaResponse> disponibilidadDeBicicletasEstacionEncicla(
			@PathVariable(value = "id-estacion") Long id) {

		DisponibilidadCiclaDTO disponibilidadEncicla = enciclaService.findAvailabilityByEstacionId(id);

		if (!Utils.isNull(disponibilidadEncicla)) {
			return new ResponseEntity<DisponibilidadCiclaResponse>(
					new DisponibilidadCiclaResponse(disponibilidadEncicla), HttpStatus.OK);
		}
		return new ResponseEntity<DisponibilidadCiclaResponse>(HttpStatus.NO_CONTENT);

	}

	@ApiOperation(value = "Obtener la informacion de rutas y lineas por medio de su codigo o nombre", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = RutaAndLineaResponse.class),
			@ApiResponse(code = 204, message = "No la informacion de la ruta o linea"),
			@ApiResponse(code = 500, message = "Intentalo mas tarde") })
	@RequestMapping(value = "/lineas/{parametro}", method = RequestMethod.GET)
	public ResponseEntity<RutaAndLineaResponse> obtenerRutasOrLineas(
			@PathVariable(value = "parametro") String parametro) {

		List<LineaMetroDTO> lineasDTO = lineaMetroService.findByCodigoOrDescripcion(parametro.toUpperCase());
		List<RutaMetroDTO> rutasDTO = rutaService.findByCodigoOrDescripcion(parametro.toUpperCase());

		RutaAndLineaResponse respuesta = null;
		
		if (Utils.isEmpty(lineasDTO) && Utils.isEmpty(rutasDTO)) {
			
			respuesta = new RutaAndLineaResponse();
			respuesta.setCodigo(2);
			respuesta.setDescripcion("No se encuentra información relacionada");
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
			
		} else {
			
			respuesta = new RutaAndLineaResponse(lineasDTO, rutasDTO);
			respuesta.setCodigo(1);
			respuesta.setDescripcion("Exitoso");
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
			
		}
	}

	@ApiOperation(value = "Dar las posibles opciones de turas", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = CicloviaResponse.class),
			@ApiResponse(code = 204, message = "No se encuentran viajes Cerca"),
			@ApiResponse(code = 404, message = "No se encuentran rutas cercanas en la ubicación y preferencias de transporte especificado"),
			@ApiResponse(code = 500, message = "Intentalo mas tarde") })
	@RequestMapping(value = "/viajes/{fecha}/{latitudOrigen}/{longitudOrigen}/{radio}/{modosTransporte}", method = RequestMethod.GET)
	public ResponseEntity<PosibilidadViajeResponse> posiblesViajes(
			@PathVariable(value = "fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
			@PathVariable(value = "latitudOrigen") double latitudOrigen,
			@PathVariable(value = "longitudOrigen") double longitudOrigen,
			@PathVariable(value = "radio") double radio,
			@PathVariable(value = "modosTransporte") List<Integer> modosTransporte) {
		
		try {

			Coordenada coordenada = new Coordenada(latitudOrigen, longitudOrigen, radio);
			Map<ClasificacionInformacion, List<Object>> informacion = rutaService
					.obtenerInformacionTransporteCercano(coordenada, modosTransporte);

			if (informacion.containsKey(ClasificacionInformacion.RUTAS)
					&& !informacion.get(ClasificacionInformacion.RUTAS).isEmpty()) {
				PosibilidadViajeResponse respuesta = new PosibilidadViajeResponse(informacion);
				return new ResponseEntity<>(respuesta, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			String msg = String.format("Ocurrió una excepción al momento de obtener las ruta mas cercanas: %s", e);
			LoggingUtil.logException(msg);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@ApiOperation(value = "Obtiene el nombre o descripcion de las lineas o rutas que coincidan con el parametro de entrada", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = CicloviaResponse.class),
			@ApiResponse(code = 204, message = "No se encuentra informacion de lineas o rutas con el parametro fijado"),
			@ApiResponse(code = 500, message = "Intentalo mas tarde") })
	@RequestMapping(value = "/autocompletado/{parametro}", method = RequestMethod.GET)
	public ResponseEntity<List<RutaConcretaDTO>> obtenerNombreDescripcionLineasRutas(
			@PathVariable(value = "parametro") String parametro) {

		try {

			List<RutaConcretaDTO> rutasConcretasDTO = rutaService
					.findInfoLineasAndRutasByCodigoOrDescripcion(parametro);
			return new ResponseEntity<>(rutasConcretasDTO, HttpStatus.OK);

		} catch (Exception e) {
			String msg = String.format(
					"Ocurrió una excepción al momento de consultar la informacion de las lineas y rutas por el parametro %s",
					parametro);
			LoggingUtil.logException(msg);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Obtener la informacion de rutas y lineas por medio del tipo de ruta (ruta o linea) y su id", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = RutaAndLineaResponse.class),
			@ApiResponse(code = 204, message = "No la informacion de la ruta o linea"),
			@ApiResponse(code = 500, message = "Intentalo mas tarde") })
	@RequestMapping(value = "/lineas/{id}/{tipo}", method = RequestMethod.GET)
	public ResponseEntity<RutaOrLineaInfoResponse> obtenerInfoLineaORuta(@PathVariable(value = "id") Long id,
			@PathVariable(value = "tipo") Character tipo) {

		RutaOrLineaInfoResponse resultado = new RutaOrLineaInfoResponse();
		HttpStatus httpStatus = null;

		try {

			if (tipo == 'L') {
				resultado.setLinea(new LineaResponse(lineaMetroService.findById(id, true, true)));
			} else if (tipo == 'R') {
				resultado.setRuta(new RutaGtpcResponse(rutaService.findById(id, true, true)));
			}

			if (Utils.isNull(resultado.getLinea()) && Utils.isNull(resultado.getRuta())) {
				resultado.setCodigo(EstadoRespuestaWS.NO_ENCONTRO_INFO.getIndice());
				resultado.setDescripcion(EstadoRespuestaWS.NO_ENCONTRO_INFO.getMsg());
				httpStatus = HttpStatus.NOT_FOUND;
			} else {
				resultado.setCodigo(EstadoRespuestaWS.EXITOSO.getIndice());
				resultado.setDescripcion(EstadoRespuestaWS.EXITOSO.getMsg());
				httpStatus = HttpStatus.OK;
			}

			return new ResponseEntity<>(resultado, httpStatus);

		} catch (Exception e) {
			String msg = String.format(
					"Ocurrió una excepción al momento de consultar la informacion de las lineas y rutas por el id %s y el tipo de ruta %s",
					id, tipo);
			LoggingUtil.logException(msg);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
