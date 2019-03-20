package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.gov.metropol.area247.model.UsuarioDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.UsuarioRepository;
import co.gov.metropol.area247.repository.domain.Usuario;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class UsuarioMapper{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@ObjectFactory
	protected Usuario createUser(UsuarioDTO userDTO) {
		if(!Utils.isNull(userDTO) && !Utils.isNull(userDTO.getId())){
			return usuarioRepository.findOne(userDTO.getId());
		}
		return new Usuario();
	}
	
	public abstract UsuarioDTO toUsuarioDTO(Usuario usuario);
	
	public abstract List<UsuarioDTO> mapToUsuarioDTO (List<Usuario> usuarios);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "enabled", ignore = true),
	})
	public abstract Usuario toUsuario(UsuarioDTO usuarioDTO);
}
