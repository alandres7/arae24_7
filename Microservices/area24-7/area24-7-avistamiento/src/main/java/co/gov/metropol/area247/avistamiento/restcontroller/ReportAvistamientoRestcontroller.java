package co.gov.metropol.area247.avistamiento.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.avistamiento.model.dto.ReportDto;
import co.gov.metropol.area247.avistamiento.service.IAAvistamientoReporteService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RequestMapping(value = "/api/avistamiento/report")
@RestController
public class ReportAvistamientoRestcontroller {

	@Autowired
	private IAAvistamientoReporteService iAAvistamientoReporteService;
	
	private static Logger LOGGER = LoggerFactory.getLogger(IAAvistamientoReporteService.class);

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/usuario/rank/date")
	public ResponseEntity<List<ReportDto>> getReportPorRangoFechas(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin,
			@RequestParam(value = "filtroCapa", required = false) String filtroCapa,
			@RequestParam(value = "filtroCateg", required = false) String filtroCateg) {
		try {
			return new ResponseEntity<List<ReportDto>>(
					iAAvistamientoReporteService.obtenerReportePorRangoFecha(fechaInicio,fechaFin,filtroCapa,filtroCateg), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al consultar la lista de reportes por rango de fechas --{}{}");
			return new ResponseEntity<List<ReportDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/municipio/rank/date")
	public ResponseEntity<List<ReportDto>> getReportMunicipioPorRangoFechas(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin,
			@RequestParam(value = "filtroCapa", required = false) String filtroCapa,
			@RequestParam(value = "filtroCateg", required = false) String filtroCateg) {
		try {
			return new ResponseEntity<List<ReportDto>>(
					iAAvistamientoReporteService.obtenerReporteMunicipioPorRangoFecha(fechaInicio,fechaFin,filtroCapa,filtroCateg), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al consultar la lista de reportes por rango de fechas --{}{}");
			return new ResponseEntity<List<ReportDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/totalAvistamientos/date")
	public ResponseEntity<Integer> getTotalAvistamientosPorRangoFechas(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin,
			@RequestParam(value = "filtroCapa", required = false) String filtroCapa,
			@RequestParam(value = "filtroCateg", required = false) String filtroCateg,
			@RequestParam(value = "soloPendientes", required = false) boolean soloPendientes) {
		try {
			return new ResponseEntity<Integer>(
					iAAvistamientoReporteService.obtenerTotalAvistamientosPorRangoFecha(fechaInicio,fechaFin,filtroCapa,
							filtroCateg,soloPendientes), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al consultar la lista de reportes por rango de fechas --{}{}");
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	
	
		
	

}
