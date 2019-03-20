package co.gov.metropol.area247.centrocontrol.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class CirculacionDTO  implements Serializable {

 private static final long serialVersionUID = 1L;

 @JsonProperty(value = "codDireccion")
 private Integer codigoDireccion;
 
 @JsonProperty(value = "carril")
 private String carril;
 
 @JsonProperty(value = "fechaPaso")
 private String fechaPaso;
 
 @JsonProperty(value = "velocidadMedia")
 private Integer velocidadMedida;
 
 @JsonProperty(value = "placa")
 private String placa;
 
 @JsonProperty(value = "fechaRegistro")
 private String fechaRegistro;
 
 @JsonProperty(value = "dia")
 private Integer fechaDia;
 
 @JsonProperty(value = "hora")
 private Integer fechaHora;
 
 @JsonProperty(value = "idSecretaria")
 private Long idSecretaria;

 public CirculacionDTO()
 {
	 
 }
 
 public CirculacionDTO(@JsonProperty(value = "codDireccion") Integer codigoDireccion, 
		 @JsonProperty(value = "carril") String carril, 
		 @JsonProperty(value = "fechaPaso") String fechaPaso, 
		 @JsonProperty(value = "velocidadMedia") Integer velocidadMedida,
		 @JsonProperty(value = "placa") String placa, 
		 @JsonProperty(value = "fechaRegistro") String fechaRegistro, 
		 @JsonProperty(value = "dia") Integer fechaDia, 
		 @JsonProperty(value = "hora") Integer fechaHora, 
		 @JsonProperty(value = "idSecreataria")Long idSecretaria) {
		super();
		this.codigoDireccion = codigoDireccion;
		this.carril = carril;
		this.fechaPaso = fechaPaso;
		this.velocidadMedida = velocidadMedida;
		this.placa = placa;
		this.fechaRegistro = fechaRegistro;
		this.fechaDia = fechaDia;
		this.fechaHora = fechaHora;
		this.idSecretaria = idSecretaria;
	}
 

 public Integer getCodigoDireccion() {
  return codigoDireccion;
 }

public void setCodigoDireccion(Integer codigoDireccion) {
  this.codigoDireccion = codigoDireccion;
 }

 public String getCarril() {
  return carril;
 }

 public void setCarril(String carril) {
  this.carril = carril;
 }

 public String getFechaPaso() {
  return fechaPaso;
 }

 public void setFechaPaso(String fechaPaso) {
  this.fechaPaso = fechaPaso;
 }

 public Integer getVelocidadMedida() {
  return velocidadMedida;
 }

 public void setVelocidadMedida(Integer velocidadMedida) {
  this.velocidadMedida = velocidadMedida;
 }

 public String getPlaca() {
  return placa;
 }

 public void setPlaca(String placa) {
  this.placa = placa;
 }

 public String getFechaRegistro() {
  return fechaRegistro;
 }

 public void setFechaRegistro(String fechaRegistro) {
  this.fechaRegistro = fechaRegistro;
 }

 public Integer getFechaDia() {
  return fechaDia;
 }

 public void setFechaDia(Integer fechaDia) {
  this.fechaDia = fechaDia;
 }

 public Integer getFechaHora() {
  return fechaHora;
 }

 public void setFechaHora(Integer fechaHora) {
  this.fechaHora = fechaHora;
 }

 public Long getIdSecretaria() {
  return idSecretaria;
 }

 public void setIdSecretaria(Long idSecretaria) {
  this.idSecretaria = idSecretaria;
 }
}