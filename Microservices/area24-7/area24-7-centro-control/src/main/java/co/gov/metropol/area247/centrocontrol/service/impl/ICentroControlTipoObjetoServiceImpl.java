package co.gov.metropol.area247.centrocontrol.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dao.ICentroControlTipoObjetoRepository;
import co.gov.metropol.area247.centrocontrol.model.TipoObjeto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlTipoObjetoService;

@Service
public class ICentroControlTipoObjetoServiceImpl implements ICentroControlTipoObjetoService {

	@Autowired
	ICentroControlTipoObjetoRepository tipoObjetoDao;	
	
	private final Logger LOGGER = LoggerFactory.getLogger(ICentroControlTipoObjetoServiceImpl.class);
	
	@Override
	public TipoObjeto tipoObjetoObtenerPorId(Long idTipoObjeto) {
		TipoObjeto tipoObjeto = tipoObjetoDao.findOne(idTipoObjeto);
		if(tipoObjeto==null){
			LOGGER.info("No se encuentra un tipo de objeto con id " + idTipoObjeto);
		}
		return tipoObjeto;
	}

}
