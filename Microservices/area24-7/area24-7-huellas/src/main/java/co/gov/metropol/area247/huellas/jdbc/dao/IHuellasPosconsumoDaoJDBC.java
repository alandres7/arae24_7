package co.gov.metropol.area247.huellas.jdbc.dao;

import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.huellas.entity.Posconsumo;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface IHuellasPosconsumoDaoJDBC extends CrudRepository<Posconsumo>{

	String getDetailXMarker(Long idMarcador) throws SQLException ;

}
