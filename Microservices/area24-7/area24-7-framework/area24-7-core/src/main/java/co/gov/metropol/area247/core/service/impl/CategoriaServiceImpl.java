package co.gov.metropol.area247.core.service.impl;

import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.core.domain.context.web.Categoria;
import co.gov.metropol.area247.core.repository.CategoriaRepository;
import co.gov.metropol.area247.core.service.CategoriaService;
import co.gov.metropol.area247.core.service.ex.ServiceException;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	private static Logger LOGGER = LoggerFactory.getLogger(CategoriaServiceImpl.class);

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public List<Categoria> obtenerCategoriaPorCapaYTipoCategorias(Long idCapa, String tipoCategorias)
			throws ServiceException {
		try {
			return categoriaRepository.obtenerCategoriaPorCapaYTipoCategorias(idCapa, tipoCategorias);
		} catch (Exception e) {
			LOGGER.error("Error al consultar la lista de categoria por id capa --{}{}", idCapa, e);
			throw new ServiceException("Error al consultar la lista de categoria por id capa --{}{}", e);
		}
	}

}
