package co.gov.metropol.area247.contenedora.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.dao.IContenedoraCoordenadaRepository;
import co.gov.metropol.area247.contenedora.model.Coordenada;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Contenedora")
@RequestMapping(value = "/api")
public class CoordenadaRestController {
	@Autowired
	IContenedoraCoordenadaRepository coordenadaService;
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/coordenada/{idCoordenada}", method = RequestMethod.GET)
	public ResponseEntity<?> CoordenadaObtenerPorId(@PathVariable(value="idCoordenada")Long idCoordenada){
		try{
			Coordenada coordenada = coordenadaService.findOne(idCoordenada);
			return new ResponseEntity<Coordenada>(coordenada,HttpStatus.OK);

		}catch(Exception e){
			return new ResponseEntity<String>("Error: "+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
