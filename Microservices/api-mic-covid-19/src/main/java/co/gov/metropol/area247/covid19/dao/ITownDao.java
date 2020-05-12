package co.gov.metropol.area247.covid19.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.covid19.entity.Town;

@Transactional
@Repository
public interface ITownDao extends CrudRepository<Town, Long> {
	
	List<Town> findByValleAburra(boolean valleAburra);
	
}
