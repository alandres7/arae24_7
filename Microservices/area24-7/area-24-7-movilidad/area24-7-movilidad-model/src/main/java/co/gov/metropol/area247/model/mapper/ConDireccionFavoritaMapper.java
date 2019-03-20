package co.gov.metropol.area247.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.ConCatUbicacionFavoritaDTO;
import co.gov.metropol.area247.model.ConDireccionFavoritaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.ConDireccionFavoritaRepository;
import co.gov.metropol.area247.repository.domain.ConDireccionFavorita;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class ConDireccionFavoritaMapper {

	@Autowired
	private ConDireccionFavoritaRepository repository;

	@ObjectFactory
	protected ConDireccionFavorita createConDireccionFavorita(ConDireccionFavoritaDTO direccionDTO) {
		if (!Utils.isNull(direccionDTO)) { 
			
			ConDireccionFavorita conDireccionFavorita = null; 
			
			if (!Utils.isNull(direccionDTO.getId())) {
				conDireccionFavorita = repository.findOne(direccionDTO.getId());
			}
			
			if (!Utils.isNull(conDireccionFavorita)) {
				conDireccionFavorita = new ConDireccionFavorita();
			}
			
			if (null != conDireccionFavorita) {
				conDireccionFavorita.setNombre(direccionDTO.getNombre());
				conDireccionFavorita.setDescripcion(direccionDTO.getDescripcion());
				conDireccionFavorita.setIdCoordenada(direccionDTO.getIdCoordenada());
				conDireccionFavorita.setIdUsuario(direccionDTO.getIdUsuario());
				conDireccionFavorita.setCoordenada(direccionDTO.getCoordenada());
				conDireccionFavorita.setIdCategoria(
						Utils.isNull(direccionDTO.getCategoriaDTO()) ? null : direccionDTO.getCategoriaDTO().getId());
			}
			
			return conDireccionFavorita;
		}
		
		return new ConDireccionFavorita();
	}
	
	@ObjectFactory
	protected ConDireccionFavoritaDTO createConDireccionFavoritaDTO(ConDireccionFavorita direccion) {
		
		ConDireccionFavoritaDTO  conDireccionFavoritaDTO = new ConDireccionFavoritaDTO();
		
		if (!Utils.isNull(direccion)) {
			if (!Utils.isNull(direccion.getIdCategoria())) {
				ConCatUbicacionFavoritaDTO categoriaDTO = new ConCatUbicacionFavoritaDTO();
				categoriaDTO.setId(direccion.getIdCategoria());
				conDireccionFavoritaDTO.setCategoriaDTO(categoriaDTO);
			}
		}
		
		return conDireccionFavoritaDTO;
		
	}

	public abstract ConDireccionFavoritaDTO toConDireccionFavoritaDTO(ConDireccionFavorita direccion);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract ConDireccionFavorita toConDireccionFavorita(ConDireccionFavoritaDTO entity);
}
