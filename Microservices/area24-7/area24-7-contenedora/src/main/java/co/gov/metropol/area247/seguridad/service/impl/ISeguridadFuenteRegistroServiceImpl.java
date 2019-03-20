package co.gov.metropol.area247.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.seguridad.dao.ISeguridadFuenteRegistroRepository;
import co.gov.metropol.area247.seguridad.model.FuenteRegistro;
import co.gov.metropol.area247.seguridad.service.ISeguridadFuenteRegistroService;

@Service
public class ISeguridadFuenteRegistroServiceImpl implements ISeguridadFuenteRegistroService {

	@Autowired
	ISeguridadFuenteRegistroRepository fuenteRegistroDao;
	
	@Override
	public List<FuenteRegistro> fuenteRegistroListarTodas() {
		
		return (List<FuenteRegistro>) fuenteRegistroDao.findAll();
	}

	@Override
	public FuenteRegistro fuenteRegistroObtenerPorId(Long fuenteRegistroId) {
		return fuenteRegistroDao.findOne(fuenteRegistroId);
	}

	@Override
	public FuenteRegistro fuenteRegistroObtenerPorNombre(String fuenteRegistroNombre) {
		return fuenteRegistroDao.findByNombre(fuenteRegistroNombre);
	}

}
