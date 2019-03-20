package co.gov.metropol.area247.centrocontrol.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.model.PermisoRol;
import co.gov.metropol.area247.centrocontrol.model.PermisoUsuario;
import co.gov.metropol.area247.centrocontrol.model.Rol;
import co.gov.metropol.area247.centrocontrol.security.service.ex.SecurityServiceException;
import co.gov.metropol.area247.centrocontrol.seguridad.dao.ISeguridadPermisoRolRepository;
import co.gov.metropol.area247.centrocontrol.seguridad.dao.ISeguridadRolRepository;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadPermisoRolService;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadRolService;


@Service
public class ISeguridadPermisoRolServiceImpl implements ISeguridadPermisoRolService {

	
	@Autowired
	ISeguridadPermisoRolRepository permisoRolDao;	
	
	
	@Override
	public List<PermisoRol> permisoRolListarTodos(){
		return (List<PermisoRol>) permisoRolDao.findAll();				
	}
	
	@Override
	public List<PermisoRol> findByIdRol(Long idRol){
		return permisoRolDao.findByIdRol(idRol);
		//return null;
	}
	
	@Override
	public PermisoRol permisoRolObtenerPermisoPorRolPorObjeto(Long idRol, Long idObjeto){
		List<PermisoRol> permisosRol = findByIdRol(idRol);
		if(permisosRol!=null) {
			for(PermisoRol permisoActual : permisosRol) {
				if(permisoActual !=null) {
					if(permisoActual.getIdObjeto().longValue()==idObjeto.longValue()) {
						return permisoActual; 
					}
				}
			}
		}
		return null;
	}
	
	/*@Override
	public PermisoRol permisoRolObtenerPermisoPorRolPorObjeto(Long idRol, Long idObjeto){
		try {
			return permisoRolDao.permisoRolObtenerPermisoPorRolPorObjeto(idRol, idObjeto);
		} catch (SecurityServiceException e) {
			return null;
		}
	}*/
	
	@Override
	public boolean safeUpdatePermisoRol(PermisoRol permisoRol){
	    try {
	    	permisoRolDao.save(permisoRol) ;
		    return true;
        }catch(Exception e) {
	        return false;
        }
	}	

}
