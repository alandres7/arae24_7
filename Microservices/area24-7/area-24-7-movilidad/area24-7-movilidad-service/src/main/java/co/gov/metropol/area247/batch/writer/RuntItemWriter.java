package co.gov.metropol.area247.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.annotations.Log;
import co.gov.metropol.area247.batch.abstracts.AbstractRuntItemProcessor;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.repository.domain.VehiculosRunt;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Component
public class RuntItemWriter  extends AbstractRuntItemProcessor implements ItemWriter<VehiculosRunt>{
	
	/**
	 * Componente creado para guardar los objetos de vehiculos del RUNT
	 * */
	@Override
	public void write(@Log List<? extends VehiculosRunt> vehiculosRunt) throws Exception {
		try {
			vehiculosRunt.forEach(vehiculo -> {
				repository.save(vehiculo);
			});
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al guardar el archivo del runt %s", vehiculosRunt), e);
			throw new Area247Exception(String.format("Error al guardar el archivo del runt %s", vehiculosRunt), e);
		}
		
	}

}
