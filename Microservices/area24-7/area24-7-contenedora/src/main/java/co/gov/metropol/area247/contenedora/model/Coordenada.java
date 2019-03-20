package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import co.gov.metropol.area247.core.util.GeometryUtil;


@Deprecated
@Entity
@Table (name = "T247CON_COORDENADA", schema = "CONTENEDOR")
public class Coordenada implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	  @SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_COORDENADA_ID", allocationSize=1)
	  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	  private Long id;
	
	@Column(name = "N_LATITUD")
	private float latitud;
	
	@Column(name = "N_LONGITUD")
	private float longitud;
	
	@Column(name = "S_DIRECCION")
	private String direccion;
	
	@ManyToOne
	@JoinColumn(name = "ID_MARCADOR", referencedColumnName = "ID")
	@JsonIgnore
	private Marcador marcador;
	
	//, columnDefinition = "Point"
	@JsonFormat(shape=JsonFormat.Shape.ARRAY)
	@Column(name = "CLB_COORDENADA_PUNTO")
	private Point coordenadaPunto;
	
	@JsonFormat(shape=JsonFormat.Shape.ARRAY)
	@Column(name = "CLB_COORDENADA_POLYGON")
	private Polygon coordenadaPolygon;
	
	@JsonFormat(shape=JsonFormat.Shape.ARRAY)
	@Column(name = "CLB_COORDENADA_LINESTRING")
	private LineString coordenadaLinestring;
	
	public List<String> getCoordenadaPunto() {
		return GeometryUtil.conversorCoordenada(coordenadaPunto);
	}
	
	public Point getPoint() {
		return coordenadaPunto;
	}

	public void setCoordenadaPunto(Point coordenadaPunto) {
		this.coordenadaPunto = coordenadaPunto;
	}

	public List<String> getCoordenadaPolygon() {
		return GeometryUtil.conversorCoordenada(coordenadaPolygon);
	}
	
	public Polygon getPolygon() {
		return coordenadaPolygon;
	}

	public void setCoordenadaPolygon(Polygon coordenadaPolygon) {
		this.coordenadaPolygon = coordenadaPolygon;
	}

	public List<String>  getCoordenadaLinestring() {
		return GeometryUtil.conversorCoordenada(coordenadaLinestring);
	}

	public void setCoordenadaLinestring(LineString coordenadaLinestring) {
		this.coordenadaLinestring = coordenadaLinestring;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float Longitud) {
		this.longitud = Longitud;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Marcador getMarcador() {
		return marcador;
	}

	public void setMarcador(Marcador marcador) {
		this.marcador = marcador;
	}

}
