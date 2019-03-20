package co.gov.metropol.area247.contenedora.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraAvistamientoRepository;
import co.gov.metropol.area247.contenedora.model.Avistamiento;
import co.gov.metropol.area247.contenedora.service.IContenedoraAvistamientoService;

@Service
public class IContenedoraAvistamientoServiceImpl implements IContenedoraAvistamientoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraAvistamientoServiceImpl.class);

	@Autowired
	private IContenedoraAvistamientoRepository avistamientoDao;
	
	
	@Override
	public Avistamiento avistamientoPorId(Long idAvistamiento) {
    	return avistamientoDao.findById(idAvistamiento);
    }
	
	@Override
	public Avistamiento avistamientoPorIdMarcador(Long idMarcador) {
		return avistamientoDao.findByIdMarcador(idMarcador);		
	}
	
	@Override
	public boolean avistamientoEliminar(Long idAvistamiento) {
		try{
			Avistamiento avistamiento = avistamientoDao.findById(idAvistamiento);
			avistamientoDao.delete(avistamiento.getId());
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	


}
