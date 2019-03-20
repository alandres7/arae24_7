package co.gov.metropol.area247.centrocontrol.dto;

import java.util.ArrayList;
import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.Fotodeteccion;
import co.gov.metropol.area247.centrocontrol.model.Vehiculo;

public class VehiculoDto {
	
	private Vehiculo vehiculo = new Vehiculo();
	private List<Fotodeteccion> detecciones = new ArrayList<>();
	private String distanciaRecorrida;
	private String porcentajeHuellaCarbono;
	private String porcentajeCarroceria;
	
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	public List<Fotodeteccion> getDetecciones() {
		return detecciones;
	}
	public void setDetecciones(List<Fotodeteccion> detecciones) {
		this.detecciones = detecciones;
	}
	public String getDistanciaRecorrida() {
		return distanciaRecorrida;
	}
	public void setDistanciaRecorrida(String distanciaRecorrida) {
		this.distanciaRecorrida = distanciaRecorrida;
	}
	public String getPorcentajeHuellaCarbono() {
		return porcentajeHuellaCarbono;
	}
	public void setPorcentajeHuellaCarbono(String porcentajeHuellaCarbono) {
		this.porcentajeHuellaCarbono = porcentajeHuellaCarbono;
	}
	public String getPorcentajeCarroceria() {
		return porcentajeCarroceria;
	}
	public void setPorcentajeCarroceria(String porcentajeCarroceria) {
		this.porcentajeCarroceria = porcentajeCarroceria;
	}
	
	@Override
	public String toString() {
		return "VehiculoDto [vehiculo=" + vehiculo + ", detecciones=" + detecciones + ", distanciaRecorrida="
				+ distanciaRecorrida + ", porcentajeHuellaCarbono=" + porcentajeHuellaCarbono
				+ ", porcentajeCarroceria=" + porcentajeCarroceria + "]";
	}
	
}
