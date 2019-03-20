package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.TipoMultimedia;

public interface IContenedoraTipoMultimediaService {
	
	boolean tipoMultimediaCrear(TipoMultimedia tipoMultimedia);
	List<TipoMultimedia> tipoMultimediaObtenerTodos();
	TipoMultimedia tipoMultimediaObtenerPorId(Long idTipoMultimedia);
	List<TipoMultimedia> tipoMultimediaObtenerPorTipo(String tipoMultimedia);
	TipoMultimedia tipoMultimediaObtenerPorSubtipoMultimedia(String subtipoMultimedia);

}
