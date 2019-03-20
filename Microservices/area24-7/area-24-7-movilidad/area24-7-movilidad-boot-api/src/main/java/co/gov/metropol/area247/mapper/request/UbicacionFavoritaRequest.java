package co.gov.metropol.area247.mapper.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.ConDireccionFavoritaDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class UbicacionFavoritaRequest {

	@ApiModelProperty(value = "Identificador unico de la ubicacion", required = true)
	private Long id;

	@ApiModelProperty(value = "Nombre de la ubicaion", required = true)
	private String nombre;

	@ApiModelProperty(value = "Descripcion de la estacion", required = true)
	private String descripcion;

	@ApiModelProperty(value = "Latitud", required = true)
	private double latitud;

	@ApiModelProperty(value = "Longitud", required = true)
	private double longitud;

	@ApiModelProperty(value = "Identificador del usuario", required = true)
	private Long idUsuario;
	
	@ApiModelProperty(value = "Categoria de la direccion favorita", required = true)
	private Long idCategoria;

	public UbicacionFavoritaRequest() {

	}

	public UbicacionFavoritaRequest(ConDireccionFavoritaDTO direccionFavorita) {
		this.id = direccionFavorita.getId();
		this.nombre = direccionFavorita.getNombre();
		this.descripcion = direccionFavorita.getDescripcion();
		this.idUsuario = direccionFavorita.getIdUsuario();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

}
