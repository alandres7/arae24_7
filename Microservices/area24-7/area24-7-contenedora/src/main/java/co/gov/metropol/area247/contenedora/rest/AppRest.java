package co.gov.metropol.area247.contenedora.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.model.dto.App;
import co.gov.metropol.area247.contenedora.service.IContenedoraAplicacionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="Rest lte para carga genérica de datos asociados a las capas de AREA24/7")
@RestController
@RequestMapping("/api")
public class AppRest {
	
	@Autowired
	IContenedoraAplicacionService appSvc;
	
	@ResponseBody
	@ApiOperation(value="/contenedora/apps", notes="")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/contenedora/apps")
	public ResponseEntity<List<App>> getApps(){
		try {
			return new ResponseEntity<List<App>>(appSvc.getApps(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<App>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
