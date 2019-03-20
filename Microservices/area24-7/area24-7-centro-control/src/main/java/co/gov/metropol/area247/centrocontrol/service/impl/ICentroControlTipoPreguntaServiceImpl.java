package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlTipoPreguntaRepository;
import co.gov.metropol.area247.centrocontrol.model.TipoPregunta;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlTipoPreguntaService;

@Service
public class ICentroControlTipoPreguntaServiceImpl implements ICentroControlTipoPreguntaService {
	@Autowired
	ICentroControlTipoPreguntaRepository tipoPreguntaDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlTipoPreguntaServiceImpl.class);

	@Override
	public List<TipoPregunta> tipoPreguntaObtenerTodos() throws Exception {
		List<TipoPregunta> tipoPreguntaTodos= (List<TipoPregunta>) tipoPreguntaDao.findAll();
		if(tipoPreguntaTodos==null){
			LOGGER.info("No se encuentran tipo preguntas");
		}
		return tipoPreguntaTodos;
	}

	@Override
	public TipoPregunta tipoPreguntaObtenerPorId(Long idTipoPregunta) throws Exception {
		TipoPregunta tipoPregunta = tipoPreguntaDao.findOne(idTipoPregunta);
		if(tipoPregunta==null){
			LOGGER.info(String.format("No se encuentran el tipo pregunta con id %s", idTipoPregunta));
		}
		return tipoPregunta;
	}

	@Override
	public TipoPregunta tipoPreguntaGuardar(TipoPregunta tipoPregunta) throws Exception {
		TipoPregunta tipoPreguntaAux = tipoPreguntaDao.save(tipoPregunta);
		LOGGER.info("Pregunta guardada exitosamente con id " + tipoPreguntaAux.getId());
		return tipoPreguntaAux;
	}

	@Override
	public boolean tipoPreguntaEliminar(Long idTipoPregunta) throws Exception {
		try{
			tipoPreguntaDao.delete(idTipoPregunta);
			LOGGER.info(String.format("TipoPregunta con id %s eliminada exitosamente ", idTipoPregunta));
			return true;
		}catch(Exception e){
			LOGGER.error("No se ha podido eliminar la tipo pregunta con id " + idTipoPregunta + " ; " + e);
			return false;
		}
	}

}
