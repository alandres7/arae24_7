package co.gov.metropol.area247.centrocontrol.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "T247CAR_FORMATO_CARGA", schema = "CCONTROL")
public class FormatoCarga {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "S_NOMBRE")
    private String nombre;

    @Column(name = "S_SEPARADOR")
    private String separador;

    @Column(name = "N_LINEAS_ENCABEZADO")
    private Integer lineasEncabezado;

    @Column(name = "S_FORMATO_FECHA")
    private String formatoFecha;

    @Column(name = "N_MAX_BYTES")
    private Integer maxBytes;

    @Column(name = "N_MAX_ERRORES")
    private Integer maxErrores;

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

	public String getSeparador() {
		return separador;
	}

	public void setSeparador(String separador) {
		this.separador = separador;
	}

	public Integer getLineasEncabezado() {
		return lineasEncabezado;
	}

	public void setLineasEncabezado(Integer lineasEncabezado) {
		this.lineasEncabezado = lineasEncabezado;
	}

	public String getFormatoFecha() {
		return formatoFecha;
	}

	public void setFormatoFecha(String formatoFecha) {
		this.formatoFecha = formatoFecha;
	}

	public Integer getMaxBytes() {
		return maxBytes;
	}

	public void setMaxBytes(Integer maxBytes) {
		this.maxBytes = maxBytes;
	}

	public Integer getMaxErrores() {
		return maxErrores;
	}

	public void setMaxErrores(Integer maxErrores) {
		this.maxErrores = maxErrores;
	}

	@Override
	public String toString() {
		return "FormatoCarga [id=" + id + ", nombre=" + nombre + ", separador=" + separador + ", lineasEncabezado="
				+ lineasEncabezado + ", formatoFecha=" + formatoFecha + ", maxBytes=" + maxBytes + ", maxErrores="
				+ maxErrores + "]";
	}
    
}
