package co.gov.metropol.area247.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

import co.gov.metropol.area247.model.RutaConcretaDTO;
import co.gov.metropol.area247.model.mapper.abstracts.MapperConfig;
import co.gov.metropol.area247.repository.domain.LineaMetro;
import co.gov.metropol.area247.repository.domain.Ruta;
import co.gov.metropol.area247.util.Utils;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public abstract class RutaConcretaMapper {
	
	private static final char LINEA = 'L';
	private static final char RUTA = 'R';
	
	@ObjectFactory
	protected RutaConcretaDTO createRutaConcretaDTO(Object objeto) {

		RutaConcretaDTO rutaConcretaDTO = new RutaConcretaDTO();

		if (!Utils.isNull(objeto)) {
			
			if (objeto instanceof LineaMetro) {
				LineaMetro linea = (LineaMetro) objeto;
				rutaConcretaDTO.setId(linea.getId());
				rutaConcretaDTO.setCodigo(linea.getCodigo());
				rutaConcretaDTO.setDescripcion(linea.getDescripcion());
				rutaConcretaDTO.setTipo(LINEA);
			} else if (objeto instanceof Ruta) {
				Ruta ruta = (Ruta) objeto;
				rutaConcretaDTO.setId(ruta.getId());
				rutaConcretaDTO.setCodigo(ruta.getCodigo());
				rutaConcretaDTO.setDescripcion(ruta.getDescripcion());
				rutaConcretaDTO.setTipo(RUTA);
			}
			
		}

		return rutaConcretaDTO;
	}

	public abstract RutaConcretaDTO toRutaConcretaDTO(Object objeto);
	
	public abstract List<RutaConcretaDTO> mapToRutaConcretaDTO(List<Object> objetos);
	
}
