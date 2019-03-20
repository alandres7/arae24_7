package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.repository.custom.TarifaIntegradaRepositoryCustom;
import co.gov.metropol.area247.service.ITarifaIntegradaMetroService;
import co.gov.metropol.area247.util.Utils;

@Service
public class TarifaIntegradaMetroServiceImpl implements ITarifaIntegradaMetroService {

	@Autowired
	private TarifaIntegradaRepositoryCustom repository;

	@Override
	public Double obtenerTarifaPorCombinaciones(List<Long> idsModosTransporte) {

		try {
			if (Utils.isNotEmpty(idsModosTransporte)) {
				//
				List<Long> combinacionExistente = encontrarCombinacionExistente(Lists.newArrayList(),
						idsModosTransporte);

				if (Utils.isNotEmpty(combinacionExistente)) {
					return repository.getTarifaByCombinaciones(combinacionExistente);
				}

			}
			return 0.0;

		} catch (Exception e) {
			return 0.0;

		}

	}

	/**
	 * Va creando combinaciones y va verificando la existencia de esta
	 * combinacion el la base de datos
	 * 
	 * @param combinacionAComparar
	 *            - inicia como una lista vacia, este argumento es valido en el
	 *            llamado recursivo
	 * @param idsModosTransporte
	 *            - los ids del modo de transporte a combinar
	 * 
	 * @return la lista con la combinacion existente en la base de datos o una
	 *         lista vacia si no lo encontro
	 */
	private List<Long> encontrarCombinacionExistente(List<Long> combinacionAComparar, List<Long> idsModosTransporte) {

		if (idsModosTransporte.size() == 1) {
			combinacionAComparar.add(idsModosTransporte.get(0));
			if (repository.existeCombinacion(combinacionAComparar)) {
				return combinacionAComparar;
			}
			combinacionAComparar.remove(idsModosTransporte.get(0));
		}
		for (int i = 0; i < idsModosTransporte.size(); i++) {
			Long b = idsModosTransporte.remove(i);
			combinacionAComparar.add(b);
			encontrarCombinacionExistente(combinacionAComparar, idsModosTransporte);
			idsModosTransporte.add(i, b);
			combinacionAComparar.remove(b);
		}

		return combinacionAComparar;
	}

}
