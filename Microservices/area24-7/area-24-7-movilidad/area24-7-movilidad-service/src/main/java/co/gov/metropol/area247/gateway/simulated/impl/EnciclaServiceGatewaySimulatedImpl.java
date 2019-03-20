package co.gov.metropol.area247.gateway.simulated.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.constants.PerfilesAplicacion;
import co.gov.metropol.area247.gateway.IEnciclaServiceGateway;
import co.gov.metropol.area247.services.rest.encicla.EnciclaWSDTO;
import co.gov.metropol.area247.services.rest.encicla.FeatureWSDTO;

@Primary
@Service
@Profile(PerfilesAplicacion.ENCICLA_SIMULADO)
public class EnciclaServiceGatewaySimulatedImpl implements IEnciclaServiceGateway{

	@Override
	public EnciclaWSDTO consultarEstatusEncicla() {
		return null;
		
	}

	@Override
	public List<FeatureWSDTO> consultarCicloRutas() {
		return null;
	}

}
