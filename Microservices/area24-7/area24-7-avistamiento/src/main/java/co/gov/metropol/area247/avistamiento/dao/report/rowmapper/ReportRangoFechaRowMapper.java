package co.gov.metropol.area247.avistamiento.dao.report.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.gov.metropol.area247.avistamiento.model.dto.ReportDto;

public class ReportRangoFechaRowMapper implements RowMapper<ReportDto> {

	@Override
	public ReportDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReportDto reportDto = new ReportDto();
		reportDto.setId(rs.getLong("ID"));
		reportDto.setNombre(rs.getString("NOMBRE"));
		reportDto.setCantidadAvistamientoAprobado(rs.getInt("ESTADO_APROBADO"));
		reportDto.setCantidadAvistamientoPendiente(rs.getInt("ESTADO_PENDIENTE"));
		reportDto.setCantidadAvistamientoRechazado(rs.getInt("ESTADO_RECHAZADO"));
		reportDto.setTotalAvistamiento(rs.getInt("TOTAL"));
		
		return reportDto;
	} 

}
