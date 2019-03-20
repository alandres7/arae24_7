package co.gov.metropol.area247.services.rest.metro;

import co.gov.metropol.area247.model.CivicaDTO;

public class FeatureCivicaExpedicionWSDTO {

	private AttributesCivicaExpedicionWSDTO attributes;
	
	private GeometryCivicaExpedicion geometry;
	
	public FeatureCivicaExpedicionWSDTO(AttributesCivicaExpedicionWSDTO attributes, GeometryCivicaExpedicion geometry) {
		super();
		this.attributes = attributes;
		this.geometry = geometry;
	}

	public AttributesCivicaExpedicionWSDTO getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributesCivicaExpedicionWSDTO attributes) {
		this.attributes = attributes;
	}

	public GeometryCivicaExpedicion getGeometry() {
		return geometry;
	}

	public void setGeometry(GeometryCivicaExpedicion geometry) {
		this.geometry = geometry;
	}
	
	public CivicaDTO getCivicaDTO() {
		CivicaDTO civicaDTO = new CivicaDTO();

		civicaDTO.setIdItem(this.attributes.getFid());
		civicaDTO.setDescripcion(
				String.format("%s (%s)", this.attributes.getDireccion(), this.attributes.getMunicipio()));
		/**
		 * TODO Cambiar a civicaDTO.setTipoPunto(this.attributes.getTipoPunto()); cuando el
		 * servicio de Puntos_de_Recarga_y_Expedición_de_Cívica no devuelva
		 * vacio este campo
		 */
		civicaDTO.setTipoPunto("R");
		civicaDTO.setLatitud(this.geometry.getY());
		civicaDTO.setLongitud(this.geometry.getX());
		civicaDTO.setActivo("S");
		
		return civicaDTO;
	}
	
}
