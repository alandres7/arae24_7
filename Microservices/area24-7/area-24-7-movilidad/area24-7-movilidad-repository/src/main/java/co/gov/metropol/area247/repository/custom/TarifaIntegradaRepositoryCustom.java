package co.gov.metropol.area247.repository.custom;

import java.util.List;

public interface TarifaIntegradaRepositoryCustom {

	/**
	 * Este metodo obtiene la tarifa segun los modos de transporte indicados
	 * como argumneto, ej. Metro(2) y Tranvia(0) = 2190
	 * 
	 * @param idsModosTrasnporte - filtros de busqueda
	 *            
	 * @return - el valor de la tarifa que corresponde a la combinacion indicada
	 */
	Double getTarifaByCombinaciones(List<Long> idsModosTrasnporte);
	
	/**
	 * Verifica si existe tarifa para la combinacion de medios de transporte
	 * indicados en el argumento de entrada
	 * 
	 * @param idsModosTrasnporte
	 *            - filtros de busqueda
	 * 
	 * @return <code>true</code> si existe tarifa para la combinacion dada,
	 *         <code>false</code> en caso contrario.
	 */
	boolean existeCombinacion(List<Long> idsModosTrasnporte);

}
