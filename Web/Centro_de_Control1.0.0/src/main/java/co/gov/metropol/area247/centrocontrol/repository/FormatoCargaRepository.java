package co.gov.metropol.area247.centrocontrol.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.FormatoCarga;

@Repository
public interface FormatoCargaRepository extends CrudRepository<FormatoCarga , Long> {

}
