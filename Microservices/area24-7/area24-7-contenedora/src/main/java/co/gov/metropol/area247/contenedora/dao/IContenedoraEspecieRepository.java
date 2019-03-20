package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Especie;

@Repository
public interface IContenedoraEspecieRepository extends CrudRepository<Especie, Long> {

	@Query("SELECT e FROM co.gov.metropol.area247.contenedora.model.Especie e WHERE e.id = ?1 ")
	Especie findById(Long idEspecie);
	
	@Query("SELECT e FROM co.gov.metropol.area247.contenedora.model.Especie e WHERE e.idCategoria = ?1 ")
	List<Especie> findByIdCategoria(Long idCategoria);
	
}
