package co.gov.metropol.area247.services.rest.metro;

// TODO Cambiar los nombres de las variables como lo indica el javabeans
/**
 * Clase que mapea los campos que retorna el servicio de puntos de recarga y
 * expedicion civica del area metropolitana 
 * Creado 19/07/2018 10:51:10 a.m
 *
 */
public class AttributesCivicaExpedicionWSDTO {
	
	private Long FID;
	private String nombre_del;
	private String Dirección;
	private String Municipio;
	private String Tipo_de_pu;
	private String HORARIO;

	public AttributesCivicaExpedicionWSDTO() {

	}

	public AttributesCivicaExpedicionWSDTO(Long fid, String nombreDel, String direccion,
			String municipio, String tipoPunto, String horario) {
		super();
		this.FID = fid;
		this.nombre_del = nombreDel;
		this.Dirección = direccion;
		this.Municipio = municipio;
		this.HORARIO = tipoPunto;
		this.HORARIO = horario;
	}

	public Long getFid() {
		return FID;
	}

	public void setFid(Long fid) {
		this.FID = fid;
	}

	public String getNombreDel() {
		return nombre_del;
	}

	public void setNombreDel(String nombreDel) {
		this.nombre_del = nombreDel;
	}

	public String getDireccion() {
		return Dirección;
	}

	public void setDireccion(String direccion) {
		this.Dirección = direccion;
	}

	public String getMunicipio() {
		return Municipio;
	}

	public void setMunicipio(String municipio) {
		this.Municipio = municipio;
	}

	public String getTipoPunto() {
		return Tipo_de_pu;
	}

	public void setTipoPunto(String tipoPunto) {
		this.Tipo_de_pu = tipoPunto;
	}

	public String getHorario() {
		return HORARIO;
	}

	public void setHorario(String horario) {
		this.HORARIO = horario;
	}

}
