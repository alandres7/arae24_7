package co.gov.metropol.area247.service;

import java.util.Date;
import java.util.List;

import co.gov.metropol.area247.services.rest.opt.PosibilidadViajeWSDTO;
import co.gov.metropol.area247.util.web.Coordenada;

public interface IPosibilidadViajeService {

	/**
	 * Consulta los posibles caminos que hay de un punto de origen hasta un
	 * destino segun el o los modos de transporte indicados:
	 * 
	 * @param origen
	 *            - contiene la latitud y longitud del punto origen
	 * @param destino
	 *            - contiene la latitud y longitud del punto de destino
	 * @param fecha
	 *            - la fecha en la que se realiza la consulta
	 * @param modoTransporte
	 *            - cadena que contiene los modos de transporte por
	 *            los cuales consultar los posibles caminos
	 * 
	 * @return un objeto {@link PosibilidadViajeWSDTO} que contiene la informacion
	 *         de los caminos encontrados
	 */
	PosibilidadViajeWSDTO consultarPosiblesViajes(Coordenada origen, Coordenada destino, Date fecha,
			List<Long> modoTransporte);

}
