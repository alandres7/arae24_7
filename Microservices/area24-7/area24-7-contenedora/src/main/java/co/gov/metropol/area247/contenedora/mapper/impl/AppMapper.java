package co.gov.metropol.area247.contenedora.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.contenedora.mapper.IContenedoraMapper;
import co.gov.metropol.area247.contenedora.model.Aplicacion;
import co.gov.metropol.area247.contenedora.model.dto.App;

@Component("appMapper")
public class AppMapper implements IContenedoraMapper<Aplicacion, App> {

	@Override
	public Aplicacion modelToEntity(App model) {
		Aplicacion entity = new Aplicacion();
		ModelMapper mapper = new ModelMapper();
		entity = mapper.map(model, Aplicacion.class);
		return entity;
	}

	@Override
	public App entityToModel(Aplicacion entity) {
		App model = new App();
		ModelMapper mapper = new ModelMapper();
		model = mapper.map(entity, App.class);
		return model;
	}

}
