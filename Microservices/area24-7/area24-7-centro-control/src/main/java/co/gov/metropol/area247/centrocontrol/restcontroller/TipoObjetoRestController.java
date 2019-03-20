package co.gov.metropol.area247.centrocontrol.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.centrocontrol.model.TipoObjeto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlTipoObjetoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Centro de control")
@RequestMapping(value = "/api")
public class TipoObjetoRestController {
	
	@Autowired
	ICentroControlTipoObjetoService tipoObjetoService;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/tipoobjeto/{idTipoObjeto}", method = RequestMethod.GET)
	public ResponseEntity<?> getTipoObjeto(@PathVariable ("idTipoObjeto") Long idTipoObjeto){
		TipoObjeto tipoObjeto = tipoObjetoService.tipoObjetoObtenerPorId(idTipoObjeto);
		if(tipoObjeto!=null){
			return new ResponseEntity<TipoObjeto>(tipoObjeto, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(String.format("No se encuentran objetos asociados al id %s , revisar y reintentar", idTipoObjeto), HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
