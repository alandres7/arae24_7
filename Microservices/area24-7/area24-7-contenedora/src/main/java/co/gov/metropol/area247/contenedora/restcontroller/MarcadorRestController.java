package co.gov.metropol.area247.contenedora.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.contenedora.util.NivelCapa;
import co.gov.metropol.area247.seguridad.dao.ISeguridadMunicipioRepositoryCustom;
import co.gov.metropol.area247.seguridad.model.Municipio;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;


@RestController
@RequestMapping(value = "/api")
public class MarcadorRestController {
	
	private static final int NIVEL_CAPA = 2;
	private static final int NIVEL_CATEGORIA = 3;
	
	@Autowired
	IContenedoraMarcadorService marcadorService;
	
	@Autowired
	IContenedoraCapaService capaService;
	
	@Autowired
	IContenedoraCategoriaService categoriaService;
	
	@Autowired
	@Qualifier("municipioDao")
	ISeguridadMunicipioRepositoryCustom municipioDao;
	
	
	

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ResponseBody
	@RequestMapping(value = "/marcador", method = RequestMethod.PUT)
	public ResponseEntity<?> marcadorActualizar(
			@RequestParam(value = "idMarcador") Long idMarcador, 
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "rutaImagen", required = false) String rutaImagen, 
			@RequestParam(value = "direccion", required = false) String direccion,
			@RequestParam(value = "nivelCapa", required = false) String nivelCapa,
			@RequestParam(value = "idCapaCategoria", required = false) Long idCapaCategoria) {
		try {
			Marcador marcador = marcadorService.obtenerMarcadorPorId(idMarcador);
			if (marcador != null) {
				if ( (nombre != null) && (!nombre.equals(""))) {
					marcador.setNombre(nombre);
				}
				if ( (descripcion != null) && (!descripcion.equals(""))) {
					marcador.setDescripcion(descripcion);
				}
				if ( (rutaImagen != null) && (!rutaImagen.equals(""))) {
					marcador.setRutaImagen(rutaImagen);
				}
				if ( (direccion != null) && (!direccion.equals(""))) {
					marcador.setDireccion(direccion);
				}
				
				if ((nivelCapa != null) && (!nivelCapa.equals(""))) {
					int nivel = NivelCapa.CAPA.toString().equals(nivelCapa) ? NivelCapa.CAPA.getNivel()
							: NivelCapa.CATEGORIA.getNivel();
					if (nivel == NIVEL_CAPA) {
						Capa capa = capaService.capaGetById(idCapaCategoria);
						if(capa != null) {
						    marcador.setCapa(capa);
						    marcador.setCategoria(null);
						}else {
							return new ResponseEntity<String>(
									"El id de Capa que introdujo no es valido", HttpStatus.BAD_REQUEST);
						}
					} else {
						if(nivel == NIVEL_CATEGORIA) {
						    Categoria categoria = categoriaService.categoriaObtenerPorId(idCapaCategoria);
						    if(categoria != null) {
						        marcador.setCapa(null);
						        marcador.setCategoria(categoria);
						    }else {
								return new ResponseEntity<String>(
										"El id de Categoria que introdujo no es valido", HttpStatus.BAD_REQUEST);
							}
						}else {
							return new ResponseEntity<String>(
									"El nivel capa que introdujo no es valido", HttpStatus.BAD_REQUEST);
						}
					}
				}
				
				if(marcadorService.marcadorCrear(marcador)) {
					return new ResponseEntity<String>(
							"marcador actualizado exitosamente con id: " + marcador.getId(), HttpStatus.OK);					
				}else {
					return new ResponseEntity<String>(
							"No se pudo actualizar el marcador", HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<String>("El marcador que intenta actualizar no existe",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/marcador/{idMarcador}", method = RequestMethod.GET)
	public ResponseEntity<?> marcadorObtenerPorId(@PathVariable(value = "idMarcador") Long idMarcador) {
		try {
			Marcador marcador = marcadorService.obtenerMarcadorPorId(idMarcador);
			return new ResponseEntity<Marcador>(marcador, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Hubo un error recuperando el marcador; " + e,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/marcador/actualizar-municipio/{idCapa}", method = RequestMethod.GET)
	public ResponseEntity<?> marcadorActualizarMunicipioPorCapa(@PathVariable(value = "idCapa") Long idCapa) {
		try {
			List<Marcador> lisMarcador = marcadorService.obtenerPorCapaSinMunicipio(idCapa);
			
			for (Marcador marcador : lisMarcador) {
				Double latitud = marcador.getPoint().getCoordinate().y;
				Double longitud = marcador.getPoint().getCoordinate().x;				
				if((latitud!=null) && (longitud!=null)) {				
				    List<Municipio> listMunicipio = municipioDao.coordenadaInterceptoMunicipio(latitud,longitud);
				    marcador.setNombreMunicipio(listMunicipio.get(0).getNombre());
				    
				    marcadorService.marcadorActualizar(marcador);
				    
				   	System.out.println("Marcador con id: " + marcador.getId() + 
				    		" en las coordenadas: ("+latitud+","+longitud+")" +
				    		" se le pone el municipio: "+listMunicipio.get(0).getNombre());				    				    				    			    
				}								
			}
			return new ResponseEntity<String>("Se les ha actualizado el municipio a los marcadores con id capa: " + idCapa, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se le pudo actualizar el municipio a los marcadores; " + e,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/marcador/eliminar-por-capa/{idCapa}", method = RequestMethod.DELETE)
	public ResponseEntity<?> marcadorEliminarPorIdCapa(@PathVariable(value = "idCapa") Long idCapa) {
		try {
			if(marcadorService.eliminarMarcadoresPorIdCapa(idCapa)) {
				return new ResponseEntity<String>("Se han eliminado los marcadores de la capa: " + idCapa, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("No se le pudo eliminar los marcadores; ", HttpStatus.NOT_FOUND);
			}			
		} catch (Exception e) {
			return new ResponseEntity<String>("No se le pudo eliminar los marcadores; ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/marcador/eliminar-por-categoria/{idCategoria}", method = RequestMethod.DELETE)
	public ResponseEntity<?> marcadorEliminarPorIdCategoria(@PathVariable(value = "idCategoria") Long idCategoria) {
		try {
			if(marcadorService.eliminarMarcadoresPorIdCategoria(idCategoria)) {
				return new ResponseEntity<String>("Se han eliminado los marcadores de la categoria: " + idCategoria, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("No se le pudo eliminar los marcadores; ", HttpStatus.NOT_FOUND);
			}			
		} catch (Exception e) {
			return new ResponseEntity<String>("No se le pudo eliminar los marcadores; ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	

}
