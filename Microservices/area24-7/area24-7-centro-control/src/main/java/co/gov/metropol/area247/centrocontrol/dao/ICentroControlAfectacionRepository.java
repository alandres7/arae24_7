package co.gov.metropol.area247.centrocontrol.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.Afectacion;

@Repository
public interface ICentroControlAfectacionRepository extends CrudRepository<Afectacion, Long> {

}
