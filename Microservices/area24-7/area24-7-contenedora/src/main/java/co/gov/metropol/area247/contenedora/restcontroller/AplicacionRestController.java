package co.gov.metropol.area247.contenedora.restcontroller;

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

import co.gov.metropol.area247.contenedora.model.Aplicacion;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Recomendacion;
import co.gov.metropol.area247.contenedora.model.dto.AplicacionDto;
import co.gov.metropol.area247.contenedora.model.dto.AplicacionDtoSinListas;
import co.gov.metropol.area247.contenedora.service.IContenedoraAplicacionService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.core.restcontroller.BaseRestController;
import co.gov.metropol.area247.jdbc.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Contenedora")
@RestController
public class AplicacionRestController extends BaseRestController {

	private static Logger LOGGER = LoggerFactory.getLogger(AplicacionRestController.class);

	@Autowired
	IContenedoraAplicacionService iContenedoraAplicacionService;

	@Autowired
	IContenedoraIconoService iconoService;

	@RequestMapping(value = "/aplicacion/obtenerPorIdOTipoCapa", method = RequestMethod.GET)
	public ResponseEntity<?> aplicacionObtenerPorIdOTipoCapa(
			@RequestParam(required = false) String tipoCapas,
			@RequestParam(required = false) Long idAplicacion) {
		try {
			return new ResponseEntity<List<co.gov.metropol.area247.core.domain.context.web.Aplicacion>>(
					getAplicacionService().obtenerCapasPorTipoCapasOAplicacion(tipoCapas, idAplicacion), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al obtener las aplicaciones y sus capas", e);
			return new ResponseEntity<String>("No se pudo recuperar el listado de aplicaciones",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/aplicacion/obtenerTodas", method = RequestMethod.GET)
	public ResponseEntity<?> aplicacionDtoObtenerTodas(
			@RequestParam(required = true) boolean contenedora) {
		try {
			List<AplicacionDto> aplicaciones = 
					iContenedoraAplicacionService.aplicacionDtoObtenerTodas(contenedora);
			if (!Utils.isNull.apply(aplicaciones)) {
				return new ResponseEntity<List<AplicacionDto>>(aplicaciones,
						HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se pudo recuperar el listado de aplicaciones", HttpStatus.OK);
			}

		} catch (Exception e) {
			LOGGER.error("Error al obtener las aplicaciones", e);
			return new ResponseEntity<List<AplicacionDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@ApiOperation(value = "/aplicacion/menu", notes = "Permite la consulta de las diferentes aplicaciones y capas para el menú y sus caracterísiticas presentes en el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@GetMapping(value = "/aplicacion/menu")
	public ResponseEntity<?> obtenerAplicaciones() {
		try {
			List<AplicacionDto> aplicaciones = iContenedoraAplicacionService.obtenerMenu();
			if (!Utils.isNull.apply(aplicaciones)) {
				return new ResponseEntity<List<AplicacionDto>>(aplicaciones,
						HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se pudo recuperar el listado de aplicaciones", HttpStatus.OK);
			}

		} catch (Exception e) {
			LOGGER.error("Error al obtener las aplicaciones y sus capas", e);
			return new ResponseEntity<List<AplicacionDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/aplicacion/{aplicacionId}", method = RequestMethod.GET)
	public ResponseEntity<?> aplicacionObtenerPorId(@PathVariable(value = "aplicacionId") Long aplicacionId) {
		try {
			co.gov.metropol.area247.core.domain.context.web.Aplicacion aplicacion = getAplicacionService().obtenerAplicaciomPorId(aplicacionId);
			if (!Utils.isNull.apply(aplicacion)) {
				return new ResponseEntity<co.gov.metropol.area247.core.domain.context.web.Aplicacion>(aplicacion,
						HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se encontró la aplicación con el id asociado, reintentar",
						HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener la aplicacion por su id --{}{}", aplicacionId, e);
			return new ResponseEntity<co.gov.metropol.area247.core.domain.context.web.Aplicacion>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "/aplicacion", notes = "Permite la actualización de las diferentes aplicaciones presentes en el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Actualización exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ResponseBody
	@RequestMapping(value = "/aplicacion", method = RequestMethod.PUT)
	public ResponseEntity<?> aplicacionActualizar(
			@RequestParam(value = "icono", required = false) MultipartFile icono,
			@RequestParam(value = "id") Long id, 
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "codigoColor") String codigoColor, 
			@RequestParam(value = "activo") boolean activo,
			@RequestParam(value = "capas", required = false) List<Capa> capas,
			@RequestParam(value = "recomendaciones", required = false) List<Recomendacion> recomendaciones) {
		try {
			Aplicacion aplicacion = iContenedoraAplicacionService.aplicacionObtenerPorId(id);
			if (aplicacion != null) {
				aplicacion.setNombre(nombre);
				aplicacion.setDescripcion(descripcion);
				aplicacion.setCodigoColor(codigoColor);
				aplicacion.setActivo(activo);
				if (capas != null) {
					aplicacion.setCapas(capas);
				}
				if (recomendaciones != null) {
					aplicacion.setRecomendaciones(recomendaciones);
				}
				if (icono != null && !icono.isEmpty()) {
					Long idIcono = iconoService.iconoCrear(icono, aplicacion.getIcono().getId());
					if (idIcono != null) {
						aplicacion.setIcono(iconoService.iconoObtenerPorId(idIcono));
					}
				}
				if (iContenedoraAplicacionService.aplicacionActualizar(aplicacion)) {
					return new ResponseEntity<String>("La aplicación se ha actualizado exitosamente", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("Los datos en la aplicación son incorrectos, reintentar",
							HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<String>("La aplicación que intenta actualizar no existe",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
    @ResponseBody
	@RequestMapping(value = "/aplicacion/updateSettings", method = RequestMethod.PUT)
	public ResponseEntity<?> aplicacionUpdateSettings(
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "defaultRadius", required = false) Integer defaultRadius,
			@RequestParam(value = "maxRadius", required = false) Integer maxRadius,
			@RequestParam(value = "minRadius", required = false) Integer minRadius,
			@RequestParam(value = "otherPrefs", required = false) String otherPrefs)
	{
		try {
			Aplicacion aplicacion = iContenedoraAplicacionService.aplicacionObtenerPorId(id);
			if (aplicacion != null) {
				if (defaultRadius != null) aplicacion.setDefaultRadius(defaultRadius);
				if (minRadius != null) aplicacion.setMinRadius(minRadius);
				if (maxRadius != null) aplicacion.setMaxRadius(maxRadius);
				if (otherPrefs != null) aplicacion.setOtherPrefs(otherPrefs);

				if (iContenedoraAplicacionService.aplicacionActualizar(aplicacion)) {
					return new ResponseEntity<String>("La aplicación se ha actualizado exitosamente", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("Los datos son incorrectos, reintentar",
							HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<String>("La aplicación que intenta actualizar no existe",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/aplicacion/nolistas/{idAplicacion}", method = RequestMethod.GET)
	public ResponseEntity<?> aplicacionDtoSinListasObtenerPorId(
			@PathVariable(name = "idAplicacion") Long idAplicacion) {
		try {
			AplicacionDtoSinListas aplicacion = iContenedoraAplicacionService
					.aplicacionDtoSinListasObtenerPorId(idAplicacion);
			if (aplicacion == null) {
				return new ResponseEntity<String>("Aplicacion no encontrada", HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<AplicacionDtoSinListas>(aplicacion, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se pudo retorna la aplicacion; " + e,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
    @RequestMapping(value = "/aplicacion/listaNombres", method = RequestMethod.GET)
	public ResponseEntity<?> aplicacionDtoObtenerNombres() {
		try {
			List<String> listaNombre = iContenedoraAplicacionService.aplicacionDtoObtenerNombres();
			if (!listaNombre.isEmpty()) {
				return new ResponseEntity<List<String>>(listaNombre, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>("No se encuentra el listado de nombres", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Hubo un error al buscar el listado de nombres",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
