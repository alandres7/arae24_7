package co.gov.metropol.area247.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.repository.LogEventoRepository;
import co.gov.metropol.area247.repository.domain.LogEvento;
import co.gov.metropol.area247.service.ILogEventoService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class LogEventoServiceImpl implements ILogEventoService {

	@Autowired
	private LogEventoRepository repository;

	@Override
	public void saveLogEvento(LogEvento logEvento) {
		try {
			if (!Utils.isNull(logEvento))
				repository.save(logEvento);
		} catch (Exception e) {
			throw new Area247Exception("Ocurrio un error al guardar el log del evento", e.getCause());
		}

	}

}
