package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlAutoridadMunicipioRepository;
import co.gov.metropol.area247.centrocontrol.model.AutoridadMunicipio;
import co.gov.metropol.area247.centrocontrol.model.dto.AutoridadMunicipioDto;
import co.gov.metropol.area247.centrocontrol.model.enums.Municipio;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAutoridadCompetenteService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAutoridadMunicipioService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlNodoArbolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Centro de control")
@RequestMapping(value = "/api")
public class AutoridadMunicipioRestController {
		
	@Autowired
	ICentroControlNodoArbolService nodoArbolService;
	
	@Autowired
	ICentroControlAutoridadCompetenteService autoridadCompetenteService;
	
	@Autowired
	ICentroControlAutoridadMunicipioService autoridadMunicipioService;
	
	@Autowired
	ICentroControlAutoridadMunicipioRepository autoridadMunicipioDao;
	
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
    })
    @RequestMapping(value = "/autoridadMunicipio", method = RequestMethod.POST)
	public ResponseEntity<?> autoridadMunicipioGuardar(
			@RequestParam(value = "idNodoArbol", required = false) Long idNodoArbol,
			@RequestParam(value = "municipio") final Municipio municipio,
			@RequestParam(value = "idAutoridadCompetente", required = false) Long idAutoridadCompetente) {
		try {								
			if (nodoArbolService.nodoArbolObtenerPorId(idNodoArbol) != null) {								
				if(autoridadCompetenteService.autoridadCompetenteObtenerPorId(idAutoridadCompetente) != null) {
					AutoridadMunicipio autoridadMunicipio = new AutoridadMunicipio();
					autoridadMunicipio.setIdNodoArbol(idNodoArbol);
					autoridadMunicipio.setMunicipio(municipio.getMunicipio());
					autoridadMunicipio.setIdAutoridadCompetente(idAutoridadCompetente);	
					
					if(autoridadMunicipioService.autoridadMunicipioGuardar(autoridadMunicipio)) {
						return new ResponseEntity<String>("Registro exitoso",HttpStatus.OK);
					}else {
						return new ResponseEntity<String>("El nuevo registro no pudo ser creado",HttpStatus.BAD_REQUEST);
					}
				}else {
					return new ResponseEntity<String>
					("El idAutoridad que ha insertado no corresponde a ningún registro",HttpStatus.OK);					
				}
			} else {
				return new ResponseEntity<String>
				("El idNodo que ha insertado no corresponde a ningún registro",HttpStatus.NOT_FOUND);				
			}			
		} catch (Exception e) {
			return new ResponseEntity<String>("El nuevo registro no pudo ser creado, revisar datos y reintentar",
					HttpStatus.BAD_REQUEST);
		}
	}	
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
    })
    @RequestMapping(value = "/autoridadMunicipio", method = RequestMethod.PUT)
	public ResponseEntity<?> autoridadMunicipioActualizar(
			@RequestParam(value = "idNodoArbol", required = false) Long idNodoArbol,
			@RequestParam(value = "municipio") final Municipio municipio,
			@RequestParam(value = "idAutoridadCompetente", required = false) Long idAutoridadCompetente) {
		try {	
			AutoridadMunicipioDto autoridadMunicipioDto = 
					autoridadMunicipioService.autoridadMunicipioPorIdNodoAndMunicipio(idNodoArbol,municipio.toString());
			if (autoridadMunicipioDto != null) {
				if(autoridadCompetenteService.autoridadCompetenteObtenerPorId(idAutoridadCompetente) != null) {
					AutoridadMunicipio autoridadMunicipio = autoridadMunicipioDao.findOne(autoridadMunicipioDto.getId());
					autoridadMunicipio.setIdAutoridadCompetente(idAutoridadCompetente);	
					
					if(autoridadMunicipioService.autoridadMunicipioGuardar(autoridadMunicipio)) {
						return new ResponseEntity<String>("Actualización exitosa",HttpStatus.OK);
					}else {
						return new ResponseEntity<String>("El nuevo registro no pudo ser actualizado",HttpStatus.BAD_REQUEST);
					}
				} else {
					return new ResponseEntity<String>
					("El idAutoridad que ha insertado no corresponde a ningún registro",HttpStatus.OK);					
				}				
			} else {
				return new ResponseEntity<String>
				("No existe ningún registro asociado al idNodo y al municipio que acaba de insertar",HttpStatus.OK);				
			}			
		} catch (Exception e) {
			return new ResponseEntity<String>("El nuevo registro no pudo ser actualizado, revisar datos y reintentar",
					HttpStatus.BAD_REQUEST);
		}
	}	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/autoridadMunicipio/{idNodo}", method = RequestMethod.GET)
	public ResponseEntity<?> autoridadMunicipioPorIdNodo(@PathVariable ("idNodo") Long idNodo){
		List<AutoridadMunicipioDto> autoridadMunicipio = autoridadMunicipioService.autoridadMunicipioPorIdNodo(idNodo);
		if(autoridadMunicipio!=null){
			return new ResponseEntity<List<AutoridadMunicipioDto>>(autoridadMunicipio, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(String.format("No se encuentran registros asociados al idNodo: %s , revisar y reintentar",idNodo), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/autoridadMunicipio/byNodoAndMunicipio", method = RequestMethod.GET)
	public ResponseEntity<?> autoridadMunicipioPorIdNodoAndMunicipio(
			@RequestParam(value = "idNodoArbol") Long idNodoArbol,
			@RequestParam(value = "municipio") final Municipio municipio){
		AutoridadMunicipioDto autoridadMunicipio 
		   = autoridadMunicipioService.autoridadMunicipioPorIdNodoAndMunicipio(idNodoArbol,municipio.getMunicipio());
		if(autoridadMunicipio!=null){
			return new ResponseEntity<AutoridadMunicipioDto>(autoridadMunicipio, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(String.format("No se encuentran registros asociados con los parametros , revisar y reintentar"), HttpStatus.BAD_REQUEST);
		}
	}	
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/autoridadMunicipioDeleteByIdNodo/{idNodoArbol}", method = RequestMethod.DELETE)
	public ResponseEntity<String> autoridadMunicipioDeleteByIdNodo(@PathVariable ("idNodoArbol") Long idNodoArbol){
		try{
			autoridadMunicipioService.autoridadMunicipioEliminarByIdNodoArbol(idNodoArbol);
			return new ResponseEntity<String>(String.format("Registro eliminado correctamente", idNodoArbol) , HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No ha sido posible la eliminación del registro", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
}
