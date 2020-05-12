package co.gov.metropol.area247.covid19.svc;

import co.gov.metropol.area247.covid19.entity.Layer;

public interface ILayerSvc {

	Layer getByNombre(String nombre);

	Layer getById(long id);
	
	void borrarMarkersXId( Long idCapa );
	
	

}
