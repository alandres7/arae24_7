package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlAutoridadCompetenteRepository;
import co.gov.metropol.area247.centrocontrol.model.AutoridadCompetente;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlAutoridadCompetenteService;
@Service
public class ICentroControlAutoridadCompetenteServiceImpl implements ICentroControlAutoridadCompetenteService {
	
	@Autowired
	ICentroControlAutoridadCompetenteRepository autoridadCompetenteDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlAutoridadCompetenteServiceImpl.class);
	@Override
	public List<AutoridadCompetente> autoridadCompetenteObtenerTodos()throws Exception {
		List<AutoridadCompetente> autoridades = (List<AutoridadCompetente>) autoridadCompetenteDao.findAll();
		if(autoridades.isEmpty()){
			LOGGER.info("No se encuentran autoridades competentes");
		}
		return autoridades;
	}

	@Override
	public AutoridadCompetente autoridadCompetenteObtenerPorId(Long id)throws Exception {
		AutoridadCompetente autoridad = autoridadCompetenteDao.findOne(id);
		if(autoridad==null){
			LOGGER.info("No se encuentra una autoridad competente con id: "+id);
		}
		return autoridad;
	}

	@Override
	public AutoridadCompetente autoridadCompetenteGuardar(AutoridadCompetente nuevaAutoridad)throws Exception {
		AutoridadCompetente autoridad = autoridadCompetenteDao.save(nuevaAutoridad);
		LOGGER.info("Autoridad guardada con id: "+autoridad.getId());
		return autoridad;
	}

	@Override
	public boolean autoridadCompetenteBorrar(Long id) {
		try{
			autoridadCompetenteDao.delete(id);
			LOGGER.info("Autoridad borrada con id: "+id);
			return true;
		}catch(Exception e){
			LOGGER.error("No se pudo borrar la Autoridad con id: "+id+" Error: "+e);
			return false;
		}
	}

}
