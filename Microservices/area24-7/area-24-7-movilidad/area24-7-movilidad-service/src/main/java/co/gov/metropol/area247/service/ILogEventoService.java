package co.gov.metropol.area247.service;

import co.gov.metropol.area247.repository.domain.LogEvento;
import co.gov.metropol.area247.util.ex.Area247Exception;

public interface ILogEventoService {

	/**
	 * Persiste en base de datos la informacion del log de algun evento, ya sean
	 * resultados de consumo de servicios web, entre otros.
	 * 
	 * @param logEvento
	 *            - contiene la informacion del log.
	 * @throws Area247Exception
	 *             - Excepion
	 */
	void saveLogEvento(LogEvento logEvento);

}
