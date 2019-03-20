package co.gov.metropol.area247.centrocontrol.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T247CAR_CIRCULACION_VEHICULO", schema = "CCONTROL")
public class CirculacionVehiculo {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 @Column(name = "ID")
 private Long id;

 @Column(name = "S_TOKEN_AUTENTICACION")
 private String tokenAutenticacion;

 @Column(name = "D_FECHA")
 private Date fecha;

 @Column(name = "N_CODIGO_DIRECCION")
 private Integer codigoDireccion;

 @Column(name = "S_CARRIL")
 private String carril;

 @Column(name = "D_FECHA_PASO")
 private Date fechaPaso;

 @Column(name = "N_VELOCIDAD_MEDIDA")
 private Integer valocidadMedida;

 @Column(name = "S_PLACA")
 private String placa;

 @Column(name = "D_FECHA_REGISTRO")
 private Date fechaRegistro;

 @Column(name = "N_FECHA_DIA")
 private Integer fechaDia;

 @Column(name = "N_FECHA_HORA")
 private Integer fechaHora;

 @Column(name = "ID_SECRETARIA")
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

 public Integer getValocidadMedida() {
  return valocidadMedida;
 }

 public void setValocidadMedida(Integer valocidadMedida) {
  this.valocidadMedida = valocidadMedida;
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