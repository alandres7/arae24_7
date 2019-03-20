package co.gov.metropol.area247.contenedora.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.dto.CapaDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoEstadoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraTipoCapaService;
import co.gov.metropol.area247.core.restcontroller.BaseRestController;
import co.gov.metropol.area247.jdbc.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Contenedora")
public class CapaRestController extends BaseRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CapaRestController.class);

	@Autowired
	IContenedoraCapaService capaService;

	@Autowired
	IContenedoraIconoService iconoService;

	@Autowired
	IContenedoraTipoCapaService tipoCapaService;

	@Autowired
	IContenedoraIconoEstadoService iconoEstadoService;

	@ResponseBody
	@ApiOperation(value = "/capa", notes = "Permite la creación de una capa para una aplicación del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 302, message = "Creación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/capa", method = RequestMethod.POST)
	public ResponseEntity<String> capaCrear(
			@RequestParam(value = "icono") MultipartFile icono, 
			@RequestParam(value = "idAplicacion") Long idAplicacion,
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "activo") boolean activo, 
			@RequestParam(value = "favorito") boolean favorito,
			@RequestParam(value = "urlRecurso", required = false) String urlRecurso,
			@RequestParam(value = "aliasNombre", required = false) String aliasNombre,
			@RequestParam(value = "aliasMunicipio", required = false) String aliasMunicipio,
			@RequestParam(value = "aliasDescripcion", required = false) String aliasDescripcion,
			@RequestParam(value = "aliasCategoria", required = false) String aliasCategoria,
			@RequestParam(value = "aliasImagen", required = false) String aliasImagen,
			@RequestParam(value = "aliasDireccion", required = false) String aliasDireccion,
			@RequestParam(value = "tipoCapa", required = false) Long tipoCapa,
			@RequestParam(value = "iconoMarcador", required = false) MultipartFile iconoMarcador,
			@RequestParam(value = "fichaCaracterizacion") boolean fichaCaracterizacion,
			@RequestParam(value = "poligono") boolean poligono,
			@RequestParam(value = "iconoPen", required = false) MultipartFile iconoPen,
			@RequestParam(value = "iconoApr", required = false) MultipartFile iconoApr,
			@RequestParam(value = "iconoRec", required = false) MultipartFile iconoRec,
			@RequestParam(value = "contieneHistoria") boolean contieneHistoria) {
		if (capaService.capaGetByNombre(nombre) != null) {
			return new ResponseEntity<String>("La capa que intenta crear ya existe", HttpStatus.CONFLICT);
		} else {
			Capa capa = new Capa();
			capa.setNombre(nombre);
			capa.setDescripcion(descripcion);
			capa.setActivo(activo);
			capa.setFavorito(favorito);
			capa.setUrlRecurso(urlRecurso);
			capa.setAliasNombre(aliasNombre);
			capa.setAliasMunicipio(aliasMunicipio);
			capa.setAliasDescripcion(aliasDescripcion);
			capa.setAliasCategoria(aliasCategoria);
			capa.setAliasImagen(aliasImagen);
			capa.setAliasDireccion(aliasDireccion);
			capa.setFichaCaracterizacion(fichaCaracterizacion);
			capa.setContieneHistoria(contieneHistoria);
			capa.setPoligono(poligono);
			if (tipoCapa != null) {
				capa.setTipoCapa(tipoCapaService.tipoCapaObtenerPorId(tipoCapa));
			}
			if (icono != null && !icono.isEmpty()) {
				Long idIcono = iconoService.iconoCrear(icono, null);
				if (idIcono != null) {
					capa.setIcono(iconoService.iconoObtenerPorId(idIcono));
				}
			}
			if (iconoMarcador != null && !iconoMarcador.isEmpty()) {
				Long idIconoMarcador = iconoService.iconoCrear(iconoMarcador, null);
				if (idIconoMarcador != null) {
					capa.setIconoMarcador(iconoService.iconoObtenerPorId(idIconoMarcador));
				}
			}
			if (capaService.capaCrear(capa, idAplicacion)) {
				if (iconoPen != null && !iconoPen.isEmpty()) {
					iconoEstadoService.iconoEstadoRegistrar("CAPA", capa.getId(), 2, iconoPen);
				}
				if (iconoApr != null && !iconoApr.isEmpty()) {
					iconoEstadoService.iconoEstadoRegistrar("CAPA", capa.getId(), 1, iconoApr);
				}
				if (iconoRec != null && !iconoRec.isEmpty()) {
					iconoEstadoService.iconoEstadoRegistrar("CAPA", capa.getId(), 0, iconoRec);
				}
				return new ResponseEntity<String>("Capa creada exitosamente con id: "+capa.getId(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<String>("No se pudo realizar la creación de la capa, reintentar",
						HttpStatus.BAD_REQUEST);
			}
		}
	}

	@ResponseBody
	@ApiOperation(value = "/capa", notes = "Permite la actualización de una capa del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualización exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/capa", method = RequestMethod.PUT)
	public ResponseEntity<String> capaActualizar(
			@RequestParam(value = "icono", required = false) MultipartFile icono,
			@RequestParam(value = "id") Long id, 
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,			
			@RequestParam(value = "activo") boolean activo,
			@RequestParam(value = "favorito") boolean favorito,
			@RequestParam(value = "urlRecurso", required = false) String urlRecurso,
			@RequestParam(value = "aliasNombre", required = false) String aliasNombre,
			@RequestParam(value = "aliasMunicipio", required = false) String aliasMunicipio,
			@RequestParam(value = "aliasDescripcion", required = false) String aliasDescripcion,
			@RequestParam(value = "aliasCategoria", required = false) String aliasCategoria,
			@RequestParam(value = "aliasImagen", required = false) String aliasImagen,
			@RequestParam(value = "aliasDireccion", required = false) String aliasDireccion,
			@RequestParam(value = "tipoCapa", required = false) Long tipoCapa,
			@RequestParam(value = "iconoMarcador", required = false) MultipartFile iconoMarcador,
			@RequestParam(value = "fichaCaracterizacion") boolean fichaCaracterizacion,
			@RequestParam(value = "poligono") boolean poligono,
			@RequestParam(value = "iconoPen", required = false) MultipartFile iconoPen,
			@RequestParam(value = "iconoApr", required = false) MultipartFile iconoApr,
			@RequestParam(value = "iconoRec", required = false) MultipartFile iconoRec,
			@RequestParam(value = "contieneHistoria") boolean contieneHistoria) {
		Capa capa = capaService.capaGetById(id);
		if (capa == null) {
			return new ResponseEntity<String>("La capa que intenta actualizar no existe", HttpStatus.NOT_FOUND);
		} else {
			try {
				capa.setNombre(nombre);
				capa.setDescripcion(descripcion);
				capa.setActivo(activo);
				capa.setFavorito(favorito);
				capa.setUrlRecurso(urlRecurso);
				capa.setAliasNombre(aliasNombre);
				capa.setAliasMunicipio(aliasMunicipio);
				capa.setAliasDescripcion(aliasDescripcion);
				capa.setAliasCategoria(aliasCategoria);
				capa.setAliasImagen(aliasImagen);
				capa.setAliasDireccion(aliasDireccion);
				capa.setFichaCaracterizacion(fichaCaracterizacion);
				capa.setContieneHistoria(contieneHistoria);
				capa.setPoligono(poligono);
				if (tipoCapa != null) {
					capa.setTipoCapa(tipoCapaService.tipoCapaObtenerPorId(tipoCapa));
				}
				if (icono != null && !icono.isEmpty()) {
					Long idIconoActual = null;
					if (capa.getIcono() != null) {
						idIconoActual = capa.getIcono().getId();
					}
					Long idIcono = iconoService.iconoCrear(icono, idIconoActual);
					if (idIcono != null) {
						capa.setIcono(iconoService.iconoObtenerPorId(idIcono));
					}
				}
				if (iconoMarcador != null && !iconoMarcador.isEmpty()) {
					Long idIconoMarcadorActual = null;
					if (capa.getIconoMarcador() != null) {
						idIconoMarcadorActual = capa.getIconoMarcador().getId();
					}
					Long idIconoMarcador = iconoService.iconoCrear(iconoMarcador, idIconoMarcadorActual);
					if (idIconoMarcador != null) {
						capa.setIconoMarcador(iconoService.iconoObtenerPorId(idIconoMarcador));
					}
				}
				if (iconoPen != null && !iconoPen.isEmpty()) {
					iconoEstadoService.iconoEstadoRegistrar("CAPA", id, 2, iconoPen);
				}
				if (iconoApr != null && !iconoApr.isEmpty()) {
					iconoEstadoService.iconoEstadoRegistrar("CAPA", id, 1, iconoApr);
				}
				if (iconoRec != null && !iconoRec.isEmpty()) {
					iconoEstadoService.iconoEstadoRegistrar("CAPA", id, 0, iconoRec);
				}

				capaService.capaActualizar(capa);
				return new ResponseEntity<String>("Capa actualizada exitosamente con id: " + id, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("No se pudo realizar actualización, revisar datos y reintentar ",
						HttpStatus.BAD_REQUEST);
			}
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/capa/{idCapa}", method = RequestMethod.GET)
	public ResponseEntity<?> capaDtoObtenerPorId(@PathVariable(name = "idCapa") Long idCapa) {
		try {
			CapaDto capa = capaService.capaDtoObtenerPorId(idCapa);
			if (capa != null) {
				return new ResponseEntity<CapaDto>(capa, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se encuentra la capa", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error recuperando la capa; " + e, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/capa/aplicacion/{idAplicacion}", method = RequestMethod.GET)
	public ResponseEntity<?> capaDtoObtenerPorIdAplicacion(@PathVariable(name = "idAplicacion") Long idAplicacion) {
		try {
			List<CapaDto> capas = capaService.capaDtoObtenerPorIdAplicacion(idAplicacion);
			if (!capas.isEmpty()) {
				return new ResponseEntity<List<CapaDto>>(capas, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>("No se encuentran las capas solicitadas", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Hubo un error al buscar las capas solicitadas",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/capa/{idCapa}", notes = "Permite la eliminación de las diferentes capas que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualziación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/capa/{idCapa}", method = RequestMethod.DELETE)
	public ResponseEntity<?> capaEliminar(@PathVariable("idCapa") Long idCapa) {
		try {
			if (capaService.capaGetById(idCapa) != null) {
				if (capaService.capaEliminar(idCapa)) {
					return new ResponseEntity<String>("La capa ha sido eliminada exitosamente", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("La capa no pudo ser eliminada", HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<String>("La capa que intenta borrar no existe", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/capa/nombre", method = RequestMethod.GET)
	public ResponseEntity<?> capaDtoObtenerNombres() {
		try {
			List<String> capas = capaService.capaDtoObtenerNombres();
			if (!capas.isEmpty()) {
				return new ResponseEntity<List<String>>(capas, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>("No se encuentran las capas solicitadas", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Hubo un error al buscar las capas solicitadas",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/capa/find")
	public ResponseEntity<List<co.gov.metropol.area247.core.domain.context.web.Capa>> obtenerCapasPorAplicacion(@RequestParam Long idAplicacion) {
		try {
			List<co.gov.metropol.area247.core.domain.context.web.Capa> capas  = getCapaService().obtenerCapasPorIdAplicacion(idAplicacion);
			//List<Capa> capass = capaService.obtenerCappasPorIdAplicacion(idAplicacion);
			return new ResponseEntity<List<co.gov.metropol.area247.core.domain.context.web.Capa>>(capas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<co.gov.metropol.area247.core.domain.context.web.Capa>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/capa/find/", method = RequestMethod.GET)
	public ResponseEntity<List<co.gov.metropol.area247.core.domain.context.web.Capa>> obtenerCapasYCategoriasPorTipoCategoriasOAplicacion(
			@RequestParam(required = false) String tipoCategorias, @RequestParam(required = false) String tipoCapas,
			@RequestParam(required = false) Long idAplicacion) {
		try {
			List<co.gov.metropol.area247.core.domain.context.web.Capa> capas = getCapaService()
					.obtenerCapasConSuCategoriasPorTiposCapaCategoriaOAplicacion(tipoCategorias, tipoCapas, idAplicacion);
			if (!Utils.isNull.apply(capas)) {
				return new ResponseEntity<List<co.gov.metropol.area247.core.domain.context.web.Capa>>(capas,
						HttpStatus.OK);
			} else {
				return new ResponseEntity<List<co.gov.metropol.area247.core.domain.context.web.Capa>>(new ArrayList<>(),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Error al intener obtener las capas con sus categorias por por tipo o por id aplicacion o categorias",
					e);
			return new ResponseEntity<List<co.gov.metropol.area247.core.domain.context.web.Capa>>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/capa/urls", method = RequestMethod.GET)
	public ResponseEntity<?> capaDtoObtenerUrls() {
		try {
			List<String> urls = capaService.capaDtoObtenerUrls();
			if (!urls.isEmpty()) {
				return new ResponseEntity<List<String>>(urls, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>("No se encuentran las urls solicitadas", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Hubo un error al buscar las urls solicitadas",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	

}
