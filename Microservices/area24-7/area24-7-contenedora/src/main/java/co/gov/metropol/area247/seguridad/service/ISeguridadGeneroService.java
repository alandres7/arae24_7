package co.gov.metropol.area247.seguridad.service;

import java.util.List;

import co.gov.metropol.area247.seguridad.model.Genero;

public interface ISeguridadGeneroService {

	List<Genero> generoListarTodos();
	Genero generoObtenerPorId(Long generoId);
	Genero generoObtenerPorNombre(String generoNombre);
	
}
