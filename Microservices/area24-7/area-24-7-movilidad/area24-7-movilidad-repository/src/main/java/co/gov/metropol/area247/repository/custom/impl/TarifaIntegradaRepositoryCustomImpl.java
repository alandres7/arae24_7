package co.gov.metropol.area247.repository.custom.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.gov.metropol.area247.repository.custom.TarifaIntegradaRepositoryCustom;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Repository
@Transactional(readOnly = true)
public class TarifaIntegradaRepositoryCustomImpl implements TarifaIntegradaRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Cacheable("tarifasCombinaciones")
	public Double getTarifaByCombinaciones(List<Long> idsModosTransporte) {

		try {
			Query query = entityManager.createNativeQuery(crearSQL("N_VALOR_TARIFA", idsModosTransporte));
			fijarParametros(idsModosTransporte, query);

			return ((BigDecimal) query.getSingleResult()).doubleValue();

		} catch (NoResultException e) {

			String ids = idsModosTransporte.stream().map(x -> x.toString()).collect(Collectors.joining(", "));
			throw new Area247Exception(String.format("No encontro tarifas para la combinacion %s", ids), e.getCause());

		} catch (NonUniqueResultException e) {

			String ids = idsModosTransporte.stream().map(x -> x.toString()).collect(Collectors.joining(", "));
			throw new Area247Exception(String.format("Encontro mas de una tarifa para la combinacion %s", ids),
					e.getCause());
		}

	}

	private static String crearSQL(String seleccion, List<Long> idsModosTransporte) {
		
		final int maxModosTransporte = 5;
		final int tamIdsModosTransporte = idsModosTransporte.size();
		
		StringBuilder sql = new StringBuilder(
				"SELECT %s FROM MOVILIDAD.D247_TARIFA_INTEGRADA_METRO WHERE ID_TIPO_MODO_TRANSPORTE = ?");

		for (int i = 2; i <= maxModosTransporte; i++) {
			if (i <= tamIdsModosTransporte)
				sql.append(" AND ID_TIPO_MODO_TRANSPORTE_" + i + " = ?");
			else
				sql.append(String.format(" AND ID_TIPO_MODO_TRANSPORTE_%s IS NULL", i));
		}
		
		return String.format(sql.toString(), seleccion);
	}

	private static void fijarParametros(List<Long> idsModosTransporte, Query query) {

		final int maxModosTransporte = 5;
		final int tamIdsModosTransporte = idsModosTransporte.size();

		query.setParameter(1, idsModosTransporte.get(0));

		for (int i = 0; i < maxModosTransporte; i++) {
			if (i < tamIdsModosTransporte)
				query.setParameter((i + 1), idsModosTransporte.get(i));
		}
	}

	@Override
	public boolean existeCombinacion(List<Long> idsModosTransporte) {

		try {
			
			Query query = entityManager.createNativeQuery(crearSQL("COUNT(*)", idsModosTransporte));
			fijarParametros(idsModosTransporte, query);

			return (((BigDecimal) query.getSingleResult()).longValue() > 0);

		} catch (Exception e) {
			throw new Area247Exception("Error al verificar la existencia de la tarifa por la combinacion",
					e.fillInStackTrace());
		}
	}

}
