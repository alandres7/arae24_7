package co.gov.metropol.area247.avistamiento.dao;


import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import co.gov.metropol.area247.avistamiento.model.Historia;

@Transactional
@Repository
public interface IAvistamientoHistoriaRepository extends CrudRepository<Historia, Long> {

	

}
