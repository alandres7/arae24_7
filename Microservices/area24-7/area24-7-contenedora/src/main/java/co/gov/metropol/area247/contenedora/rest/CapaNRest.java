package co.gov.metropol.area247.contenedora.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.service.IContenedoraCapaNService;
import co.gov.metropol.area247.contenedora.util.NivelCapa;
import co.gov.metropol.area247.core.domain.capa.dto.CapaN;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="Rest lte para carga genérica de datos asociados a las capas de AREA24/7")
@RestController
@RequestMapping("/api")
public class CapaNRest {
	
	@Autowired
	IContenedoraCapaNService capaSvc;
	
	@ResponseBody
	@ApiOperation(value="/contenedora/capa-n/{nivel}/{idCapaN}", notes="Se obtienen las capas del nivel ingresado (N) contenidas dentro de la capa (N-1) identificada por ID ingresado")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/capa-n/{nivelCapa}/{idCapaN}")
	public ResponseEntity<List<CapaN>> getCapa(@PathVariable("nivelCapa") final NivelCapa nivelCapa, @PathVariable("idCapaN") Long idCapaN){
		return new ResponseEntity<List<CapaN>>(capaSvc.getCapaN(nivelCapa, idCapaN), HttpStatus.OK);
	}
}
