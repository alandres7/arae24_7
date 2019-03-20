package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraAvistamientoRepository;
import co.gov.metropol.area247.contenedora.dao.IContenedoraEspecieRepository;
import co.gov.metropol.area247.contenedora.model.Avistamiento;
import co.gov.metropol.area247.contenedora.model.Especie;
import co.gov.metropol.area247.contenedora.service.IContenedoraAvistamientoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraEspecieService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;

@Service
public class IContenedoraEspecieServiceImpl implements IContenedoraEspecieService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraEspecieServiceImpl.class);

	@Autowired
	private IContenedoraEspecieRepository especieDao;
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Override
	public Especie especiePorId(Long idEspecie) {
    	return especieDao.findById(idEspecie);
    }
	
	@Override
	public List<Especie> especiePorIdCategoria(Long idCategoria) {
		try {
			List<Especie> especies = especieDao.findByIdCategoria(idCategoria);
			return especies;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean especieEliminar(Long idEspecie) {
		Especie especie = especieDao.findOne(idEspecie);
		if (especie != null) {
			iconoService.iconoEliminar(especie.getIcono().getId());
			especieDao.delete(especie);
			return true;
		} else {
			return false;
		}
	}
	


}
