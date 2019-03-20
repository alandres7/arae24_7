package co.gov.metropol.area247.seguridad.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.seguridad.mapper.ISeguridadMapper;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.model.dto.UsuarioDto;

@Component("usuarioMapper")
public class UsuarioMapper implements ISeguridadMapper<Usuario, UsuarioDto> {

	@Override
	public Usuario modelToEntity(UsuarioDto model) {
		Usuario entity = new Usuario();
		ModelMapper mapper = new ModelMapper();
		entity = mapper.map(model, Usuario.class);
		return entity;
	}

	@Override
	public UsuarioDto entityToModel(Usuario entity) {
		UsuarioDto model = new UsuarioDto();
		ModelMapper mapper = new ModelMapper();
		model = mapper.map(entity, UsuarioDto.class);
		return model;
	}
	
}
