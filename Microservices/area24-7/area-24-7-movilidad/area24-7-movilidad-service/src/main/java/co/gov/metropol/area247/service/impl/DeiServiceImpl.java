package co.gov.metropol.area247.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.service.IDeiService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractDeiService;

@Service
public class DeiServiceImpl extends AbstractDeiService implements IDeiService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeiServiceImpl.class);
	
	// @Autowired
	// private IDeiServiceGateway deiServiceGateway;
	
	@Override
	public void cargarCirculacionDeVehiculos() {
		LOGGER.info("Dei Service");
	}

}
