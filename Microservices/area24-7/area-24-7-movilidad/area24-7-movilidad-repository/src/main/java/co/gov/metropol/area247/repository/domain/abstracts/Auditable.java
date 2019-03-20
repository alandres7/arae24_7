package co.gov.metropol.area247.repository.domain.abstracts;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

	@CreatedBy()
	protected U creadoPor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@CreatedDate
	protected Date fecha;
	
	@LastModifiedBy
	protected U ultimaModificacionPor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@LastModifiedDate
	protected Date ultimaModificacion;
	
	@Version
	protected Integer version;

	public U getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(U creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public U getUltimaModificacionPor() {
		return ultimaModificacionPor;
	}

	public void setUltimaModificacionPor(U ultimaModificacionPor) {
		this.ultimaModificacionPor = ultimaModificacionPor;
	}

	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}

	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
