package co.gov.metropol.area247.seguridad.service;

import java.util.List;

import com.google.maps.model.LatLng;

import co.gov.metropol.area247.core.domain.marker.dto.Town;
import co.gov.metropol.area247.core.domain.marker.dto.TownPolygon;
import co.gov.metropol.area247.seguridad.model.Municipio;

public interface ISeguridadMunicipioService {
	
	Municipio municipioObtenerPorId(Long municipioId);
	Municipio municipioObtenerPorNombre(String municipioNombre);
	List<Municipio> municipioListarTodos();
	Municipio getByNombre(String nomMunicipio, String nomDepartamento);
	List<Municipio> getByValleAburra();
	boolean guardarMunicipio(Municipio municipio);
	TownPolygon getPolygonMunicipio(Long id);
	List<LatLng> getPolygonAMVA();
	List<Town> getTowns();
	String municipioXUbicacion(double lat, double lng);

}
