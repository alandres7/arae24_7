package co.gov.metropol.area247.centrocontrol.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.TipoPregunta;
@Repository
public interface ICentroControlTipoPreguntaRepository extends CrudRepository<TipoPregunta, Long> {

}
