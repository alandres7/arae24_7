package co.gov.metropol.area247.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.ConCatUbicacionFavoritaDTO;
import co.gov.metropol.area247.model.mapper.ConCatUbicacionFavoritaMapper;
import co.gov.metropol.area247.repository.ConCatUbicacionFavoritaRepository;
import co.gov.metropol.area247.repository.domain.ConCatUbicacionFavorita;
import co.gov.metropol.area247.service.IConCatUbicacionFavoritaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class ConCatUbicacionFavoritaServiceImpl implements IConCatUbicacionFavoritaService {

	@Autowired
	private ConCatUbicacionFavoritaMapper mapper;
	
	@Autowired
	private ConCatUbicacionFavoritaRepository repository;
	
	@Override
	public ConCatUbicacionFavoritaDTO findById(Long id) {
		try {
			if (!Utils.isNull(id)) {
				ConCatUbicacionFavorita categoria = repository.findById(id);
				return mapper.toConCatUbicacionFavoritaDTO(categoria);
			} 
			return null;
		} catch (Exception e) {
			throw new Area247Exception(String.format("Error al consultar la categoria de la ubicacion favorita por el id s%.", id),
					e);
		}
	}

}
