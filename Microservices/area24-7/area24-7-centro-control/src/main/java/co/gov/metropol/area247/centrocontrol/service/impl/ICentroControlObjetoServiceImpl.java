package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlObjetoRepository;
import co.gov.metropol.area247.centrocontrol.dao.ICentroControlPermisoRolRepository;
import co.gov.metropol.area247.centrocontrol.dao.ICentroControlPermisoUsuarioRepository;
import co.gov.metropol.area247.centrocontrol.model.Objeto;
import co.gov.metropol.area247.centrocontrol.model.PermisoRol;
import co.gov.metropol.area247.centrocontrol.model.PermisoUsuario;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlObjetoService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlTipoObjetoService;

@Service
public class ICentroControlObjetoServiceImpl implements ICentroControlObjetoService {

	@Autowired
	ICentroControlObjetoRepository objetoRepository;
	
	@Autowired
	ICentroControlTipoObjetoService tipoObjetoService;
	
	@Autowired
	ICentroControlPermisoUsuarioRepository permisoUsuarioRepository;
	
	@Autowired
	ICentroControlPermisoRolRepository permisoRolRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlObjetoServiceImpl.class);
	
	@Override
	public Objeto objetoObtenerPorId(Long idObjeto) {
		Objeto objeto = objetoRepository.findOne(idObjeto);
		if(objeto!=null){
			return objeto;
		}else{
			LOGGER.info("No se encuentra objeto con id " + idObjeto);
			return null;
		}
	}

	@Override
	public Objeto objetoObtenerPorNombre(String nombre) {
		Objeto objeto = objetoRepository.findByNombre(nombre);
		if(objeto!=null){
			return objeto;
		}else{
			LOGGER.info("No se encuentra objeto con nombre" + nombre);
			return null;
		}
	}

	@Override
	public List<Objeto> objetoObtenerPorIdTipoObjeto(Long idTipoObjeto) {
		List<Objeto> objetos = new ArrayList<Objeto>();
		objetos = objetoRepository.findByTipoObjeto(tipoObjetoService.tipoObjetoObtenerPorId(idTipoObjeto));
		if(objetos.isEmpty()){
			LOGGER.info("No se encuentran objetos asociados al tipo con id " + idTipoObjeto);
		}
		return objetos;
	}

	@Override
	public List<Objeto> objetoObtenerPorIdAplicacion(Long idAplicacion) {
		List<Objeto> objetos = new ArrayList<Objeto>();
		objetos = objetoRepository.findByIdAplicacion(idAplicacion);
		if(objetos.isEmpty()){
			LOGGER.info("No se encuentran objetos asociados a la aplicación con id " + idAplicacion);
		}
		return objetos;
		
	}

	@Override
	public List<Objeto> objetoObtenerTodos() {
		List<Objeto> objetos = new ArrayList<Objeto>();
		objetos = (List<Objeto>) objetoRepository.findAll();
		if(objetos.isEmpty()){
			LOGGER.info("Consulta de lista de objetos sin objetos creados ");
		}
		return objetos;
	}

	@Override
	public Objeto objetoGuardar(Objeto objeto) {
		try{
			Objeto objetoAux = objetoRepository.save(objeto);
			LOGGER.info("Objeto guardado exitosamente con id " + objetoAux.getId());
			return objetoAux;
		}catch(Exception e){
			LOGGER.error("Error en intento de guardar objeto ; " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean objetoEliminar(Long idObjeto) {
		try{			
			List<PermisoUsuario> listPermisos = permisoUsuarioRepository.findByIdObjeto(idObjeto);			
			for(PermisoUsuario permiso : listPermisos){
				permisoUsuarioRepository.delete(permiso);	
			}
			
			List<PermisoRol> listPermisosRol = permisoRolRepository.findByIdObjeto(idObjeto);			
			for(PermisoRol permiso : listPermisosRol){
				permisoRolRepository.delete(permiso);	
			}
			
			objetoRepository.delete(idObjeto);
			LOGGER.info(String.format("Objeto con id %s borrado exitosamente", idObjeto));
			return true;
		}catch(Exception e){
			LOGGER.error("Error en intento de eliminación de objeto con id " + idObjeto + " ; " + e.getMessage());
			return false;
		}
	}

}
