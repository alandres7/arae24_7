package co.gov.metropol.area247.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.Recurso;

@Repository
public interface RecursoRepository extends CrudRepository<Recurso, Long> {

}
