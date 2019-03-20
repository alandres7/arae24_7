package co.gov.metropol.area247.contenedora.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.contenedora.mapper.IContenedoraMapper;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.core.domain.capa.dto.CapaN;

@Component("categoriaLteMapper")
public class CategoriaMapper implements IContenedoraMapper<Categoria, CapaN>{

	@Override
	public Categoria modelToEntity(CapaN model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CapaN entityToModel(Categoria entity) {
		CapaN model = new CapaN();
		ModelMapper mapper = new ModelMapper();
		model = mapper.map(entity, CapaN.class);
		return model;
	}

}