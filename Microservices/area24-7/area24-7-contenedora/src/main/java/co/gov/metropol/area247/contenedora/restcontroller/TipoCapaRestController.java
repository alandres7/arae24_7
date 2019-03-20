package co.gov.metropol.area247.contenedora.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.model.TipoCapa;
import co.gov.metropol.area247.contenedora.service.IContenedoraTipoCapaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Contenedora")
@RequestMapping(value = "/api")
public class TipoCapaRestController {

	@Autowired
	IContenedoraTipoCapaService tipoCapaService;

	@ResponseBody
	@ApiOperation(value = "/tipocapa", notes = "Permite la consulta de los diferentes tipos de capas presentes en las aplicaciones del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/tipocapa", method = RequestMethod.GET)
	public ResponseEntity<?> tipoCapaObtenerTodas() {
		try {
			return new ResponseEntity<Iterable<TipoCapa>>(tipoCapaService.tipoCapaObtenerTodas(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se pudo realizar la consulta, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/tipocapa/{idTipoCapa}", method = RequestMethod.GET)
	public ResponseEntity<?> tipoCapaObtenerPorId(@PathVariable(name = "idTipoCapa") Long idTipoCapa) {
		try {
			TipoCapa tipoCapa = tipoCapaService.tipoCapaObtenerPorId(idTipoCapa);
			if (tipoCapa != null) {
				return new ResponseEntity<TipoCapa>(tipoCapa, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se encuentra el tipo de capa", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error recuperando el tipo de capa; " + e, HttpStatus.BAD_REQUEST);
		}
	}

}
