package co.gov.metropol.area247.services.rest.gtpc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.OperadorDTO;
import co.gov.metropol.area247.model.OperadorEmpresaDTO;
import co.gov.metropol.area247.model.RutaGtpcDTO;
import co.gov.metropol.area247.model.RutaTipoSistemaRutaDTO;
import co.gov.metropol.area247.model.TipoModoTransporteDTO;
import co.gov.metropol.area247.model.TipoRutaDTO;
import co.gov.metropol.area247.model.TipoSistemaRutaDTO;
import co.gov.metropol.area247.services.rest.metro.CoordenadaWSDTO;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;


@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class RutaGtpcWSDTO {
	
	@JsonProperty(value = "idRuta")
	private Long idRuta;
	
	@JsonProperty(value = "codigoRuta")
	private String codigoRuta;
	
	@JsonProperty(value = "descripcion")
	private String descripcion;
	
	@JsonProperty(value = "longitudRuta")
	private Double longitudRuta;
	
	@JsonProperty(value = "coordenadas")
	private String[] coordenadas;
	
	@JsonProperty(value = "primerpunto")
	private String[] primerPunto;
	
	@JsonProperty(value = "ultimopunto")
	private String[] ultimoPunto;
	
	@JsonProperty(value = "rutaActiva")
	private String rutaActiva;
	
	@JsonProperty(value = "idTipoRuta")
	private Long idTipoRuta;
	
	@JsonProperty(value = "nombreRuta")
	private String nombreRuta;
	
	@JsonProperty(value = "tiempoEstimadoRuta")
	private Long  tiempoEstimadoRuta;
	
	@JsonProperty(value = "idModoRuta")
	private Long  idModoRuta;
	
	@JsonProperty(value = "nombreModo")
	private String  nombreModo;
	
	/*@JsonProperty(value = "idTipoSistemaRuta")// --ok
	private Long idTipoSistemaRuta;
	
	@JsonProperty(value = "nombreSistemaRuta") // --ok
	private String nombreSistemaRuta;
	
	@JsonProperty(value = "idOperador") // --ok
	private Long idOperador;

	@JsonProperty(value = "activoOperador") // --ok
	private String  activoOperador;
	
	@JsonProperty(value = "idEmpresa") //-- ok
	private Long  idEmpresa;
	
	@JsonProperty(value = "activoOperadorEmpresa") // --ok
	private String  activoOperadorEmpresa;
	
	@JsonProperty(value = "idPersona") // --ok
	private Long  idPersona;
	
	@JsonProperty(value = "ativoEmpresa") // --ok
	private String  ativoEmpresa;
	
	@JsonProperty(value = "nombres") // ok
	private String  nombres;*/
	
	@JsonProperty(value = "infoRutaOperadorEmpresa")
	private List<InfoRutaOperadorEmpresasWSDTO> infoRutaOperadorEmpresa;
	
	@JsonProperty(value = "paraderos")
	private List<ParaderoGtpcWSDTO> paraderos;
	
	@JsonProperty(value = "frecuencia")
	private List<FrecuenciaGtpcWSDTO> frecuencia; 
	
	@JsonProperty(value = "horario")
	private List<HorarioGtpcWSDTO> horario;
	
	public List<HorarioGtpcWSDTO> getHorario() {
		return horario;
	}

	public void setHorario(List<HorarioGtpcWSDTO> horario) {
		this.horario = horario;
	}

	public List<FrecuenciaGtpcWSDTO> getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(List<FrecuenciaGtpcWSDTO> frecuencia) {
		this.frecuencia = frecuencia;
	}

	public List<ParaderoGtpcWSDTO> getParaderos() {
		return paraderos;
	}

	public void setParaderos(List<ParaderoGtpcWSDTO> paraderos) {
		this.paraderos = paraderos;
	}

	public String getCodigoRuta() {
        return codigoRuta;
    }

    public void setCodigoRuta(String codigoRuta) {
        this.codigoRuta = codigoRuta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getLongitudRuta() {
		return longitudRuta;
	}

	public void setLongitudRuta(Double longitudRuta) {
		this.longitudRuta = longitudRuta;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public String[] getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String[] coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	public String[] getPrimerPunto() {
		return primerPunto;
	}

	public void setPrimerPunto(String[] primerPunto) {
		this.primerPunto = primerPunto;
	}

	public String[] getUltimoPunto() {
		return ultimoPunto;
	}

	public void setUltimoPunto(String[] ultimoPunto) {
		this.ultimoPunto = ultimoPunto;
	}

	public String getRutaActiva() {
        return rutaActiva;
    }

    public void setRutaActiva(String rutaActiva) {
        this.rutaActiva = rutaActiva;
    }

    public Long getIdTipoRuta() {
        return idTipoRuta;
    }

    public void setIdTipoRuta(Long idTipoRuta) {
        this.idTipoRuta = idTipoRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

   /* public Long getIdTipoSistemaRuta() {
        return idTipoSistemaRuta;
    }

    public void setIdTipoSistemaRuta(Long idTipoSistemaRuta) {
        this.idTipoSistemaRuta = idTipoSistemaRuta;
    }

    public String getNombreSistemaRuta() {
        return nombreSistemaRuta;
    }

    public void setNombreSistemaRuta(String nombreSistemaRuta) {
        this.nombreSistemaRuta = nombreSistemaRuta;
    }

    public Long getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Long idOperador) {
        this.idOperador = idOperador;
    }

    public String getActivoOperador() {
        return activoOperador;
    }

    public void setActivoOperador(String activoOperador) {
        this.activoOperador = activoOperador;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getActivoOperadorEmpresa() {
        return activoOperadorEmpresa;
    }

    public void setActivoOperadorEmpresa(String activoOperadorEmpresa) {
        this.activoOperadorEmpresa = activoOperadorEmpresa;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getAtivoEmpresa() {
        return ativoEmpresa;
    }

    public void setAtivoEmpresa(String ativoEmpresa) {
        this.ativoEmpresa = ativoEmpresa;
    }

	public String getNombre() {
		return nombres;
	}

	public void setNombre(String nombre) {
		this.nombres = nombre;
	}*/

	public Long getTiempoEstimadoRuta() {
		return tiempoEstimadoRuta;
	}

	public void setTiempoEstimadoRuta(Long tiempoEstimadoRuta) {
		this.tiempoEstimadoRuta = tiempoEstimadoRuta;
	}

	public Long getIdModoRuta() {
        return idModoRuta;
    }

    public void setIdModoRuta(Long idModoRuta) {
        this.idModoRuta = idModoRuta;
    }

    public String getNombreModo() {
        return nombreModo;
    }

    public void setNombreModo(String nombreModo) {
        this.nombreModo = nombreModo;
    }
    
    public RutaGtpcDTO getRutaGtpcDTO() {
    	try {
    		RutaGtpcDTO rutaGtpcDTO = new RutaGtpcDTO();
        	rutaGtpcDTO.setIdItem(this.idRuta);
        	rutaGtpcDTO.setFuenteDatos(2);
        	rutaGtpcDTO.setCodigo(this.codigoRuta);
        	rutaGtpcDTO.setDescripcion(this.descripcion);
        	rutaGtpcDTO.setLongitudRuta(this.longitudRuta);
        	rutaGtpcDTO.setCoordenadas(GeometryUtil.obtenerLineString(this.coordenadas, true));
        	rutaGtpcDTO.setPrimerPunto(GeometryUtil.obtenerPunto(this.primerPunto));
        	rutaGtpcDTO.setUltimoPunto(GeometryUtil.obtenerPunto(this.ultimoPunto));
        	rutaGtpcDTO.setRutaActiva(this.rutaActiva);
        	rutaGtpcDTO.setIdTipoRuta(this.idTipoRuta);
        	//rutaGtpcDTO.setIdTipoSistemaRuta(this.idTipoSistemaRuta);
        	rutaGtpcDTO.setTiempoEstimado(this.tiempoEstimadoRuta);
        	rutaGtpcDTO.setValorTarifa(null);
        	return rutaGtpcDTO;
		} catch (Exception e) {
			throw new Area247Exception(String.format("Error al obtener el DTO de la ruta %s", this.idRuta));
		}
    }
    
    public TipoModoTransporteDTO getTipoModoTransporteDTO() {
    	TipoModoTransporteDTO tipoModoTransporteDTO = new TipoModoTransporteDTO();
    	tipoModoTransporteDTO.setIdItem(this.idModoRuta);
    	tipoModoTransporteDTO.setFuenteDatos(2);
    	tipoModoTransporteDTO.setNombre(this.nombreModo);
    	tipoModoTransporteDTO.setDescripcion("-");
    	return tipoModoTransporteDTO;
    }
    
    public TipoRutaDTO getTipoRutaDTO() {
    	TipoRutaDTO tipoRutaDTO = new TipoRutaDTO();
    	tipoRutaDTO.setIdItem(this.idTipoRuta);
    	tipoRutaDTO.setFuenteDatos(2);
    	tipoRutaDTO.setNombre(this.nombreRuta);
    	tipoRutaDTO.setDescripcion("-");
    	return tipoRutaDTO;
    }
    
    
    public List<TipoSistemaRutaDTO> getTiposSitemaRutaDTO() {
    	Set<TipoSistemaRutaDTO> listTipoSistemaRutaDTO = new HashSet<>(); 
    	if (Utils.isNotEmpty(this.infoRutaOperadorEmpresa)) {
	    	for (InfoRutaOperadorEmpresasWSDTO infoRutaOperadorEmpresasWSDTO : this.infoRutaOperadorEmpresa) {
	        	TipoSistemaRutaDTO tipoSistemaRutaDTO = infoRutaOperadorEmpresasWSDTO.getTipoSistemaRutaDTO();
	        	listTipoSistemaRutaDTO.add(tipoSistemaRutaDTO);
			}
    	}
    	return new ArrayList<>(listTipoSistemaRutaDTO);
    }
    
    public List<RutaTipoSistemaRutaDTO> getRutaTiposSitemaRutaDTO() {
    	Set<RutaTipoSistemaRutaDTO> listRutaTipoSistemaRutaDTO = new HashSet<>();
    	if (Utils.isNotEmpty(this.infoRutaOperadorEmpresa)) {
	    	for (InfoRutaOperadorEmpresasWSDTO infoRutaOperadorEmpresasWSDTO : this.infoRutaOperadorEmpresa) {
	    		RutaTipoSistemaRutaDTO rutaTipoSistemaRutaDTO = new RutaTipoSistemaRutaDTO();
	    		rutaTipoSistemaRutaDTO.setIdRuta(this.idRuta);
	    		rutaTipoSistemaRutaDTO.setIdTipoSistemaRuta(infoRutaOperadorEmpresasWSDTO.getTipoSistemaRuta());
	    		listRutaTipoSistemaRutaDTO.add(rutaTipoSistemaRutaDTO);
			}
    	}
    	return new ArrayList<>(listRutaTipoSistemaRutaDTO);
    }
    
    public List<OperadorDTO> getOperadoresDTO() {
    	List<OperadorDTO> operadoresDTO = new ArrayList<>();
    	if (Utils.isNotEmpty(this.infoRutaOperadorEmpresa)) {
	    	for (InfoRutaOperadorEmpresasWSDTO infoRutaOperadorEmpresasWSDTO : this.infoRutaOperadorEmpresa) {
	    		OperadorDTO operadorDTO = new OperadorDTO();
	    		operadorDTO.setIdItem(infoRutaOperadorEmpresasWSDTO.getIdOperador());
	        	operadorDTO.setIdSistemaRuta(infoRutaOperadorEmpresasWSDTO.getTipoSistemaRuta());
	        	operadorDTO.setActivo(infoRutaOperadorEmpresasWSDTO.getOperadorActivo());
	        	operadoresDTO.add(operadorDTO);
	    	} 
    	}
    	return operadoresDTO;
    }
        
    public List<OperadorEmpresaDTO> getOperadoresEmpresaDTO() {
    	List<OperadorEmpresaDTO> operadoresEmpresaDTO = new ArrayList<>();
    	if (Utils.isNotEmpty(this.infoRutaOperadorEmpresa)) {
	    	for (InfoRutaOperadorEmpresasWSDTO infoRutaOperadorEmpresasWSDTO : this.infoRutaOperadorEmpresa) {
	    		OperadorEmpresaDTO operadorEmpresaDTO = new OperadorEmpresaDTO();
	        	operadorEmpresaDTO.setIdItem(infoRutaOperadorEmpresasWSDTO.getIdEmpresa());
	        	operadorEmpresaDTO.setIdOperador(infoRutaOperadorEmpresasWSDTO.getIdOperador());
	        	operadorEmpresaDTO.setIdEmpresaTransporte(infoRutaOperadorEmpresasWSDTO.getIdPersona());
	        	if(infoRutaOperadorEmpresasWSDTO.getEmpresaActivo().trim().equals("S") &&  
	        		infoRutaOperadorEmpresasWSDTO.getOperadorActivo().trim().equals("S")) {
	        		operadorEmpresaDTO.setActivo("S");
	        	}else {
	        		operadorEmpresaDTO.setActivo("N");
	        	}
	        	
	        	operadoresEmpresaDTO.add(operadorEmpresaDTO);
	    	}
    	}
    	return operadoresEmpresaDTO;
    }
        
    public List<EmpresaTransporteDTO> getEmpresasTransporteDTO() {
    	List<EmpresaTransporteDTO> empresasTransporteDTO = new ArrayList<>();
    	if (Utils.isNotEmpty(this.infoRutaOperadorEmpresa)) {
	    	for (InfoRutaOperadorEmpresasWSDTO infoRutaOperadorEmpresasWSDTO : this.infoRutaOperadorEmpresa) {
	    		EmpresaTransporteDTO empresaTransporteDTO = new EmpresaTransporteDTO();
	        	empresaTransporteDTO.setIdItem(infoRutaOperadorEmpresasWSDTO.getIdPersona());
	        	empresaTransporteDTO.setNombre(infoRutaOperadorEmpresasWSDTO.getNombrePersona());
	        	empresaTransporteDTO.setActivo(infoRutaOperadorEmpresasWSDTO.getEmpresaActivo());
	        	empresasTransporteDTO.add(empresaTransporteDTO);
	    	}
    	}
    	return empresasTransporteDTO;
    }

}
