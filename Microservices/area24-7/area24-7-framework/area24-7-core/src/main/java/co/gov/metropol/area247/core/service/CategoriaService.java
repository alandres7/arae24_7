package co.gov.metropol.area247.core.service;

import java.util.List;

import co.gov.metropol.area247.core.domain.context.web.Categoria;
import co.gov.metropol.area247.core.service.ex.ServiceException;

public interface CategoriaService {

	public List<Categoria> obtenerCategoriaPorCapaYTipoCategorias(Long idCapa, String tipoCategorias)
			throws ServiceException;
	
}
