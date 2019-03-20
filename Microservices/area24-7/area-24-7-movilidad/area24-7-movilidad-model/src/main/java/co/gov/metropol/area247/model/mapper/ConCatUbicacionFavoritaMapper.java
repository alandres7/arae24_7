package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import co.gov.metropol.area247.model.ConCatUbicacionFavoritaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.domain.ConCatUbicacionFavorita;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class ConCatUbicacionFavoritaMapper {

	public abstract ConCatUbicacionFavoritaDTO toConCatUbicacionFavoritaDTO(ConCatUbicacionFavorita entity);

	public abstract List<ConCatUbicacionFavoritaDTO> mapToConCatUbicacionFavoritaDTO(
			List<ConCatUbicacionFavorita> categorias);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "enabled", ignore = true), })
	public abstract ConCatUbicacionFavorita toConCatUbicacionFavorita(ConCatUbicacionFavoritaDTO dto);

}
