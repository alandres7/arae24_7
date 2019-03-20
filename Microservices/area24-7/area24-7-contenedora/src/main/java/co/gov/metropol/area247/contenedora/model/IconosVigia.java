package co.gov.metropol.area247.contenedora.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="T247VIG_ICONOS_VIGIA", schema="VIGIAS")
public class IconosVigia implements Serializable {

	/*
	CREATE SEQUENCE  "VIGIAS"."SEQ_ICONOS_VIGIA_ID"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE;
	*/
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="VIGIAS.SEQ_ICONOS_VIGIA_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
	@Column(name = "ID_NODO_ARBOL")
	private Long idNodoArbol;
	
	@Column(name = "ID_ICONO_VIGIA_PEN")
	private Long idIconoVigiaPen;
	
	@Column(name = "ID_ICONO_VIGIA_APR")
	private Long idIconoVigiaApr;
	
	@Column(name = "ID_ICONO_VIGIA_REC")
	private Long idIconoVigiaRec;
	
	@Column(name = "ID_ICONO_VIGIA_WIN")
	private Long idIconoVigiaWin;
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdNodoArbol() {
		return idNodoArbol;
	}

	public void setIdNodoArbol(Long idNodoArbol) {
		this.idNodoArbol = idNodoArbol;
	}

	public Long getIdIconoVigiaPen() {
		return idIconoVigiaPen;
	}

	public void setIdIconoVigiaPen(Long idIconoVigiaPen) {
		this.idIconoVigiaPen = idIconoVigiaPen;
	}

	public Long getIdIconoVigiaApr() {
		return idIconoVigiaApr;
	}

	public void setIdIconoVigiaApr(Long idIconoVigiaApr) {
		this.idIconoVigiaApr = idIconoVigiaApr;
	}

	public Long getIdIconoVigiaRec() {
		return idIconoVigiaRec;
	}

	public void setIdIconoVigiaRec(Long idIconoVigiaRec) {
		this.idIconoVigiaRec = idIconoVigiaRec;
	}

	public Long getIdIconoVigiaWin() {
		return idIconoVigiaWin;
	}

	public void setIdIconoVigiaWin(Long idIconoVigiaWin) {
		this.idIconoVigiaWin = idIconoVigiaWin;
	}
	
}

