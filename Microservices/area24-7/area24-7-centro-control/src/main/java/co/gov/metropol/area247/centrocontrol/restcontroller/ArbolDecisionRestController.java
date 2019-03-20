package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.centrocontrol.model.ArbolDecision;
import co.gov.metropol.area247.centrocontrol.model.NodoArbol;
import co.gov.metropol.area247.centrocontrol.model.Objeto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlArbolDecisionService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlObjetoService;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Centro de control")
@RequestMapping(value = "/api")
public class ArbolDecisionRestController {
		
	@Autowired
	ICentroControlArbolDecisionService arbolDecisionService;
	
	@Autowired
	IContenedoraCapaService capaService;
	
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
    })
    @RequestMapping(value = "/arbol", method = RequestMethod.POST)
	public ResponseEntity<?> arbolGuardar(
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "activo") boolean activo,
			@RequestParam(value = "idCapa", required = false) Long idCapa,
			@RequestParam(value = "idCategoria", required = false) Long idCategoria) {
		try {					
			Capa capa = capaService.capaGetById(idCapa);
			ArbolDecision arbol = new ArbolDecision();
			if (capa == null) {
				return new ResponseEntity<String>
				("La capa que intenta a la que intenta asociar el arbol no existe no existe",HttpStatus.NOT_FOUND);
			} else {	
				arbol.setNombre(nombre);
				arbol.setDescripcion(descripcion);
				arbol.setActivo(activo);
				arbol.setIdCapa(idCapa);
				arbol.setIdCategoria(idCategoria);
			}			
			
			String mensaje = "";
			if(arbolDecisionService.arbolGuardar(arbol)) {
				mensaje = "El árbol ha sido creado exitosamente";
			}else {
				mensaje = "El árbol no pudo ser creado";
			}
			return new ResponseEntity<String>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido crear el arbol, revisar datos y reintentar",
					HttpStatus.BAD_REQUEST);
		}
	}	

	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
    })
    @RequestMapping(value = "/arbol", method = RequestMethod.PUT)	
	public ResponseEntity<?> arbolActualizar(
			@RequestParam(value = "idArbol", required = false) Long idArbol,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "activo") boolean activo,
			@RequestParam(value = "idCapa", required = false) Long idCapa,
			@RequestParam(value = "idCategoria", required = false) Long idCategoria) {
		try {					
			Capa capa = capaService.capaGetById(idCapa);
			ArbolDecision arbol = new ArbolDecision();
			if (capa == null) {
				return new ResponseEntity<String>
				("La capa que intenta a la que intenta asociar el arbol no existe no existe",HttpStatus.NOT_FOUND);
			} else {				
				arbol.setId(idArbol);
				arbol.setNombre(nombre);
				arbol.setDescripcion(descripcion);
				arbol.setActivo(activo);
				arbol.setIdCapa(idCapa);
				arbol.setIdCategoria(idCategoria);
			}	
			
			String mensaje = "";
			if(arbolDecisionService.arbolGuardar(arbol)) {
				mensaje = "El árbol ha sido actualizado exitosamente";
			}else {
				mensaje = "El árbol no pudo ser actualizado";
			}
			return new ResponseEntity<String>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido acrualizar el arbol, revisar datos y reintentar",
					HttpStatus.BAD_REQUEST);
		}
	}	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/arbol", method = RequestMethod.GET)
	public ResponseEntity<?> arbolObtenerTodos(){
		List<ArbolDecision> arboles = arbolDecisionService.arbolObtenerTodos();
		if(!arboles.isEmpty()){
			return new ResponseEntity<List<ArbolDecision>>(arboles, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentran los arboles", HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/arbol/{idArbol}", method = RequestMethod.GET)
	public ResponseEntity<?> objeto(@PathVariable ("idArbol") Long idArbol){
		ArbolDecision arbol = arbolDecisionService.arbolObtenerPorId(idArbol);
		if(arbol!=null){
			return new ResponseEntity<ArbolDecision>(arbol, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(String.format("No se encuentran arboles asociados al id %s , revisar y reintentar", idArbol), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/arbol/capa/{idCapa}", method = RequestMethod.GET)
	public ResponseEntity<?> arbolObtenerPorIdCapa(@PathVariable ("idCapa") Long idCapa){
			List<ArbolDecision> arboles = arbolDecisionService.arbolObtenerPorIdCapa(idCapa);
			if(!arboles.isEmpty()){
				return new ResponseEntity<List<ArbolDecision>>(arboles, HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentran arboles relacionados a la capa con id " + idCapa, HttpStatus.NO_CONTENT);
			}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/arbol/categoria/{idCategoria}", method = RequestMethod.GET)
	public ResponseEntity<?> arbolObtenerPorIdCategoria(@PathVariable ("idCategoria") Long idCategoria){
	    List<ArbolDecision> arboles = arbolDecisionService.arbolObtenerPorIdCategoria(idCategoria);
		if(!arboles.isEmpty()){
		    return new ResponseEntity<List<ArbolDecision>>(arboles, HttpStatus.OK);
		}else{
		    return new ResponseEntity<String>("No se encuentran arboles relacionados a la capa con id " + idCategoria, HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/arbol/{idArbol}", method = RequestMethod.DELETE)
	public ResponseEntity<String> objetoEliminar(@PathVariable ("idArbol") Long idArbol){
			try{
				arbolDecisionService.arbolEliminar(idArbol);
				return new ResponseEntity<String>(String.format("Arbol eliminado correctamente", idArbol) , HttpStatus.OK);
			}catch(Exception e){
				return new ResponseEntity<String>("No ha sido posible la eliminación del arbol", HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}	
	
}
