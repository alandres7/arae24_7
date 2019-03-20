package co.gov.metropol.area247.huellas.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.huellas.model.ActividadDto;
import co.gov.metropol.area247.huellas.rest.response.HuellasResponse;
import co.gov.metropol.area247.huellas.rest.response.msg.HuellasMsgs;
import co.gov.metropol.area247.huellas.svc.IHuellasActividadSvc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Huellas")
@RestController
@RequestMapping("/api/huellas/encuesta-hidrica")
public class ActividadRestController {
	
	private static final String EMPTY = "";
	private static final String NOMBRE_ENTIDAD = "actividad(es)";
	private String msg;
	
	private HuellasResponse<ActividadDto> actividadResponse;
	private ResponseEntity<HuellasResponse<ActividadDto>> response;
	
	@Autowired
	private IHuellasActividadSvc actividadSvc; 
	
	@ResponseBody
	@ApiOperation(value="/actividades", notes="Obtener un listado de actividades basado en el ID de la capa.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value={
			@ApiResponse(code=200,message="Se han recuperado todas las actividades"),
			@ApiResponse(code=404,message="No se han encontrado actividades para la capa respectiva"),
			@ApiResponse(code=500,message="Error recuperando las actividades desde el servidor")
						})
	@GetMapping(value="/actividades")
	public ResponseEntity<HuellasResponse<ActividadDto>> getActividadesXCapa(@RequestParam("idCapa") long idCapa){
		actividadResponse = new HuellasResponse<>();
		try {
			List<ActividadDto> actividades = actividadSvc.getActividadesXCapa(idCapa);
			actividadResponse.setResponses(actividades);
			if(!actividades.isEmpty()) {
				actividadResponse.configResponse(HuellasMsgs.SUCCESS_LIST, NOMBRE_ENTIDAD, EMPTY);
				response = new ResponseEntity<>(actividadResponse, HttpStatus.OK);
			}else {
				actividadResponse.configResponse(HuellasMsgs.EMPTY_LIST, NOMBRE_ENTIDAD, EMPTY);
				response = new ResponseEntity<>(actividadResponse, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			actividadResponse.configResponse(HuellasMsgs.FAILURE_LIST, NOMBRE_ENTIDAD, EMPTY, e);
			response = new ResponseEntity<>(actividadResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@ResponseBody
	@ApiOperation(value="/actividad", notes="Obtener un listado de actividades basado en el ID.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value={
			@ApiResponse(code=200,message="Se han recuperado todas las actividades"),
			@ApiResponse(code=404,message="No se han encontrado actividades para la capa respectiva"),
			@ApiResponse(code=500,message="Error recuperando las actividades desde el servidor")
						})
	@GetMapping(value="/actividad")
	public ResponseEntity<HuellasResponse<ActividadDto>> getActividadXId(@RequestParam("id") long id){
		actividadResponse = new HuellasResponse<>();
		try {
			ActividadDto actividad = actividadSvc.getActividadXId(id);
			if(actividad != null) {
				actividadResponse.setResponse(actividad);
				actividadResponse.configResponse(HuellasMsgs.SUCCESS, NOMBRE_ENTIDAD, EMPTY);
				response = new ResponseEntity<>(actividadResponse, HttpStatus.OK);
			}else {
				actividadResponse.configResponse(HuellasMsgs.EMPTY, NOMBRE_ENTIDAD, EMPTY);
				response = new ResponseEntity<>(actividadResponse, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			actividadResponse.configResponse(HuellasMsgs.FAILURE, NOMBRE_ENTIDAD, EMPTY, e);
			response = new ResponseEntity<>(actividadResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@ResponseBody
	@ApiOperation(value="/actividad", notes="Obtener un listado de actividades basado en el ID.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value={
			@ApiResponse(code=200,message="Se ha creado satisfactoriamente la actividad"),
			@ApiResponse(code=404,message="No se han encontrado actividades para la capa respectiva"),
			@ApiResponse(code=500,message="Error recuperando las actividades desde el servidor")
						})
	@PostMapping(value="/actividad")
	public ResponseEntity<HuellasResponse<ActividadDto>> saveActividad(@RequestBody ActividadDto actividad ){
		actividadResponse = new HuellasResponse<>();
		try {
			if(actividadSvc.saveActividad(actividad)) {
				actividadResponse.setResponse(new ActividadDto());
				actividadResponse.configResponse(HuellasMsgs.SUCCESS_CREATE, NOMBRE_ENTIDAD, EMPTY);
				response = new ResponseEntity<>(actividadResponse, HttpStatus.OK);
			}else {
				msg = "No se pudo crear la entidad";
				actividadResponse.configResponse(HuellasMsgs.EMPTY, NOMBRE_ENTIDAD, msg);
				response = new ResponseEntity<>(actividadResponse, HttpStatus.BAD_REQUEST);
			}			
		}catch (Exception e) {
			actividadResponse.configResponse(HuellasMsgs.FAILURE_CREATE, NOMBRE_ENTIDAD, EMPTY, e);
			response = new ResponseEntity<>(actividadResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  response;
		
	}
	
	@ResponseBody
	@ApiOperation(value="/actividad", notes="Obtener un listado de actividades basado en el ID.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value={
			@ApiResponse(code=200,message="Se ha creado satisfactoriamente la actividad"),
			@ApiResponse(code=400,message="No existe una actividad con ID determinado"),
			@ApiResponse(code=404,message="No se han encontrado actividades para la capa respectiva"),
			@ApiResponse(code=500,message="Error recuperando las actividades desde el servidor")
						})
	@PutMapping(value="/actividad")
	public ResponseEntity<HuellasResponse<ActividadDto>> updateActividad(@RequestBody ActividadDto actividad ){
		actividadResponse = new HuellasResponse<>();
		try {
			if(!actividadSvc.existActividad(actividad.getId())) {
				msg = "No se encontró un actividad con el ID determinado";
				actividadResponse.configResponse(HuellasMsgs.EMPTY, NOMBRE_ENTIDAD, msg);
				return new ResponseEntity<>(actividadResponse, HttpStatus.NOT_FOUND);
			}
			if(actividadSvc.saveActividad(actividad)) {
				actividadResponse.setResponse(new ActividadDto());
				actividadResponse.configResponse(HuellasMsgs.SUCCESS_UPDATE, NOMBRE_ENTIDAD, EMPTY);
				response = new ResponseEntity<>(actividadResponse, HttpStatus.OK);
			}else {
				msg = "No se pudo actualizar la entidad para ID determinado";
				actividadResponse.configResponse(HuellasMsgs.EMPTY, NOMBRE_ENTIDAD, msg);
				response = new ResponseEntity<>(actividadResponse, HttpStatus.BAD_REQUEST);
			}			
		}catch (Exception e) {
			actividadResponse.configResponse(HuellasMsgs.FAILURE_UPDATE, NOMBRE_ENTIDAD, EMPTY, e);
			response = new ResponseEntity<>(actividadResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  response;
	}
	
	@ResponseBody
	@ApiOperation(value="/actividad", notes="Obtener un listado de actividades basado en el ID.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value={
			@ApiResponse(code=200,message="Se han recuperado todas las actividades"),
			@ApiResponse(code=400,message="No existe una actividad con ID determinado"),
			@ApiResponse(code=404,message="No se han encontrado actividades para la capa respectiva"),
			@ApiResponse(code=500,message="Error recuperando las actividades desde el servidor")
						})
	@DeleteMapping(value="/actividad")
	public ResponseEntity<HuellasResponse<ActividadDto>> deleteActividad(@RequestParam("id") long id){
		actividadResponse = new HuellasResponse<>();
		try {
			if(!actividadSvc.existActividad(id)) {
				msg = "No se encontró un actividad con el ID determinado";
				actividadResponse.configResponse(HuellasMsgs.EMPTY, NOMBRE_ENTIDAD, msg);
				return new ResponseEntity<>(actividadResponse, HttpStatus.NOT_FOUND);
			}
			if(actividadSvc.deleteActividad(id)) {
				actividadResponse.configResponse(HuellasMsgs.SUCCESS_DELETE, NOMBRE_ENTIDAD, EMPTY);
				response = new ResponseEntity<>(actividadResponse, HttpStatus.OK);
			}else {
				msg = "No se pudo eliminar la entidad para ID determinado";
				actividadResponse.configResponse(HuellasMsgs.EMPTY, NOMBRE_ENTIDAD, msg);
				response = new ResponseEntity<>(actividadResponse, HttpStatus.BAD_REQUEST);
			}			
		}catch (Exception e) {
			actividadResponse.configResponse(HuellasMsgs.FAILURE_DELETE, NOMBRE_ENTIDAD, EMPTY, e);
			response = new ResponseEntity<>(actividadResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  response;
	}
	
}
