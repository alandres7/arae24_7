package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.ConCatUbicacionFavoritaDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class CatUbicacionFavoritaResponse {

	@ApiModelProperty(value = "Identificador unico de la categoria", required = true)
	private Long id;

	@ApiModelProperty(value = "Codigo de la categoria de la ubicaion favorita", required = true)
	private String codigo;

	@ApiModelProperty(value = "Nombre de la categoria de la ubicaion favorita", required = true)
	private String nombre;

	public CatUbicacionFavoritaResponse() {
	}

	public CatUbicacionFavoritaResponse(ConCatUbicacionFavoritaDTO dto) {
		this.id = dto.getId();
		this.codigo = dto.getCodigo();
		this.nombre = dto.getNombre();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
