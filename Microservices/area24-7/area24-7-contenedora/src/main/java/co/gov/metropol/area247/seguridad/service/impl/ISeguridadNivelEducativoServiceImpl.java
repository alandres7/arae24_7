package co.gov.metropol.area247.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.seguridad.dao.ISeguridadNivelEducativoRepository;
import co.gov.metropol.area247.seguridad.model.NivelEducativo;
import co.gov.metropol.area247.seguridad.service.ISeguridadNivelEducativoService;

@Service
public class ISeguridadNivelEducativoServiceImpl implements ISeguridadNivelEducativoService {

	@Autowired
	ISeguridadNivelEducativoRepository nivelEducativoDao;
	
	@Override
	public List<NivelEducativo> nivelEducativoListarTodos() {

		return (List<NivelEducativo>) nivelEducativoDao.findAll();

	}

	@Override
	public NivelEducativo nivelEducativoObtenerPorId(Long nivelEducativoId) {

		return nivelEducativoDao.findOne(nivelEducativoId);
		
	}

	@Override
	public NivelEducativo nivelEducativoObtenerPorNombre(String nivelEducativoNombre) {
		
		return nivelEducativoDao.findByNombre(nivelEducativoNombre);
		
	}

}
