package co.gov.metropol.area247.seguridad.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.seguridad.model.Departamento;

@Repository
public interface ISeguridadDepartamentoRepository extends CrudRepository<Departamento, Long> {

}
