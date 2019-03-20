package co.gov.metropol.area247.avistamiento.restcontroller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.avistamiento.domain.context.web.DetalleAvistamiento;
import co.gov.metropol.area247.avistamiento.model.Avistamiento;
import co.gov.metropol.area247.avistamiento.model.Especie;
import co.gov.metropol.area247.avistamiento.model.dto.AvistamientoDto;
import co.gov.metropol.area247.avistamiento.model.enums.Colores;
import co.gov.metropol.area247.avistamiento.model.enums.Estados;
import co.gov.metropol.area247.avistamiento.repository.DetalleAvistamientoRepository;
import co.gov.metropol.area247.avistamiento.repository.MarcadorPuntoAvistamientoRepository;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoAvistamientoService;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoEspecieService;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.model.Multimedia;
import co.gov.metropol.area247.contenedora.model.VentanaInformacion;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoEstadoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMensajeService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMultimediaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraVentanaInformacionService;
import co.gov.metropol.area247.contenedora.util.NivelCapa;
import co.gov.metropol.area247.core.domain.capa.dto.CapaPointMarkerList;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.repository.MarcadorRepository;
import co.gov.metropol.area247.core.util.GeometryUtil;
import co.gov.metropol.area247.jdbc.util.Utils;
import co.gov.metropol.area247.seguridad.dao.ISeguridadMunicipioRepositoryCustom;
import co.gov.metropol.area247.seguridad.model.Municipio;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Avistamiento")
@RequestMapping(value = "/api")
public class AvistamientoRestController {

	private static Logger LOGGER = LoggerFactory.getLogger(AvistamientoRestController.class);

	private static final int NIVEL_CAPA = 2;
	private static final int ESTADO_PENDIENTE = 2;
	
	private Integer numeroAvistamientos = 0;
	
	private HttpStatus status; 

	@Autowired
	IAvistamientoAvistamientoService avistamientoService;
	
	@Autowired
	IAvistamientoEspecieService especieService;

	@Autowired
	IContenedoraMultimediaService multimediaService;

	@Autowired
	IContenedoraMarcadorService marcadorService;

	@Autowired
	IContenedoraVentanaInformacionService ventanaInformacionService;

	@Autowired
	IContenedoraCapaService capaService;

	@Autowired
	IContenedoraCategoriaService categoriaService;

	@Autowired
	MarcadorRepository marcadorRepository;

	@Autowired
	ISeguridadUsuarioService usuarioService;

	@Autowired
	IContenedoraIconoEstadoService iconoEstadoService;

	@Autowired
	MarcadorPuntoAvistamientoRepository marcadorPuntoAvistamientoRepository;
	
	@Autowired
	@Qualifier("municipioDao")
	ISeguridadMunicipioRepositoryCustom municipioDao;

	@Autowired
	private DetalleAvistamientoRepository detalleAvistamientoRepository;
	
	@Autowired
	IContenedoraMensajeService mensajeService;
	
