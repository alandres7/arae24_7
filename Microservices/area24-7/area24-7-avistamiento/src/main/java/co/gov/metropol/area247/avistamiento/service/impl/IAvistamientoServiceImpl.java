package co.gov.metropol.area247.avistamiento.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.maps.model.LatLng;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.avistamiento.dao.IAvistamientoAvistamientoJDBCRepository;
import co.gov.metropol.area247.avistamiento.dao.IAvistamientoAvistamientoRepository;
import co.gov.metropol.area247.avistamiento.model.Avistamiento;
import co.gov.metropol.area247.avistamiento.model.dto.AvistamientoDto;
import co.gov.metropol.area247.avistamiento.model.dto.FloraDto;
import co.gov.metropol.area247.avistamiento.model.enums.Colores;
import co.gov.metropol.area247.avistamiento.model.enums.Estados;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoAvistamientoService;
import co.gov.metropol.area247.contenedora.dao.IContenedoraMultimediaRepository;
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
import co.gov.metropol.area247.contenedora.service.IContenedoraVentanaInformacionService;
import co.gov.metropol.area247.core.domain.constants.Constants;
import co.gov.metropol.area247.core.svc.ICoreMarkerSvc;
import co.gov.metropol.area247.core.util.GeometryUtil;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;
import io.reactivex.Observable;

