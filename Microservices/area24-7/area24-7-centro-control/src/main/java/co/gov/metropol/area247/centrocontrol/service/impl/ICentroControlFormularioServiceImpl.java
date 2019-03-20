package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlFormularioRepository;
import co.gov.metropol.area247.centrocontrol.model.Formulario;
import co.gov.metropol.area247.centrocontrol.model.Objeto;
import co.gov.metropol.area247.centrocontrol.model.enums.TiposObjeto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlFormularioService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlObjetoService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlPreguntaService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlTipoObjetoService;

@Service
public class ICentroControlFormularioServiceImpl implements ICentroControlFormularioService {

	@Autowired
	ICentroControlFormularioRepository formularioDao;
	
	@Autowired
	ICentroControlPreguntaService preguntaService;
	
	@Autowired
	ICentroControlObjetoService objetoService;
	
	@Autowired
	ICentroControlTipoObjetoService tipoObjetoService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlFormularioServiceImpl.class);

	@Override
	public List<Formulario> formularioObtenerTodos() {
		List<Formulario> formularios = (List<Formulario>) formularioDao.findAll();
		if(formularios.isEmpty()){
			LOGGER.info("No se encuentran formularios dosponibles");
		}
		return formularios; 
	}

	@Override
	public Formulario formularioObtenerPorNombre(String nombreFormulario) {
		Formulario formulario =formularioDao.findByNombre(nombreFormulario); 
		if(formulario==null){
			LOGGER.info("No se encuentra formulario con nombre " + nombreFormulario);
		}
		return formulario; 
	}

	@Override
	public Formulario formularioObtenerPorId(Long idFormulario) {
		Formulario formulario = formularioDao.findOne(idFormulario); 
		if(formulario==null){
			LOGGER.info("No se encuentra un formulario con id " + idFormulario);
		}
		return formulario;
	}

	@Override
	public boolean formularioGuardar(Formulario formulario) {
		try{
			formularioDao.save(formulario);	
			LOGGER.info("formulario creado exitosamente con id " + formulario.getId());
			return true;
		}catch(Exception e){
			LOGGER.error("No ha sido posible crear el formulario ;  " + e); 
			return false;
		}
	}

	@Override
	public boolean formularioEliminar(Long idFormulario) {
		try{			
			formularioDao.delete(idFormulario);			
			LOGGER.info(String.format("formulario con id %s eliminado correctamente", idFormulario));
			return true;
		}catch(Exception e){
			LOGGER.error("error al intentar eliminar nodo con id " + idFormulario + " ; " + e);
			return false;
		}
	}

}
