package co.gov.metropol.area247.contenedora.service;


import co.gov.metropol.area247.contenedora.model.Avistamiento;


public interface IContenedoraAvistamientoService {
	
	public Avistamiento avistamientoPorId(Long idAvistamiento);
	
	public Avistamiento avistamientoPorIdMarcador(Long idMarcador);
	
	public boolean avistamientoEliminar(Long idAvistamiento);
	
}
