package co.gov.metropol.area247.contenedora.util;

import java.util.ArrayList;
import java.util.List;

import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.model.dto.CoordenadaDtoM;
import co.gov.metropol.area247.contenedora.model.dto.MarcadorDto;

public class MarcadoresUtils {
	
	
	public static List<MarcadorDto> getListaMarkerDto(List<Marcador> listaMarcadores) {		
		List<MarcadorDto> listaMarcadorDto = new ArrayList<MarcadorDto>();
		for (Marcador marcador : listaMarcadores) {
			MarcadorDto marcadorDto = new MarcadorDto();
			marcadorDto.setId(marcador.getId());
			marcadorDto.setNombre(marcador.getNombre());
			marcadorDto.setDescripcion(marcador.getDescripcion());
			marcadorDto.setIcono(marcador.getIcono());
			marcadorDto.setColorBorde(marcador.getColorBorde());
			marcadorDto.setColorFondo(marcador.getColorFondo());
			marcadorDto.setVentanaInformacion(marcador.getVentanaInformacion());			
			List<CoordenadaDtoM> listaCoordenadaDtoM = new ArrayList<CoordenadaDtoM>();
			
			CoordenadaDtoM coordenadaDtoM = new CoordenadaDtoM();
				coordenadaDtoM.setCoordenadaPunto((List<String>)marcador.getCoordenadaPunto());
				coordenadaDtoM.setCoordenadaPolygon((List<String>)marcador.getCoordenadaPolygon());
				listaCoordenadaDtoM.add(coordenadaDtoM);
			marcadorDto.setCoordenadas(listaCoordenadaDtoM);
			listaMarcadorDto.add(marcadorDto);	
		}		
	    return listaMarcadorDto;	
	}
	
	
	public static MarcadorDto getMarkerDto(Marcador marcador) {		
	    MarcadorDto marcadorDto = new MarcadorDto();
		marcadorDto.setId(marcador.getId());
		marcadorDto.setNombre(marcador.getNombre());
		marcadorDto.setDescripcion(marcador.getDescripcion());
		marcadorDto.setIcono(marcador.getIcono());
		marcadorDto.setColorBorde(marcador.getColorBorde());
		marcadorDto.setColorFondo(marcador.getColorFondo());
		marcadorDto.setVentanaInformacion(marcador.getVentanaInformacion());			
		List<CoordenadaDtoM> listaCoordenadaDtoM = new ArrayList<CoordenadaDtoM>();
			
		CoordenadaDtoM coordenadaDtoM = new CoordenadaDtoM();
			coordenadaDtoM.setCoordenadaPunto((List<String>)marcador.getCoordenadaPunto());
			coordenadaDtoM.setCoordenadaPolygon((List<String>)marcador.getCoordenadaPolygon());
			listaCoordenadaDtoM.add(coordenadaDtoM);						
		marcadorDto.setCoordenadas(listaCoordenadaDtoM);						
		return marcadorDto;		
	}
	

}
