package co.gov.metropol.area247.centrocontrol.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import co.gov.metropol.area247.centrocontrol.model.ArbolDecision;


@Repository
public interface ICentroControlArbolDecisionRepository extends CrudRepository<ArbolDecision,Long> {

	List<ArbolDecision> findByIdCapa(Long idCapa);
	
	List<ArbolDecision> findByIdCategoria(Long idCategoria);
	
}
