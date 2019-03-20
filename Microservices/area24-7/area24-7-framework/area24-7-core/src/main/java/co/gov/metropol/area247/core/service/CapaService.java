package co.gov.metropol.area247.core.service;

import java.util.List;

import co.gov.metropol.area247.core.domain.context.web.Capa;
import co.gov.metropol.area247.core.service.ex.ServiceException;

public interface CapaService {

	public List<Capa> obtenerCapasConSuCategoriasPorTiposCapaCategoriaOAplicacion(String tipoCategorias,
			String tipoCapas, Long idAplicacion) throws ServiceException;

	public List<Capa> obtenerCapasPorIdAplicacion(Long idAplicacion) throws ServiceException;
	

}
