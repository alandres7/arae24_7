package co.gov.metropol.area247.centrocontrol.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import co.gov.metropol.area247.centrocontrol.model.AutoridadMunicipio;
import co.gov.metropol.area247.centrocontrol.model.dto.AutoridadMunicipioDto;


@Repository
public interface ICentroControlAutoridadMunicipioRepository extends CrudRepository<AutoridadMunicipio,Long> {
	
	List<AutoridadMunicipio> findByIdNodoArbol(Long idNodoArbol);
	
	@Query("SELECT new co.gov.metropol.area247.centrocontrol.model.dto.AutoridadMunicipioDto( "
			+ "autMun.id, autMun.idNodoArbol, autMun.municipio, autMun.idAutoridadCompetente) "
			+ "FROM AutoridadMunicipio AS autMun where autMun.idNodoArbol = ?1 ")
	List<AutoridadMunicipioDto> autoridadMunicipioPorIdNodo(Long idNodo);
	
	@Query("SELECT new co.gov.metropol.area247.centrocontrol.model.dto.AutoridadMunicipioDto( "
			+ "autMun.id, autMun.idNodoArbol, autMun.municipio, autMun.idAutoridadCompetente) "
			+ "FROM AutoridadMunicipio AS autMun where autMun.idNodoArbol = ?1 AND autMun.municipio = ?2 ")
	AutoridadMunicipioDto autoridadMunicipioPorIdNodoAndMunicipio(Long idNodo, String municipio);	
	
}