	@Value("${iconos.server.url}")
	String urlIconos;
	

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento", method = RequestMethod.POST)
	public ResponseEntity<?> avistamientoReportar(
			@RequestParam(value = "nombreComun", required = false) String nombreComun,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "nombreCientifico", required = false) String nombreCientifico,
			@RequestParam(value = "multimedia") MultipartFile multimedia,
			@RequestParam(value = "username") String username, @RequestParam(value = "nivelCapa") String nivelCapa,
			@RequestParam(value = "idCapaCategoria") Long idCapaCategoria,
			@RequestParam(value = "latitud") Float latitud, @RequestParam(value = "longitud") Float longitud) {

		if (multimedia != null && multimedia.isEmpty()) {
			return new ResponseEntity<String>(mensajeService.crearRespuestaJson("error subir evidencia avistamiento", "", "")
					, HttpStatus.I_AM_A_TEAPOT);
		} else {
			Capa capa = null;
			Categoria categoria = null;
			Marcador marcador = new Marcador();

			int nivel = NivelCapa.CAPA.toString().equals(nivelCapa) ? NivelCapa.CAPA.getNivel()
					: NivelCapa.CATEGORIA.getNivel();

			String tipoAvistamiento = "";
			Icono iconoMar;

			if (nivel == NIVEL_CAPA) {
				capa = capaService.capaGetById(idCapaCategoria);
				tipoAvistamiento = capa.getNombre();
				iconoMar = iconoEstadoService.iconoEstadoPorCapaEstado(capa.getId(),ESTADO_PENDIENTE).getIcono();
			} else {
				categoria = categoriaService.categoriaObtenerPorId(idCapaCategoria);
				tipoAvistamiento = categoria.getNombre();
				iconoMar = iconoEstadoService.iconoEstadoPorCategoriaEstado(categoria.getId(),ESTADO_PENDIENTE).getIcono();
			}

			Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);

			Avistamiento avistamiento = new Avistamiento();
			avistamiento.setIdUsuario(usuario.getId());
			avistamiento.setNombreComun(nombreComun);
			avistamiento.setDescripcion(descripcion);
			avistamiento.setNombreCientifico(nombreCientifico);
			avistamiento.setEstado(Estados.PENDIENTE.getEstado());
			avistamiento.setFechaCreacion(new Date());
			avistamiento.setTipoAvistamiento(tipoAvistamiento);
			avistamiento.setTipoEspecie("Sin Especie");
			if(idCapaCategoria == 264){
				avistamiento.setTieneHistoria(Boolean.TRUE);
			}
			
			if (latitud == null || longitud == null) {
				return new ResponseEntity<String>("Se deben de ingresar las coordenadas del avistamiento",
						HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				try {
					Point coordenadaPunto = GeometryUtil.obtenerPunto(latitud,longitud);
					marcador.setCoordenadaPunto(coordenadaPunto);
					List<Municipio> listMunicipio = municipioDao.coordenadaInterceptoMunicipio(latitud, longitud);
					marcador.setNombreMunicipio(listMunicipio.get(0).getNombre());
					marcador.setIcono(iconoMar);
					marcador.setNombre(nombreComun);
					marcador.setPoligono(false);
					marcador.setActivo(true);
					marcador.setDescripcion(descripcion);
				} catch (Exception e) {
					LOGGER.error("Error al guardar el avistamiento", e);
					return new ResponseEntity<String>(
							"No se ha podido registrar la ubicación del avistamiento, reintentar",
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

			VentanaInformacion ventanaInformacion = new VentanaInformacion();

			if (avistamientoService.avistamientoReportar(avistamiento)) {
				Long idEvento = avistamiento.getId();
				Multimedia multimediaAuxiliar = multimediaService.multimediaCrear(null,multimedia);
				if (multimediaAuxiliar != null) {
					try {
						VentanaInformacion ventanaInfoPrev = cargarVentanaInformacion(iconoMar, nombreComun,
								nombreCientifico, marcador.getNombre(), multimediaAuxiliar, descripcion);

						VentanaInformacion newVentanaInformacion = ventanaInformacionService
								.ventanaInformacionCrear(ventanaInfoPrev);

						if (newVentanaInformacion != null) {
							marcador.setVentanaInformacion(newVentanaInformacion);
							if(avistamiento.getNombreComun() != null  && !avistamiento.getNombreComun().equals("")){
								marcador.setNombre(avistamiento.getNombreComun());
							} else if(avistamiento.getNombreCientifico() != null  && !avistamiento.getNombreCientifico().equals("")){
								marcador.setNombre(avistamiento.getNombreCientifico());
							} else{
								marcador.setNombre("AVISTAMIENTO");
							}
							
							if (nivel == NIVEL_CAPA) {
								marcador.setCapa(capa);
								marcador.setCategoria(null);
							} else {
								marcador.setCapa(null);
								marcador.setCategoria(categoria);
							}
							marcadorService.marcadorCrear(marcador);
							avistamiento.setMarcador(marcador);
							avistamientoService.avistamientoReportar(avistamiento);
						}

						return new ResponseEntity<String>("Avistamiento registrado exitosamente", HttpStatus.OK);
					} catch (Exception e) {
						LOGGER.error("Error al registrar el avistamiento con id --{}", avistamiento.getId());
						avistamientoService.avistamientoEliminar(idEvento);
						marcadorService.marcadorEliminar(marcador.getId());
						ventanaInformacionService.ventanaInformacionEliminar(ventanaInformacion.getId());
						return new ResponseEntity<String>(
								"No se ha podido registrar el avistamiento, revisar datos y reintentar",
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					avistamientoService.avistamientoEliminar(idEvento);
					return new ResponseEntity<String>(
							"No se ha podido registrar la evidencia del avistamiento, reintentar proceso",
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<String>("No se ha podido registrar avistamiento, reintentar",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento", method = RequestMethod.PUT)
	public ResponseEntity<String> avistamientoActualizar(
			@RequestParam(value = "idAvistamiento") Long idAvistamiento,
			@RequestParam(value = "nombreComun", required = false) String nombreComun,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "nombreCientifico", required = false) String nombreCientifico,
			@RequestParam(value = "tipoEspecie", required = false) Long tipoEspecie) {

		Avistamiento avistamiento = avistamientoService.avistamientoPorId(idAvistamiento);

		if (avistamiento == null) {
			return new ResponseEntity<String>("El Avistamiento que intenta actualizar no existe", HttpStatus.NOT_FOUND);
		} else {
			try {
				Marcador marcador = avistamiento.getMarcador();
				Capa capa = marcador.getCapa();
				Categoria categoria = marcador.getCategoria();
				
				avistamiento.setNombreComun(nombreComun);
				avistamiento.setDescripcion(descripcion);
				avistamiento.setNombreCientifico(nombreCientifico);
																
				if(categoria!=null) {
					avistamiento.setTipoAvistamiento(categoria.getNombre()); 
				}else {
					avistamiento.setTipoAvistamiento(capa.getNombre()); 
				}

				if ((tipoEspecie != null) && (tipoEspecie != 0)) {
					Especie especie = especieService.especiePorId(tipoEspecie);
					avistamiento.setIdEspecie(especie.getId());
					avistamiento.setTipoEspecie(especie.getNombre());
				}

				avistamientoService.avistamientoActualizar(avistamiento);
				return new ResponseEntity<String>("Actualización exitosa", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("No se pudo realizar actualización, revisar datos y reintentar ",
						HttpStatus.BAD_REQUEST);
			}
		}

	}

	/**
	 * Recibe como parametro idUsuario, activo en la sesion para obtener los
	 * avistamientos pertenecientes al usuario
	 * 
	 * @param idUsuario
	 * @return
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/usuario/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<CapaPointMarkerList> avistamientoObtenerPorUsuario(
			@PathVariable("idUsuario") Long idUsuario) {

		try {
			co.gov.metropol.area247.core.domain.capa.dto.CapaPointMarkerList avistamientoDTO = new co.gov.metropol.area247.core.domain.capa.dto.CapaPointMarkerList();

			List<MarkerPoint> markersPoint = marcadorRepository.obtenerMarcadoresPorIdUsuario(idUsuario);
			if (!Utils.isNull.apply(markersPoint)) {
				avistamientoDTO = new co.gov.metropol.area247.core.domain.capa.dto.CapaPointMarkerList();
				avistamientoDTO.setMarkersPoint(markersPoint);
				return new ResponseEntity<CapaPointMarkerList>(avistamientoDTO, HttpStatus.OK);
			} else {
				avistamientoDTO.setMarkersPoint(new ArrayList<>());
				return new ResponseEntity<CapaPointMarkerList>(avistamientoDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener el avistamiento por el usuario {}{}--", idUsuario, e);
			return new ResponseEntity<CapaPointMarkerList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/find", method = RequestMethod.GET)
	public ResponseEntity<?> avistamientoObtenerPorId(@RequestParam(required = false) Long idAvistamiento,
			@RequestParam(required = false) Long idMarcador) {
		AvistamientoDto avistamiento = avistamientoService
				.buscarAvistamientoPorIdAvistamientoOIdMarcador(idAvistamiento, idMarcador);
		if (avistamiento != null) {
			return new ResponseEntity<AvistamientoDto>(avistamiento, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(mensajeService.crearRespuestaJson("no avistamientos", "", "")
					, HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/estado/{estado}", method = RequestMethod.GET)
	public ResponseEntity<?> avistamientoObtenerPorEstado(@PathVariable("estado") int estado) {
		List<AvistamientoDto> avistamientos = avistamientoService.avistamientoPorEstado(estado);
		if (avistamientos != null) {
			return new ResponseEntity<List<AvistamientoDto>>(avistamientos, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se han encontrado avistamientos asociados al estado solicitado",
					HttpStatus.NOT_FOUND);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento", method = RequestMethod.GET)
	public ResponseEntity<?> obtenerAvistamientos() {
		List<AvistamientoDto> avistamientos = avistamientoService.avistamientoObtenerTodos();
		if (avistamientos != null) {
			return new ResponseEntity<List<AvistamientoDto>>(avistamientos, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(mensajeService.crearRespuestaJson("no avistamientos", "", ""), HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/avistamiento/estado", notes = "Permite la actualización de un avistamiento del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualización exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/estado", method = RequestMethod.PUT)
	public ResponseEntity<String> cambiarEstadoAvistamientoa(@RequestParam(value = "id") Long id,
			@RequestParam(value = "estado", required = false) String estado) {

		Avistamiento avistamiento = avistamientoService.avistamientoPorId(id);

		if (avistamiento == null) {
			return new ResponseEntity<String>("El Avistamiento que intenta actualizar no existe", HttpStatus.NOT_FOUND);
		} else {
			Marcador marcador = avistamiento.getMarcador();
			Capa capa = marcador.getCapa();
			Categoria categoria = marcador.getCategoria();
			Icono iconoMar = null;
			Long idEspecie = avistamiento.getIdEspecie(); 

			try {
				if (estado.equals("aprobado")) {
					avistamiento.setEstado(Estados.APROBADO.getEstado());
					if (idEspecie == null) {
					    if (categoria == null) {
						    iconoMar = iconoEstadoService
								    .iconoEstadoPorCapaEstado(capa.getId(), Estados.APROBADO.getEstado()).getIcono();
					    } else {
						    iconoMar = iconoEstadoService
								    .iconoEstadoPorCategoriaEstado(categoria.getId(), Estados.APROBADO.getEstado())
								    .getIcono();
					    }
					}else {
						Especie especie = especieService.especiePorId(idEspecie);
	                    iconoMar = especie.getIcono();
					}
				} else {
					if (estado.equals("rechazado")) {
						avistamiento.setEstado(Estados.RECHAZADO.getEstado());
						if (categoria == null) {
							iconoMar = iconoEstadoService
									.iconoEstadoPorCapaEstado(capa.getId(), Estados.RECHAZADO.getEstado()).getIcono();
						} else {
							iconoMar = iconoEstadoService
									.iconoEstadoPorCategoriaEstado(categoria.getId(), Estados.RECHAZADO.getEstado())
									.getIcono();
						}
					} else {
						if (estado.equals("pendiente")) {
							avistamiento.setEstado(Estados.PENDIENTE.getEstado());
							if (categoria == null) {
								iconoMar = iconoEstadoService
										.iconoEstadoPorCapaEstado(capa.getId(), Estados.PENDIENTE.getEstado())
										.getIcono();
							} else {
								iconoMar = iconoEstadoService
										.iconoEstadoPorCategoriaEstado(categoria.getId(), Estados.PENDIENTE.getEstado())
										.getIcono();
							}
						} else {
							return new ResponseEntity<String>(
									"No se pudo realizar actualización, el estado que coloco no existe",
									HttpStatus.BAD_REQUEST);
						}
					}
				}
				avistamientoService.avistamientoActualizar(avistamiento);
				marcador.setIcono(iconoMar);
				marcadorService.marcadorCrear(marcador);
				return new ResponseEntity<String>("Actualización exitosa", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("No se pudo realizar actualización, revisar datos y reintentar ",
						HttpStatus.BAD_REQUEST);
			}
		}
	}

	@ResponseBody
	@ApiOperation(value = "/avistamiento/{idAvistamiento}", notes = "Permite la eliminación de Avistamientos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Eliminacion exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/{idAvistamiento}", method = RequestMethod.DELETE)
	public ResponseEntity<?> avistamientoEliminar(@PathVariable("idAvistamiento") Long idAvistamiento) {
		try {
			if (avistamientoService.avistamientoEliminar(idAvistamiento)) {
				return new ResponseEntity<String>("La Avistamiento ha sido eliminado exitosamente", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("El Avistamiento no pudo ser eliminado o no existe",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Permite obtener los marcadores de los avistamientos por nivel capa: Se
	 * refiere a alguna opciones del menu por ejemplo (Mis avistamientos,
	 * consurso de fotografia) son capas por latitud y longitud para consultar
	 * la ubicaciones del marcador (avistamiento) y tambien por radio accion, es
	 * decir los mas cercanos a la ubicacion del usuario.
	 * 
	 * @param nivelCapa
	 * @param idCapa
	 * @param latitud
	 * @param longitud
	 * @param radioAccion
	 * @return
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/avistamiento/find/{nivelCapa}/{idCapa}")
	public ResponseEntity<CapaPointMarkerList> obtenerAvistamientosPorLatLonYRadio(@PathVariable String nivelCapa,
			@PathVariable Long idCapa, @RequestParam Double latitud, @RequestParam Double longitud,
			@RequestParam int radioAccion) {
		try {
			CapaPointMarkerList avistamientoDTO = new CapaPointMarkerList();
			avistamientoDTO.setIdCapa(idCapa);
			List<MarkerPoint> markersPoint = marcadorRepository.obtenerMarcadoresPorLatYLonYRadioYCapa(latitud,
					longitud, radioAccion, idCapa, nivelCapa);
			if (!Utils.isNull.apply(markersPoint)) {
				avistamientoDTO = new CapaPointMarkerList();
				avistamientoDTO.setIdCapa(idCapa);
				avistamientoDTO.setMarkersPoint(markersPoint);
				return new ResponseEntity<CapaPointMarkerList>(avistamientoDTO, HttpStatus.OK);
			} else {
				avistamientoDTO.setMarkersPoint(new ArrayList<>());
				return new ResponseEntity<CapaPointMarkerList>(avistamientoDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener los avistamientos por capa {}{}", idCapa);
			return new ResponseEntity<CapaPointMarkerList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private VentanaInformacion cargarVentanaInformacion(Icono iconoMarcador, String nombreComun,
			String nombreCientifico, String nombreMarcador, Multimedia multimediaAuxiliar, String descripcion) {
		VentanaInformacion ventanaInformacion = new VentanaInformacion();
		ventanaInformacion.setColor(Colores.PENDIENTE.getColor());
		ventanaInformacion.setIcono(iconoMarcador);
		ventanaInformacion.setMultimedia(multimediaAuxiliar);
		ventanaInformacion.setNombre(nombreMarcador);
		ventanaInformacion.setTitulo(nombreComun + " | " + nombreCientifico);
		ventanaInformacion.setDescripcion(descripcion);
		ventanaInformacion.setDescripcion2(new Date().toString());
		return ventanaInformacion;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/cantidad/estado/{estado}", method = RequestMethod.GET)
	public ResponseEntity<?> cantidadAvistamientoPorEstado(@PathVariable("estado") int estado) {
		Integer cantidad = avistamientoService.cantidadAvistamientoPorEstado(estado);
		if (cantidad != null) {
			return new ResponseEntity<Integer>(cantidad, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se pudo realizar la consulta", HttpStatus.NOT_FOUND);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/parametros", method = RequestMethod.GET)
	public ResponseEntity<?> avistamientoObtenerPorParametros(
			@RequestParam(value = "nombreComun", required = false) String nombreComun,
			@RequestParam(value = "nombreCientifico", required = false) String nombreCientifico,
			@RequestParam(value = "municipio", required = false) String municipio,
			@RequestParam(value = "tipoAvistamiento", required = false) String tipoAvistamiento) {
		List<AvistamientoDto> avistamientos = avistamientoService.obtenerAvistamientoPorParametros(nombreComun,
				nombreCientifico, municipio, tipoAvistamiento);
		if (avistamientos != null) {
			return new ResponseEntity<List<AvistamientoDto>>(avistamientos, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se han encontrado avistamientos asociados a los parametros",
					HttpStatus.NOT_FOUND);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/cantidad/parametros", method = RequestMethod.GET)
	public ResponseEntity<Integer> cantidadAvistamientoPorParametro(
			@RequestParam(value = "tipoAvistamiento", required = false) String tipoAvistamiento,
			@RequestParam(value = "idCategoria", required = false) String idCategoria,
			@RequestParam(value = "estado", required = false) Integer estado,
			@RequestParam(value = "fechaDesde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(value = "fechaHasta", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
			@RequestParam(value = "soloComHis", required = false) boolean soloComHis,
			@RequestParam(value = "conComenDeHis", required = false) boolean conComenDeHis) {
		
		avistamientoService.obtenerNumeroAvistamientoPorTipoAvistamientoOrEstadoOrFecha(tipoAvistamiento, idCategoria,
				estado, fechaDesde, fechaHasta, soloComHis,conComenDeHis).subscribe(data -> {
					numeroAvistamientos = data;
					status = HttpStatus.OK;
					}, error -> {
					status = HttpStatus.INTERNAL_SERVER_ERROR;
					LOGGER.error("Error al consultar la cantidad de avistamientos por tipo avistamiento o tipo especie o fechas --{}{}",tipoAvistamiento, error);
				});

		return new ResponseEntity<Integer>(numeroAvistamientos, status);
		/*
		 * if (cantidad != null) { return new ResponseEntity<Integer>(cantidad,
		 * HttpStatus.OK); } else { return new
		 * ResponseEntity<String>("No se pudo realizar la consulta",
		 * HttpStatus.NOT_FOUND); }
		 */
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/capafecha", method = RequestMethod.GET)
	public ResponseEntity<?> obtenerAvistamientoPorIdCapa(@RequestParam(value = "idCapa", required = false) Long idCapa,
			@RequestParam(value = "fechaDesde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
			@RequestParam(required = false) Long idCategoria) {
		try {
			if (!Utils.isNull.apply(fechaDesde) && !Utils.isNull.apply(fechaHasta)) {
				if (fechaDesde.isAfter(fechaHasta)) {
					return new ResponseEntity<String>("La fecha inicial debe ser mayor que la fecha hasta",
							HttpStatus.OK);
				}
			}

			return new ResponseEntity<List<AvistamientoDto>>(
					avistamientoService.obtenerAvistamientoPorCapaFecha(idCapa, fechaDesde, fechaHasta, idCategoria),
					HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.error("Error al obtener los avistamiento por un rango de fechas", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
@RequestMapping(value = "/avistamiento/paginated/capa-fecha", method = RequestMethod.GET)
public ResponseEntity<?> getPaginatedAvistamientoCapaFecha(@RequestParam(value = "idCapa", required = false) Long idCapa,
		@RequestParam(value = "fechaDesde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
		@RequestParam(required = false) Long idCategoria,
		@RequestParam(required = false) String whereClause, 
		@RequestParam(required = true) String orderClause, 
		@RequestParam(required = true) int pageStart, 
		@RequestParam(required = true) int pageSize,
		@RequestParam(required = false) String estadosList,
		@RequestParam(required = false) boolean soloComHis,
		@RequestParam(required = false) boolean conComenDeHis
		) {
	try {
		if (!Utils.isNull.apply(fechaDesde) && !Utils.isNull.apply(fechaHasta)) {
			if (fechaDesde.isAfter(fechaHasta)) {
				return new ResponseEntity<String>("La fecha inicial debe ser mayor que la fecha hasta",
						HttpStatus.OK);
			}
		}

		return new ResponseEntity<List<AvistamientoDto>>(
				avistamientoService.getPaginatedAvistamientoPorCapaFecha(idCapa, fechaDesde, fechaHasta, idCategoria, 
						whereClause, orderClause, pageStart, pageSize, estadosList, soloComHis, conComenDeHis),
				HttpStatus.OK);

	} catch (Exception e) {
		LOGGER.error("Error al obtener los avistamiento por un rango de fechas", e);
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}                      
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/cantidad/capa-fecha", method = RequestMethod.GET)
	public ResponseEntity<?> getNumAvistamientoPorIdCapa(@RequestParam(value = "idCapa", required = false) Long idCapa,
			@RequestParam(value = "fechaDesde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
			@RequestParam(required = false) Long idCategoria,
			@RequestParam(required = false) String estadosList,
			@RequestParam(required = false) boolean soloComHis,
			@RequestParam(required = false) boolean conComenDeHis) {
		try {
			if (!Utils.isNull.apply(fechaDesde) && !Utils.isNull.apply(fechaHasta)) {
				if (fechaDesde.isAfter(fechaHasta)) {
					return new ResponseEntity<String>("La fecha inicial debe ser mayor que la fecha hasta",
							HttpStatus.OK);
				}
			}

			return new ResponseEntity<Integer>(
					avistamientoService.getNumAvistamXCapaFecha(idCapa,fechaDesde,fechaHasta,idCategoria,
							estadosList,soloComHis,conComenDeHis),
					HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.error("Error al obtener los avistamiento por un rango de fechas", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/avistamiento/filtered/cantidad/capa-fecha", method = RequestMethod.GET)
	public ResponseEntity<?> getNumFilteredAvistamientoPorIdCapa(@RequestParam(value = "idCapa", required = false) Long idCapa,
			@RequestParam(value = "fechaDesde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
			@RequestParam(required = false) Long idCategoria,
			@RequestParam(required = false) String whereClause,
			@RequestParam(required = false) String estadosList,
			@RequestParam(required = false) boolean soloComHis,
			@RequestParam(required = false) boolean conComenDeHis) {
		try {
			if (!Utils.isNull.apply(fechaDesde) && !Utils.isNull.apply(fechaHasta)) {
				if (fechaDesde.isAfter(fechaHasta)) {
					return new ResponseEntity<String>("La fecha inicial debe ser mayor que la fecha hasta",
							HttpStatus.OK);
				}
			}

			return new ResponseEntity<Integer>(
					avistamientoService.getFilteredAvistamientoPorCapaFecha(idCapa, fechaDesde, fechaHasta, 
							idCategoria, whereClause, estadosList, soloComHis,conComenDeHis),HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.error("Error al obtener los avistamiento por un rango de fechas", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Obtiene los nombres por el nombre de avistamiento (alguna arbol u animal)
	 * 
	 * @param nombre
	 * @param latitud
	 * @param longitud
	 * @return
	 */
	@GetMapping(value = "/avistamiento/findbyname")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	public ResponseEntity<CapaPointMarkerList> obtenerAvistamientosPorNombreCientificoOComun(
			@RequestParam String nombre, @RequestParam double latitud, @RequestParam double longitud) {
		CapaPointMarkerList markerDTO = new CapaPointMarkerList();
		try {
			List<MarkerPoint> marcadores = marcadorPuntoAvistamientoRepository
					.obtenerMarcadorAvistamientoPorNombreOCapa(nombre, longitud, latitud);
			markerDTO.setMarkersPoint(marcadores);
			return new ResponseEntity<CapaPointMarkerList>(markerDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<CapaPointMarkerList>(markerDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/***
	 * Recibe como parametro un id de avistamiento, consulta un avistamiento
	 * especifico por el id
	 * 
	 * @param idAvistamiento
	 * @return
	 */
	@GetMapping(value = "/avistamiento/detalle")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	public ResponseEntity<DetalleAvistamiento> obtenerDetalleAvistamientoPorIdAvistamiento(
			@RequestParam Long idAvistamiento) {
		try {
			DetalleAvistamiento detalleAvistamiento = detalleAvistamientoRepository.buscarPorId(idAvistamiento);
			if (!Utils.isNull.apply(detalleAvistamiento)) {			
				if(detalleAvistamiento.getImageAvistamiento()!=null) {
					String rutaIcono = urlIconos + detalleAvistamiento.getImageAvistamiento().getId();
				    detalleAvistamiento.getImageAvistamiento().setRutaLogo(rutaIcono);
				}
				return new ResponseEntity<DetalleAvistamiento>(detalleAvistamiento, HttpStatus.OK);
			} else {
				return new ResponseEntity<DetalleAvistamiento>(new DetalleAvistamiento(), HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener el detalle del avistamiento por id --{}{}", idAvistamiento, e);
			return new ResponseEntity<DetalleAvistamiento>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@PutMapping(value = "/avistamiento/flora-inventory")
	public ResponseEntity<Boolean> loadFloraInventory() {
		return new ResponseEntity<Boolean>(avistamientoService.registerFloraInventoryAsSighting(), HttpStatus.OK);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/avistamiento/flora-inventory/size")
	public ResponseEntity<Integer> getFloraInventorySize() {
		try {
			return new ResponseEntity<Integer>(avistamientoService.getFloraInventorySize(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/avistamiento/find/capa/{idCapa}")
	public ResponseEntity<Integer> getSightingByRadiusAction(
			@PathVariable("idCapa") Long idCapa,
			@RequestParam("latitud") Double latitud,
			@RequestParam("longitud") Double longitud,
			@RequestParam("radioAccion") Integer radioAccion
			) {
	try {
		//sadf
		return new ResponseEntity<Integer>(avistamientoService.getFloraInventorySize(), HttpStatus.OK);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

}
