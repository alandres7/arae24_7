package co.gov.metropol.area247.core.repository;

import java.util.List;

import co.gov.metropol.area247.core.domain.context.web.Capa;
import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface CapaRepository extends CrudRepository<Capa>{

	public List<Capa> obtenerAplicacionYcapasPorTipoCapas(String tipoCapas, Long idAplicacion) throws SQLException;
	
}
