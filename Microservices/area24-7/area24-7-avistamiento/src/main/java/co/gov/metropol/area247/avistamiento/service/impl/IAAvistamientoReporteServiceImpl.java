package co.gov.metropol.area247.avistamiento.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.avistamiento.dao.IAAvistamientoReporteJDBCRepository;
import co.gov.metropol.area247.avistamiento.model.dto.ReportDto;
import co.gov.metropol.area247.avistamiento.service.IAAvistamientoReporteService;
import co.gov.metropol.area247.contenedora.service.ex.ServiceException;

@Service
public class IAAvistamientoReporteServiceImpl implements IAAvistamientoReporteService {

	@Autowired
	private IAAvistamientoReporteJDBCRepository iAAvistamientoReporteJDBCRepository;

	private static Logger LOGGER = LoggerFactory.getLogger(IAAvistamientoReporteServiceImpl.class);

	//private float totalAvistamientos;

	@Override
	public List<ReportDto> obtenerReportePorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin,
			                                           String filtroCapa, String filtroCateg)
			throws ServiceException {
		try {
			List<ReportDto> reporDtoList = iAAvistamientoReporteJDBCRepository
					.obtenerAvistamientosPorRangoFecha(fechaInicio,fechaFin,filtroCapa,filtroCateg);
			
			List<ReportDto> reportDtoListNew = new ArrayList<>();

			reporDtoList.forEach(reportDto -> {
				reportDtoListNew.add(reportDto);
			});

			return reportDtoListNew;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el reporte de avistamiento por rando de fechas --{}{}", e);
			throw new ServiceException("Error al consultar el reporte de avistamiento por rando de fechas --{}{} ", e);
		}
	}
	
	
	@Override
	public List<ReportDto> obtenerReporteMunicipioPorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin,
			                                                    String filtroCapa, String filtroCateg)
			throws ServiceException {
		try {
			List<ReportDto> reporDtoList = iAAvistamientoReporteJDBCRepository
					.obtenerAvistamientosMunicipioPorRangoFecha(fechaInicio,fechaFin,filtroCapa,filtroCateg);
			
			List<ReportDto> reportDtoListNew = new ArrayList<>();

			reporDtoList.forEach(reportDto -> {
				reportDtoListNew.add(reportDto);
			});

			return reportDtoListNew;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el reporte de avistamiento por rando de fechas --{}{}", e);
			throw new ServiceException("Error al consultar el reporte de avistamiento por rando de fechas --{}{} ", e);
		}
	}
	
	@Override
	public Integer obtenerTotalAvistamientosPorRangoFecha(LocalDate fechaInicio,LocalDate fechaFin,
			String filtroCapa,String filtroCateg,boolean soloPendientes) throws ServiceException{
		try {
			return iAAvistamientoReporteJDBCRepository
					        .obtenerTotalAvistamientosPorRangoFecha(fechaInicio,fechaFin,filtroCapa,
					        		                                filtroCateg,soloPendientes);
		} catch (Exception e) {
			LOGGER.error("Error al consultar el reporte de avistamiento por rando de fechas --{}{}", e);
			throw new ServiceException("Error al consultar el reporte de avistamiento por rando de fechas --{}{} ", e);
		}
	}

}
