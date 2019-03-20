package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "TAREVIA_HORARIO_RUTA", schema = "MOVILIDAD")
@NamedQueries({
	/*@NamedQuery(name = "EstacionEncicla.findByLocalizacion" , query = "Select e From EstacionEncicla" )*/
})
public class Frecuencia extends Entities {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "N_FRECUENCIA_MINIMA_PICO_AM")
	private Long frecuenciaMinimaPicoAM;
	
	@Column(name = "N_FRECUENCIA_MIN_VALLE_DIURNO")
	private Long frecuenciaMinValleDiurno;
	
	@Column(name = "N_FRECUENCIA_MIN_VALLE_NOCTURN")
	private Long frecuenciaMinValleNocturn;
	
	@Column(name = "N_FRECUENCIA_MINIMA_PICO_PM")
	private Long frecuenciaMinimaPicoPm;
	
	@Column(name = "N_INTERVALO_MAXIMO_PICO_AM")
	private Long intervaloMaximoPicoAm;
	
	@Column(name = "N_INTERVALO_MAX_VALLE_DIURNO")
	private Long intervaloMaximoValleDiurno;
	
	@Column(name = "N_INTERVALO_MAX_VALLE_NOCTURNO")
	private Long intervaloMaximoValleNocturno;
	
	@Column(name = "N_INTERVALO_MAXIMO_PICO_PM")
	private Long intervaloMaximoPicoPm;
	
	@Column(name = "ID_TIPO_ESTADO_REGISTRO")
	private Long idTipoEstadoRegistro;
	
	@Column(name = "ID_RUTA")
	private Long idRuta;
	
	@Override
	public <T extends Entities> T withId(Long id) {
		return null;
	}

	@Override
	public <T extends Entities> T withEnabled(boolean enabled) {
		return null;
	}

    public Long getFrecuenciaMinimaPicoAM() {
        return frecuenciaMinimaPicoAM;
    }

    public void setFrecuenciaMinimaPicoAM(Long frecuenciaMinimaPicoAM) {
        this.frecuenciaMinimaPicoAM = frecuenciaMinimaPicoAM;
    }

    public Long getFrecuenciaMinValleDiurno() {
        return frecuenciaMinValleDiurno;
    }

    public void setFrecuenciaMinValleDiurno(Long frecuenciaMinValleDiurno) {
        this.frecuenciaMinValleDiurno = frecuenciaMinValleDiurno;
    }

    public Long getFrecuenciaMinValleNocturn() {
        return frecuenciaMinValleNocturn;
    }

    public void setFrecuenciaMinValleNocturn(Long frecuenciaMinValleNocturn) {
        this.frecuenciaMinValleNocturn = frecuenciaMinValleNocturn;
    }

    public Long getFrecuenciaMinimaPicoPm() {
        return frecuenciaMinimaPicoPm;
    }

    public void setFrecuenciaMinimaPicoPm(Long frecuenciaMinimaPicoPm) {
        this.frecuenciaMinimaPicoPm = frecuenciaMinimaPicoPm;
    }

    public Long getIntervaloMaximoPicoAm() {
        return intervaloMaximoPicoAm;
    }

    public void setIntervaloMaximoPicoAm(Long intervaloMaximoPicoAm) {
        this.intervaloMaximoPicoAm = intervaloMaximoPicoAm;
    }

    public Long getIntervaloMaximoValleDiurno() {
        return intervaloMaximoValleDiurno;
    }

    public void setIntervaloMaximoValleDiurno(Long intervaloMaximoValleDiurno) {
        this.intervaloMaximoValleDiurno = intervaloMaximoValleDiurno;
    }

    public Long getIntervaloMaximoValleNocturno() {
        return intervaloMaximoValleNocturno;
    }

    public void setIntervaloMaximoValleNocturno(Long intervaloMaximoValleNocturno) {
        this.intervaloMaximoValleNocturno = intervaloMaximoValleNocturno;
    }

    public Long getIntervaloMaximoPicoPm() {
        return intervaloMaximoPicoPm;
    }

    public void setIntervaloMaximoPicoPm(Long intervaloMaximoPicoPm) {
        this.intervaloMaximoPicoPm = intervaloMaximoPicoPm;
    }

    public Long getIdTipoEstadoRegistro() {
        return idTipoEstadoRegistro;
    }

    public void setIdTipoEstadoRegistro(Long idTipoEstadoRegistro) {
        this.idTipoEstadoRegistro = idTipoEstadoRegistro;
    }

    public Long getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Long idRuta) {
        this.idRuta = idRuta;
    }

}
