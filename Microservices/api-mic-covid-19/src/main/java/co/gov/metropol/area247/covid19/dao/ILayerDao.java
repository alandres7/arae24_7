package co.gov.metropol.area247.covid19.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.covid19.entity.Layer;

@Transactional
@Repository
public interface ILayerDao extends CrudRepository<Layer, Long> {
	
	Layer findByNombre(String nombre);
	
}
