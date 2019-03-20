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

import co.gov.metropol.area247.centrocontrol.model.Objeto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlObjetoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Centro de control")
@RequestMapping(value = "/api")
public class ObjetoRestController {
	
	@Autowired
	ICentroControlObjetoService objetoService;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/objeto", method = RequestMethod.POST)
	public ResponseEntity<?> objetoGuardar(@RequestBody Objeto objeto){
		try{
			Objeto objetoAux = objetoService.objetoGuardar(objeto);
			return new ResponseEntity<Objeto>(objetoAux, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo crear el objeto, revisar datos y reintentar", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/objeto", method = RequestMethod.PUT)
	public ResponseEntity<?> objetoActualizar(@RequestBody Objeto objeto){
		try{
			Objeto objetoAux = objetoService.objetoGuardar(objeto);
			return new ResponseEntity<Objeto>(objetoAux, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo actualizar el objeto, revisar datos y reintentar", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/objeto", method = RequestMethod.GET)
	public ResponseEntity<?> objetoObtenerTodos(){
		List<Objeto> objetos = objetoService.objetoObtenerTodos();
		if(!objetos.isEmpty()){
			return new ResponseEntity<List<Objeto>>(objetos, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentran objetos", HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/objeto/{idObjeto}", method = RequestMethod.GET)
	public ResponseEntity<?> objeto(@PathVariable ("idObjeto") Long idObjeto){
		Objeto objeto = objetoService.objetoObtenerPorId(idObjeto);
		if(objeto!=null){
			return new ResponseEntity<Objeto>(objeto, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(String.format("No se encuentran objetos asociados al id %s , revisar y reintentar", idObjeto), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/objeto/tipo/{idTipoObjeto}", method = RequestMethod.GET)
	public ResponseEntity<?> objetoObtenerPorTipoObjeto(@PathVariable ("idTipoObjeto") Long idTipoObjeto){
			List<Objeto> objetos = objetoService.objetoObtenerPorIdTipoObjeto(idTipoObjeto);
			if(!objetos.isEmpty()){
				return new ResponseEntity<List<Objeto>>(objetos, HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentran objetos relacionado al tipo de objeto con id " + idTipoObjeto, HttpStatus.NO_CONTENT);
			}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/objeto/aplicacion/{idAplicacion}", method = RequestMethod.GET)
	public ResponseEntity<?> objetoObtenerPorIdAplicacion(@PathVariable ("idAplicacion") Long idAplicacion){
			List<Objeto> objetos = objetoService.objetoObtenerPorIdAplicacion(idAplicacion);
			if(!objetos.isEmpty()){
				return new ResponseEntity<List<Objeto>>(objetos, HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentran objetos relacionados a la aplicación con id " + idAplicacion, HttpStatus.NO_CONTENT);
			}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/objeto/{idObjeto}", method = RequestMethod.DELETE)
	public ResponseEntity<String> objetoEliminar(@PathVariable ("idObjeto") Long idObjeto){
			try{
				objetoService.objetoEliminar(idObjeto);
				return new ResponseEntity<String>(String.format("Objeto con id %s eliminado correctamente", idObjeto) , HttpStatus.OK);
			}catch(Exception e){
				return new ResponseEntity<String>("No ha sido posible la eliminación del objeto con id " + idObjeto, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}	
	
}
