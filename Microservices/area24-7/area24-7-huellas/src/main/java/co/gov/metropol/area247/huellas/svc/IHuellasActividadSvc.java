package co.gov.metropol.area247.huellas.svc;

import java.util.List;

import co.gov.metropol.area247.huellas.model.ActividadDto;

public interface IHuellasActividadSvc {

	List<ActividadDto> getActividadesXCapa(long idCapa);

	ActividadDto getActividadXId(long id);

	Boolean saveActividad(ActividadDto actividad) throws Exception;

	boolean existActividad(long id);

	boolean deleteActividad(long id) throws Exception;
	
}
