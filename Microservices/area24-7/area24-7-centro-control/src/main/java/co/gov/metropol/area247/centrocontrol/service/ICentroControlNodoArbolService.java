package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.NodoArbol;
import co.gov.metropol.area247.centrocontrol.model.dto.NodoArbolDto;

public interface ICentroControlNodoArbolService {

	List<NodoArbolDto> nodoArbolDtoObtenerPorIdPadre(Long idPadre);
	List<NodoArbolDto> nodoRaizArbolDtoObtenerPorIdArbol(Long idArbol);
	NodoArbolDto nodoArbolDtoObtenerPorId(Long idNodoArbol);
	NodoArbol nodoArbolObtenerPorId(Long idNodoArbol);
	boolean nodoArbolGuardar(NodoArbol nodoArbol);
	boolean nodoArbolEliminar(Long idNodoArbol);
	Integer cantidadNodosHijos(Long idPadre);

}
