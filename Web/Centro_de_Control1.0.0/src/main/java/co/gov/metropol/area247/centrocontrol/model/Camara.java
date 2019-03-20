package co.gov.metropol.area247.centrocontrol.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "T247CAR_INFORMACION_CAMARAS", schema = "CCONTROL")
public class Camara {
	
	public Camara() {
	
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CAMARA")
    private Long idCamara;
	
	@Column(name= "S_DESCRIPCION_CAMARA")
	private String descripcionCamara;
	
	@Column(name="S_CODIGO_CAMARA")
	private String codigoCamara;
	
	@Column(name="S_VIGENTE")
	private String vigente;
	
	@Column(name="D_HORA_INICIO")
	private Date horaInicio;
	
	@Column(name="D_HORA_FIN")
	private Date horaFin;
	
	@Column(name = "S_LONGITUD")
	private BigDecimal longitud;
	
	@Column(name = "S_LATITUD")
	private BigDecimal latitud;
	
	@Column(name="S_ALIAS_CAMARA")
	private String aliasCamara;
	
	@Column(name="S_IP_CAMARA")
	private String ipCamara;
	
	@Column(name="ID_TIPO_CAMARA")
	private Long tipoCamara ;
	
	@Column(name="S_VALIDA_GPS")
	private String validaGps;
	
	@Column(name = "N_ID_CAMARA_INFRACCION_TEC")
	private Long infraccionTecId;
	
	@Column(name = "ID_ZONA")
	private Long idZona;
		
	@Column(name = "ID_TIPO_VIA")
	private Long idTipoVia;
	
	@Column(name = "N_NUMERO_VIA")
	private Long numeroVia;
	
	@Column(name = "ID_LETRA")
	private Long idLetra;
	
	@Column(name = "ID_CARDINALIDAD")
	private Long idCardinalidad;
	
	@Column(name = "ID_TIPO_VIA_CRUCE")
	private Long idTipoViaCruce;
	
	@Column(name = "ID_NUMERO_VIA_CRUCE")
	private Long idNumeroViaCruce;
	
	@Column(name = "ID_LETRA_VIA_CRUCE")
	private Long letraViaCruceId;
	
	@Column(name = "ID_CARDINALIDAD_VIA_CRUCE")
	private Long idCardinalidadViaCruce;
	
	@Column(name = "ID_SECRETARIA")
	private Long idSecretaria;
	
	@Column(name = "ID_CIUDAD")
	private Long idCiudad;

	public Long getIdCamara() {
		return idCamara;
	}

	public void setIdCamara(Long idCamara) {
		this.idCamara = idCamara;
	}

	public String getDescripcionCamara() {
		return descripcionCamara;
	}

	public void setDescripcionCamara(String descripcionCamara) {
		this.descripcionCamara = descripcionCamara;
	}

	public String getCodigoCamara() {
		return codigoCamara;
	}

	public void setCodigoCamara(String codigoCamara) {
		this.codigoCamara = codigoCamara;
	}

	public String getVigente() {
		return vigente;
	}

	public void setVigente(String vigente) {
		this.vigente = vigente;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public BigDecimal getLongitud() {
		return longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	public BigDecimal getLatitud() {
		return latitud;
	}

	public void setLatitud(BigDecimal latitud) {
		this.latitud = latitud;
	}

	public String getAliasCamara() {
		return aliasCamara;
	}

	public void setAliasCamara(String aliasCamara) {
		this.aliasCamara = aliasCamara;
	}

	public String getIpCamara() {
		return ipCamara;
	}

	public void setIpCamara(String ipCamara) {
		this.ipCamara = ipCamara;
	}

	public Long getTipoCamara() {
		return tipoCamara;
	}

	public void setTipoCamara(Long tipoCamara) {
		this.tipoCamara = tipoCamara;
	}

	public String getValidaGps() {
		return validaGps;
	}

	public void setValidaGps(String validaGps) {
		this.validaGps = validaGps;
	}

	public Long getInfraccionTecId() {
		return infraccionTecId;
	}

	public void setInfraccionTecId(Long infraccionTecId) {
		this.infraccionTecId = infraccionTecId;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public Long getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(Long idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public Long getNumeroVia() {
		return numeroVia;
	}

	public void setNumeroVia(Long numeroVia) {
		this.numeroVia = numeroVia;
	}

	public Long getIdLetra() {
		return idLetra;
	}

	public void setIdLetra(Long idLetra) {
		this.idLetra = idLetra;
	}

	public Long getIdCardinalidad() {
		return idCardinalidad;
	}

	public void setIdCardinalidad(Long idCardinalidad) {
		this.idCardinalidad = idCardinalidad;
	}

	public Long getIdTipoViaCruce() {
		return idTipoViaCruce;
	}

	public void setIdTipoViaCruce(Long idTipoViaCruce) {
		this.idTipoViaCruce = idTipoViaCruce;
	}

	public Long getIdNumeroViaCruce() {
		return idNumeroViaCruce;
	}

	public void setIdNumeroViaCruce(Long idNumeroViaCruce) {
		this.idNumeroViaCruce = idNumeroViaCruce;
	}
	

	public Long getLetraViaCruceId() {
		return letraViaCruceId;
	}

	public void setLetraViaCruceId(Long letraViaCruceId) {
		this.letraViaCruceId = letraViaCruceId;
	}

	public Long getIdCardinalidadViaCruce() {
		return idCardinalidadViaCruce;
	}

	public void setIdCardinalidadViaCruce(Long idCardinalidadViaCruce) {
		this.idCardinalidadViaCruce = idCardinalidadViaCruce;
	}

	public Long getIdSecretaria() {
		return idSecretaria;
	}

	public void setIdSecretaria(Long idSecretaria) {
		this.idSecretaria = idSecretaria;
	}

	public Long getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Long idCiudad) {
		this.idCiudad = idCiudad;
	}

	@Override
	public String toString() {
		return "Camara [idCamara=" + idCamara + ", descripcionCamara=" + descripcionCamara + ", codigoCamara="
				+ codigoCamara + ", vigente=" + vigente + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin
				+ ", longitud=" + longitud + ", latitud=" + latitud + ", aliasCamara=" + aliasCamara + ", ipCamara="
				+ ipCamara + ", tipoCamara=" + tipoCamara + ", validaGps=" + validaGps + ", infraccionTecId="
				+ infraccionTecId + ", idZona=" + idZona + ", idTipoVia=" + idTipoVia + ", numeroVia=" + numeroVia
				+ ", idLetra=" + idLetra + ", idCardinalidad=" + idCardinalidad + ", idTipoViaCruce=" + idTipoViaCruce
				+ ", idNumeroViaCruce=" + idNumeroViaCruce + ", idCardinalidadViaCruce=" + idCardinalidadViaCruce
				+ ", idSecretaria=" + idSecretaria + ", idCiudad=" + idCiudad + "]";
	}
}
