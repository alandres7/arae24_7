package co.gov.metropol.area247.contenedora.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraCapaRepository;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.dto.CapaDto;
import co.gov.metropol.area247.contenedora.model.dto.CategoriaDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraAplicacionService;
import co.gov.metropol.area247.contenedora.service.IContenedoraArbolDecisionService;
import co.gov.metropol.area247.contenedora.service.IContenedoraAvistamientoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoEstadoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.contenedora.service.IContenedoraTipoCapaService;

@Service
public class IContenedoraCapaServiceImpl implements IContenedoraCapaService {

	@Autowired
	IContenedoraCapaService capaService;

	@Autowired
	IContenedoraCapaRepository capaDao;

	@Autowired
	IContenedoraIconoService iconoService;

	@Autowired
	IContenedoraTipoCapaService tipoCapaService;

	@Autowired
	IContenedoraCategoriaService categoriaService;

	@Autowired
	IContenedoraAplicacionService aplicacionService;
	
	@Autowired
	IContenedoraMarcadorService marcadorService;
	
	@Autowired
	IContenedoraIconoEstadoService iconoEstadoService;
	
	@Autowired
	IContenedoraAvistamientoService avistamientoService;
	
	@Autowired
	IContenedoraArbolDecisionService arbolDecisionService;
	
	@Value("${iconos.server.url}")
	String urlIconos;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraCapaServiceImpl.class);

	@Override
	public boolean capaCrear(Capa capa, Long idAplicacion) {
		try {
			capa.setUltimaActualizacion(new Date());
			capa.setAplicacion(aplicacionService.aplicacionObtenerPorId(idAplicacion));
			capaDao.save(capa);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Capa capaGetByNombre(String nombre) {
		return capaDao.findByNombre(nombre);
	}

	@Override
	public Capa capaGetById(Long idCapa) {
		Capa aux = capaDao.findOne(idCapa);
		return aux;
	}

	@Override
	public boolean capaActualizar(Capa capa) {
		try {
//			capa.setUltimaActualizacion(new Date());
			capaDao.save(capa);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public CapaDto capaDtoObtenerPorId(Long idCapa) throws Exception {
		CapaDto capaDto = capaDao.capaDtoObtenerPorId(idCapa);		
		if (capaDto == null) {
			LOGGER.info("No se encuentra la capa con id: " + idCapa);
		}else {
			if (capaDto.getIcono() == null) {
				capaDto.setIcono(new Icono());
			}else {
				capaDto.getIcono().setRutaLogo(urlIconos + capaDto.getIcono().getId());
			}
			if (capaDto.getIconoMarcador() == null) {
				capaDto.setIconoMarcador(new Icono());
			}else {
				capaDto.getIconoMarcador().setRutaLogo(urlIconos + capaDto.getIconoMarcador().getId());
			}
		}
		return capaDto;
	}

	@Override
	public List<CapaDto> capaDtoObtenerPorIdAplicacion(Long idAplicacion) throws Exception {
		List<CapaDto> capas = capaDao.capaDtoObtenerPorIdAplicacion(idAplicacion);
		if (capas.isEmpty()) {
			LOGGER.info("No se encuentran capas por id de aplicacion: " + idAplicacion);
		}else {
			for (CapaDto capaDto : capas) {
				if (capaDto.getIcono() == null) {
					capaDto.setIcono(new Icono());
				}else {
					capaDto.getIcono().setRutaLogo(urlIconos + capaDto.getIcono().getId());
				}
				if (capaDto.getIconoMarcador() == null) {
					capaDto.setIconoMarcador(new Icono());
				}else {
					capaDto.getIconoMarcador().setRutaLogo(urlIconos + capaDto.getIconoMarcador().getId());
				}	
			}
		}
		return capas;
	}

	@Override
	public List<String> capaDtoObtenerNombres() throws Exception {
		List<String> listaNombres = capaDao.capaDtoObtenerNombres();
		return listaNombres;
	}

	@Override
	public boolean capaEliminar(Long idCapa) {
		try {
			CapaDto capa = capaDao.capaDtoObtenerPorId(idCapa);
			
			List<CategoriaDto> categorias = categoriaService.categoriaDtoObtenerPorIdCapa(idCapa);
			for (CategoriaDto categoria : categorias) {
				categoriaService.categoriaEliminar(categoria.getId());
			}			
			Icono icono = capa.getIcono();
			if(icono!=null) {
			    iconoService.iconoEliminar(icono.getId());
			}				
			Icono iconoMar = capa.getIconoMarcador();
			if(iconoMar!=null) {
			    iconoService.iconoEliminar(iconoMar.getId());
			}			
			iconoEstadoService.iconoEstadoEliminarByCapa(idCapa,0);
			iconoEstadoService.iconoEstadoEliminarByCapa(idCapa,1);
			iconoEstadoService.iconoEstadoEliminarByCapa(idCapa,2);
			
			marcadorService.eliminarMarcadoresPorIdCapa(idCapa);
			
			arbolDecisionService.arbolEliminarCapas(idCapa);
			
			capaDao.delete(idCapa);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	@Override
	public List<Capa> obtenerCappasPorIdAplicacion(Long idAplicacion) throws Exception {
		try {
			return capaDao.obtenerCapaPorIdAplicacion(idAplicacion);
		} catch (Exception e) {
			LOGGER.info("No se encuentran capas por id de aplicacion: " + idAplicacion);
			throw new Exception("Error al obtener las capas por id aplicacion", e);
		}
	}
	
	public List<Capa> capasFichaCaracterizacion(){
		try {
			return capaDao.findByFichaCaracterizacion(Boolean.TRUE);
		}catch(Exception e) {
			LOGGER.error("Error recuperando capas asociadas a la ficha de caracterización "+e);
			return null;
		}
	}
	
	@Override
	public List<String> capaDtoObtenerUrls() throws Exception {
		List<String> listaUrls = capaDao.capaDtoObtenerUrls();
		return listaUrls;
	}

}
