package co.gov.metropol.area247.centrocontrol.seguridad.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.LoginExterno;


@Repository
public interface ISeguridadLoginExternoRepository extends CrudRepository<LoginExterno, Long> {
	
	LoginExterno findByUsername(String username);

}
