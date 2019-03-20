package co.gov.metropol.area247.entorno.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.entorno.model.RecomenaireEstacion;

@Repository
public interface IEntornoRecomenaireEstacionRepository extends CrudRepository<RecomenaireEstacion, Long> {
	
	
	List<RecomenaireEstacion> findByIdRecomendacion(Long idRecomendacion);
	
	List<RecomenaireEstacion> findByIdEstacion(Long idEstacion);
	
	@Query("SELECT rae FROM RecomenaireEstacion AS rae WHERE rae.idEstacion = ?1 AND rae.idRecomendacion = ?2 ")
	RecomenaireEstacion findByRecomendacionAndEstacion(Long idEstacion, Long idRecomendacion);
	
}
