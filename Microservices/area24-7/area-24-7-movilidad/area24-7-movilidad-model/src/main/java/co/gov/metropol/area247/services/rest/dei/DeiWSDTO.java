package co.gov.metropol.area247.services.rest.dei;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class DeiWSDTO {

	@JsonProperty(value = "coddireccion")
	private Long coddireccion;
	
	@JsonProperty(value = "carril")
	private Long carril;
	
	@JsonProperty(value = "fechapaso")
	private Long fechapaso;
	
	@JsonProperty(value = "velmedida")
	private Long velmedida;

	@JsonProperty(value = "placa")
	private Long placa;
	
	@JsonProperty(value = "feregistro")
	private String feregistro;
	
	@JsonProperty(value = "fechadia")
	private Long fechadia;
	
	@JsonProperty(value = "fechahora")
	private Long fechahora;
	
	@JsonProperty(value = "id_secretaria")
	private Long idSecretaria;
	
	public DeiWSDTO(){
		super();
	}
	
	public Long getCodDireccion() {
		return this.coddireccion; 
	}
	
	public void setCodDireccion(Long coddireccion) {
		this.coddireccion = coddireccion; 
	}
	
	public Long getCarril() {
		return this.carril; 
	}
	
	public void setCarril(Long carril) {
		this.carril = carril; 
	}
	
	public Long getFechaPaso() {
		return this.fechapaso; 
	}
	
	public void setFechaPaso(Long fechapaso) {
		this.fechapaso = fechapaso; 
	}
	
	public Long getVelMedida() {
		return this.velmedida; 
	}
	
	public void setVelMedida(Long velmedida) {
		this.velmedida = velmedida; 
	}
	
	public Long getPlaca() {
		return this.placa; 
	}
	
	public void setPlaca(Long placa) {
		this.placa = placa; 
	}
	
	public String getFeRegistro() {
		return this.feregistro; 
	}
	
	public void setFeRegistro(String feregistro) {
		this.feregistro = feregistro; 
	}
	
	public Long getFechaDia() {
		return this.fechadia; 
	}
	
	public void setFechaDia(Long fechadia) {
		this.fechadia = fechadia; 
	}
	
	public Long getFechaHora() {
		return this.fechahora; 
	}
	
	public void setFechaHora(Long fechahora) {
		this.fechahora = fechahora; 
	}
	
	public Long getIdSecretaria() {
		return this.idSecretaria; 
	}
	
	public void setIdSecretaria(Long idSecretaria) {
		this.idSecretaria = idSecretaria; 
	}
	
}
