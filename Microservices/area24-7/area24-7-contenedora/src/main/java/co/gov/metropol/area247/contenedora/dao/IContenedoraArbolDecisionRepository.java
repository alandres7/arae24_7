package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import co.gov.metropol.area247.contenedora.model.ArbolDecisionConte;


@Repository
public interface IContenedoraArbolDecisionRepository extends CrudRepository<ArbolDecisionConte,Long> {

	List<ArbolDecisionConte> findByIdCapa(Long idCapa);
	
	List<ArbolDecisionConte> findByIdCategoria(Long idCategoria);
	
}
