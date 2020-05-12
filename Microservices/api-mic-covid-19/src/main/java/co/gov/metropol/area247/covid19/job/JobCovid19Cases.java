package co.gov.metropol.area247.covid19.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.covid19.svc.ICaseSvc;

@Component
@Profile("!prod")
public class JobCovid19Cases {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(JobCovid19Cases.class);
	
	@Autowired
	private ICaseSvc caseSvc;
	
	@Scheduled(cron = "${update.covid19.cases}")
	private void updateCovid19Cases() {
		LOGGER.info("Se ejecutará la tarea de actualización de los casos de COVID-19");
		try {
			caseSvc.updateCasosCovid();
			LOGGER.info("Se ejecutó la tarea de actualización de los casos de COVID-19");
		}catch (Exception e) {
			LOGGER.error("Error ejecutando tarea de actualización de los casos de COVID-19", e);
		}
		
	}

}
