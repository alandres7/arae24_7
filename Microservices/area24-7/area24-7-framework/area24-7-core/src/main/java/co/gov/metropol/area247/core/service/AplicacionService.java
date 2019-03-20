package co.gov.metropol.area247.core.service;

import java.util.List;

import co.gov.metropol.area247.core.domain.context.web.Aplicacion;
import co.gov.metropol.area247.core.service.ex.ServiceException;

public interface AplicacionService {
	
	public Aplicacion obtenerAplicaciomPorId(Long idAplicacion) throws ServiceException;
	
	public List<Aplicacion> obtenerCapasPorTipoCapasOAplicacion(String tipoCapas, Long idAplicacion) throws ServiceException;

}
