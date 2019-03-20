package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.TipoCapa;

public interface IContenedoraTipoCapaService {

	//boolean tipoCapaCrear(TipoCapa tipoCapa);
	TipoCapa tipoCapaObtenerPorNombre(String nombreTipoCapa);
	TipoCapa tipoCapaObtenerPorId(Long idTipoCapa);
	List<TipoCapa> tipoCapaObtenerTodas();
	
}
