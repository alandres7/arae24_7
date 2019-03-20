package co.gov.metropol.area247.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.TipoParametro;

@Repository
public interface TipoParametroRepository extends CrudRepository<TipoParametro, Long> {

}
