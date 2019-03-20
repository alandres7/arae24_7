package co.gov.metropol.area247.service.impl.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.mapper.ConDireccionFavoritaMapper;
import co.gov.metropol.area247.repository.ConDireccionFavoritaRepository;
import co.gov.metropol.area247.service.IConCatUbicacionFavoritaService;

public class AbstractConDireccionFavoritaService {

	@Autowired
	protected ConDireccionFavoritaMapper mapper;
	
	@Autowired
	protected ConDireccionFavoritaRepository repository;
	
	@Autowired
	protected IConCatUbicacionFavoritaService conCatUbicacionFavoritaService;
}
