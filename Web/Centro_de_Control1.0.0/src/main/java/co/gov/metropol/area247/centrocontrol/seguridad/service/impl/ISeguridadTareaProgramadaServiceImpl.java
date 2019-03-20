package co.gov.metropol.area247.centrocontrol.seguridad.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.model.TareaProgramada;
import co.gov.metropol.area247.centrocontrol.seguridad.dao.ISeguridadTareaProgramadaRepository;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadTareaProgramadaService;

@Service
public class ISeguridadTareaProgramadaServiceImpl implements ISeguridadTareaProgramadaService {

	@Autowired
	ISeguridadTareaProgramadaRepository tareaProgramadaDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ISeguridadTareaProgramadaServiceImpl.class);

	@Override
	public List<TareaProgramada> tareaProgramadaObtenerTodos() throws Exception {
		List<TareaProgramada> tareaProgramada = null;
		try {
			tareaProgramada = (List<TareaProgramada>) tareaProgramadaDao.findAll();
			if (tareaProgramada.isEmpty()) {
				LOGGER.info("No se encuentran Tareas Programadas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tareaProgramada;
	}

	@Override
	public TareaProgramada tareaProgramadaObtenerPorId(Long id) throws Exception {
		TareaProgramada tareaProgramada = tareaProgramadaDao.findOne(id);
		if (tareaProgramada == null) {
			LOGGER.info("No se encuentra una Tarea Programada con id: " + id);
		}
		return tareaProgramada;
	}

	@Override
	public boolean tareaProgramadaGuardar(TareaProgramada nuevaTareaProgramada){
		try {
			tareaProgramadaDao.save(nuevaTareaProgramada);
			LOGGER.info("Tarea Programada guardada con exito");
			return true;
		} catch (Exception e) {
			LOGGER.error("No se pudo guardar la Tarea Programada. Error: " + e);
			return false;
		}		
		
	}

	@Override
	public boolean tareaProgramadaBorrar(Long id) {
		try {
			tareaProgramadaDao.delete(id);
			LOGGER.info("Tarea Programada borrada con id: " + id);
			return true;
		} catch (Exception e) {
			LOGGER.error("No se pudo borrar la Tarea Programada con id: " + id + " Error: " + e);
			return false;
		}
	}
		
	@Override
	public List<TareaProgramada> findByActivo(Boolean activo) throws Exception {
		List<TareaProgramada> tareaProgramada = null;
		try {
			tareaProgramada = (List<TareaProgramada>) tareaProgramadaDao.findByActivo(activo);
			if (tareaProgramada.isEmpty()) {
				LOGGER.info("No se encuentran Tareas Programadas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tareaProgramada;
	}


}
