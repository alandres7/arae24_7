package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.TipoMultimedia;

@Repository
public interface IContenedoraTipoMultimediaRepository extends CrudRepository<TipoMultimedia, Long> {
	
	List<TipoMultimedia> findByTipo(String tipo);
	TipoMultimedia findBySubtipo(String subtipo);
	
}
