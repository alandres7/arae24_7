package co.gov.metropol.area247.centrocontrol.carga.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.carga.service.IFormatoCargaService;
import co.gov.metropol.area247.centrocontrol.common.Area247Exception;
import co.gov.metropol.area247.centrocontrol.model.FormatoCarga;
import co.gov.metropol.area247.centrocontrol.repository.FormatoCargaRepository;

@Service
public class FormatoCargaServiceImpl implements IFormatoCargaService {
	private static Logger LOGGER = LoggerFactory.getLogger(FormatoCargaServiceImpl.class);

	@Autowired
	private FormatoCargaRepository repository;
	
	@Override
	public List<FormatoCarga> findAll() {
		try {
			return (List<FormatoCarga>) repository.findAll();
		} catch (Exception e) {
			LOGGER.error(String.format("Error al consultar formatos de carga."), e);
			throw new Area247Exception(String.format("Error al consultar formatos de carga."), e);
		}
	}

}
