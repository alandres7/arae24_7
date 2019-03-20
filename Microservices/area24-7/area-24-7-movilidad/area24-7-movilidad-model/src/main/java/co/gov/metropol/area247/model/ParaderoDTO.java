package co.gov.metropol.area247.model;

public class ParaderoDTO {

	private Long idParadero;
	
	private String descripcion;
	
	private Long latitud;
	
	private Long longitud;
	
	private Long codigoParadero;
	
	private Long idTipoParadero;
	
	private String nombreTipoParadero;
	
	private Long idTipoOrientacion;
	
	private String nombreTipoOrientacion;
	
	private String activo;
	
	private Long idRuta;
	
	public Long getIdParadero() {
        return idParadero;
    }

    public void setIdParadero(Long idParadero) {
        this.idParadero = idParadero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getLatitud() {
        return latitud;
    }

    public void setLatitud(Long latitud) {
        this.latitud = latitud;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }

    public Long getCodigoParadero() {
        return codigoParadero;
    }

    public void setCodigoParadero(Long codigoParadero) {
        this.codigoParadero = codigoParadero;
    }

    public Long getIdTipoParadero() {
        return idTipoParadero;
    }

    public void setIdTipoParadero(Long idTipoParadero) {
        this.idTipoParadero = idTipoParadero;
    }

    public String getNombreTipoParadero() {
        return nombreTipoParadero;
    }

    public void setNombreTipoParadero(String nombreTipoParadero) {
        this.nombreTipoParadero = nombreTipoParadero;
    }

   
    public Long getIdTipoOrientacion() {
		return idTipoOrientacion;
	}

	public void setIdTipoOrientacion(Long idTipoOrientacion) {
		this.idTipoOrientacion = idTipoOrientacion;
	}

    public String getNombreTipoOrientacion() {
        return nombreTipoOrientacion;
    }

    public void setNombreTipoOrientacion(String nombreTipoOrientacion) {
        this.nombreTipoOrientacion = nombreTipoOrientacion;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public Long getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Long idRuta) {
        this.idRuta = idRuta;
    }
	
}
