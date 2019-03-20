package co.gov.metropol.area247.contenedora.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.TipoCapa;

@Repository
public interface IContenedoraTipoCapaRepository extends CrudRepository<TipoCapa, Long> {

	TipoCapa findByNombre(String nombre);
	
}
