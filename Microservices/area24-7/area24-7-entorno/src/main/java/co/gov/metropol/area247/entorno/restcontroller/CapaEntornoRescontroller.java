package co.gov.metropol.area247.entorno.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.core.gateway.siata.dto.StationAgua;
import co.gov.metropol.area247.core.gateway.siata.dto.StationAire;
import co.gov.metropol.area247.core.gateway.siata.dto.StationClima;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataAgua;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataAire;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataClima;
import co.gov.metropol.area247.entorno.model.Capas;
import co.gov.metropol.area247.entorno.service.IEntornoCapaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@RestController
@RequestMapping(value = "/api")
public class CapaEntornoRescontroller {
	
	@Autowired
	IEntornoCapaService capaService;
	
	@ResponseBody
	@ApiOperation(value = "/entorno/capa/{idCapa}", notes = "Permite la actualización de una capa de la aplicación Mi entorno del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Actualización exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
            @ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
            @ApiResponse(code = 404, message = "Not Found. La url o el recurso que se intenta acceder no se encuetra disponible o no existe"),
		    @ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/entorno/capa/{idCapa}", method = RequestMethod.PUT)
	public ResponseEntity<String> actualizarCapaEntorno(@PathVariable (value = "idCapa") Long idCapa){
		try {
			Capas capa = Capas.obtenerPorId(idCapa.intValue());
			switch(capa){
				case AIRE:
					capaService.capaObtenerMedicionEstaciones(idCapa,new StationsDataAire());
					return new ResponseEntity<String>("Actualizacion de capa aire exitosa", HttpStatus.OK);
				case AGUA:
					capaService.capaObtenerMedicionEstaciones(idCapa,new StationsDataAgua());
					return new ResponseEntity<String>("Actualizacion de capa nivel agua exitosa", HttpStatus.OK);
				case CLIMA:
					capaService.capaObtenerMedicionEstaciones(idCapa,new StationsDataClima());
					return new ResponseEntity<String>("Actualizacion de capa clima exitosa", HttpStatus.OK);
				default:
					return new ResponseEntity<String>("Capa no actualizada, revisar ID", HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido realizar la actualización", HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/entorno/pruebagateway/{idCapa}", method = RequestMethod.GET)
	public ResponseEntity<?> entornoProbarGateWay(@PathVariable(name="idCapa")Long idCapa){
		int idCapaEntero = idCapa.intValue();
		List<?> marcadores = null;
		switch(idCapaEntero){
			case 10:
//				marcadores = estacionesGateway.obtenerMedicionEstaciones(idCapa,new StationsDataAire());
				return new ResponseEntity<List<StationAire>>((List<StationAire>) marcadores,HttpStatus.FOUND);
			case 11:
//				marcadores = estacionesGateway.obtenerMedicionEstaciones(idCapa,new StationsDataAire());
				return new ResponseEntity<List<StationAgua>>((List<StationAgua>) marcadores,HttpStatus.FOUND);
			case 12:
//				marcadores = estacionesGateway.obtenerMedicionEstaciones(idCapa,new StationsDataClima());
				return new ResponseEntity<List<StationClima>>((List<StationClima>) marcadores,HttpStatus.FOUND);
			case 13:
			default:
		}
		return new ResponseEntity<String>("",HttpStatus.NOT_FOUND);

	}
}
