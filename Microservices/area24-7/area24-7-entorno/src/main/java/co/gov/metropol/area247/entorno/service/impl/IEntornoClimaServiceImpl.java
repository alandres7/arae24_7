package co.gov.metropol.area247.entorno.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.entorno.model.dto.ClimaDetail;
import co.gov.metropol.area247.entorno.model.dto.PronosticoDetail;
import co.gov.metropol.area247.entorno.model.dto.TiempoDetail;
import co.gov.metropol.area247.entorno.repository.ClimaRepository;
import co.gov.metropol.area247.entorno.service.IEntornoClimaService;

@Service
public class IEntornoClimaServiceImpl implements IEntornoClimaService {
	
	 @Autowired
	 private ClimaRepository climaDaoJDBC; 
	 
	 private Date now;
	 
	 private static final int NUM_HORAS = 6;
	 
	 
	
	@Override
	public ClimaDetail getDetailClima(Long idMarcador) {
		now = new Date();
		List<PronosticoDetail> pronosticoDetails = climaDaoJDBC.getDetailClimaXMarker(idMarcador);
		ClimaDetail climaDetail = new ClimaDetail();
		climaDetail.setNombreEstacion(pronosticoDetails.isEmpty()?"":pronosticoDetails.get(0).getNombreEstacion());
		climaDetail.setTemperatura(pronosticoDetails.isEmpty()?"":pronosticoDetails.get(0).getTemperatura());
		climaDetail.setMunicipio(pronosticoDetails.isEmpty()?"":pronosticoDetails.get(0).getMunicipio()); 
		climaDetail.setTiempoDetails(getTiempoDetails(pronosticoDetails));
		climaDetail.setTemperaturaMinima(pronosticoDetails.isEmpty()?"":pronosticoDetails.get(0).getTemperaturaMinima());
		climaDetail.setTemperaturaMaxima(pronosticoDetails.isEmpty()?"":pronosticoDetails.get(0).getTemperaturaMaxima());		
		return climaDetail;		
	}
	
	@Override
	public ClimaDetail getDetailClimaXLocation(double lat, double lng) {
		return getDetailClima(climaDaoJDBC.getContainsMarker(lat, lng));
		
	}
	
	private List<TiempoDetail> getTiempoDetails(List<PronosticoDetail> pronosticoDetails) {
		Calendar calAfterNow = Calendar.getInstance();
		calAfterNow.setTime(now);
		calAfterNow.add(Calendar.DAY_OF_YEAR, 1);
		Date afterNow = calAfterNow.getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<TiempoDetail> tiempoDetails = new ArrayList<>();
		pronosticoDetails.forEach(pronosticoDetail -> {
			Date afterDATE;
			try {
				afterDATE = dateFormat.parse(dateFormat.format(afterNow));
			} catch (ParseException e) {
				afterDATE = null;
			}
			if (pronosticoDetail.getCodigoVentana() == 3L) {
				if(afterDATE.before(pronosticoDetail.getTiempoInicial())) {
					tiempoDetails.add(getTiempoDetail(pronosticoDetail, Boolean.FALSE));
				}else {
					tiempoDetails.add(getTiempoDetail(pronosticoDetail, Boolean.TRUE));
				}
			}else {
				if(afterDATE.before(pronosticoDetail.getTiempoFinal())) {
					tiempoDetails.add(getTiempoDetail(pronosticoDetail, Boolean.FALSE));
				}else {
					tiempoDetails.add(getTiempoDetail(pronosticoDetail, Boolean.TRUE));
				}
			}
		});
		return tiempoDetails;
	}
	
	private TiempoDetail getTiempoDetail(PronosticoDetail pronosticoDetail, boolean actual) {
		TiempoDetail tiempoDetail = new TiempoDetail();
		tiempoDetail.setTiempo(pronosticoDetail.getTiempo());
		tiempoDetail.setDescripcion(pronosticoDetail.getDescripcion());
		tiempoDetail.setUrlIcono(pronosticoDetail.getUrlIcono());
		if(actual) {
			tiempoDetail.setEtiqueta("ACTUAL");
		}else {
			tiempoDetail.setEtiqueta("PRONOSTICO");
		}
		return tiempoDetail;
	}
	
	
}
