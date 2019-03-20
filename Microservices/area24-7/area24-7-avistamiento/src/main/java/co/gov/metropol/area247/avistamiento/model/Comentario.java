package co.gov.metropol.area247.avistamiento.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import co.gov.metropol.area247.avistamiento.model.dto.ComentarioDto;

@Entity
@Table(name = "T247AVI_COMENTARIO", schema = "AVISTAM")

@SqlResultSetMapping(name = "groupComments", classes = {
		@ConstructorResult(targetClass = ComentarioDto.class, columns = { @ColumnResult(name = "id", type = Long.class),
				@ColumnResult(name = "descripcion", type = String.class),
				@ColumnResult(name = "fechaPublicacion", type = Date.class),
				@ColumnResult(name = "estadoPublicacion", type = int.class),
				@ColumnResult(name = "idUsuario", type = Long.class) }) })
@NamedNativeQuery(name = "getCommentsByState", query = "SELECT " + " COM.ID AS id, "
		+ " COM.S_DESCRIPCION AS descripcion, " + " COM.D_PUBLICACION AS fechaPublicacion, "
		+ " COM.N_ESTADO AS estadoPublicacion, " + " COM.ID_USUARIO AS idUsuario " + " FROM AVISTAM.T247AVI_COMENTARIO COM "
		+ " LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON USU.ID = COM.ID_USUARIO " + " WHERE USU.USERNAME = :username "
		+ " AND COM.ID_AVISTAMIENTO = :idAvistamiento AND COM.N_ESTADO <> 0"
		+ " UNION " + " SELECT " + " COM.ID AS id, " + " COM.S_DESCRIPCION AS descripcion, "
		+ " COM.D_PUBLICACION AS fechaPublicacion, " + " COM.N_ESTADO AS estado, " + " COM.ID_USUARIO AS idUsuario "
		+ " FROM AVISTAM.T247AVI_COMENTARIO COM " + " WHERE  COM.ID_AVISTAMIENTO = :idAvistamiento AND COM.N_ESTADO = 1"
		+ " AND COM.N_ESTADO <> 0", resultSetMapping = "groupComments")
public class Comentario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "AVISTAM.SEQ_COMENTARIO_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private Long id;

	@Size(max = 4000)
	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Column(name = "D_PUBLICACION")
	private Date fechaPublicacion;

	@Column(name = "N_ESTADO")
	private int estado;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_AVISTAMIENTO", referencedColumnName = "ID")
	private Avistamiento avistamiento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Avistamiento getAvistamiento() {
		return avistamiento;
	}

	public void setAvistamiento(Avistamiento avistamiento) {
		this.avistamiento = avistamiento;
	}

}
