package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlPreguntaRepository;
import co.gov.metropol.area247.centrocontrol.model.Pregunta;
import co.gov.metropol.area247.centrocontrol.model.TipoPregunta;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlFormularioService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlPreguntaService;

@Service
public class ICentroControlPreguntaServiceImpl implements ICentroControlPreguntaService {

	@Autowired
	ICentroControlPreguntaRepository preguntaDao;
	
	@Autowired
	ICentroControlFormularioService formularioService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlPreguntaServiceImpl.class);
	
	@Override
	public List<Pregunta> preguntaObtenerPorIdFormulario(Long idFormulario)throws Exception {
		List<Pregunta> preguntas = preguntaDao.findByFormulario(formularioService.formularioObtenerPorId(idFormulario));
		if(preguntas == null){
			LOGGER.info("No se encuentran preguntas asociadas al formulario con id "+ idFormulario);
		}
		return preguntas;
	}

	@Override
	public List<Pregunta> preguntaObtenerTodas()throws Exception {
		List<Pregunta> preguntas = (List<Pregunta>) preguntaDao.findAll();
		if(preguntas == null){
			LOGGER.info("No se encuentran preguntas");
		}
		return preguntas;
	}

	@Override
	public Pregunta preguntaObtenerPorId(Long idPregunta) throws Exception{
		Pregunta pregunta = preguntaDao.findOne(idPregunta);
		if(pregunta==null){
			LOGGER.info("No se encuentra pregunta con id " + idPregunta);
		}
		return pregunta;
	}

	@Override
	public List<Pregunta> preguntaObtenerPorTipo(TipoPregunta tipoPregunta) throws Exception{
		List<Pregunta> preguntas = (List<Pregunta>) preguntaDao.findByTipoPregunta(tipoPregunta);
		if(preguntas == null){
			LOGGER.info("No se encuentran preguntas asociadas al tipo de pregunta " + tipoPregunta);
		}
		return preguntas;
	}

	@Override
	public Pregunta preguntaGuardar(Pregunta pregunta)throws Exception {
			Pregunta preguntaAux = preguntaDao.save(pregunta);
			LOGGER.info("Pregunta guardada exitosamente con id " + preguntaAux.getId());
			return preguntaAux;
	}

	@Override
	@Transactional
	public boolean preguntaGuardar(List<Pregunta> preguntas) throws Exception{
		try{
			List<Pregunta> listaPreguntas = (List<Pregunta>) preguntaDao.save(preguntas);
			listaPreguntas.forEach(x -> LOGGER.info("pregunta creada exitosamente con id " + x.getId()));
			return true;
		}catch(Exception e){
			LOGGER.error("No se han podido crear las preguntas; " + e);
			return false;
		}
	}

	@Override
	public boolean preguntaEliminar(Long idPregunta) throws Exception{
		try{
			preguntaDao.delete(idPregunta);
			LOGGER.info(String.format("Pregunta con id %s eliminada exitosamente ", idPregunta));
			return true;
		}catch(Exception e){
			LOGGER.error("No se ha podido eliminar la pregunta con id " + idPregunta + " ; " + e);
			return false;
		}
	}

}
