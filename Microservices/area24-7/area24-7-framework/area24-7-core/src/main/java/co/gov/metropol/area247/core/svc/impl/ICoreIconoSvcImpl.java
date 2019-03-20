package co.gov.metropol.area247.core.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.core.repository.IconoRepository;
import co.gov.metropol.area247.core.svc.ICoreIconoSvc;

@Service
public class ICoreIconoSvcImpl implements ICoreIconoSvc {
	
	@Autowired
	IconoRepository iconoDaoJDBC;
	
	@Override
	public Long getIdIconoPronostico(long idMarcador) {
		return iconoDaoJDBC.getIconoClimaActual(idMarcador);
	}
	
}
