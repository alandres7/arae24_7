package co.gov.metropol.area247.entorno.service;

import java.util.List;

import co.gov.metropol.area247.entorno.model.RecomenaireEstacion;


public interface IEntornoRecomenaireEstacionService {

	boolean recomenaireEstacionCrear(RecomenaireEstacion recomenaireEstacion);
	
	List<RecomenaireEstacion> recomenaireEstacionObtenerPorEstacion(Long idEstacion);
	
	boolean recomenaireEstacionEliminarTodos();

}
