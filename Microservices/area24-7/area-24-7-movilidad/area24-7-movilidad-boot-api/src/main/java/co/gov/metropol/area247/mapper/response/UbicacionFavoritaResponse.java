package co.gov.metropol.area247.mapper.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.vividsolutions.jts.geom.Coordinate;

import co.gov.metropol.area247.model.ConDireccionFavoritaDTO;
import co.gov.metropol.area247.util.Utils;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class UbicacionFavoritaResponse {

	@ApiModelProperty(value = "Identificador unico de la ubicacion", required = true)
	private Long id;

	@ApiModelProperty(value = "Nombre de la ubicaion", required = true)
	private String nombre;

	@ApiModelProperty(value = "Descripcion de la estacion", required = true)
	private String descripcion;

	@ApiModelProperty(value = "Coordenada de la direccion favorita", required = true)
	private List<Double> coordenada;

	@ApiModelProperty(value = "Identificador del usuario", required = true)
	private Long idUsuario;
	
	@ApiModelProperty(value = "Id de la categoria de la direccion favorita", required = true)
	private Long idCategoria;
	
	@ApiModelProperty(value = "Nombre de la categoria de la direccion favorita", required = true)
	private String nombreCategoria;

	public UbicacionFavoritaResponse() {

	}

	public UbicacionFavoritaResponse(ConDireccionFavoritaDTO direccionFavorita) {
		this.id = direccionFavorita.getId();
		this.nombre = direccionFavorita.getNombre();
		this.descripcion = direccionFavorita.getDescripcion();
		Coordinate[] cordenadas = direccionFavorita.getCoordenada().getCoordinates();
		this.coordenada = new ArrayList<>();
		for (int i = 0; i < cordenadas.length; i++) {
			this.coordenada.add(cordenadas[i].x);
			this.coordenada.add(cordenadas[i].y);
		}

		this.idUsuario = direccionFavorita.getIdUsuario();
		
		if (!Utils.isNull(direccionFavorita.getCategoriaDTO())) {
			 this.idCategoria = direccionFavorita.getCategoriaDTO().getId();
			 this.nombreCategoria = direccionFavorita.getCategoriaDTO().getNombre();
		}
	   
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

	public List<Double> getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(List<Double> coordenada) {
		this.coordenada = coordenada;
	}
	
	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

}
