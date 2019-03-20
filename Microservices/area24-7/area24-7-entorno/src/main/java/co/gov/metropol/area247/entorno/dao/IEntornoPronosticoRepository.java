package co.gov.metropol.area247.entorno.dao;

import org.springframework.data.repository.CrudRepository;

import co.gov.metropol.area247.entorno.model.Pronostico;

public interface IEntornoPronosticoRepository extends CrudRepository<Pronostico, Long> {
	
}
