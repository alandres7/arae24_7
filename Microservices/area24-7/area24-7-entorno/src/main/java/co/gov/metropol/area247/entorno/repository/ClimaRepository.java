package co.gov.metropol.area247.entorno.repository;

import java.util.List;

import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.entorno.domain.Clima;
import co.gov.metropol.area247.entorno.model.dto.PronosticoDetail;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface ClimaRepository extends CrudRepository<Clima> {

	public Clima

			obtenerClimaPorIdCapaOMarcador(Long idCapa, Long idMarcador) throws SQLException;

	List<PronosticoDetail> getDetailClimaXMarker(Long idMarcador);

	Long getContainsMarker(double lat, double lng);

}
