package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraRecomendacionRepository;
import co.gov.metropol.area247.contenedora.model.Aplicacion;
import co.gov.metropol.area247.contenedora.model.Recomendacion;
import co.gov.metropol.area247.contenedora.model.dto.RecomendacionDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraAplicacionService;
import co.gov.metropol.area247.contenedora.service.IContenedoraRecomendacionService;

@Service
public class IContenedoraRecomendacionServiceImpl implements IContenedoraRecomendacionService {

	@Autowired
	IContenedoraRecomendacionRepository recomendacionDao;
	
	@Autowired
	IContenedoraAplicacionService aplicacionService;
	
	@Override
	public boolean recomendacionCrear(Recomendacion recomendacion, Long aplicacionId) {
		try {
			Aplicacion aplicacion = aplicacionService.aplicacionObtenerPorId(aplicacionId);
			recomendacion.setAplicacion(aplicacion);
			recomendacionDao.save(recomendacion);
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean recomendacionActualizar(Recomendacion recomendacion, Long aplicacionId) {
		try {
			recomendacionCrear(recomendacion,aplicacionId);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public List<RecomendacionDto> recomendacionObtenerPorAplicacionId(Long idAplicacion) {		
		return recomendacionDao.encontrarPorIdAplicacion(idAplicacion);
	}

	@Override
	public List<Recomendacion> recomendacionObtenerTodas() {
		List<Recomendacion> ee = (List<Recomendacion>) recomendacionDao.findAll();
		return ee;
	}

}
