package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.model.ConCatUbicacionFavoritaDTO;
import co.gov.metropol.area247.model.mapper.ConCatUbicacionFavoritaMapper;
import co.gov.metropol.area247.repository.ConCatUbicacionFavoritaRepository;
import co.gov.metropol.area247.repository.domain.ConCatUbicacionFavorita;
import co.gov.metropol.area247.service.ICatUbicacionFavoritaService;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class CatUbicacionFavoritaServiceImpl implements ICatUbicacionFavoritaService {

	@Autowired
	private ConCatUbicacionFavoritaRepository repository;

	@Autowired
	private ConCatUbicacionFavoritaMapper mapper;

	@Override
	public List<ConCatUbicacionFavoritaDTO> getAll() {
		try {
			Iterable<ConCatUbicacionFavorita> categorias = repository.findAll();
			return mapper.mapToConCatUbicacionFavoritaDTO(Lists.newArrayList(categorias));
		} catch (Exception e) {
			throw new Area247Exception("Error al obtener todas las categorias de las ubicaciones favoritas");
		}
	}

}
