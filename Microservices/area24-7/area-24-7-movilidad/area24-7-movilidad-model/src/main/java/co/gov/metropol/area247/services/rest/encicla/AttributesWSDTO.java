package co.gov.metropol.area247.services.rest.encicla;

public class AttributesWSDTO {
	
	private Long OBJECTID;
	private String Name;
	private Double Shape_Leng;
	private String Municipio;
	
	public AttributesWSDTO(Long objId, String nombre, Double longitud, String municipio) {
		this.OBJECTID = objId;
		this.Name = nombre;
		this.Shape_Leng = longitud;
		this.Municipio = municipio;
	}

	public String getNombre() {
		return Name;
	}

	public void setNombre(String nombre) {
		this.Name = nombre;
	}

	public Double getLongitud() {
		return Shape_Leng;
	}

	public void setLongitud(Double longitud) {
		this.Shape_Leng = longitud;
	}

	public String getMunicipio() {
		return Municipio;
	}

	public void setMunicipio(String municipio) {
		this.Municipio = municipio;
	}

	public Long getObjId() {
		return OBJECTID;
	}

	public void setObjId(Long objId) {
		this.OBJECTID = objId;
	}
	
	
}
