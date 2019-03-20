package co.gov.metropol.area247.avistamiento.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.avistamiento.model.Avistamiento;

@Repository
public interface IAvistamientoAvistamientoRepository extends CrudRepository<Avistamiento, Long> {


}
