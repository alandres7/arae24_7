package co.gov.metropol.area247.gateway;

import java.util.List;

import co.gov.metropol.area247.services.rest.gtpc.RutaGtpcWSDTO;
import co.gov.metropol.area247.services.rest.gtpc.ViajesGtpcWSDTO;

public interface IGtpcServiceGateway {

	/**
	 * Obtener informacion de las rutas del servicio GTPC
	 * @return lista de objetos {@link RutaGtpcWSDTO}
	 */
	List<RutaGtpcWSDTO> cargarInformacionRutas();
	
	/**
	 * Obtiene la informacion de los viajes que realizan las rutas en un dia en
	 * especifico, este dia es definido como argumento de entrada
	 * <P>
	 * Creado 3/10/2018 2:35 p.m
	 * 
	 * @param fecha - debe tener el formato ddMMyyyy , ejemplo 03102018
	 * 
	 * @return la informacion de los viajes realizados por las rutas de GTPC en
	 *         el dia fijado como parametro del servicio web
	 */
	List<ViajesGtpcWSDTO> cargarInformacionViajesRuta(String fecha);
}
