package co.gov.metropol.area247.services.rest.encicla;

import java.util.ArrayList;
import java.util.List;

import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.model.CiclorutaDTO;
import co.gov.metropol.area247.services.rest.metro.CoordenadaWSDTO;

public class FeatureWSDTO {
	
	private AttributesWSDTO attributes;
		
	private GeometryWSDTO geometry;
	
	public FeatureWSDTO(AttributesWSDTO attributes, GeometryWSDTO geometry) {
		this.attributes = attributes;
		this.geometry = geometry;
	}

	public AttributesWSDTO getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributesWSDTO attributes) {
		this.attributes = attributes;
	}

	public GeometryWSDTO getGeometry() {
		return geometry;
	}

	public void setGeometry(GeometryWSDTO geometry) {
		this.geometry = geometry;
	}
	
	public CiclorutaDTO getCicloRutaDTO() {
		CiclorutaDTO cicloRutaDTO = new CiclorutaDTO();
		cicloRutaDTO.setIdItem(this.attributes.getObjId());
		cicloRutaDTO.setCodigo((this.attributes.getMunicipio()==null)?"-":this.attributes.getMunicipio()+"");
		cicloRutaDTO.setDescripcion((this.attributes.getNombre()==null)?"-":this.attributes.getNombre()+"");
		cicloRutaDTO.setLongitud(this.attributes.getLongitud());
		
		cicloRutaDTO.setCoordenadas(GeometryUtil.obtenerLineString(getCoordenadas(this.geometry.getPaths().get(0)).toArray(new CoordenadaWSDTO[0])));
		double primerPunto = geometry.getPaths().get(0).get(0)[0];
		double ultimoPunto = geometry.getPaths().get(0).get(0)[1];
		
		cicloRutaDTO.setPrimerPunto(GeometryUtil.obtenerPunto(ultimoPunto, primerPunto));

		int tamLista = geometry.getPaths().get(0).size();
		primerPunto = geometry.getPaths().get(0).get(tamLista-1)[0];
		ultimoPunto = geometry.getPaths().get(0).get(tamLista-1)[1];
		cicloRutaDTO.setUltimoPunto(GeometryUtil.obtenerPunto(ultimoPunto, primerPunto));
		
		cicloRutaDTO.setActiva("S");
		return cicloRutaDTO;
	}
	
	private List<CoordenadaWSDTO> getCoordenadas(List<float[]> paths){
		List<CoordenadaWSDTO> coordenadasWSDTO = new ArrayList<>();
		for(int i=0; i<paths.size(); i++) {
			CoordenadaWSDTO coordenada  = new CoordenadaWSDTO();
			coordenada.setLatitud((double) paths.get(i)[0]);
			coordenada.setLongitud((double) paths.get(i)[1]);
			coordenadasWSDTO.add(coordenada);
		}
		return coordenadasWSDTO;
	}
	
	
}
