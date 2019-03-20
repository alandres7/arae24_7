package co.gov.metropol.area247.batch.processor;

import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.annotations.Log;
import co.gov.metropol.area247.annotations.LogReturnValue;
import co.gov.metropol.area247.batch.abstracts.AbstractRuntItemProcessor;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.RuntDTO;
import co.gov.metropol.area247.model.TipoArchivoDTO;
import co.gov.metropol.area247.repository.domain.EstructuraDeArchivo;
import co.gov.metropol.area247.repository.domain.VehiculosRunt;
import co.gov.metropol.area247.service.ITipoArchivoService;
import co.gov.metropol.area247.util.StringUtils;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Component
public class RuntItemProcessor extends AbstractRuntItemProcessor implements ItemProcessor<RuntDTO, VehiculosRunt> {
		
	@Autowired
	private ITipoArchivoService tipoArchivoService;
	
	private boolean isValido = false;

	@Override
	@LogReturnValue
	public VehiculosRunt process(@Log RuntDTO runtDTO) throws Exception {
		try {
			TipoArchivoDTO tipoArchivo = tipoArchivoService.findByName("RUNT");			
			if(Utils.isNull(repository.findByPlaca(runtDTO.getPlaca())) && !Utils.isNull(tipoArchivo)){
				isValido = validarEstructura(tipoArchivo.getEstructura(), runtDTO);
				
				if(isValido) {
					return mapper.toVehiculosRunt(runtDTO);
				}
				
			}
			return null;
			
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al transformar el objeto de vehiculos runt a la entidad %s", runtDTO), e);
			throw new Area247Exception(String.format("Error al transformar el objeto de vehiculos runt a la entidad %s", runtDTO), e);
		}
	}
	
	@LogReturnValue
	private boolean validarEstructura(@Log Set<EstructuraDeArchivo> estructuraDeArchivo, @Log RuntDTO vehiculo) {
		isValido = true;
		estructuraDeArchivo.iterator().forEachRemaining(estructura ->{
			if(estructura.isRequerido()) {
				try {
					Object validate = PropertyUtils.getProperty(vehiculo, StringUtils.valueToCamelCase(estructura.getNombre()));
					
					if(validate instanceof String) {
						String result = (String) validate;
						if(Utils.isNull(result)) {
							isValido = false;
						}else if(estructura.getLength() < result.length()) {
							isValido = false;
						}
					}else if(validate instanceof Long) {
						Long result = (Long) validate;
						if(Utils.isNull(result)) {
							isValido = false;
						}else if(estructura.getLength() < result) {
							isValido = false;
						}
					}
					
				} catch (Exception e) {
					LoggingUtil.logException(String.format("Error al validar la estructura %s de los valores del runt %s.", estructura, vehiculo), e);
					throw new Area247Exception(String.format("Error al validar la estructura %s de los valores del runt %s.", estructura, vehiculo), e);
				}
			}
		});
		return isValido;
	}

}
