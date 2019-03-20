package co.gov.metropol.area247.huellas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import co.gov.metropol.area247.contenedora.model.Capa;

@Entity
@Table(name="D247HUE_ENCUESTADO", schema="HUELLAS" )
public class Encuestado{
		
		@Id
		@NotNull
		@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_ENCUESTADO_ID")
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
		@Column(unique=true, precision=10)
		private Long id;
		
		@NotNull
		@Size(max=100)
		@Column(name="S_USERNAME", unique=true) 
		private String username;
		
		@Column(name = "N_RETO")
		private boolean aceptaReto;
		
		@Column(name = "F_CALCULO_HUELLA")
		private float calculoHuella;
		
		@Column(name = "FECHA_ENCUESTA")
		@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
		private Date fechaEncuesta;
		
		@NotNull
		@ManyToOne
	    @JoinColumn(name = "ID_ENCUESTA")
		private Capa capa;
				
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		
		public boolean isAceptaReto() {
			return aceptaReto;
		}

		public void setAceptaReto(boolean aceptaReto) {
			this.aceptaReto = aceptaReto;
		}

		public float getCalculoHuella() {
			return calculoHuella;
		}

		public void setCalculoHuella(float calculoHuella) {
			this.calculoHuella = calculoHuella;
		}

		public Date getFechaEncuesta() {
			return fechaEncuesta;
		}

		public void setFechaEncuesta(Date fechaEncuesta) {
			this.fechaEncuesta = fechaEncuesta;
		}

		public Capa getCapa() {
			return capa;
		}

		public void setCapa(Capa capa) {
			this.capa = capa;
		}

		public Encuestado() {}
		
}
