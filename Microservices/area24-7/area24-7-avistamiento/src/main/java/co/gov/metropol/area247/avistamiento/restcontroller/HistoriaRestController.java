package co.gov.metropol.area247.avistamiento.restcontroller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.avistamiento.model.Avistamiento;
import co.gov.metropol.area247.avistamiento.model.Historia;
import co.gov.metropol.area247.avistamiento.model.dto.HistoriaDto;
import co.gov.metropol.area247.avistamiento.model.enums.Estados;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoAvistamientoService;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoHistoriaService;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Avistamiento")
@RequestMapping(value = "/api")
public class HistoriaRestController {

	private Logger LOGGER = LoggerFactory.getLogger(HistoriaRestController.class);

	@Autowired
	IAvistamientoHistoriaService historiaService;

	@Autowired
	IAvistamientoAvistamientoService avistamientoService;

	@Autowired
	ISeguridadUsuarioService usuarioService;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/historia/avistamiento/{idAvistamiento}", method = RequestMethod.GET)
	public ResponseEntity<?> historiaObtenerPorIdAvistamiento(@PathVariable("idAvistamiento") Long idAvistamiento,
			@RequestParam(required = false) String nickname) {

		List<HistoriaDto> historiaList = historiaService.historiaObtenerPorIdAvistamiento(idAvistamiento, nickname);
		if (historiaList != null) {
			return new ResponseEntity<List<HistoriaDto>>(historiaList, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se encuentra ningúna historia asociado al id", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/historia/list/{idAvistamiento}", method = RequestMethod.GET)
	public ResponseEntity<?> getListHistorysByIdAvistamiento(@PathVariable("idAvistamiento") Long idAvistamiento) {

		List<HistoriaDto> historiaList = historiaService.getHistoriasPorIdAvistamiento(idAvistamiento);
		if (historiaList != null) {
			return new ResponseEntity<List<HistoriaDto>>(historiaList, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se encuentra ningúna historia asociado al id", HttpStatus.NOT_FOUND);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/historia/parametros", method = RequestMethod.GET)
	public ResponseEntity<?> obtenerHistoriaPorParametros(
			@RequestParam(value = "idAvistamiento", required = false) Long idAvistamiento,
			@RequestParam(value = "idUsuario", required = false) Long idUsuario,
			@RequestParam(value = "estado", required = false) Integer estado) {

		List<HistoriaDto> historiaList = historiaService.obtenerHistoriaPorParametros(idAvistamiento, idUsuario,
				estado);
		if (historiaList != null) {
			return new ResponseEntity<List<HistoriaDto>>(historiaList, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se encuentra ningúna historia asociada", HttpStatus.NOT_FOUND);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/historia/{idHistoria}", method = RequestMethod.GET)
	public ResponseEntity<?> historiaObtenerPorId(@PathVariable("idHistoria") Long idHistoria) {
		HistoriaDto historia = historiaService.historiaDtoPorId(idHistoria);
		if (historia != null) {
			return new ResponseEntity<HistoriaDto>(historia, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se encuentra ningún avistamiento asociado al id",
					HttpStatus.NOT_FOUND);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/historia", method = RequestMethod.POST)
	public ResponseEntity<String> historiaCrear(@RequestParam(value = "idAvistamiento") Long idAvistamiento,
			@RequestParam(value = "titulo", required = false) String titulo,
			@RequestParam(value = "texto", required = false) String texto,
			@RequestParam(value = "username", required = false) String username) {
		try {
			Avistamiento avistamiento = avistamientoService.avistamientoPorId(idAvistamiento);
			if (avistamiento != null) {
				Marcador marcador = avistamiento.getMarcador();
				boolean tieneHistoria = false;
				if (marcador.getCategoria() != null) {
					tieneHistoria = marcador.getCategoria().getTieneHistoria();
				} else {
					if (marcador.getCapa() != null) {
						tieneHistoria = marcador.getCapa().getContieneHistoria();
					}
				}
				if (tieneHistoria) {
					Historia historia = new Historia();
					historia.setTitulo(titulo);
					historia.setTexto(texto);
					historia.setFechaPublicacion(new Date());
					historia.setEstado(2);
					historia.setIdUsuario(usuarioService.obtenerUsuarioPorUsername(username).getId());
					if (historiaService.historiaCrear(historia, idAvistamiento)) {
						return new ResponseEntity<String>("Historia adicionada correctamente", HttpStatus.OK);
					} else {
						return new ResponseEntity<String>("No se ha podido agregar historia", HttpStatus.OK);
					}
				} else {
					return new ResponseEntity<String>("No se pueden asociar historias a este avistamiento",
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<String>("No existen avistamientos correspondientes al idAvistamiento",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("Error al intentar guardar la historia del avistamiento por id avistamiento --{}{}", e);
			return new ResponseEntity<String>("Error al intentar guardar la historia del avistamiento --{}{}",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/historia/{idAvistamiento}", method = RequestMethod.PUT)
	public ResponseEntity<String> historiaActualizar(@RequestBody Historia historia) {
		try {
			historiaService.historiaActualizar(historia);
			return new ResponseEntity<String>("historia actualizada correctamente", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido actualizar la historia", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/historia/{idHistoria}", method = RequestMethod.DELETE)
	public ResponseEntity<?> historiaBorrar(@PathVariable("idHistoria") Long idHistoria) {
		try {
			boolean historiaBorrado = historiaService.historiaEliminar(idHistoria);
			if (historiaBorrado) {
				return new ResponseEntity<String>("Comentario borrado", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se pudo borrar el comentario" + ", revisar los datos",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Hubo un error al borrar el comentario" + ", revisar los datos; " + e,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/historia/estado", method = RequestMethod.PUT)
	public ResponseEntity<String> cambiarEstadoAvistamientoa(@RequestParam(value = "id") Long id,
			@RequestParam(value = "estado", required = false) String estado) {

		Historia historia = historiaService.historiaPorId(id);

		if (historia == null) {
			return new ResponseEntity<String>("No fue posible cambiar el estado de la historia", HttpStatus.NOT_FOUND);
		} else {
			try {
				if (estado.equals("aprobado")) {
					historia.setEstado(Estados.APROBADO.getEstado());
				} else {
					if (estado.equals("rechazado")) {
						historia.setEstado(Estados.RECHAZADO.getEstado());
					} else {
						if (estado.equals("pendiente")) {
							historia.setEstado(Estados.PENDIENTE.getEstado());
						} else {
							return new ResponseEntity<String>(
									"No se pudo realizar actualización, el estado que coloco no existe",
									HttpStatus.BAD_REQUEST);
						}
					}
				}
				historiaService.historiaActualizar(historia);
				return new ResponseEntity<String>("Actualización exitosa", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("No se pudo realizar actualización, revisar datos y reintentar ",
						HttpStatus.BAD_REQUEST);
			}
		}
	}

}
