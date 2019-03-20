package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.ArbolDecision;
import co.gov.metropol.area247.centrocontrol.model.Objeto;

public interface ICentroControlArbolDecisionService {
	
	ArbolDecision arbolObtenerPorId(Long idArbol);
	List<ArbolDecision>arbolObtenerPorIdCapa(Long idArbol);
	List<ArbolDecision> arbolObtenerPorIdCategoria(Long idCategoria);
	List<ArbolDecision> arbolObtenerTodos();
	boolean arbolGuardar(ArbolDecision arbol);
	boolean arbolEliminar(Long idArbol);
	boolean arbolEliminarCategorias(Long idCategoria);

}
