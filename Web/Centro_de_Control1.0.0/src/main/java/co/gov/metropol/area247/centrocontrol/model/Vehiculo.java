package co.gov.metropol.area247.centrocontrol.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "T247CAR_VEHICULO", schema = "CCONTROL")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

	@Column(name = "S_NRO_PLACA")
	private String placa;
    
	@Column(name = "N_CAP_TONELADAS")
	private BigDecimal toneladas;
	
	@Column(name = "S_DESC_CARROCERIA")
	private String carroceria;

	@Column(name = "S_DESC_CLASE")
	private String clase;

	@Column(name = "S_DESC_COMBUTIBLE")
	private String combustible;

	@Column(name = "S_DESC_MARCA")
	private String marca;

	@Column(name = "ID_CARROCERIA")
	private Long carroceriaId;

	@Column(name = "ID_CLASE")
	private Long claseId;

	@Column(name = "ID_COMBUSTIBLE")
	private Long combustibleId;

	@Column(name = "ID_MARCA")
	private Long marcaId;

	@Column(name = "N_MODELO")
	private Long modelo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public BigDecimal getToneladas() {
		return toneladas;
	}

	public void setToneladas(BigDecimal toneladas) {
		this.toneladas = toneladas;
	}

	public String getCarroceria() {
		return carroceria;
	}

	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Long getModelo() {
		return modelo;
	}

	public void setModelo(Long modelo) {
		this.modelo = modelo;
	}

	public Long getCarroceriaId() {
		return carroceriaId;
	}

	public void setCarroceriaId(Long carroceriaId) {
		this.carroceriaId = carroceriaId;
	}

	public Long getClaseId() {
		return claseId;
	}

	public void setClaseId(Long claseId) {
		this.claseId = claseId;
	}

	public Long getCombustibleId() {
		return combustibleId;
	}

	public void setCombustibleId(Long combustibleId) {
		this.combustibleId = combustibleId;
	}

	public Long getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Long marcaId) {
		this.marcaId = marcaId;
	}

	@Override
	public String toString() {
		return "Vehiculo [id=" + id + ", placa=" + placa + ", toneladas=" + toneladas + ", carroceria=" + carroceria
				+ ", clase=" + clase + ", combustible=" + combustible + ", marca=" + marca + ", carroceriaId="
				+ carroceriaId + ", claseId=" + claseId + ", combustibleId=" + combustibleId + ", marcaId=" + marcaId
				+ ", modelo=" + modelo + "]";
	}
	
}
