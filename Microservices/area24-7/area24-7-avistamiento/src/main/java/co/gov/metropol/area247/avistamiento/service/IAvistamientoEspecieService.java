package co.gov.metropol.area247.avistamiento.service;

import java.util.List;

import co.gov.metropol.area247.avistamiento.model.Especie;

public interface IAvistamientoEspecieService {

	boolean especieCrear(Especie especie, Long idCategoria);

	boolean especieActualizar(Especie especie);
	
	List<Especie> especiePorIdCategoria(Long idCategoria);

	Especie especiePorId(Long id);

	boolean especieEliminar(Long idEspecie);

}
