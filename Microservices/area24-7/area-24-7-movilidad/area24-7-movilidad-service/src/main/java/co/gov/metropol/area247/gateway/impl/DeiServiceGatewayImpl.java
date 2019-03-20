package co.gov.metropol.area247.gateway.impl;

import org.springframework.stereotype.Service;

import co.gov.metropol.area247.gateway.IDeiServiceGateway;
import co.gov.metropol.area247.services.rest.dei.DeiWSDTO;

@Service
public class DeiServiceGatewayImpl implements IDeiServiceGateway {

	//private static final Logger LOGGER = LoggerFactory.getLogger(DeiServiceGatewayImpl.class);
	
	@Override
	public DeiWSDTO consultarDei() {
		return null;
	}

}
