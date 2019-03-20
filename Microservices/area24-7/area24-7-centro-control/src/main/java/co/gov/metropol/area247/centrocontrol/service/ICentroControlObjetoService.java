package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.Objeto;

public interface ICentroControlObjetoService {
	
	Objeto objetoObtenerPorId(Long idObjeto);
	Objeto objetoObtenerPorNombre(String nombre);
	List<Objeto> objetoObtenerPorIdTipoObjeto(Long idTipoObjeto);
	List<Objeto> objetoObtenerPorIdAplicacion(Long idAplicacion);
	List<Objeto> objetoObtenerTodos();
	Objeto objetoGuardar(Objeto objeto);
	boolean objetoEliminar(Long idObjeto);

}
