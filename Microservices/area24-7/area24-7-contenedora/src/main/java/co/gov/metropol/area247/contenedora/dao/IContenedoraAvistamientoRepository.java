package co.gov.metropol.area247.contenedora.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Avistamiento;

@Repository
public interface IContenedoraAvistamientoRepository extends CrudRepository<Avistamiento, Long> {

	@Query("SELECT a FROM co.gov.metropol.area247.contenedora.model.Avistamiento a WHERE a.id = ?1 ")
	Avistamiento findById(Long idAvistamiento);
	
	@Query("SELECT a FROM co.gov.metropol.area247.contenedora.model.Avistamiento a WHERE a.idMarcador = ?1 ")
	Avistamiento findByIdMarcador(Long idMarcador);
	
}
