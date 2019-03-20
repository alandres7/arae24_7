package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlAfectacionRepository;
import co.gov.metropol.area247.centrocontrol.model.Afectacion;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAfectacionService;

@Service
public class ICentroControlAfectacionServiceImpl implements ICentroControlAfectacionService {

	@Autowired
	ICentroControlAfectacionRepository afectacionDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlAfectacionServiceImpl.class);

	@Override
	public List<Afectacion> afectacionObtenerTodos() throws Exception {
		List<Afectacion> afectacion = null;
		try {
			afectacion = (List<Afectacion>) afectacionDao.findAll();
			if (afectacion.isEmpty()) {
				LOGGER.info("No se encuentran afectaciones");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return afectacion;
	}

	@Override
	public Afectacion afectacionObtenerPorId(Long id) throws Exception {
		Afectacion afectacion = afectacionDao.findOne(id);
		if (afectacion == null) {
			LOGGER.info("No se encuentra un recurso con id: " + id);
		}
		return afectacion;
	}

	@Override
	public Afectacion afectacionGuardar(Afectacion nuevaAfectacion) throws Exception {
		Afectacion afectacion = afectacionDao.save(nuevaAfectacion);
		LOGGER.info("Autoridad guardada con id: " + afectacion.getId());
		return afectacion;
	}

	@Override
	public boolean afectacionBorrar(Long id) {
		try {
			afectacionDao.delete(id);
			LOGGER.info("Autoridad borrada con id: " + id);
			return true;
		} catch (Exception e) {
			LOGGER.error("No se pudo borrar la Autoridad con id: " + id + " Error: " + e);
			return false;
		}
	}

}
