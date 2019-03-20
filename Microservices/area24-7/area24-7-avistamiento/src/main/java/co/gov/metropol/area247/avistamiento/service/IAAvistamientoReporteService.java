package co.gov.metropol.area247.avistamiento.service;

import java.time.LocalDate;
import java.util.List;

import co.gov.metropol.area247.avistamiento.model.dto.ReportDto;
import co.gov.metropol.area247.contenedora.service.ex.ServiceException;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface IAAvistamientoReporteService {

	public List<ReportDto> obtenerReportePorRangoFecha(LocalDate fechaInicio,LocalDate fechaFin,
			                                           String filtroCapa,String filtroCateg) throws ServiceException;
	
	public List<ReportDto> obtenerReporteMunicipioPorRangoFecha(LocalDate fechaInicio,LocalDate fechaFin,
			                                                    String filtroCapa,String filtroCateg) throws ServiceException;
	
	public Integer obtenerTotalAvistamientosPorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin,
			String filtroCapa, String filtroCateg,boolean soloPendientes) throws ServiceException;

}
