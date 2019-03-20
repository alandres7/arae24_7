package co.gov.metropol.area247.contenedora.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.contenedora.mapper.IContenedoraMapper;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.core.domain.marker.dto.Marker;

@Component("markerLteMapper")
public class MarkerMapper implements IContenedoraMapper<Marcador, Marker> {

	@Override
	public Marcador modelToEntity(Marker model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Marker entityToModel(Marcador entity) {
		Marker model = new Marker();
		ModelMapper mapper = new ModelMapper();
		model = mapper.map(entity, Marker.class);
		return model;
	}

}
