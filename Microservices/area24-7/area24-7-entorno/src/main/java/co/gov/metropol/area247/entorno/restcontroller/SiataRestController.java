package co.gov.metropol.area247.entorno.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.entorno.service.IEntornoSiataSvc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Rest para test de los servicios del siata")
@RequestMapping("/api/entorno/siata")
public class SiataRestController {
	
	@Autowired
	IEntornoSiataSvc siataSvc;
	
	@ResponseBody
	@ApiOperation(value="/stations-by-ID-alerta", notes="")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/stations-by-ID-alerta")
	public ResponseEntity<List<?>> getStationData(@RequestParam("idCapa")Long idCapa)
	{
		try {
			return new ResponseEntity<List<?>>( siataSvc.getStationsData(idCapa), HttpStatus.OK);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<List<?>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
