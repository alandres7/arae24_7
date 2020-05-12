package co.gov.metropol.area247.covid19.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

@Entity
@Table(name = "D247SEG_MUNICIPIO", schema = "CONTENEDOR")
public class Town {

	@Id
	@NotNull
	@Column(name="ID", unique=true, precision=10)
	private Long id;
	
	@NotNull
	@Column(name="S_NOMBRE", length=100 )
	private String nombre;
	
	@JsonFormat(shape=JsonFormat.Shape.ARRAY)
	@Column(name="CLB_POLYGON")
	private Polygon polygon;
	
	@Column(name="N_VALLE_ABURRA")
	private boolean valleAburra;
	
	@ManyToOne
	@JoinColumn(name="ID_DEPARTAMENTO")
	private Department departamento;
	
	@Column(name="CLB_ZERO_POINT")
	private Point zeroPoint;

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

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}

	public boolean isValleAburra() {
		return valleAburra;
	}

	public void setValleAburra(boolean valleAburra) {
		this.valleAburra = valleAburra;
	}

	public Department getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Department departamento) {
		this.departamento = departamento;
	}

	public Point getZeroPoint() {
		return zeroPoint;
	}

	public void setZeroPoint(Point zeroPoint) {
		this.zeroPoint = zeroPoint;
	}

	public Town() {}


}
