package co.gov.metropol.area247.contenedora.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraAplicacionRepository;
import co.gov.metropol.area247.contenedora.mapper.IContenedoraMapper;
import co.gov.metropol.area247.contenedora.model.Aplicacion;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.Recomendacion;
import co.gov.metropol.area247.contenedora.model.dto.AplicacionDto;
import co.gov.metropol.area247.contenedora.model.dto.AplicacionDtoSinListas;
import co.gov.metropol.area247.contenedora.model.dto.App;
import co.gov.metropol.area247.contenedora.model.dto.CapaMenuDto;
import co.gov.metropol.area247.contenedora.model.dto.RecomendacionDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraAplicacionService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;

@Service
public class IContenedoraAplicacionServiceImpl implements IContenedoraAplicacionService {
	
	@Autowired
	IContenedoraAplicacionRepository aplicacionDao;
	
	@Autowired
	IContenedoraCapaService capaService;
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Autowired
	@Qualifier("appMapper")
	IContenedoraMapper<Aplicacion, App> mapper;
	
	@Value("${iconos.server.url}")
	String urlIconos;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraAplicacionServiceImpl.class);
	
	@Override
	public Aplicacion aplicacionObtenerPorNombre(String nombre) {
		return aplicacionDao.findByNombre(nombre);
	}

	@Override
	public Aplicacion aplicacionObtenerPorId(Long aplicacionId) {
		return aplicacionDao.findOne(aplicacionId);
	}
	
	@Override
	public List<AplicacionDto> aplicacionDtoObtenerTodas(boolean contenedora) {
		Long idContenedora = 7L;
		if(contenedora) {
			idContenedora = 0L;
		}		
		List<AplicacionDto> appList = new ArrayList<AplicacionDto>();		
		for (AplicacionDto aplicacionDto : aplicacionDao.aplicacionDtoObtenerTodos()) {
			if(aplicacionDto.getId() != idContenedora) {
				Icono icono = aplicacionDto.getIcono();
				if(icono != null) {
					aplicacionDto.getIcono().setRutaLogo(urlIconos + icono.getId());
				}
				appList.add(aplicacionDto);
			}			
		}
		return appList;
	}

	@Override
	public List<Aplicacion> aplicacionObtenerTodas() {
		return (List<Aplicacion>) aplicacionDao.findAll();
	}
	
	@Override
	public boolean aplicacionActualizar(Aplicacion aplicacion) {
			//Se validan campos que no pueden estar en blanco
			if(!aplicacion.getNombre().equals("") && !aplicacion.getCodigoColor().equals("") && aplicacion.getIcono()!=null){
				try {
					aplicacion.setUltimaActualizacion(new Date());
					aplicacionDao.save(aplicacion);
					return true;
				}catch(Exception e) {
					return false;
				}
			}else {
				return false;
			}
	}

	@Override
	public List<AplicacionDto> obtenerMenu() {
		List<AplicacionDto> aplicaciones = new ArrayList<AplicacionDto>();
		
		for(Aplicacion aplicacion : aplicacionObtenerTodas())
		{
			List<CapaMenuDto> capas = new ArrayList<CapaMenuDto>();
			for (Capa capa : aplicacion.getCapas()){
				capas.add(capaMapper(capa));
			}
			
			if(aplicacion!=null) {
			    if(aplicacion.getId()==3L) {	
				    List<CapaMenuDto> capasAuxiliar = machetazoCapasDeAvistamientos(capas);
				    capas.clear();
				    capas.addAll(capasAuxiliar);
			    }
			}
														
			AplicacionDto aplicacionDto = aplicacionMapper(aplicacion);
			aplicacionDto.setCapas(capas);
			aplicacionDto.setRecomendaciones(recomendacionMapper(aplicacion.getRecomendaciones()));
			aplicaciones.add(aplicacionDto);
		}
		return aplicaciones;
	}
	
	private AplicacionDto aplicacionMapper(Aplicacion app) {
		AplicacionDto dto = new AplicacionDto();
		dto.setId(app.getId());
		dto.setNombre(app.getNombre());
		dto.setCodigoColor(app.getCodigoColor());
		dto.setCodigoToggle(app.getCodigoToggle());
		dto.setActivo(app.isActivo());
		dto.setRadioAccion(app.getDefaultRadius());
		dto.setMinRadius(app.getMinRadius());
		dto.setMaxRadius(app.getMaxRadius());
		dto.setOtherPrefs(app.getOtherPrefs());
		dto.setUltimaActualizacion(app.getUltimaActualizacion());
		dto.setRutaIcono(urlIconos+app.getIcono().getId());
		return dto;
	}
	
	private CapaMenuDto capaMapper(Capa capa) {
		CapaMenuDto dto = new CapaMenuDto(
				capa.getId(), capa.getNombre(), capa.isActivo(),
				capa.isFavorito(),capa.getUltimaActualizacion(),
				capa.getIcono()!=null?capa.getIcono().getId():null,
				capa.getIconoMarcador()!=null?capa.getIconoMarcador().getId():null,
						capa.getTipoCapa().getNombre()
				);
		dto.setRutaIconoCapa(urlIconos+dto.getRutaIconoCapa());
		if("null".equals(dto.getRutaIconoMarker())) {
			dto.setRutaIconoMarker("");
		}else {
			dto.setRutaIconoMarker(urlIconos+dto.getRutaIconoMarker());
		}
		return dto;
	}
	
	private List<RecomendacionDto> recomendacionMapper(List<Recomendacion> entities) {
		List<RecomendacionDto> models = new ArrayList<>();
		entities.forEach(entity->{
			RecomendacionDto model = new RecomendacionDto();
			model.setNombre(entity.getNombre());
			model.setDescripcion(entity.getDescripcion());
			model.setTipo(entity.getTipo());
			models.add(model);
		});
		return models;
	}

	@Override
	public AplicacionDto aplicacionDtoObtenerMenu(Long aplicacionId) {
		return aplicacionDao.aplicacionDtoObtenerMenu(aplicacionId);
	}

	@Override
	public AplicacionDto aplicacionDtoObtenerPreferenciasUsuario(Long aplicacionId) {
		return aplicacionDao.aplicacionDtoObtenerPreferenciasUsuario(aplicacionId);
	}

	@Override
	public AplicacionDto obtenerAplicacionDtoPorIdCategoria(Long idCategoria) {
		return aplicacionDao.obtenerAplicacionDtoPorIdCategoria(idCategoria);
	}

	

	@Override
	public AplicacionDtoSinListas aplicacionDtoSinListasObtenerPorId(Long idAplicacion) 
			throws Exception {
		AplicacionDtoSinListas aplicacionSeleccionada = aplicacionDao
				.aplicacionDtoSinListasObtenerPorId(idAplicacion);		
		if(aplicacionSeleccionada==null){
			LOGGER.info("No se encuentra la capa con id: "+idAplicacion);
		}else {
			if(aplicacionSeleccionada.getIcono()!=null) {
				aplicacionSeleccionada.getIcono().setRutaLogo(urlIconos + aplicacionSeleccionada.getIcono().getId());
			}
		}
		return aplicacionSeleccionada;
	}

	@Override
	public List<App> getApps() throws Exception {
		List<Aplicacion> applications = (List<Aplicacion>) aplicacionDao.findAll();
		List<App> apps = new ArrayList<>();
		applications.forEach(application->{
			App dto = mapper.entityToModel(application);
			dto.setRutaIcono(urlIconos+application.getIcono().getId());
			apps.add(dto);
		});
		return apps;
	}
	
	@Override
	public String getDefaultAppsPreferences() throws Exception {
		List<Aplicacion> applications = (List<Aplicacion>) aplicacionDao.findAll();
		String jsonPrefs = " {\"_appsPreferences\":[";
		jsonPrefs += applications.stream()
			.map(application -> {
				String layersPrefs = "[";
				layersPrefs += application.getCapas().stream()
					.map(layer -> {
						return "{"
								+ "\"_id\":" + layer.getId()
								+ ", \"_active\":" + layer.isActivo()
								+ "}"; 
					}).reduce("",
						(acc, layerPrefs) -> {
							if (!acc.equals("")) acc += ",";
							acc += layerPrefs;
							return acc;
					});
				layersPrefs += "]";
				
				return "{"
						+ "\"_id\":" + application.getId()
						+ ", \"_active\":" + application.isActivo()
						+ ", \"_notification\":true"
						+ ", \"_radius\":" + application.getDefaultRadius()
						+ ", \"_minRadius\":" + application.getMinRadius()
						+ ", \"_maxRadius\":" + application.getMaxRadius()
						+ ", \"_layersPreferences\":" + layersPrefs
						+ "}";
			}).reduce("", 
				(acc, applicationPrefs) -> {
					if (!acc.equals("")) acc += ",";
					acc += applicationPrefs;
					return acc;
			});
		jsonPrefs += "]}";
		return jsonPrefs;
	}
	
	@Override
	public List<String> aplicacionDtoObtenerNombres() throws Exception {
		List<String> listaNombres = aplicacionDao.aplicacionDtoObtenerNombres();
		return listaNombres;
	}
	
	public List<CapaMenuDto> machetazoCapasDeAvistamientos(List<CapaMenuDto> capas){
		List<CapaMenuDto> capasAuxiliar = new ArrayList<CapaMenuDto>();
		for (CapaMenuDto capa : capas) {
			if(capa.getId()==223L) {
				capasAuxiliar.add(capa);
			}			
		}
		for (CapaMenuDto capa : capas) {
			if(capa.getId()==4L) {
				capasAuxiliar.add(capa);
			}			
		}
		for (CapaMenuDto capa : capas) {
			if(capa.getId()==224L) {
				capasAuxiliar.add(capa);
			}			
		}
		for (CapaMenuDto capa : capas) {
			if(capa.getId()==211L) {
				capasAuxiliar.add(capa);
			}			
		}
		for (CapaMenuDto capa : capas) {
			if( (capa.getId()!=223L) && (capa.getId()!=4L) &&
				(capa.getId()!=224L) && (capa.getId()!=211L) ) {
				capasAuxiliar.add(capa);
			}			
		}
		return capasAuxiliar;
	}
}

