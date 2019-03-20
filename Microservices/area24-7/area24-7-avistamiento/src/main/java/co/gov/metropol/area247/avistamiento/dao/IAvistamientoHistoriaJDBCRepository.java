package co.gov.metropol.area247.avistamiento.dao;

import java.util.List;
import co.gov.metropol.area247.avistamiento.model.Historia;
import co.gov.metropol.area247.avistamiento.model.dto.HistoriaDto;
import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface IAvistamientoHistoriaJDBCRepository extends CrudRepository<Historia> {
	
	public HistoriaDto obtenerHistoriaPorId(Long idHistoria) throws SQLException;
	
	public List<HistoriaDto> obtenerHistoriaPorParametros(Long idAvistamiento, Long idUsuario, Integer Estado) throws SQLException;
	
	public List<HistoriaDto> historiaObtenerPorIdAvistamiento(Long idAvistamiento, String nickname) throws SQLException;
	
	public List<HistoriaDto> getHistoriasPorIdAvistamiento(Long idAvistamiento) throws SQLException;
	

}
