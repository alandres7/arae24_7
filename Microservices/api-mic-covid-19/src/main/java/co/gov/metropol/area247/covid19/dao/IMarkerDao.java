package co.gov.metropol.area247.covid19.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.covid19.entity.Marker;

@Transactional
@Repository
public interface IMarkerDao extends CrudRepository<Marker, Long> {

}
