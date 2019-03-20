package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Multimedia;
import co.gov.metropol.area247.contenedora.model.TipoMultimedia;

@Repository
public interface IContenedoraMultimediaRepository extends CrudRepository<Multimedia, Long> {

	List<Multimedia> findByTipoMultimedia(TipoMultimedia tipoMultimedia);
	Multimedia findByNombre(String nombre);
	
}
