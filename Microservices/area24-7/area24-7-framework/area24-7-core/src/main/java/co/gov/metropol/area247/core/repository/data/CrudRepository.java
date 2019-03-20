package co.gov.metropol.area247.core.repository.data;

import java.util.List;

import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface CrudRepository<T> {

	public List<T> buscarTodos()throws SQLException;
	
	public T buscarPorId(Long id) throws SQLException;
	
}
