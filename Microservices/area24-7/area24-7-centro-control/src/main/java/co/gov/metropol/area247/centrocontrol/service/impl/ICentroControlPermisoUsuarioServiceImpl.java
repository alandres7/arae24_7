package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlPermisoUsuarioRepository;
import co.gov.metropol.area247.centrocontrol.model.PermisoUsuario;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlObjetoService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlPermisoUsuarioService;

@Service
public class ICentroControlPermisoUsuarioServiceImpl implements ICentroControlPermisoUsuarioService {
	
	@Autowired
	ICentroControlObjetoService objetoService;
	
	@Autowired
	ICentroControlPermisoUsuarioService permisoUsuarioService;
	
	@Autowired
	ICentroControlPermisoUsuarioRepository permisoUsuarioRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlPermisoUsuarioServiceImpl.class);
	
	@Override
	public List<PermisoUsuario> permisoUsuarioObtenerPorIdUsuario(Long idUsuario) {
		try {
			List<PermisoUsuario> permisos = permisoUsuarioRepository.findByIdUsuario(idUsuario); 
			return permisos;
		}catch(Exception e) {
			LOGGER.error("Error en intento de recuperar permisos; " + e.getMessage());
			return null;
		}
		
	}

	@Override
	public PermisoUsuario permisoUsuarioGuardar(PermisoUsuario permisoUsuario) {
		try{
			PermisoUsuario permiso = permisoUsuarioRepository.save(permisoUsuario);
			LOGGER.info("Permiso Usuario guardado exitosamente con id :" + permiso.getId());
			return permiso;
		}catch(Exception e){
			LOGGER.error("Error en intento de guardar objeto ; " + e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional
	public List<PermisoUsuario> permisoUsuarioGuardarPorLote(List<PermisoUsuario> permisos) {
		try {
			List<PermisoUsuario> permisosGuardados = (List<PermisoUsuario>)permisoUsuarioRepository
					.save(permisos);
			return permisosGuardados;
		}catch(Exception e) {
			LOGGER.error("Error en intento de guardar los objetos; " + e.getMessage());
			return null;
		}
	}

//	@Override
//	public List<PermisoUsuarioDto> permisoUsuarioObtenerPermisosDto(Long idPermisoUsuario) {
//		try {
//			List<PermisoUsuarioDto> permisosDtoPorUsuario = permisoUsuarioRepository
//					.obtenerPermisoUsuarioDtoPorIdUsuario(idPermisoUsuario);
//			return permisosDtoPorUsuario;
//		}catch(Exception e) {
//			LOGGER.error("Error en intento de recuperar permisos; " + e.getMessage());
//		}
//		return null;
//	}
	@Override
	public boolean permisoUsuarioEliminar(Long idPermisoUsuario) {
		try {
			permisoUsuarioRepository.delete(idPermisoUsuario);
			LOGGER.info(String.format("eliminación del permiso con id %s exitosa", idPermisoUsuario));
			return true;
		}catch(Exception e) {
			LOGGER.error("Error en intento de eliminación del permiso con id " + idPermisoUsuario 
					+ " ; " + e.getMessage());
			return false;
		}
	}

	@Override
	public PermisoUsuario permisoUsuarioObtenerPermisoPorUsuarioPorObjeto(Long idUsuario, Long idObjeto) {
		List<PermisoUsuario> permisosUsuario = permisoUsuarioObtenerPorIdUsuario(idUsuario);
		if(permisosUsuario!=null) {
			for(PermisoUsuario permisoActual : permisosUsuario) {
				if(permisoActual !=null) {
					if(permisoActual.getIdObjeto().longValue()==idObjeto.longValue()) {
						return permisoActual; 
					}
				}
			}
		}
		return null;
	}
	
	
}
