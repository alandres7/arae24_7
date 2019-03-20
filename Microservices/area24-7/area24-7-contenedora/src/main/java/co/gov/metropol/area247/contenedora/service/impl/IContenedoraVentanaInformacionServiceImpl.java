package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraVentanaInformacionRepository;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.Multimedia;
import co.gov.metropol.area247.contenedora.model.VentanaInformacion;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMultimediaService;

@Service
public class IContenedoraVentanaInformacionServiceImpl
		implements co.gov.metropol.area247.contenedora.service.IContenedoraVentanaInformacionService {

	@Autowired
	private IContenedoraVentanaInformacionRepository ventanaInformacionDao;
	
	@Autowired
	private IContenedoraMultimediaService multimediaService;
	
	@Autowired
	private IContenedoraIconoService iconoService;
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(IContenedoraVentanaInformacionServiceImpl.class);
	
	@Override
	public VentanaInformacion ventanaInformacionCrear(VentanaInformacion ventanaInformacion) {
		try {
			VentanaInformacion ventanaInformacionAux = ventanaInformacionDao.findByNombre(ventanaInformacion.getNombre());
			if (ventanaInformacionAux!=null){
				ventanaInformacion.setId(ventanaInformacionAux.getId());
			}
			return ventanaInformacionDao.save(ventanaInformacion);
		} catch (Exception e) {
			LOGGER.error("Error guardando ventanaInformacion id: " + ventanaInformacion.getNombre() +",  excepcion: " + e);
			return null;
		}
		
	}

	@Override
	public boolean ventanaInformacionActualizar(VentanaInformacion ventanaInformacion) {
		try{
			ventanaInformacionDao.save(ventanaInformacion);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public List<VentanaInformacion> ventanaInformacionObtenerTodas() {
		return (List<VentanaInformacion>) ventanaInformacionDao.findAll();
	}

	@Override
	public VentanaInformacion ventanaInformacionObtenerPorNombre(String nombreVentanaInformacion) {
		return  ventanaInformacionDao.findByNombre(nombreVentanaInformacion);
	}

	@Override
	public VentanaInformacion ventanaInformacionObtenerPorId(Long idVentanaInformacion) {
		return  ventanaInformacionDao.findOne(idVentanaInformacion);
	}

	@Override
	public boolean ventanaInformacionEliminar(Long idVentanaInformacion) {
		try{
			int cantidadReferencias = ventanaInformacionDao.getCantidadReferenciasMarcador(idVentanaInformacion);
			if(cantidadReferencias<=1) {
			    VentanaInformacion ventanaInfo = ventanaInformacionDao.findOne(idVentanaInformacion);
			    if (ventanaInfo != null) {				
				    Icono icono = ventanaInfo.getIcono();
				    if(icono!=null) {
				        iconoService.iconoEliminar(icono.getId());
				    }					
				    Multimedia multimedia = ventanaInfo.getMultimedia();
				    if(multimedia != null) {
					    multimediaService.multimediaEliminar(multimedia.getId());					
				    }				
			        ventanaInformacionDao.delete(idVentanaInformacion);
			        return true;
			    }else {
				    return false;
			    }
			}else {
				return true;
			}
		}catch(Exception e) {
			return false;
		}
		
	}

}
