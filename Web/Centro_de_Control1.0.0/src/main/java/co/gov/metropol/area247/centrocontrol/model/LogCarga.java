package co.gov.metropol.area247.centrocontrol.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "T247CAR_LOG_CARGA", schema = "CONTROL")
public class LogCarga {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "D_FECHA")
    private Date fecha;
    
    @Column(name = "S_USUARIO")
    private String usuario;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FORMATO", nullable = false)
	private FormatoCarga formatoCarga;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public FormatoCarga getFormatoCarga() {
		return formatoCarga;
	}

	public void setFormatoCarga(FormatoCarga formatoCarga) {
		this.formatoCarga = formatoCarga;
	}

	@Override
	public String toString() {
		return "LogCarga [id=" + id + ", fecha=" + fecha + ", usuario=" + usuario + ", formatoCarga=" + formatoCarga
				+ "]";
	}
	
}
