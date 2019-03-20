package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.centrocontrol.model.NodoArbol;
import co.gov.metropol.area247.centrocontrol.model.dto.NodoArbolDto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAutoridadCompetenteService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlNodoArbolService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlObjetoService;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.Multimedia;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconosVigiaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMultimediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Centro de control")
@RequestMapping(value = "/api")
public class NodoArbolRestController {

	@Autowired
	ICentroControlNodoArbolService nodoArbolService;

	@Autowired
	ICentroControlObjetoService objetoService;

	@Autowired
	IContenedoraMultimediaService multimediaService;

	@Autowired
	ICentroControlAutoridadCompetenteService autoridadCompetenteService;
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Autowired
	IContenedoraIconosVigiaService iconosVigiaService;
	

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/nodo-arbol", method = RequestMethod.POST)
	public ResponseEntity<?> nodoArbolGuardar(
			@RequestParam(value = "idArbol", required = true) Long idArbol,
			@RequestParam(value = "idPadre", required = false) Long idPadre,
			@RequestParam(value = "multimedia", required = false) MultipartFile multimedia,			
			@RequestParam(value = "iconoVigiaPen", required = false) MultipartFile iconoVigiaPen,
			@RequestParam(value = "iconoVigiaApr", required = false) MultipartFile iconoVigiaApr,
			@RequestParam(value = "iconoVigiaRec", required = false) MultipartFile iconoVigiaRec,
			@RequestParam(value = "iconoVigiaWin", required = false) MultipartFile iconoVigiaWin,									
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "orden", required = false) int orden,	
			@RequestParam(value = "alias", required = false) String alias,
			@RequestParam(value = "instrucciones", required = false) String instrucciones,
			@RequestParam(value = "instruccionesDetalladas", required = false) String instruccionesDetalladas,
			@RequestParam(value = "flagHijosDropdown") boolean flagHijosDropdown,
			@RequestParam(value = "urlMediaVideoAudio", required = false) String urlMediaVideoAudio,
			@RequestParam(value = "formatoMedia", required = false) String formatoMedia,
			@RequestParam(value = "idAutoridadCompetente", required = false) Long idAutoridadCompetente) {		
		try {
			NodoArbol nodoArbol = new NodoArbol();
			nodoArbol.setIdArbol(idArbol);
			nodoArbol.setIdPadre(idPadre);
			nodoArbol.setNombre(nombre);
			nodoArbol.setAlias(alias);
			nodoArbol.setInstrucciones(instrucciones);
			nodoArbol.setInstruccionesDetalladas(instruccionesDetalladas);
			nodoArbol.setOrden(orden);
			nodoArbol.setDescripcion(descripcion);			
			nodoArbol.setFlagHijosDropdown(flagHijosDropdown);					
			nodoArbol.setFormatoMedia(formatoMedia);
			nodoArbol.setIdAutoridadCompetente(idAutoridadCompetente);
		    
			if(urlMediaVideoAudio!=null) {
				nodoArbol.setUrlMediaVideoAudio(urlMediaVideoAudio);
			}
			if (multimedia != null && !multimedia.isEmpty()) {
				Multimedia multimediaAux = new Multimedia();				
				multimediaAux = multimediaService.multimediaCrear(null,multimedia);
				nodoArbol.setIdMultimedia(multimediaAux.getId());   
			}	
													
			if (nodoArbolService.nodoArbolGuardar(nodoArbol)) {
				
				if ((iconoVigiaPen != null && !iconoVigiaPen.isEmpty()) ||
					(iconoVigiaApr != null && !iconoVigiaApr.isEmpty()) ||
					(iconoVigiaRec != null && !iconoVigiaRec.isEmpty()) ||
					(iconoVigiaWin != null && !iconoVigiaWin.isEmpty())) {
					
					iconosVigiaService.iconosVigiasGuardar(nodoArbol.getId(),iconoVigiaPen,iconoVigiaApr,
							                               iconoVigiaRec,iconoVigiaWin);
				}
				
				return new ResponseEntity<String>("Nodo creado exitosamente con id: "+nodoArbol.getId(), HttpStatus.OK);				
			} else {
				return new ResponseEntity<String>("No se pudo realizar la creación del nodo, reintentar",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido crear el nodo, revisar datos y reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })	
	@RequestMapping(value = "/nodo-arbol", method = RequestMethod.PUT)
	public ResponseEntity<?> nodoArbolActualizar(
			@RequestParam(value = "multimedia", required = false) MultipartFile multimedia,
			@RequestParam(value = "iconoVigiaPen", required = false) MultipartFile iconoVigiaPen,
			@RequestParam(value = "iconoVigiaApr", required = false) MultipartFile iconoVigiaApr,
			@RequestParam(value = "iconoVigiaRec", required = false) MultipartFile iconoVigiaRec,
			@RequestParam(value = "iconoVigiaWin", required = false) MultipartFile iconoVigiaWin,
			@RequestParam(value = "idNodo", required = true) Long idNodo,			
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "orden", required = false) int orden,	
			@RequestParam(value = "alias", required = false) String alias,
			@RequestParam(value = "instrucciones", required = false) String instrucciones,
			@RequestParam(value = "instruccionesDetalladas", required = false) String instruccionesDetalladas,
			@RequestParam(value = "flagHijosDropdown", required = true) boolean flagHijosDropdown,
			@RequestParam(value = "urlMediaVideoAudio", required = false) String urlMediaVideoAudio,
			@RequestParam(value = "formatoMedia", required = false) String formatoMedia,
			@RequestParam(value = "idAutoridadCompetente", required = false) Long idAutoridadCompetente) {	
		
		try {		
			NodoArbol nodoArbol = nodoArbolService.nodoArbolObtenerPorId(idNodo);			
			if(nodoArbol != null) {								
				nodoArbol.setId(idNodo);
				nodoArbol.setNombre(nombre);
				nodoArbol.setAlias(alias);
				nodoArbol.setInstrucciones(instrucciones);
				nodoArbol.setInstruccionesDetalladas(instruccionesDetalladas);
				nodoArbol.setOrden(orden);
				nodoArbol.setDescripcion(descripcion);			
				nodoArbol.setFlagHijosDropdown(flagHijosDropdown);					
				nodoArbol.setFormatoMedia(formatoMedia);
				nodoArbol.setIdAutoridadCompetente(idAutoridadCompetente);
			    
				if(urlMediaVideoAudio!=null) {
					nodoArbol.setUrlMediaVideoAudio(urlMediaVideoAudio);
				}
				if (multimedia != null && !multimedia.isEmpty()) {
					Multimedia multimediaAux = new Multimedia();
					if(nodoArbol.getIdMultimedia()==null) {
						multimediaAux = multimediaService.multimediaCrear(null,multimedia);
						nodoArbol.setIdMultimedia(multimediaAux.getId());
					}else {
						multimediaAux = multimediaService.multimediaCrear(nodoArbol.getIdMultimedia(),multimedia);
						nodoArbol.setIdMultimedia(multimediaAux.getId());
					}   
				}	
								
				if ((iconoVigiaPen != null && !iconoVigiaPen.isEmpty()) ||
					(iconoVigiaApr != null && !iconoVigiaApr.isEmpty()) ||
					(iconoVigiaRec != null && !iconoVigiaRec.isEmpty()) ||
					(iconoVigiaWin != null && !iconoVigiaWin.isEmpty())) {
					
					iconosVigiaService.iconosVigiasGuardar(nodoArbol.getId(),iconoVigiaPen,iconoVigiaApr,
								                           iconoVigiaRec,iconoVigiaWin);
				}
				
				if (nodoArbolService.nodoArbolGuardar(nodoArbol)) {
					return new ResponseEntity<String>("Nodo actualizado exitosamente con id: "+nodoArbol.getId(), 
							HttpStatus.OK);			
				} else {
					return new ResponseEntity<String>("No se pudo realizar la actualización del nodo, reintentar",
							HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<String>("El idNodo no corresponde a ningún nodo existente",
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido actualizar el nodo, revisar datos y reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/nodo-arbol/padre/{idPadre}", method = RequestMethod.GET)
	public ResponseEntity<?> nodoArbolObtenerPorIdPadre(@PathVariable("idPadre") Long idPadre) {
		List<NodoArbolDto> nodosHijo = nodoArbolService.nodoArbolDtoObtenerPorIdPadre(idPadre);
		if (!nodosHijo.isEmpty()) {
			return new ResponseEntity<List<NodoArbolDto>>(nodosHijo, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se encuentran nodos asociados al nodo padre con id: " + idPadre,
					HttpStatus.NOT_FOUND);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/nodo-arbol/raiz/arbol/{idArbol}", method = RequestMethod.GET)
	public ResponseEntity<?> nodoArbolObtenerPorArbol(@PathVariable("idArbol") Long idArbol) {
		List<NodoArbolDto> nodos = nodoArbolService.nodoRaizArbolDtoObtenerPorIdArbol(idArbol);
		if (!nodos.isEmpty()) {
			return new ResponseEntity<List<NodoArbolDto>>(nodos, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se encuentran nodos asociados al arbol con id " + idArbol,
					HttpStatus.NOT_FOUND);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/nodo-arbol/{idNodoArbol}", method = RequestMethod.GET)
	public ResponseEntity<?> nodoArbolObtenerPorId(@PathVariable("idNodoArbol") Long idNodoArbol) {
		NodoArbolDto nodo = nodoArbolService.nodoArbolDtoObtenerPorId(idNodoArbol);
		if (nodo != null) {
			return new ResponseEntity<NodoArbolDto>(nodo, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se encuentra nodo con id " + idNodoArbol, HttpStatus.NOT_FOUND);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/nodo-arbol/{idNodoArbol}", method = RequestMethod.DELETE)
	public ResponseEntity<?> nodoArbolEliminar(@PathVariable("idNodoArbol") Long idNodoArbol) {
		NodoArbol nodo = nodoArbolService.nodoArbolObtenerPorId(idNodoArbol);
		if (nodo != null) {
			try {
				nodoArbolService.nodoArbolEliminar(idNodoArbol);
				return new ResponseEntity<String>("Eliminación de nodo exitosa", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<String>("No se encuentra nodo con id " + idNodoArbol, HttpStatus.NOT_FOUND);
		}
	}
	

	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
	})
    @RequestMapping(value = "/nodo-arbol/cantidadhijos/{idPadre}", method = RequestMethod.GET)
    public ResponseEntity<Integer> cantidadNodosHijos(@PathVariable("idPadre") Long idPadre) {
		try {
			return new ResponseEntity<Integer>(nodoArbolService.cantidadNodosHijos(idPadre), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(0,HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
	})
	@RequestMapping(value = "/nodo-arbol/cambiarPadre", method = RequestMethod.PUT)
	public ResponseEntity<?> nodoArbolGuardar(@RequestParam(value = "idNodo", required = true) Long idNodo,
			                                  @RequestParam(value = "nuevoPadre", required = true) Long idPadre) {		
		try {			
			NodoArbol nodoArbol = nodoArbolService.nodoArbolObtenerPorId(idNodo);			
			if(nodoArbol!=null) {
				NodoArbol nodoPadre = nodoArbolService.nodoArbolObtenerPorId(idPadre);				
				if(nodoPadre!=null) {
					nodoArbol.setIdPadre(idPadre);					
					if (nodoArbolService.nodoArbolGuardar(nodoArbol)) {
						return new ResponseEntity<String>("Se logró cambiar el padre del nodo con id: "+nodoArbol.getId(), 
								HttpStatus.OK);			
				    } else {
					    return new ResponseEntity<String>("No se pudo cambiar el padre del nodo, reintentar",
							HttpStatus.BAD_REQUEST);
				    }					
				}else {
					return new ResponseEntity<String>("El idNodo Padre que ingreso no corresponde a ningún registro existente",
							HttpStatus.BAD_REQUEST);
				}					
			}else {
				return new ResponseEntity<String>("El idNodo que ingreso no corresponde a ningún registro existente",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se pudo cambiar el padre del nodo",
					HttpStatus.BAD_REQUEST);
		}
	}	
	
	
	

}
