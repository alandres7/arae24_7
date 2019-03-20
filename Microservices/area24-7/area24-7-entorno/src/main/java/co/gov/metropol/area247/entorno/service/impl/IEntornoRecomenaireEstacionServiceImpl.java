package co.gov.metropol.area247.entorno.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import co.gov.metropol.area247.entorno.dao.IEntornoEstacionRepository;
import co.gov.metropol.area247.entorno.dao.IEntornoRecomenaireEstacionRepository;
import co.gov.metropol.area247.entorno.dao.IEntornoRecomendacionAireRepository;
import co.gov.metropol.area247.entorno.model.RecomenaireEstacion;
import co.gov.metropol.area247.entorno.service.IEntornoRecomenaireEstacionService;

@Service
public class IEntornoRecomenaireEstacionServiceImpl implements IEntornoRecomenaireEstacionService {

	@Autowired
	IEntornoEstacionRepository estacionDao;
	
	@Autowired
	IEntornoRecomendacionAireRepository recomendacionDao;
	
	@Autowired
	IEntornoRecomenaireEstacionRepository recomenaireEstacionDao;
	

	@Override
	public boolean recomenaireEstacionCrear(RecomenaireEstacion recomenaireEstacion) {
		try {
			RecomenaireEstacion recomenaireEstacionDB 
			   = recomenaireEstacionDao.findByRecomendacionAndEstacion(recomenaireEstacion.getIdEstacion(),
					                                                   recomenaireEstacion.getIdRecomendacion());
			if(recomenaireEstacionDB==null) {
			    recomenaireEstacionDao.save(recomenaireEstacion);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	@Override
	public List<RecomenaireEstacion> recomenaireEstacionObtenerPorEstacion(Long idEstacion) {
		try {
			List<RecomenaireEstacion> recomenaireEstacion = 
			    recomenaireEstacionDao.findByIdEstacion(idEstacion);
			return recomenaireEstacion;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean recomenaireEstacionEliminarTodos() {
		try {
			List<RecomenaireEstacion> listRecomenaireEstacion = 
					(List<RecomenaireEstacion>) recomenaireEstacionDao.findAll();			
			if (listRecomenaireEstacion != null) {				
				for (RecomenaireEstacion recomenaireEstacion : listRecomenaireEstacion) { 
					recomenaireEstacionDao.delete(recomenaireEstacion);
				}
			} 
			return true;
		} catch (Exception e) {
			return false;
		}
	}



}
