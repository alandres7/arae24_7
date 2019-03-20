package co.gov.metropol.area247.contenedora.service;


import java.util.List;

import co.gov.metropol.area247.contenedora.model.Especie;


public interface IContenedoraEspecieService {
	
	public Especie especiePorId(Long idEspecie);
	
	public List<Especie> especiePorIdCategoria(Long idCategoria);
	
	public boolean especieEliminar(Long idEspecie);
	
}
