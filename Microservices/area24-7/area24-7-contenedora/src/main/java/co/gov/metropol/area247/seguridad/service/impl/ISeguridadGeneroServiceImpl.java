package co.gov.metropol.area247.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.seguridad.dao.ISeguridadGeneroRepository;
import co.gov.metropol.area247.seguridad.model.Genero;
import co.gov.metropol.area247.seguridad.service.ISeguridadGeneroService;

@Service
public class ISeguridadGeneroServiceImpl implements ISeguridadGeneroService {

	@Autowired
	ISeguridadGeneroRepository generoDao;
	
	@Override
	public List<Genero> generoListarTodos() {
		return (List<Genero>) generoDao.findAll();
	}

	@Override
	public Genero generoObtenerPorId(Long generoId) {
		return generoDao.findOne(generoId);
	}

	@Override
	public Genero generoObtenerPorNombre(String generoNombre) {
		return generoDao.findByNombre(generoNombre);
	}

}
