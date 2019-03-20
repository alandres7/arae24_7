package co.gov.metropol.area247.avistamiento.dao;

import java.time.LocalDate;
import java.util.List;

import co.gov.metropol.area247.avistamiento.model.dto.ReportDto;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface IAAvistamientoReporteJDBCRepository {

	public List<ReportDto> obtenerAvistamientosPorRangoFecha(LocalDate fechaInicio,LocalDate fechaFin,
			                                                 String filtroCapa,String filtroCateg) throws SQLException;
	
	public List<ReportDto> obtenerAvistamientosMunicipioPorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin,
			                                                          String filtroCapa, String filtroCateg) throws SQLException;
	
	public Integer obtenerTotalAvistamientosPorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin,
			String filtroCapa, String filtroCateg,boolean soloPendientes) throws SQLException;

}
