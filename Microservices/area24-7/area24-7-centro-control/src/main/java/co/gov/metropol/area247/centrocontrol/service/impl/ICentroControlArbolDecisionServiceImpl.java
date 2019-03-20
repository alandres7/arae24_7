package co.gov.metropol.area247.centrocontrol.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlArbolDecisionRepository;
import co.gov.metropol.area247.centrocontrol.dao.ICentroControlNodoArbolRepository;
import co.gov.metropol.area247.centrocontrol.model.ArbolDecision;
import co.gov.metropol.area247.centrocontrol.model.NodoArbol;
import co.gov.metropol.area247.centrocontrol.model.dto.NodoArbolDto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlArbolDecisionService;

@Service
public class ICentroControlArbolDecisionServiceImpl implements ICentroControlArbolDecisionService {

	@Autowired
	ICentroControlArbolDecisionRepository arbolDecisionRepository;
	
	@Autowired
	ICentroControlNodoArbolRepository nodoArbolRepository;

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroControlArbolDecisionServiceImpl.class);
	
	@Override
	public ArbolDecision arbolObtenerPorId(Long idArbol) {
		ArbolDecision arbol = arbolDecisionRepository.findOne(idArbol);
		if(arbol!=null){
			return arbol;
		}else{
			LOGGER.info("No se encuentra arbol con id " + idArbol);
			return null;
		}
	}

	@Override
	public List<ArbolDecision> arbolObtenerPorIdCapa(Long idCapa) {
		List<ArbolDecision> arboles = new ArrayList<ArbolDecision>();
		arboles = arbolDecisionRepository.findByIdCapa(idCapa);
		if(arboles.isEmpty()){
			LOGGER.info("No se encuentran arboles asociados a la capa con id " + idCapa);
		}
		return arboles;		
	}
	
	@Override
	public List<ArbolDecision> arbolObtenerPorIdCategoria(Long idCategoria) {
		List<ArbolDecision> arboles = new ArrayList<ArbolDecision>();
		arboles = arbolDecisionRepository.findByIdCategoria(idCategoria);
		if(arboles.isEmpty()){
			LOGGER.info("No se encuentran arboles asociados a la capa con id " + idCategoria);
		}
		return arboles;		
	}

	@Override
	public List<ArbolDecision> arbolObtenerTodos() {
		List<ArbolDecision> arboles = new ArrayList<ArbolDecision>();
		arboles = (List<ArbolDecision>) arbolDecisionRepository.findAll();

		return arboles;
	}

	@Override
	public boolean arbolGuardar(ArbolDecision arbol) {
		try{
			arbolDecisionRepository.save(arbol);
			LOGGER.info("Arbol guardado exitosamente con id " + arbol.getId());
			return true;
		}catch(Exception e){
			LOGGER.error("Error en intento de guardar arbol ; " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean arbolEliminar(Long idArbol) {
		try{	
			List<NodoArbol> listaNodos = nodoArbolRepository.findByIdArbol(idArbol);			
			for (NodoArbol nodo : listaNodos) {
				nodoArbolRepository.delete(nodo);
			}						
			arbolDecisionRepository.delete(idArbol);
			LOGGER.info(String.format("Arbol con id %s borrado exitosamente", idArbol));
			return true;
		}catch(Exception e){
			LOGGER.error("Error en intento de eliminación de arbol con id " + idArbol + " ; " + e.getMessage());
			return false;
		}
	}
	
	
	@Override
	public boolean arbolEliminarCategorias(Long idCategoria) {
		try{
		    List<ArbolDecision> arboles = arbolDecisionRepository.findByIdCategoria(idCategoria);
		    if(!arboles.isEmpty()){
			    for (ArbolDecision arbol : arboles) {
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

}
