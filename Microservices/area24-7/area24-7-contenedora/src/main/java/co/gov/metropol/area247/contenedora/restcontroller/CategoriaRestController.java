package co.gov.metropol.area247.contenedora.restcontroller;

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

import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.dto.CategoriaDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoEstadoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraTipoCapaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Contenedora")
@RequestMapping(value = "/api")
public class CategoriaRestController {

	@Autowired
	IContenedoraCategoriaService categoriaService;

	@Autowired
	IContenedoraIconoService iconoService;

	@Autowired
	IContenedoraTipoCapaService tipoCapaService;

	@Autowired
	IContenedoraIconoEstadoService iconoEstadoService;

	@ResponseBody
	@ApiOperation(value = "/categoria", notes = "Permite la creación de las diferentes categorias que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Creación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/categoria", method = RequestMethod.POST)
	public ResponseEntity<?> categoriaCrear(
			@RequestParam(value = "icono", required = false) MultipartFile icono,
			@RequestParam(value = "idCapa") Long idCapa,			
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "urlRecurso", required = false) String urlRecurso,
			@RequestParam(value = "aliasNombre", required = false) String aliasNombre,
			@RequestParam(value = "aliasMunicipio", required = false) String aliasMunicipio,
			@RequestParam(value = "aliasDescripcion", required = false) String aliasDescripcion,
			@RequestParam(value = "aliasTipo", required = false) String aliasTipo,
			@RequestParam(value = "aliasImagen", required = false) String aliasImagen,
			@RequestParam(value = "aliasDireccion", required = false) String aliasDireccion,
			@RequestParam(value = "iconoMarcador", required = false) MultipartFile iconoMarcador,
			@RequestParam(value = "tipoCategoria", required = false) Long tipoCategoria,
			@RequestParam(value = "fichaCaracterizacion") boolean fichaCaracterizacion,
			@RequestParam(value = "respuestaFichaCarac", required = false) String respuestaFichaCarac,
			@RequestParam(value = "poligono") boolean poligono,
			@RequestParam(value = "msgOrdenamiento") boolean msgOrdenamiento,
			@RequestParam(value = "iconoPen", required = false) MultipartFile iconoPen,
			@RequestParam(value = "iconoApr", required = false) MultipartFile iconoApr,
			@RequestParam(value = "iconoRec", required = false) MultipartFile iconoRec,
			@RequestParam(value = "tieneHistoria") boolean tieneHistoria) {
		try {
			if (nombre != null) {
				if (categoriaService.categoriaObtenerPorNombre(nombre) != null) {
					return new ResponseEntity<String>(
							"Conflicto. Ya existe una categoría con el mismo nombre, revisar y reintentar",
							HttpStatus.CONFLICT);
				} else {
					try {
						Categoria categoria = new Categoria();
						if (icono != null && !icono.isEmpty()) {
							Long idIcono = iconoService.iconoCrear(icono, null);
							if (idIcono != null) {
								categoria.setIcono(iconoService.iconoObtenerPorId(idIcono));
							}
						}
						if (iconoMarcador != null && !iconoMarcador.isEmpty()) {
							Long idIconoMarcador = iconoService.iconoCrear(iconoMarcador, null);
							if (idIconoMarcador != null) {
								categoria.setIconoMarcador(iconoService.iconoObtenerPorId(idIconoMarcador));
							}
						}
						categoria.setNombre(nombre);
						categoria.setDescripcion(descripcion);
						categoria.setUrlRecurso(urlRecurso);
						categoria.setAliasNombre(aliasNombre);
						categoria.setAliasMunicipio(aliasMunicipio);
						categoria.setAliasDescripcion(aliasDescripcion);
						categoria.setTieneHistoria(Boolean.TRUE);
						categoria.setAliasTipo(aliasTipo);
						categoria.setAliasImagen(aliasImagen);
						categoria.setAliasDireccion(aliasDireccion);
						if (tipoCategoria != null) {
							categoria.setTipoCategoria(tipoCapaService.tipoCapaObtenerPorId(tipoCategoria));
						}
						categoria.setFichaCaracterizacion(fichaCaracterizacion);
						categoria.setRespuestaFichaCarac(respuestaFichaCarac);
						categoria.setPoligono(poligono);
						categoria.setMsgOrdenamiento(msgOrdenamiento);
						categoriaService.categoriaCrear(categoria, idCapa);

						if (iconoPen != null && !iconoPen.isEmpty()) {
							iconoEstadoService.iconoEstadoRegistrar("CATEGORIA", categoria.getId(), 2, iconoPen);
						}
						if (iconoApr != null && !iconoApr.isEmpty()) {
							iconoEstadoService.iconoEstadoRegistrar("CATEGORIA", categoria.getId(), 1, iconoApr);
						}
						if (iconoRec != null && !iconoRec.isEmpty()) {
							iconoEstadoService.iconoEstadoRegistrar("CATEGORIA", categoria.getId(), 0, iconoRec);
						}

						return new ResponseEntity<String>("Categoría creada exitosamente con id: " + categoria.getId(), HttpStatus.OK);
					} catch (Exception e) {
						return new ResponseEntity<String>(
								"No ha podido crearse la categoría, revisar los datos y reintentar",
								HttpStatus.BAD_REQUEST);
					}
				}
			} else {
				return new ResponseEntity<String>("El nombre de la categoría no puede estar en blanco",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/categoria", notes = "Permite la actualización de las diferentes categorias que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualziación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/categoria", method = RequestMethod.PUT)
	public ResponseEntity<?> categoriaActualizar(
			@RequestParam(value = "icono", required = false) MultipartFile icono,
			@RequestParam(value = "id") Long id, 
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "urlRecurso", required = false) String urlRecurso,
			@RequestParam(value = "aliasNombre", required = false) String aliasNombre,
			@RequestParam(value = "aliasMunicipio", required = false) String aliasMunicipio,
			@RequestParam(value = "aliasDescripcion", required = false) String aliasDescripcion,
			@RequestParam(value = "aliasTipo", required = false) String aliasTipo,
			@RequestParam(value = "aliasImagen", required = false) String aliasImagen,
			@RequestParam(value = "aliasDireccion", required = false) String aliasDireccion,
			@RequestParam(value = "iconoMarcador", required = false) MultipartFile iconoMarcador,
			@RequestParam(value = "tipoCategoria", required = false) Long tipoCategoria,
			@RequestParam(value = "fichaCaracterizacion") boolean fichaCaracterizacion,
			@RequestParam(value = "respuestaFichaCarac", required = false) String respuestaFichaCarac,
			@RequestParam(value = "poligono") boolean poligono,
			@RequestParam(value = "msgOrdenamiento") boolean msgOrdenamiento,
			@RequestParam(value = "iconoPen", required = false) MultipartFile iconoPen,
			@RequestParam(value = "iconoApr", required = false) MultipartFile iconoApr,
			@RequestParam(value = "iconoRec", required = false) MultipartFile iconoRec,
			@RequestParam(value = "tieneHistoria") boolean tieneHistoria) {
		try {
			if (id != null || nombre != null) {
				Categoria categoria = categoriaService.categoriaObtenerPorId(id);
				if (categoria == null) {
					return new ResponseEntity<String>("No existe una categoría asociada al id, revisar y reintentar",
							HttpStatus.NOT_FOUND);
				} else {
					try {
						if (icono != null && !icono.isEmpty()) {
							Long idIconoActual = null;
							if (categoria.getIcono() != null) {
								idIconoActual = categoria.getIcono().getId();
							}
							Long idIcono = iconoService.iconoCrear(icono, idIconoActual);
							if (idIcono != null) {
								categoria.setIcono(iconoService.iconoObtenerPorId(idIcono));
							}
						}						
						if (iconoMarcador != null && !iconoMarcador.isEmpty()) {
							Long idIconoMarcadorActual = null;
							if (categoria.getIconoMarcador() != null) {
								idIconoMarcadorActual = categoria.getIconoMarcador().getId();
							}
							Long idIconoMarcador = iconoService.iconoCrear(iconoMarcador, idIconoMarcadorActual);
							if (idIconoMarcador != null) {
								categoria.setIconoMarcador(iconoService.iconoObtenerPorId(idIconoMarcador));
							}
						}
						categoria.setNombre(nombre);
						categoria.setDescripcion(descripcion);
						categoria.setUrlRecurso(urlRecurso);
						categoria.setAliasNombre(aliasNombre);
						categoria.setAliasMunicipio(aliasMunicipio);
						categoria.setAliasDescripcion(aliasDescripcion);
						categoria.setAliasTipo(aliasTipo);
						categoria.setAliasImagen(aliasImagen);
						categoria.setAliasDireccion(aliasDireccion);
						categoria.setTieneHistoria(tieneHistoria);
						if (tipoCategoria != null) {
							categoria.setTipoCategoria(tipoCapaService.tipoCapaObtenerPorId(tipoCategoria));
						}
						categoria.setFichaCaracterizacion(fichaCaracterizacion);
						categoria.setRespuestaFichaCarac(respuestaFichaCarac);
						categoria.setPoligono(poligono);
						categoria.setMsgOrdenamiento(msgOrdenamiento);

						if (iconoPen != null && !iconoPen.isEmpty()) {
							iconoEstadoService.iconoEstadoRegistrar("CATEGORIA", id, 2, iconoPen);
						}
						if (iconoApr != null && !iconoApr.isEmpty()) {
							iconoEstadoService.iconoEstadoRegistrar("CATEGORIA", id, 1, iconoApr);
						}
						if (iconoRec != null && !iconoRec.isEmpty()) {
							iconoEstadoService.iconoEstadoRegistrar("CATEGORIA", id, 0, iconoRec);
						}

						categoriaService.categoriaActualizar(categoria);
						return new ResponseEntity<String>("Categoría actualizada exitosamente con id: " + id, HttpStatus.OK);
					} catch (Exception e) {
						return new ResponseEntity<String>(
								"No ha podido crearse la categoría, revisar los datos y reintentar",
								HttpStatus.BAD_REQUEST);
					}
				}
			} else {
				return new ResponseEntity<String>("El nombre ni el id de la categoría pueden estar en blanco",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ResponseBody
	@ApiOperation(value = "/categoria/{idCategoria}", notes = "Permite la eliminación de las diferentes categorias que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualziación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/categoria/{idCategoria}", method = RequestMethod.DELETE)
	public ResponseEntity<?> categoriaEliminar(@PathVariable("idCategoria") Long idCategoria) {
		try {
			if (categoriaService.categoriaObtenerPorId(idCategoria) != null) {
				categoriaService.categoriaEliminar(idCategoria);
				return new ResponseEntity<String>("La categoría ha sido eliminada exitosamente", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("La categoría que intenta borrar no existe", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/categoria/{idCategoria}", method = RequestMethod.GET)
	public ResponseEntity<?> categoriaDtoObtenerPorId(@PathVariable("idCategoria") Long idCategoria) {
		try {
			CategoriaDto categoria = categoriaService.categoriaDtoObtenerPorId(idCategoria);
			if (categoria != null) {
				return new ResponseEntity<CategoriaDto>(categoria, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>("No se encuentra la categoria", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se pudo retornar las categoria", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/categoria/capa/{idCapa}", method = RequestMethod.GET)
	public ResponseEntity<?> categoriaDtoObtenerTodos(@PathVariable(name = "idCapa") Long idCapa) {
		try {
			List<CategoriaDto> categorias = categoriaService.categoriaDtoObtenerPorIdCapa(idCapa);
			if (!categorias.isEmpty()) {
				return new ResponseEntity<List<CategoriaDto>>(categorias, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>("No se encuentran categorias asociadas a la capa",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se pudo buscar las categorias", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/categoria/nombre", method = RequestMethod.GET)
	public ResponseEntity<?> capaDtoObtenerNombres() {
		try {
			List<String> categorias = categoriaService.categoriaDtoObtenerNombres();
			if (!categorias.isEmpty()) {
				return new ResponseEntity<List<String>>(categorias, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>("No se encuentran los nombres solicitados", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Hubo un error al buscar los nombres", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/categoria/urls", method = RequestMethod.GET)
	public ResponseEntity<?> capaDtoObtenerUrls() {
		try {
			List<String> urls = categoriaService.categoriaDtoObtenerUrls();
			if (!urls.isEmpty()) {
				return new ResponseEntity<List<String>>(urls, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>("No se encuentran las urls solicitadas", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Hubo un error al buscar las urls", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

}
