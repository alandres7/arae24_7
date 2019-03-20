package co.gov.metropol.area247.huellas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.huellas.svc.IHuellasPosconsumoSvc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Huellas")
@RestController
@RequestMapping("/api/huellas/posconsumo")
public class PosconsumoRestController {
	
	@Autowired
	IHuellasPosconsumoSvc posconsumoSvc;
	
	@ResponseBody
	@ApiOperation(value="/update", notes="Operación para cargar los puntos posconsumo desde un servicio externo")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@PutMapping("/update")
	public ResponseEntity<Boolean> updatePuntosPosConsumo(@RequestParam("idCapa")Long idCapa){
		
		return new ResponseEntity<>( posconsumoSvc.updatePuntosPosconsumo(idCapa), HttpStatus.OK);
		
	}
	
	@ResponseBody
	@ApiOperation(value="/detail", notes="Operación para obtener el detalle de un punto posconsumo basado en el ID del marcador asociado al punto")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"),
			@ApiResponse(code = 500, message = "Error ejecutando la operación")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping("/detail")
	public ResponseEntity<String> getDetailXIdMarcador(@RequestParam("idMarcador")Long idMarcador){
		try {
			return new ResponseEntity<>(posconsumoSvc.getDetailXIdMarcador(idMarcador), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("Error recuperando el detalle del punto posconsumo", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
