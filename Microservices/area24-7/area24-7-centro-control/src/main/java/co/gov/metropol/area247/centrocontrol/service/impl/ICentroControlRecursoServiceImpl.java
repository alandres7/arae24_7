package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlRecursoRepository;
import co.gov.metropol.area247.centrocontrol.model.RecursoVigia;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlRecursoService;

@Service
public class ICentroControlRecursoServiceImpl implements ICentroControlRecursoService {

	@Autowired
	ICentroControlRecursoRepository recursoDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlRecursoServiceImpl.class);

	@Override
	public List<RecursoVigia> recursoObtenerTodos() throws Exception {
		List<RecursoVigia> recurso = null;
		try {
			recurso = (List<RecursoVigia>) recursoDao.findAll();
			if (recurso.isEmpty()) {
				LOGGER.info("No se encuentran recursos");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recurso;
	}

	@Override
	public RecursoVigia recursoObtenerPorId(Long id) throws Exception {
		RecursoVigia recurso = recursoDao.findOne(id);
		if (recurso == null) {
			LOGGER.info("No se encuentra un recurso con id: " + id);
		}
		return recurso;
	}

	@Override
	public RecursoVigia recursoGuardar(RecursoVigia nuevoRecurso) throws Exception {
		RecursoVigia recurso = recursoDao.save(nuevoRecurso);
		LOGGER.info("Autoridad guardada con id: " + recurso.getId());
		return recurso;
	}

	@Override
	public boolean recursoBorrar(Long id) {
		try {
			recursoDao.delete(id);
			LOGGER.info("Autoridad borrada con id: " + id);
			return true;
		} catch (Exception e) {
			LOGGER.error("No se pudo borrar la Autoridad con id: " + id + " Error: " + e);
			return false;
		}
	}

}
