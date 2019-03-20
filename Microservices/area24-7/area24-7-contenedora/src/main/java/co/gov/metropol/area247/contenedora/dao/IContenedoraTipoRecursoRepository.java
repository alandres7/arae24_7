package co.gov.metropol.area247.contenedora.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.TipoRecurso;

@Repository
public interface IContenedoraTipoRecursoRepository extends CrudRepository<TipoRecurso, Long> {

	TipoRecurso findByNombre(String nombre);
	
}
