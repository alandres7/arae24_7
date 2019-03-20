package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.centrocontrol.model.Afectacion;
import co.gov.metropol.area247.centrocontrol.model.Auditoria;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAfectacionService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAuditoriaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api")
public class AuditoriaRestController {

	@Autowired
	ICentroControlAuditoriaService auditoriaService;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/auditoria", method = RequestMethod.GET)
	public ResponseEntity<?> auditoriaObtenerTodas(){
		try{
			List<Auditoria> auditorias = auditoriaService.auditoriaObtenerTodas();
			return new ResponseEntity<List<Auditoria>>(auditorias,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo retornar las auditorias; "
		    + e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/auditoria/{idAuditoria}", method = RequestMethod.GET)
	public ResponseEntity<?> auditoriaObtenerPorId(@PathVariable(name="idAuditoria")Long idAuditoria){
		try{
			Auditoria auditoria = auditoriaService.auditoriaObtenerPorId(idAuditoria);
			if(auditoria!=null){
				return new ResponseEntity<Auditoria>(auditoria,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentra la auditoria; revisar id"
						,HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo retornar la auditoria;"
					+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/auditoria", method = RequestMethod.POST)
	public ResponseEntity<?> auditoriaGuardar(@RequestBody Auditoria auditoria){
		try{
			Auditoria auditoriaAuxiliar = auditoriaService.auditoriaGuardar(auditoria);
			return new ResponseEntity<Auditoria>(auditoriaAuxiliar,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo guardar la auditoria;"
					+e,HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/auditoria/{idAuditoria}", method = RequestMethod.DELETE)
	public ResponseEntity<?> auditoriaBorrar(@PathVariable(name="idAuditoria") Long idAuditoria){
		boolean auditoriaBorrado = auditoriaService.auditoriaBorrar(idAuditoria);
		if(auditoriaBorrado){
			return new ResponseEntity<String>("Borrado exitoso de la auditoria",
					HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se pudo borrar la auditoria",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
