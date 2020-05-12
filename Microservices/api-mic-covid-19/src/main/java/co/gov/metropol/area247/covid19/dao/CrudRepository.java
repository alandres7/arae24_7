package co.gov.metropol.area247.covid19.dao;

import java.util.List;

import co.gov.metropol.area247.covid19.jdbc.SQLException;

public interface CrudRepository<T> {
	
	public List<T> buscarTodos()throws SQLException;
	
	public T buscarPorId(Long id) throws SQLException;

}
