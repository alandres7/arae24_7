package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlAuditoriaRepository;
import co.gov.metropol.area247.centrocontrol.model.Auditoria;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAuditoriaService;

@Service
public class ICentroControlAuditoriaServiceImpl implements ICentroControlAuditoriaService {

	@Autowired
	ICentroControlAuditoriaRepository auditoriaDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlAuditoriaServiceImpl.class);

	@Override
	public List<Auditoria> auditoriaObtenerTodas() throws Exception {
		List<Auditoria> auditoria = null;
		try {
			auditoria = (List<Auditoria>) auditoriaDao.findAll();
			if (auditoria.isEmpty()) {
				LOGGER.info("No se encuentran auditorias");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return auditoria;
	}

	@Override
	public Auditoria auditoriaObtenerPorId(Long id) throws Exception {
		Auditoria auditoria = auditoriaDao.findOne(id);
		if (auditoria == null) {
			LOGGER.info("No se encuentra un recurso con id: " + id);
		}
		return auditoria;
	}

	@Override
	public Auditoria auditoriaGuardar(Auditoria nuevaAuditoria) throws Exception {
		Auditoria auditoria = auditoriaDao.save(nuevaAuditoria);
		LOGGER.info("Auditoria guardada con id: " + auditoria.getId());
		return auditoria;
	}

	@Override
	public boolean auditoriaBorrar(Long id) {
		try {
			auditoriaDao.delete(id);
			LOGGER.info("Auditoria borrada con id: " + id);
			return true;
		} catch (Exception e) {
			LOGGER.error("No se pudo borrar la Auditoria con id: " + id + " Error: " + e);
			return false;
		}
	}

}
