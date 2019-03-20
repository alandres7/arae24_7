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

import co.gov.metropol.area247.avistamiento.model.dto.ComentarioHistoriaDto;

@Entity
@Table(name = "T247AVI_COMENTARIO_HISTORIA", schema = "AVISTAM")

@SqlResultSetMapping(name = "groupCommentsDto", classes = {
		@ConstructorResult(targetClass = ComentarioHistoriaDto.class, columns = {
				@ColumnResult(name = "id", type = Long.class), @ColumnResult(name = "descripcion", type = String.class),
				@ColumnResult(name = "fechaCreacion", type = Date.class),
				@ColumnResult(name = "estadoPublicacion", type = int.class),
				@ColumnResult(name = "idUsuario", type = Long.class) }) })
@NamedNativeQuery(name = "getComments", query = "SELECT " + " COH.ID AS id," + " COH.S_DESCRIPCION AS descripcion,"
		+ " COH.D_PUBLICACION AS fechaCreacion," + " COH.N_ESTADO AS estadoPublicacion,"
		+ " COH.ID_USUARIO AS idUsuario" + " FROM AVISTAM.T247AVI_COMENTARIO_HISTORIA COH "
		+ " LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON USU.ID = COH.ID_USUARIO "
		+ " WHERE  COH.ID_HISTORIA =:idHistoria AND USU.USERNAME =:username AND COH.N_ESTADO <> 0 " + " UNION " + " SELECT "
		+ " COH.ID AS id, " + " COH.S_DESCRIPCION AS descripcion, " + " COH.D_PUBLICACION AS fechaCreacion, "
		+ " COH.N_ESTADO AS estadoPublicacion, " + " COH.ID_USUARIO AS idUsuario "
		+ " FROM AVISTAM.T247AVI_COMENTARIO_HISTORIA COH "
		+ " WHERE  COH.ID_HISTORIA = :idHistoria AND COH.N_ESTADO = 1 AND COH.N_ESTADO <> 0", resultSetMapping = "groupCommentsDto")
public class ComentarioHistoria implements Serializable {

	/*
	 * CREATE SEQUENCE "AVISTAM"."SEQ_COMENTARIO_HISTORIA_ID" MINVALUE 1
	 * MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER NOCYCLE
	 * ;
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "AVISTAM.SEQ_COMENTARIO_HISTORIA_ID", allocationSize = 1)
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
	@JoinColumn(name = "ID_HISTORIA", referencedColumnName = "ID")
	private Historia historia;

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

	public Historia getHistoria() {
		return historia;
	}

	public void setHistoria(Historia historia) {
		this.historia = historia;
	}

}
