package co.gov.metropol.area247.entorno.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.entorno.model.RecomendacionAire;

@Repository
public interface IEntornoRecomendacionAireRepository extends CrudRepository<RecomendacionAire, Long> {
	
	
	List<RecomendacionAire> findByCodigo(String Codigo);
	
}
