package co.gov.metropol.area247.contenedora.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.dao.IContenedoraCoordenadaRepository;
import co.gov.metropol.area247.contenedora.model.Coordenada;
import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;
import co.gov.metropol.area247.core.util.GeometryUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value="/spatial")
public class PruebasSpatialRestController {
	
	@Autowired
	IContenedoraCoordenadaRepository coordenadaDao;
	
	CoordenadaDto coordenadaDto;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "consulta/point/{idCoordenado}", method = RequestMethod.GET)
	public ResponseEntity<?> obtenerObjetoPunto(Long idCoordenado){
		Coordenada pCoordenada = (Coordenada)coordenadaDao.findOne(idCoordenado);
		//GeometryUtil.obtenerPunto(6.211362, -75.586339);
		if(pCoordenada != null){
			if(pCoordenada.getCoordenadaPunto() == null){
				pCoordenada.setCoordenadaPunto(GeometryUtil.obtenerPunto(6.211362, -75.586339));
				coordenadaDao.save(pCoordenada);
			}
			
			return new ResponseEntity<>(pCoordenada.getCoordenadaPunto(), HttpStatus.OK);
		}
		return new ResponseEntity<>("sin contenido para metodo", HttpStatus.BAD_REQUEST);
	}
	

}
