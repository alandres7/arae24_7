package co.gov.metropol.area247.centrocontrol.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.model.PermisoUsuario;
import co.gov.metropol.area247.centrocontrol.model.Rol;
import co.gov.metropol.area247.centrocontrol.seguridad.dao.ISeguridadRolRepository;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadRolService;


@Service
public class ISeguridadRolServiceImpl implements ISeguridadRolService {

	
	@Autowired
	ISeguridadRolRepository rolDao;	
	

	@Override
	public List<Rol> rolListarTodos(){
		return (List<Rol>) rolDao.findAll();				
	}
	
	@Override
	public Rol getRolById(Long id) {
		return rolDao.findOne(id) ;
	}
	
	@Override
	public boolean safeUpdateRol(Rol rol){
	    try {
		    rolDao.save(rol) ;
		    return true;
        }catch(Exception e) {
	        return false;
        }
	}	

}
