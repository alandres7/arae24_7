package co.gov.metropol.area247.core.repository;

import java.util.List;

import co.gov.metropol.area247.core.domain.context.web.Categoria;
import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.core.service.ex.ServiceException;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface CategoriaRepository extends CrudRepository<Categoria> {

	public List<Categoria> obtenerCategoriasPorTipoCapasOrTipoCategoria(String tipoCategorias, String tipoCapas, Long idAplicacion) throws SQLException;
	
	public List<Categoria> obtenerCategoriasPorIdAplicacion(Long idAplicacion) throws SQLException;
	
	public List<Categoria> obtenerCategoriaPorCapaYTipoCategorias(Long idCapa, String tipoCategorias)
			throws ServiceException;

}
