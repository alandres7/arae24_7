package co.gov.metropol.area247.vigias.restcontroller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.centrocontrol.model.dto.NodoArbolDto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlNodoArbolService;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.IconosVigia;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.model.Multimedia;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCoordenadaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoEstadoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconosVigiaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMensajeService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMultimediaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraVentanaInformacionService;
import co.gov.metropol.area247.contenedora.util.NivelCapa;
import co.gov.metropol.area247.core.domain.capa.dto.CapaPointMarkerList;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.util.GeometryUtil;
import co.gov.metropol.area247.jdbc.util.Utils;
import co.gov.metropol.area247.seguridad.dao.ISeguridadMunicipioRepositoryCustom;
import co.gov.metropol.area247.seguridad.model.Municipio;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;
import co.gov.metropol.area247.vigias.model.Vigia;
import co.gov.metropol.area247.vigias.model.dto.VigiaDto;
import co.gov.metropol.area247.vigias.model.enums.EstadoReporte;
import co.gov.metropol.area247.vigias.model.enums.Estados;
import co.gov.metropol.area247.vigias.service.IVigiasComentarioService;
import co.gov.metropol.area247.vigias.service.impl.VigiasComentarioServiceImpl;
import co.gov.metropol.area247.vigias.service.impl.VigiasVigiaServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Vigia")
@RequestMapping(value = "/api")
public class VigiasRestController {
	
	private static final int NIVEL_CAPA = 2;
		

	@Autowired
	VigiasVigiaServiceImpl vigiaService;

	@Autowired
	IContenedoraMultimediaService multimediaService;

	@Autowired
	IContenedoraCoordenadaService coordenadaService;

	@Autowired
	IContenedoraMarcadorService marcadorService;

	@Autowired
	IContenedoraIconoService iconoService;

	@Autowired
	IContenedoraVentanaInformacionService ventanaInformacionService;

	@Autowired
	ICentroControlNodoArbolService nodoArbolService;

	@Autowired
	IVigiasComentarioService comentarioService;

	@Autowired
	VigiasComentarioServiceImpl comentarioServiceImpl;
	
	@Autowired
	IContenedoraCapaService capaService;
	
	@Autowired
	IContenedoraCategoriaService categoriaService;
	
	@Autowired
	ISeguridadUsuarioService usuarioService;
	
	@Autowired
	IContenedoraIconoEstadoService iconoEstadoService;
	
	@Autowired
	IContenedoraMensajeService mensajeService;
	
	@Autowired
	@Qualifier("municipioDao")
	ISeguridadMunicipioRepositoryCustom municipioDao;
	
	@Autowired
	IContenedoraIconosVigiaService iconosVigiaService;

	Marcador marcador = null;
	
	@Value("${media.folder.url}")
	String rutaCarpetaMultimedia;
		