@Service
public class IAvistamientoServiceImpl implements IAvistamientoAvistamientoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IAvistamientoServiceImpl.class);
	
	private static final String ARROBA = "@";
	private static final String SIGHTING_TYPE_FLORA = "Flora";
	private static final Long ID_CAPA = 211L;
	
	@Value("${dominio.url.ldap}")
	private String dominioUrl;
	
	@Value("${iconos.server.url}")
	String urlIconos;
	
	@Value("${media.server.url}")
	String urlMedias;
	
	@Autowired
	private IAvistamientoAvistamientoRepository avistamientoDao;
	
	@Autowired
	private IAvistamientoAvistamientoJDBCRepository avistamientoJDBCDao;
	
	@Autowired
	private ISeguridadUsuarioService usuarioService;
	
	@Autowired
	private IContenedoraMarcadorService marcadorSvc;
	
	@Autowired
	private IContenedoraVentanaInformacionService ventanaInfoSvc;
	
	@Autowired
	private IContenedoraMultimediaRepository multimediaDao;
	
	@Autowired
	private IContenedoraCategoriaService categoriaSvc;
	
	@Autowired
	private IContenedoraCapaService capaSvc;
	
	@Autowired
	private IContenedoraIconoEstadoService iconoEstadoSvc;
	
	@Autowired
	@Qualifier("markerConverter")
	private ICoreMarkerSvc markerConverter;
	
	@Override
	public String reportSighting(
			String nombreComun,
			String descripcion,
			String nombreCientifico,
			MultipartFile multimedia,
			String username,
			String nivelCapa,
			Long idCapaCategoria,
			Float latitud,
			Float longitud
			) {
				
				return nivelCapa;
		
		
	}
	
	@Override
	public boolean avistamientoReportar(Avistamiento avistamiento) {
		try {
			avistamientoDao.save(avistamiento);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean avistamientoActualizar(Avistamiento avistamiento) {
		try {
			avistamientoDao.save(avistamiento);
			return true;
		} catch (Exception e) {
			LOGGER.error("error al actualizar el avistamiento con avistamiento id --{}{}",e);
			return false;
		}
	}

	@Override
	public List<AvistamientoDto> avistamientoObtenerTodos() {				
		return avistamientoJDBCDao.buscarTodosAvistamientos();	
	}
	

	@Override
	public List<AvistamientoDto> avistamientoPorIdUsuario(Long idUsuario) {
		return avistamientoJDBCDao.obtenerAvistamientoPorIdUsuario(idUsuario);			
	}

	@Override
	public List<AvistamientoDto> avistamientoPorEstado(int estado) {
		return avistamientoJDBCDao.obtenerAvistamientoPorEstado(estado);		
	}

	@Override
	public AvistamientoDto buscarAvistamientoPorIdAvistamientoOIdMarcador(Long idAvistamiento, Long idMarcador) {
		AvistamientoDto avistamientoDto = avistamientoJDBCDao.buscarAvistamientoPorIdAvistamientoOIdMarcador(idAvistamiento, idMarcador);		
		if(avistamientoDto!=null) {
			avistamientoDto.setRutaIcono(urlIconos + avistamientoDto.getIdIcono());
			if(avistamientoDto.getRutaMultimedia().contains("multimedia")) {
				avistamientoDto.setRutaMultimedia(urlMedias + avistamientoDto.getIdMultimedia());			
			}
			return avistamientoDto;
		}else {
			return null;
		}
	}
	
	@Override
	public Avistamiento avistamientoPorId(Long idAvistamiento) {
	    Avistamiento avistamientoDto = avistamientoDao.findOne(idAvistamiento);
		return avistamientoDto;
	}
	
	@Override
	public Integer cantidadAvistamientoPorEstado(int estado){
		return avistamientoJDBCDao.cantidadAvistamientoPorEstado(estado);
	}
	
	@Override
	public List<AvistamientoDto> obtenerAvistamientoPorParametros(String nombreComun, String nombreCientifico, 
			                                               String municipio, String tipoAvistamiento){
		
		return avistamientoJDBCDao.obtenerAvistamientoPorParametros(nombreComun,nombreCientifico, 
                                                                    municipio,tipoAvistamiento);	
	}
	
	@Override
	public Observable<Integer> obtenerNumeroAvistamientoPorTipoAvistamientoOrEstadoOrFecha(String tipoAvistamiento, 
			String idCategoria, Integer estado, LocalDate fechaInicio, LocalDate fechaFin, 
			boolean soloComHis, boolean conComenDeHis){
		return avistamientoJDBCDao.obtenerNumeroAvistamientoPorTipoAvistamientoOrEstadoOrFecha(tipoAvistamiento,idCategoria,
				estado, fechaInicio, fechaFin, soloComHis, conComenDeHis);	
	}
	
	
	@Override
	public List<AvistamientoDto> obtenerAvistamientoPorCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta , Long idCategoria){		
		return avistamientoJDBCDao.obtenerAvistamientoPorCapaFecha(idCapa, desde, hasta, idCategoria);	
	}
	
	@Override
	public List<AvistamientoDto> getPaginatedAvistamientoPorCapaFecha(Long idCapa, LocalDate fechaDesde, LocalDate fechaHasta,
			Long idCategoria, String whereClause, String orderClause, int pageStart, int pageSize, String estadosList, 
			boolean soloComHis, boolean conComenDeHis) {
		return avistamientoJDBCDao.getAvistamientoPaginatedPorCapaFecha(idCapa, fechaDesde, fechaHasta, idCategoria, 
				whereClause, orderClause, pageStart, pageSize, estadosList, soloComHis, conComenDeHis);
	}
	
	@Override
	public int getFilteredAvistamientoPorCapaFecha(Long idCapa, LocalDate fechaDesde, LocalDate fechaHasta,
			Long idCategoria, String whereClause, String estadosList, boolean soloComHis, boolean conComenDeHis) {
		return avistamientoJDBCDao.getNumFilteredAvistamXCapaFecha(idCapa, fechaDesde, fechaHasta, 
				idCategoria, whereClause, estadosList, soloComHis, conComenDeHis);
	}
	
	@Override
	public int getNumAvistamXCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta , Long idCategoria, 
			String estadosList, boolean soloComHis, boolean conComenDeHis) {
		return avistamientoJDBCDao.getNumAvistamXCapaFecha(idCapa, desde, hasta, idCategoria, estadosList, soloComHis, conComenDeHis);
	}
	

	@Override
	public boolean avistamientoEliminar(Long idAvistamiento) {
		try {
			Avistamiento avistamiento = avistamientoDao.findOne(idAvistamiento);
			if(avistamiento!=null) {
				Marcador marcador = avistamiento.getMarcador();
				if(marcador!=null) {
					marcadorSvc.marcadorEliminar(marcador.getId());
				}							
				avistamientoDao.delete(idAvistamiento);
				return true;				
			}else {
				return false;
			}	
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean registerFloraAsSighting(int limInf, int limSup, Long superAdminID, Capa capa, Icono  iconoEstado) {
		try {
			
			List<FloraDto> floraInventory = avistamientoJDBCDao.getFloraInventory(limInf, limSup);
			LOGGER.error("Limites: "+limInf+" - "+limSup);
			if (floraInventory != null) {
				floraInventory.forEach(vegetable->{
					Marcador marcador = new Marcador();
					VentanaInformacion ventanaInformacion = new VentanaInformacion();
					Multimedia multimedia = new Multimedia();
							Avistamiento avistamiento = new Avistamiento();
							avistamiento.setIdUsuario(superAdminID);
							avistamiento.setNombreComun(vegetable.getNombreComun());
							avistamiento.setDescripcion(vegetable.getNombreComun());
							avistamiento.setNombreCientifico(vegetable.getNombreCientifico());
							avistamiento.setEstado(Estados.APROBADO.getEstado());
							avistamiento.setFechaCreacion(new Date());
							avistamiento.setTipoAvistamiento(SIGHTING_TYPE_FLORA);
							avistamiento.setTipoEspecie(vegetable.getTipoArbol());
							LatLng punto = 
									markerConverter.markerConvertSpatialReference(vegetable.getCoordenadaY(), vegetable.getCoordenadaX());
							Point coordenadaPunto = GeometryUtil.obtenerPunto(punto.lat, punto.lng);
							marcador.setCoordenadaPunto(coordenadaPunto);
									//categoriaSvc.categoriaObtenerPorNombre(vegetable.getTipoArbol());
							marcador.setIcono(iconoEstado);
							marcador.setNombre(vegetable.getNombreComun());
							marcador.setPoligono(Boolean.FALSE);
							marcador.setActivo(Boolean.TRUE);
							multimedia.setRuta(vegetable.getFoto());
							if(avistamientoReportar(avistamiento)) {
								multimediaDao.save(multimedia);
								ventanaInformacion = 
										ventanaInfoSvc.ventanaInformacionCrear(cargarVentanaInformacion(
												marcador.getIcono(),
												vegetable.getNombreComun(),
												vegetable.getNombreCientifico(),
												marcador.getNombre(),
												multimedia,
												vegetable.getNombreComun()
												));
								//ventanaInfoSvc.ventanaInformacionCrear(ventanaInformacion);
								marcador.setVentanaInformacion(ventanaInformacion);
//								marcador.setCategoria(categoria);
								marcador.setCapa(capa);
								marcadorSvc.marcadorCrear(marcador);
								avistamiento.setMarcador(marcador);
								avistamientoActualizar(avistamiento);
								multimedia = null;
								avistamiento = null;
								
							}
				});
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("ERRORR",e);
		}
		return Boolean.FALSE;
	}
	
	@Override
	public int getFloraInventorySize() throws Exception {
		try {
			return avistamientoJDBCDao.countFloraInventory();
		}catch(Exception e) {
			LOGGER.error("Exception tamaño inventario flora ", e);
			throw new Exception("Exception tamaño inventario flora ", e);
		}
		
	}
	
	@Override
	public int getFloraInventoryRegisteredSize() throws Exception {
		try {
			return avistamientoJDBCDao.countFloraInventoryRegistered();
		}catch(Exception e) {
			LOGGER.error("Exception tamaño inventario flora ", e);
			throw new Exception("Exception tamaño inventario flora ", e);
		}
		
	}
	
	@Override
	public boolean registerFloraInventoryAsSighting() {
		int numRegistros = 0;
		int offset = 500;
		// 15000
		try {
			numRegistros = getFloraInventorySize();
			int limInf = getFloraInventoryRegisteredSize();
			limInf++;
			
			System.out.println("Número de árboles en el inventario de flora: "+numRegistros);
//			numRegistros = 500;
//			Categoria categoria = categoriaSvc.categoriaObtenerPorId(ID_CATEGORIA);
			Capa capa = capaSvc.capaGetById(ID_CAPA);
			Icono iconoEstado = iconoEstadoSvc
					.iconoEstadoPorCapaEstado(capa.getId(), Estados.APROBADO.getEstado()).getIcono();

			StringBuilder superAdminUsername = new StringBuilder(Constants.SUPER_ADMIN).append(ARROBA)
					.append(dominioUrl);
			Usuario superAdmin = usuarioService.usuarioObtenerPorUsername(superAdminUsername.toString());
			if(superAdmin == null) {
				superAdminUsername = new StringBuilder(Constants.SUPER_ADMIN_AMVA).append(ARROBA)
						.append(dominioUrl);
				superAdmin = usuarioService.usuarioObtenerPorUsername(superAdminUsername.toString());
			}
			Long superAdminID = superAdmin.getId();
//		List<Avistamiento> avistamientosSuperAdmin = avistamientoDao.findByIdUsuario(superAdminID);
//		avistamientosSuperAdmin.forEach(sighting -> {
//			multimediaDao.delete(sighting.getMarcador().getVentanaInformacion().getMultimedia().getId());
//			ventanaInfoSvc.ventanaInformacionEliminar(sighting.getMarcador().getVentanaInformacion().getId());
//			marcadorSvc.marcadorEliminar(sighting.getMarcador().getId());
//			sighting.setMarcador(new Marcador());
//		});
//		avistamientoDao.delete(avistamientosSuperAdmin);
			if (numRegistros > 0) {
				//int limInf = 18878;
				int limSup = 0;
				while (limSup <= numRegistros) {
					limSup = limInf + offset - 1;
					if (limSup > numRegistros) {
						limSup = numRegistros;
					}
					registerFloraAsSighting(limInf, limSup, superAdminID, capa, iconoEstado);
					limInf = ++limSup;
				}
			}
			return Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("eRROR ", e);
		}
		return Boolean.FALSE;
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

}
