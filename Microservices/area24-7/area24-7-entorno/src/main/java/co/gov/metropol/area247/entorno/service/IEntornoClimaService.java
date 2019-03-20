package co.gov.metropol.area247.entorno.service;

import co.gov.metropol.area247.entorno.model.dto.ClimaDetail;

public interface IEntornoClimaService {

	ClimaDetail getDetailClima(Long idMarcador);

	ClimaDetail getDetailClimaXLocation(double lat, double lng);

}
