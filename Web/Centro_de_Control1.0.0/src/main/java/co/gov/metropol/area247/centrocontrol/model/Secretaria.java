package co.gov.metropol.area247.centrocontrol.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "D247CAR_SECRETARIA", schema = "CCONTROL")
public class Secretaria {

 @Id
 @Column(name = "ID")
 private Long id;

 @Column(name = "S_NOMBRE")
 private String nombre;

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
}