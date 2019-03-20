package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Recomendacion;
import co.gov.metropol.area247.contenedora.model.dto.RecomendacionDto;

@Repository
public interface IContenedoraRecomendacionRepository extends CrudRepository<Recomendacion, Long> {
	
	@Query("select new co.gov.metropol.area247.contenedora.model.dto.RecomendacionDto(r.id, r.nombre, r.descripcion, r.tipo,"
			+ " a.id) from Recomendacion as r join r.aplicacion as a where a.id = ?1")
	List<RecomendacionDto> encontrarPorIdAplicacion(Long idAplicacion);

}
