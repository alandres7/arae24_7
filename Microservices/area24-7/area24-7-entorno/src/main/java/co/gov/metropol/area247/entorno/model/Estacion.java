package co.gov.metropol.area247.entorno.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import co.gov.metropol.area247.contenedora.model.Marcador;

@Entity
@Table(name="D247ENT_ESTACION", schema="CONTENEDOR")
public class Estacion {
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_ESTACION_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")  
	private long id;
	
	@Column(name="S_NOMBRE")
	private String nombre;
	
	@OneToOne
	@JoinColumn(name="ID_MARCADOR", referencedColumnName="ID")
	private Marcador marcador;
	
	@Column(name="S_CATEGORIA")
	private String categoria;
	
	@Column(name="S_COLOR_HEX")
	private String colorHexadecimal;
	
	@Column(name="S_COLOR_RGB")
	private String colorRGB;
	
	@Column(name="N_VALOR_ICA")
	private double valorICA;
	
	@Column(name="N_PORCENTAJE_NIVEL")
	private double porcentajeNivel;
	
	@Column(name="SUBCUENCA")
	private String subCuenca;
	
	@Column(name="S_RANGO_TEMPERATURA")
	private String rangoTemperatura;
	
	@Column(name="S_RECOMENDACIONES")
	private String recomendaciones;
	
	@Column(name="S_MUNICIPIO")
	private String municipio;
	
	@Column(name="S_TEMPERATURA_MINIMA")
	private String temperaturaMinima;
	
	@Column(name="S_TEMPERATURA_MAXIMA")
	private String temperaturaMaxima;
	
	@OneToMany(mappedBy="estacion")
	private List<Pronostico> pronosticos;
		

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Marcador getMarcador() {
		return marcador;
	}

	public void setMarcador(Marcador marcador) {
		this.marcador = marcador;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getColorHexadecimal() {
		return colorHexadecimal;
	}

	public void setColorHexadecimal(String colorHexadecimal) {
		this.colorHexadecimal = colorHexadecimal;
	}

	public String getColorRGB() {
		return colorRGB;
	}

	public void setColorRGB(String colorRGB) {
		this.colorRGB = colorRGB;
	}

	public double getValorICA() {
		return valorICA;
	}

	public void setValorICA(double valorICA) {
		this.valorICA = valorICA;
	}

	public double getPorcentajeNivel() {
		return porcentajeNivel;
	}

	public void setPorcentajeNivel(double porcentajeNivel) {
		this.porcentajeNivel = porcentajeNivel;
	}
	
	public String getSubCuenca() {
		return subCuenca;
	}

	public void setSubCuenca(String subCuenca) {
		this.subCuenca = subCuenca;
	}

	public String getRangoTemperatura() {
		return rangoTemperatura;
	}

	public void setRangoTemperatura(String rangoTemperatura) {
		this.rangoTemperatura = rangoTemperatura;
	}

	public String getRecomendaciones() {
		return recomendaciones;
	}

	public void setRecomendaciones(String recomendaciones) {
		this.recomendaciones = recomendaciones;
	}
		
	public String getTemperaturaMinima() {
		return temperaturaMinima;
	}

	public void setTemperaturaMinima(String temperaturaMinima) {
		this.temperaturaMinima = temperaturaMinima;
	}

	public String getTemperaturaMaxima() {
		return temperaturaMaxima;
	}

	public void setTemperaturaMaxima(String temperaturaMaxima) {
		this.temperaturaMaxima = temperaturaMaxima;
	}
	

	/**
	 * @param id
	 * @param nombre
	 * @param marcador
	 * @param categoria
	 * @param colorHexadecimal
	 * @param colorRGB
	 * @param valorICA
	 * @param porcentajeNivel
	 * @param subCuenca
	 * @param rangoTemperatura
	 * @param recomendaciones
	 * @param municipio
	 */
	public Estacion(long id, String nombre, Marcador marcador, String categoria, String colorHexadecimal,
			String colorRGB, double valorICA, double porcentajeNivel, String subCuenca, String rangoTemperatura,
			String recomendaciones, String municipio, String temperaturaMinima, String temperaturaMaxima) {
		this.id = id;
		this.nombre = nombre;
		this.marcador = marcador;
		this.categoria = categoria;
		this.colorHexadecimal = colorHexadecimal;
		this.colorRGB = colorRGB;
		this.valorICA = valorICA;
		this.porcentajeNivel = porcentajeNivel;
		this.subCuenca = subCuenca;
		this.rangoTemperatura = rangoTemperatura;
		this.recomendaciones = recomendaciones;
		this.municipio = municipio;
		this.temperaturaMinima = temperaturaMinima;
		this.temperaturaMaxima = temperaturaMaxima;
	}
	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	/**
	 * 
	 */
	public Estacion() {
		// TODO Auto-generated constructor stub
	}
	
}