	private static final Logger LOGGER = LoggerFactory.getLogger(VigiasRestController.class);
	

	


	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
    @RequestMapping(value = "/vigia", method = RequestMethod.POST)
	public ResponseEntity<?> vigiaReportar(
			@RequestParam(value = "descripcion", required = true) String descripcion,
			@RequestParam(value = "direccion", required = false) String direccion,
			@RequestParam(value = "idNodoArbol", required = false) Long idNodoArbol,						
			@RequestParam(value = "activo", required = true) boolean activo,						
			@RequestParam(value = "multimedia", required = true) MultipartFile multimedia,						
			@RequestParam(value = "multimediaOpcional1", required = false) MultipartFile multimediaOpcional1,						
			@RequestParam(value = "multimediaOpcional2", required = false) MultipartFile multimediaOpcional2,						
			@RequestParam(value = "multimediaOpcional3", required = false) MultipartFile multimediaOpcional3,						
			@RequestParam(value = "multimediaOpcional4", required = false) MultipartFile multimediaOpcional4,
			@RequestParam(value = "idUsuario", required = true) Long idUsuario, 
			@RequestParam(value = "nivelCapa", required = true) String nivelCapa,
			@RequestParam(value = "idCapaCategoria", required = true) Long idCapaCategoria,
			@RequestParam(value = "latitud", required = true) Float latitud, 
			@RequestParam(value = "longitud", required = true) Float longitud,
			@RequestParam(value = "radicadoSim", required = false) String radicadoSim,
			@RequestParam(value = "recorridoArbol", required = false) String recorridoArbol) {

		if (multimedia != null && multimedia.isEmpty()) {
			return new ResponseEntity<String>("No ha sido posible registrar el reporte de vigía, reintentar",
					HttpStatus.BAD_REQUEST);
		} else {
			Capa capa = null;
			Categoria categoria = null;
			Marcador marcador = new Marcador();

			int nivel = NivelCapa.CAPA.toString().equals(nivelCapa) ? NivelCapa.CAPA.getNivel()
					: NivelCapa.CATEGORIA.getNivel();

			Icono iconoMar;	
			Long idIconoVentana;		
			IconosVigia iconosVigia = iconosVigiaService.iconosVigiaObtenerPorIdNodoArbol(idNodoArbol);
			
			if (nivel == NIVEL_CAPA) {
				capa = capaService.capaGetById(idCapaCategoria);
				iconoMar = capa.getIconoMarcador();					
			} else {
				categoria = categoriaService.categoriaObtenerPorId(idCapaCategoria);
				iconoMar = categoria.getIconoMarcador();
			}
			
			if(iconosVigia != null) {
				iconoMar = iconoService.iconoObtenerPorId(iconosVigia.getIdIconoVigiaPen());
				idIconoVentana = iconosVigia.getIdIconoVigiaWin();
			}else {
				idIconoVentana = iconoMar.getId();
			}				
			
			Vigia reporteVigia = new Vigia();			
			reporteVigia.setIdUsuario(idUsuario);
			reporteVigia.setDescripcion(descripcion);	
			reporteVigia.setDireccion(direccion);
			reporteVigia.setIdNodoArbol(idNodoArbol);
			reporteVigia.setRadicadoSim(radicadoSim);
			reporteVigia.setRecorridoArbol(recorridoArbol);
			reporteVigia.setActivo(activo);
			reporteVigia.setEstado(Estados.PENDIENTE.name().toString());
			reporteVigia.setFechaReporte(new Date());
			reporteVigia.setIdIconoVentana(idIconoVentana);

			
			if (latitud == null || longitud == null) {
				return new ResponseEntity<String>("Se deben de ingresar las coordenadas del reporte de vigía",
						HttpStatus.BAD_REQUEST);
			} else {
				try {
					Point coordenadaPunto = GeometryUtil.obtenerPunto(latitud,longitud);
					marcador.setCoordenadaPunto(coordenadaPunto);
					List<Municipio> listMunicipio = municipioDao.coordenadaInterceptoMunicipio(latitud, longitud);
					marcador.setNombreMunicipio(listMunicipio.get(0).getNombre());
					marcador.setIcono(iconoMar);
					marcador.setNombre("Reporte vigía");
					marcador.setDescripcion(descripcion);
					marcador.setDireccion(direccion);
					marcador.setPoligono(false);
					marcador.setActivo(true);
				} catch (Exception e) {
					LOGGER.error("Error al guardar el reporte de vigía", e);
					return new ResponseEntity<String>("No se ha podido registrar la ubicación del reporte de vigía, reintentar",
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

			if (vigiaService.vigiaCrear(reporteVigia)) {
				Long idEvento = reporteVigia.getId();
				Multimedia multimediaAuxiliar = multimediaService.multimediaCrear(null,multimedia, reporteVigia);
				if (multimediaAuxiliar != null) {
					List<Multimedia> multimedias = new ArrayList<>();
					multimedias.add(multimediaAuxiliar);
					List<MultipartFile> listMultimedias = getListMultimedias(multimediaOpcional1 , multimediaOpcional2, multimediaOpcional3, multimediaOpcional4);
					for (MultipartFile multipartFile : listMultimedias) {
						multimediaAuxiliar = multimediaService.multimediaCrear(null,multimedia, reporteVigia);
						if(multimediaAuxiliar != null)
							multimedias.add(multimediaAuxiliar);
					}
					reporteVigia.setMultimedias(multimedias);
					try {
						if (nivel == NIVEL_CAPA) {
							marcador.setCapa(capa);
							marcador.setCategoria(null);
						} else {
							marcador.setCapa(null);
							marcador.setCategoria(categoria);
						}
						marcadorService.marcadorCrear(marcador);
						reporteVigia.setMarcador(marcador);
//						reporteVigia.setMultimedia(multimediaAuxiliar);
						vigiaService.vigiaCrear(reporteVigia);

						return new ResponseEntity<String>(
								"Reporte de vigía registrado exitosamente con id: " + reporteVigia.getId(), HttpStatus.OK);
					} catch (Exception e) {
						LOGGER.error("Error al registrar el reporte de vigía con id --{}", reporteVigia.getId());
						vigiaService.vigiaEliminar(idEvento);
						marcadorService.marcadorEliminar(marcador.getId());
						return new ResponseEntity<String>(
								"No se ha podido registrar el reporte de vigía, revisar datos y reintentar",
								HttpStatus.BAD_REQUEST);
					}
				} else {
					vigiaService.vigiaEliminar(idEvento);
					return new ResponseEntity<String>(
							"No se ha podido registrar el reporte de vigía, reintentar proceso",
							HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<String>("No se ha podido registrar el reporte de vigía, reintentar",
						HttpStatus.BAD_REQUEST);
			}
		}
	}	
	
		
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/vigia/obtenerPorIdVigiaOIdMarcador", method = RequestMethod.GET)
    public ResponseEntity<?> vigiaObtenerPorIdVigiaOIdMarcador(
    		@RequestParam(value = "idVigia", required = false) Long idVigia,
			@RequestParam(value = "idMarcador", required = false) Long idMarcador) {
		VigiaDto vigiaDto = vigiaService.vigiaDtoConsultarPorIdVigiaOIdMarcador(idVigia,idMarcador);
	    if(vigiaDto != null) {
		    return new ResponseEntity<VigiaDto>(vigiaDto, HttpStatus.OK);
	    } else {
		    return new ResponseEntity<String>("No se ha encontrado ningún reporte de vigías asociado",HttpStatus.I_AM_A_TEAPOT);
	    }
    }
		
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/vigia/usuario/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<?> vigiaObtenerPorIdUsuario(@PathVariable("idUsuario") Long idUsuario) {
		return new ResponseEntity<List<VigiaDto>>(vigiaService.vigiaDtoPorIdUsuario(idUsuario), HttpStatus.OK);
	}	
		
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
    @RequestMapping(value = "/vigia/marcadoresPorIdUsuarioOEstado", method = RequestMethod.GET)
	public ResponseEntity<CapaPointMarkerList> obtenerMarcadoresVigiaPorIdUsuarioOEstado(
			@RequestParam(value = "idUsuario", required = false) Long idUsuario,
			@RequestParam(value = "estado", required = false) final EstadoReporte estado) {
		try {
			co.gov.metropol.area247.core.domain.capa.dto.CapaPointMarkerList vigiaDTO 
			    = new co.gov.metropol.area247.core.domain.capa.dto.CapaPointMarkerList();
			
			String estadoNombre = null;
			if(estado != null) {
				estadoNombre = estado.getNombre();
			}
			List<MarkerPoint> markersPoint = vigiaService.obtenerMarcadoresVigiaPorIdUsuarioOEstado(idUsuario,estadoNombre);
			if (!Utils.isNull.apply(markersPoint)) {
				vigiaDTO = new co.gov.metropol.area247.core.domain.capa.dto.CapaPointMarkerList();
				vigiaDTO.setMarkersPoint(markersPoint);
				return new ResponseEntity<CapaPointMarkerList>(vigiaDTO, HttpStatus.OK);
			} else {
				vigiaDTO.setMarkersPoint(new ArrayList<>());
				return new ResponseEntity<CapaPointMarkerList>(vigiaDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener los reportes de vigía por el usuario {}{}--", idUsuario, e);
			return new ResponseEntity<CapaPointMarkerList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
	})
    @RequestMapping(value = "/vigia/estado/{estado}", method = RequestMethod.GET)
    public ResponseEntity<?> vigiaObtenerPorEstado(@PathVariable("estado") final EstadoReporte estado) {
		List<VigiaDto> listaVigia = (List<VigiaDto>) vigiaService.vigiaDtoPorEstado(estado.getNombre());
	    if(listaVigia!=null){
	        return new ResponseEntity<List<VigiaDto>>(listaVigia,HttpStatus.OK);
	    } else {
	        return new ResponseEntity<String>("No hay objeto disponibles para la consulta",HttpStatus.NOT_FOUND);
	    }	
	}	
		
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
	})
    @RequestMapping(value = "/vigia", method = RequestMethod.GET)
    public ResponseEntity<?> vigiaObtenerTodos() {
		return new ResponseEntity<List<VigiaDto>>(vigiaService.vigiaDtoObtenerTodos(), HttpStatus.OK);
	}	
	
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/vigia/cantidad/parametros", method = RequestMethod.GET)
	public ResponseEntity<Integer> cantidadAvistamientoPorParametro(
			@RequestParam(value = "idCapa", required = false) String idCapa,
			@RequestParam(value = "idCategoria", required = false) String idCategoria,
			@RequestParam(value = "estado", required = false) String estado,
			@RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
			@RequestParam(value = "fechaFin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin,
			@RequestParam(value = "comenPen", required = false) boolean comenPen) {
		
		Integer cantidad = vigiaService.obtenerCantidadVigiasPorParametros(idCapa,idCategoria,estado,fechaInicio,fechaFin,comenPen);
		
		return new ResponseEntity<Integer>(cantidad, HttpStatus.OK);
	}	
			
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
	})
    @RequestMapping(value = "/vigia/paginated/parametros", method = RequestMethod.GET)
    public ResponseEntity<?> obtenerVigiaPaginatedPorParametros(
    		@RequestParam(value = "idCapa", required = false) Long idCapa,
    		@RequestParam(value = "fechaDesde",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
    		@RequestParam(value = "fechaHasta",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
    		@RequestParam(required = false) Long idCategoria,
    		@RequestParam(required = false) String whereClause, 
    		@RequestParam(required = true) String orderClause, 
    		@RequestParam(required = true) int pageStart, 
    		@RequestParam(required = true) int pageSize,
    		@RequestParam(required = false) String estadosList,
    		@RequestParam(required = false) boolean comenPen) {
			
		try {
			return new ResponseEntity<List<VigiaDto>>(
					vigiaService.getVigiaPaginatedPorParametros(idCapa,fechaDesde,fechaHasta,idCategoria,
							whereClause,orderClause,pageStart,pageSize, estadosList, comenPen), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al obtener los reportes de Vigia", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
		
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/vigia/paginated/cantidad/parametros", method = RequestMethod.GET)
	public ResponseEntity<?> getNumAvistamientoPorIdCapa(
			@RequestParam(value = "idCapa", required = false) Long idCapa,
			@RequestParam(value = "fechaDesde",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
    		@RequestParam(value = "fechaHasta",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
			@RequestParam(required = false) Long idCategoria,
			@RequestParam(required = false) String estadosList,
			@RequestParam(required = false) boolean comenPen) {
		try {
			return new ResponseEntity<Integer>(
					vigiaService.getCantidadVigiasPaginatedPorParametros(idCapa,idCategoria,estadosList,
							fechaDesde,fechaHasta,comenPen), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al obtener la cantidad de reportes de Vigia", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
		
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") 
	})
    @RequestMapping(value = "/vigia/paginated/cantidad/filtered/parametros", method = RequestMethod.GET)
    public ResponseEntity<?> getCantidadVigiasPaginatedAndFilteredPorParametros(
    		@RequestParam(value = "idCapa", required = false) Long idCapa,
    		@RequestParam(value = "fechaDesde",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
    		@RequestParam(value = "fechaHasta",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
    		@RequestParam(required = false) Long idCategoria,
    		@RequestParam(required = false) String whereClause, 
    		@RequestParam(required = false) String estadosList,
    		@RequestParam(required = false) boolean comenPen) {			
		try {
			return new ResponseEntity<Integer>(
					vigiaService.getCantidadVigiasPaginatedAndFilteredPorParametros(idCapa,fechaDesde,fechaHasta,
							idCategoria,whereClause,estadosList,comenPen), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al obtener los reportes de Vigia", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/vigia/findByRadioYCapa/{nivelCapa}/{idCapa}")
	public ResponseEntity<CapaPointMarkerList> obtenerMarcadoresVigiaPorLatYLonYRadioYCapa(
			@PathVariable String nivelCapa, @PathVariable Long idCapa, @RequestParam Double latitud, 
			@RequestParam Double longitud, @RequestParam int radioAccion) {
		try {
			CapaPointMarkerList marcadoresVigia = new CapaPointMarkerList();
			marcadoresVigia.setIdCapa(idCapa);
			List<MarkerPoint> markersPoint = vigiaService.obtenerMarcadoresVigiaPorLatYLonYRadioYCapa(latitud,
					longitud, radioAccion, idCapa, nivelCapa);
			if (!Utils.isNull.apply(markersPoint)) {
				marcadoresVigia = new CapaPointMarkerList();
				marcadoresVigia.setIdCapa(idCapa);
				marcadoresVigia.setMarkersPoint(markersPoint);
				return new ResponseEntity<CapaPointMarkerList>(marcadoresVigia, HttpStatus.OK);
			} else {
				marcadoresVigia.setMarkersPoint(new ArrayList<>());
				return new ResponseEntity<CapaPointMarkerList>(marcadoresVigia, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener los reportes de vigía por capa {}{}", idCapa);
			return new ResponseEntity<CapaPointMarkerList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/vigia/cambiarEstado", method = RequestMethod.PUT)
	public ResponseEntity<String> cambiarEstadoVigia(
			@RequestParam(value = "idVigia") Long idVigia,
			@RequestParam(value = "estado") final EstadoReporte estado) {		
		try {
			Vigia vigia = vigiaService.vigiaConsultarPorId(idVigia);
			if (vigia == null) {
				return new ResponseEntity<String>("El reporte de vigia que intenta actualizar no existe",HttpStatus.NOT_FOUND);
			} else {
				IconosVigia iconosVigia = iconosVigiaService.iconosVigiaObtenerPorIdNodoArbol(vigia.getIdNodoArbol());
				if (iconosVigia != null) {
					Icono iconoMar = null;
					if (estado.getNombre().equals("APROBADO")) {
						vigia.setEstado(Estados.APROBADO.name().toString());
						iconoMar = iconoService.iconoObtenerPorId(iconosVigia.getIdIconoVigiaApr());
					} else {
						if (estado.getNombre().equals("RECHAZADO")) {
							vigia.setEstado(Estados.RECHAZADO.name().toString());
							iconoMar = iconoService.iconoObtenerPorId(iconosVigia.getIdIconoVigiaRec());
						} else {
							if (estado.getNombre().equals("PENDIENTE")) {
								vigia.setEstado(Estados.PENDIENTE.name().toString());
								iconoMar = iconoService.iconoObtenerPorId(iconosVigia.getIdIconoVigiaPen());
							} else {
								return new ResponseEntity<String>("No se pudo realizar actualización, "
										+ "el estado que coloco no existe",HttpStatus.BAD_REQUEST);
							}
						}
					}
					if (iconoMar != null) {
						vigia.getMarcador().setIcono(iconoMar);
						vigiaService.vigiaActalizar(vigia);
					} else {
						return new ResponseEntity<String>("No se pudo realizar actualización, "
								+ "falta el icono del estado respectivo",HttpStatus.BAD_REQUEST);
					}
					return new ResponseEntity<String>("Actualización exitosa", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("No se pudo realizar actualización, "
							+ "no se han insertado los iconos por estado",HttpStatus.BAD_REQUEST);
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se pudo realizar actualización, "
					+ "revisar datos y reintentar",HttpStatus.BAD_REQUEST);
		}
	}
	
	private List<MultipartFile> getListMultimedias(MultipartFile multimediaO1, MultipartFile multimediaO2, MultipartFile multimediaO3, MultipartFile multimediaO4){
		List<MultipartFile> listMultimedia = new ArrayList<>();
		if(multimediaO1 != null) {
			listMultimedia.add(multimediaO1);
		}
		if(multimediaO2 != null) {
			listMultimedia.add(multimediaO2);
		}
		if(multimediaO3 != null) {
			listMultimedia.add(multimediaO3);
		}
		if(multimediaO4 != null) {
			listMultimedia.add(multimediaO4);
		}
		return listMultimedia;
		
	}

	/*@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/vigia/validarReporte/{idCapa}/{latitud}/{longitud}/{aliasReporte}/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<?> vigiaValidarReporte(@PathVariable("latitud") double latitud,
			@PathVariable("longitud") double longitud, @PathVariable("aliasReporte") String aliasReporte,
			@PathVariable("nombre") String nombre) {

		Vigia vigia = new Vigia();
		List<Coordenada> coordenadas = null;
		try {
			// TODO: obtener el radio de accion desde la contenedora, radio esta en 500
			// metros
			double radio = 0.5;
			coordenadas = coordenadaService.validacionReporteVigiaPorRadio(latitud, longitud, radio, aliasReporte,
					nombre);
			if (coordenadas.size() > 0) {
				//vigia.setMarcador(coordenadas.get(0).getMarcador());

				// if (vigia.getId() > 0) {
				return new ResponseEntity<Vigia>(vigia, HttpStatus.OK);
				// }
			} else {
				return new ResponseEntity<String>("No se encontraron reportes en radio de accion", HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<String>("Error realizando validación: " + e.getMessage(), HttpStatus.CONFLICT);
		}
	}*/


	/*public String reportarAmva(String url, String tipoPeticion, String usuario, String contrasena, String data) {
		String res = "";
		HttpURLConnection con = null;
		try {
			URL myurl = new URL(url);
			con = (HttpURLConnection) myurl.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("User-Agent", "Java client");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Content-Type", "application/json;utf-8");
			con.setRequestProperty("dataType", "json");
			con.setRequestMethod(tipoPeticion);

			JsonObject jsonUsuario = new JsonObject();
			JsonObject jsonRespuesta = new JsonObject();
			JsonObject jsonData = new JsonObject();
			JsonParser jsonParser = new JsonParser();

			jsonUsuario.addProperty("usuario", usuario);
			jsonUsuario.addProperty("password", contrasena);

			jsonData.add("ClienteRestData", jsonUsuario);
			jsonData.add("TramiteData", jsonParser.parse(data));

			byte[] postData = jsonData.toString().getBytes(StandardCharsets.UTF_8);

			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
				wr.write(postData);
			}
			StringBuilder content;
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				String line;
				content = new StringBuilder();

				while ((line = in.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}
			System.out.println(content.toString());
			if (content.toString() != null && content.toString().contains("codigoTramite")) {
				jsonRespuesta = (JsonObject) jsonParser.parse(content.toString());
				res = jsonRespuesta.get("codigoTramite").getAsString();
			}
		} catch (Exception e) {
			LOGGER.error("Error radicando el reporte en servicio del AMVA  ", e);
			res = null;
		} finally {
			con.disconnect();
		}
		return res;
	}*/

	/*public static String encodeToString(BufferedImage image, String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();
			imageString = Base64Coder.encodeLines(imageBytes);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}*/

	
}
