package co.gov.metropol.area247.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.seguridad.dao.ISeguridadRolRepository;
import co.gov.metropol.area247.seguridad.model.Rol;
import co.gov.metropol.area247.seguridad.service.ISeguridadRolService;

@Service
public class ISeguridadRolServiceImpl implements ISeguridadRolService {

	@Autowired
	ISeguridadRolRepository rolDao;
	
	@Override
	public Rol rolObtenerPorNombre(String nombreRol) {
		return rolDao.findByNombre(nombreRol);
	}

	@Override
	public Rol rolObtenerPorId(Long idRol) {
		return rolDao.findOne(idRol);
	}

	@Override
	public List<Rol> rolObtenertodos() {
		return (List<Rol>) rolDao.findAll();
	}

}
