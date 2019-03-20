package co.gov.metropol.area247.mapper.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.vividsolutions.jts.geom.Coordinate;

import co.gov.metropol.area247.model.CiclorutaDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class CicloviaResponse {

	@ApiModelProperty(value = "Identificador de la ciclovia", required = true)
	private Long idCiclovia;

	@ApiModelProperty(value = "Codigo de la ciclovia", required = true)
	private String codigoCiclovia;

	@ApiModelProperty(value = "Descripción de la ciclovia", required = true)
	private String descripcion;

	@ApiModelProperty(value = "Coordenadas de la ciclovia", required = true)
	private String[] coordenadas;

	@ApiModelProperty(value = "Latitud ubicación origen", required = true)
	private List<Double> primerPunto;

	@ApiModelProperty(value = "Longitud ubicación origen", required = true)
	private List<Double> ultimoPunto;

	@ApiModelProperty(value = "Ciclovia activa", required = true)
	private String activa;

	public CicloviaResponse(CiclorutaDTO ciclorutaDTO) {
		final int tamano = 12;
		this.idCiclovia = ciclorutaDTO.getId();
		this.codigoCiclovia = ciclorutaDTO.getCodigo();
		this.descripcion = ciclorutaDTO.getDescripcion();

		String coor = ciclorutaDTO.getCoordenadas().toString().replace(", ", ",");
		coor = coor.replace(" ", ",");
		String coordenadas = coor.substring(tamano, coor.length() - 1);
		this.coordenadas = coordenadas.split(",");

		Coordinate[] primerPunto = ciclorutaDTO.getPrimerPunto().getCoordinates();
		this.primerPunto = new ArrayList<>();
		for (int i = 0; i < primerPunto.length; i++) {
			this.primerPunto.add(primerPunto[i].x);
			this.primerPunto.add(primerPunto[i].y);
		}

		Coordinate[] segundoPunto = ciclorutaDTO.getPrimerPunto().getCoordinates();
		this.ultimoPunto = new ArrayList<>();
		for (int i = 0; i < segundoPunto.length; i++) {
			this.ultimoPunto.add(segundoPunto[i].x);
			this.ultimoPunto.add(segundoPunto[i].y);
		}

		this.activa = ciclorutaDTO.getActiva();
	}

	public Long getIdCiclovia() {
		return idCiclovia;
	}

	public void setIdCiclovia(Long idCiclovia) {
		this.idCiclovia = idCiclovia;
	}

	public String getCodigoCiclovia() {
		return codigoCiclovia;
	}

	public void setCodigoCiclovia(String codigoCiclovia) {
		this.codigoCiclovia = codigoCiclovia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String[] getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String[] coordenadas) {
		this.coordenadas = coordenadas;
	}

	public List<Double> getPrimerPunto() {
		return primerPunto;
	}

	public void setPrimerPunto(List<Double> primerPunto) {
		this.primerPunto = primerPunto;
	}

	public List<Double> getUltimoPunto() {
		return ultimoPunto;
	}

	public void setUltimoPunto(List<Double> ultimoPunto) {
		this.ultimoPunto = ultimoPunto;
	}

	public String getActiva() {
		return activa;
	}

	public void setActiva(String activa) {
		this.activa = activa;
	}

}
