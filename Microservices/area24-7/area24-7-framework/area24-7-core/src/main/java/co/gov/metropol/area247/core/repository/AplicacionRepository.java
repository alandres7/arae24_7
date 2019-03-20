package co.gov.metropol.area247.core.repository;

import co.gov.metropol.area247.core.domain.context.web.Aplicacion;
import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface AplicacionRepository extends CrudRepository<Aplicacion> {

	public Aplicacion consultarAplicacionPorIdCapa(Long idCapa) throws SQLException;

}
