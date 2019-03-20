package co.gov.metropol.area247.seguridad.dao;

import java.util.List;

import co.gov.metropol.area247.seguridad.model.Municipio;

public interface ISeguridadMunicipioRepositoryCustom {
	public List<Municipio> coordenadaInterceptoMunicipio(double lat, double lng);
}
