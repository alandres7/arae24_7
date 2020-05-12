package co.gov.metropol.area247.covid19.dao;

import javax.transaction.Transactional;

import co.gov.metropol.area247.covid19.dto.LayerDto;

@Transactional
public interface ILayerDaoJdbc extends CrudRepository<LayerDto> {

	void borrarMarcadoresXIdCapa(long idCapa);
	
	

}
