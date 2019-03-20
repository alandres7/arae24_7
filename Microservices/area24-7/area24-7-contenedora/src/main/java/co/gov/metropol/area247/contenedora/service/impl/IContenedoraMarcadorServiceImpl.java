package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraMarcadorRepository;
import co.gov.metropol.area247.contenedora.model.Avistamiento;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.model.VentanaInformacion;
import co.gov.metropol.area247.contenedora.service.IContenedoraAvistamientoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.contenedora.service.IContenedoraVentanaInformacionService;

@Service
public class IContenedoraMarcadorServiceImpl implements IContenedoraMarcadorService {

	@Autowired
	IContenedoraMarcadorRepository marcadorDao;
	
	@Autowired
	IContenedoraAvistamientoService avistamientoService;
	
	@Autowired
	IContenedoraVentanaInformacionService ventanaInformacionService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraMarcadorServiceImpl.class);
	
	@Override
	public boolean marcadorCrear(Marcador marcador) {
		try{
			marcador.setActivo(Boolean.TRUE);
			marcadorDao.save(marcador);
			LOGGER.info("Se creo marcador correctamente con id: " + marcador.getId());
			return true;
		} catch (Exception e) {
			LOGGER.error("No se ha podido guardar el marcador ; " + e); 
			return false;
		}
	}
	
	@Override
	public boolean marcadorActualizar(Marcador marcador) {
		try{
			marcadorDao.save(marcador);
			LOGGER.info("Se actualizo marcador correctamente con id: " + marcador.getId());
			return true;
		} catch (Exception e) {
			LOGGER.error("No se ha podido actualizar el marcador ; " + e); 
			return false;
		}
	}

	@Override
	public Marcador obtenerMarcadorPorId(Long idMarcador) {
		return marcadorDao.findOne(idMarcador);
	}

	@Override
	public Marcador obtenerMarcadorPorNombre(String nombreMarcador) {
		return marcadorDao.findByNombre(nombreMarcador);
	}

	@Override
	public List<Marcador> obtenerMarcadorPorCodigo(int codigoMarcador, Long idCapa) {
		LOGGER.info("Codigo a buscar: "+codigoMarcador);
		return marcadorDao.encontrarPorCodigoYCapa(codigoMarcador,idCapa);
	}

	@Override
	public boolean marcadorEliminar(Long idMarcador) {
		try {
			marcadorDao.delete(idMarcador);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean eliminarMarcadores(List<Marcador> marcadores) {
		try {
			marcadorDao.delete(marcadores);
			return Boolean.TRUE;
		}catch(Exception e) {
			return Boolean.FALSE;
		}
	}

	@Override
	public Marcador obtenerMarcadorPorCategoriaCodigo(Long idCategoria, int codigo) {
		return marcadorDao.obtenerMarcadorPorCategoriaCodigo(idCategoria, codigo);
	}

	@Override
	public Marcador obtenerMarcadorPorSubCategoriaCodigo(Long idSubCategoria, int codigo) {
		return marcadorDao.obtenerMarcadorPorSubCategoriaCodigo(idSubCategoria, codigo);
	}
	
	
	@Override
	public List<Marcador> obtenerPorCapaSinMunicipio(Long idCapa) {
		return marcadorDao.obtenerPorCapaSinMunicipio(idCapa);
	}
	
	
	@Override
	public boolean eliminarMarcadoresPorIdCapa(Long idCapa) {					
		try {
			List<Marcador> marcadores = marcadorDao.obtenerPorIdCapa(idCapa);
			if(marcadores!=null) {				
				for (Marcador marcador : marcadores) {															
					VentanaInformacion ventanaInfo = marcador.getVentanaInformacion();
					if(ventanaInfo!=null) {	
						marcador.setVentanaInformacion(null);
					    marcadorActualizar(marcador);
						ventanaInformacionService.ventanaInformacionEliminar(ventanaInfo.getId());
					}
					Avistamiento avistamiento = avistamientoService.avistamientoPorIdMarcador(marcador.getId());
					if(avistamiento!=null) {
						avistamientoService.avistamientoEliminar(avistamiento.getId());
					}
					marcadorDao.delete(marcador.getId());
				}	
			}
			return true;
		}catch(Exception e) {
			return false;
		}		
	}
		
	@Override
	public boolean eliminarMarcadoresPorIdCategoria(Long idCategoria) {
		try {			
			List<Marcador> marcadores = marcadorDao.obtenerPorIdCategoria(idCategoria);
			if(marcadores!=null) {
				for (Marcador marcador : marcadores) {										
					VentanaInformacion ventanaInfo = marcador.getVentanaInformacion();
					if(ventanaInfo!=null) {
						marcador.setVentanaInformacion(null);
					    marcadorActualizar(marcador);
						ventanaInformacionService.ventanaInformacionEliminar(ventanaInfo.getId());
					}	
					Avistamiento avistamiento = avistamientoService.avistamientoPorIdMarcador(marcador.getId());
					if(avistamiento!=null) {
						avistamientoService.avistamientoEliminar(avistamiento.getId());
					}
					marcadorDao.delete(marcador.getId());					
				}																		
			}
			return true;
		}catch(Exception e) {
			return false;
		}		
	}

}
