package co.gov.metropol.area247.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.core.domain.context.web.Capa;
import co.gov.metropol.area247.core.domain.context.web.Categoria;
import co.gov.metropol.area247.core.repository.CategoriaRepository;
import co.gov.metropol.area247.core.service.CapaService;
import co.gov.metropol.area247.core.service.ex.ServiceException;

@Service
public class CapaServiceImpl implements CapaService {

	private static Logger LOGGER = LoggerFactory.getLogger(CapaServiceImpl.class);

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public List<Capa> obtenerCapasConSuCategoriasPorTiposCapaCategoriaOAplicacion(String tipoCategorias,
			String tipoCapas, Long idAplicacion) throws ServiceException {
		try {
			List<Categoria> categorias = categoriaRepository
					.obtenerCategoriasPorTipoCapasOrTipoCategoria(tipoCategorias, tipoCapas, idAplicacion);

			List<Capa> capas = categorias.stream().map(Categoria::getCapa).filter(distinctByKey(cap -> cap.getId()))
					.collect(Collectors.toList());

			capas.forEach(capa -> {
				capa.setCategorias(categorias.stream()
						.filter(categoria -> categoria.getCapa().getId().longValue() == capa.getId().longValue())
						.collect(Collectors.toList()));
			});
			return capas;
		} catch (Exception e) {
			LOGGER.error("Error al consultar la informacion por tipo categorias o tipo capas o aplicacion --{}{}",
					(tipoCapas + tipoCategorias + idAplicacion), e);
			throw new ServiceException(
					"Error al consultar la informacion por tipo categorias o tipo capas o aplicacion --{}{}", e);
		}
	}

	
	@Override
	public List<Capa> obtenerCapasPorIdAplicacion(Long idAplicacion) throws ServiceException {
		try {

			List<Categoria> categorias = categoriaRepository.obtenerCategoriasPorIdAplicacion(idAplicacion);

			List<Capa> capas = categorias.stream().map(Categoria::getCapa).filter(distinctByKey(cap -> cap.getId()))
					.collect(Collectors.toList());

			capas.forEach(capa -> {
				capa.setCategorias(
						categorias.stream().filter(cat -> cat.getCapa().getId().longValue() == capa.getId().longValue())
								.collect(Collectors.toList()));
			});

			return capas;
		} catch (Exception e) {
			LOGGER.error("Error en el servicio de la capa al consultar la informacion por id aplicacion --{}{}",
					idAplicacion, e);
			throw new ServiceException(
					"Error en el servicio de la capa al consultar la informacion por id aplicacion --{}{}", e);
		}
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
