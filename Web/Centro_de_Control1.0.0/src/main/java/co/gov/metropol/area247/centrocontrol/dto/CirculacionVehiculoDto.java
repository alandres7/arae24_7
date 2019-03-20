package co.gov.metropol.area247.centrocontrol.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class CirculacionVehiculoDto extends ArrayList<CirculacionVehiculoDto> {

  /**
  * 
  */
 private static final long serialVersionUID = 1L;
 private Long id;
 private String tokenAutenticacion;
 private Date fecha;
 private Integer codigoDireccion;
 private String carril;
 private Date fechaPaso;
 private Integer velocidadMedida;
 private String placa;
 private Date fechaRegistro;
 private Integer fechaDia;
 private Integer fechaHora;
 private Long idSecretaria;

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getTokenAutenticacion() {
  return tokenAutenticacion;
 }

 public void setTokenAutenticacion(String tokenAutenticacion) {
  this.tokenAutenticacion = tokenAutenticacion;
 }

 public Date getFecha() {
  return fecha;
 }

 public void setFecha(Date fecha) {
  this.fecha = fecha;
 }
 public CirculacionVehiculoDto()
 { }

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

 public Date getFechaPaso() {
  return fechaPaso;
 }

 public void setFechaPaso(Date fechaPaso) {
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

 public Date getFechaRegistro() {
  return fechaRegistro;
 }

 public void setFechaRegistro(Date fechaRegistro) {
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