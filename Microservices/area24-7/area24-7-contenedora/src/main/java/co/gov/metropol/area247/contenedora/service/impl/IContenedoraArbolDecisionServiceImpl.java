package co.gov.metropol.area247.contenedora.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraArbolDecisionRepository;
import co.gov.metropol.area247.contenedora.model.ArbolDecisionConte;
import co.gov.metropol.area247.contenedora.service.IContenedoraArbolDecisionService;


@Service
public class IContenedoraArbolDecisionServiceImpl implements IContenedoraArbolDecisionService {

	@Autowired
	IContenedoraArbolDecisionRepository arbolDecisionRepository;

	
	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraArbolDecisionServiceImpl.class);
		
	
	@Override
	public boolean arbolEliminarCategorias(Long idCategoria) {
		try{
		    List<ArbolDecisionConte> arboles = arbolDecisionRepository.findByIdCategoria(idCategoria);
		    if(!arboles.isEmpty()){
			    for (ArbolDecisionConte arbol : arboles) {
			    	arbol.setIdCategoria(null);
			    	arbolDecisionRepository.save(arbol);			
			    }
		    }
		}catch(Exception e){
			LOGGER.error("Error al eliminar la categoria: "+idCategoria+" de los arboles de desicion");
			return false;
		}	
		return true;
	}
	
	
	@Override
	public boolean arbolEliminarCapas(Long idCapa) {
		try{
		    List<ArbolDecisionConte> arboles = arbolDecisionRepository.findByIdCapa(idCapa);
		    if(!arboles.isEmpty()){
			    for (ArbolDecisionConte arbol : arboles) {
			    	arbol.setIdCapa(null);
			    	arbol.setIdCategoria(null);
			    	arbolDecisionRepository.save(arbol);			
			    }
		    }
		}catch(Exception e){
			LOGGER.error("Error al eliminar la capa: "+idCapa+" de los arboles de desicion");
			return false;
		}	
		return true;
	}

}
