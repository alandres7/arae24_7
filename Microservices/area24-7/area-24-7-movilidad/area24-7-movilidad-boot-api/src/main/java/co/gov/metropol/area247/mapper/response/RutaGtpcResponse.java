package co.gov.metropol.area247.mapper.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Coordinate;

import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.FrecuenciaRutaDTO;
import co.gov.metropol.area247.model.HorarioRutaDTO;
import co.gov.metropol.area247.model.RutaGtpcDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.TipoModoTransporteDTO;
import co.gov.metropol.area247.model.TipoRutaDTO;
import co.gov.metropol.area247.model.TipoSistemaRutaDTO;
import co.gov.metropol.area247.util.Utils;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RutaGtpcResponse {
	@ApiModelProperty(value = "Identificador de la ruta", required = true)
	private Long idRuta;
	
	@ApiModelProperty(value = "Codigo de la ruta", required = true)
	private String codigoRuta;
	
	@ApiModelProperty(value = "Descripción de la ruta de la ruta", required = true)
	private String descripcion;
	
	@ApiModelProperty(value = "Coordenadas de la ruta", required = true)
	private String[] coordenadas;
	
	@ApiModelProperty(value = "Primer punto de la ruta", required = true)
	private List<Double> primerPunto;
	
	@ApiModelProperty(value = "Último punto de la ruta", required = true)
	private List<Double> ultimoPunto;
	
	@ApiModelProperty(value = "Nombre del tipo ruta", required = true)
	private String nombreRuta;
	
	@ApiModelProperty(value = "Nombre del sistema de ruta", required = true)
	private String nombreSistemaRuta;
	
	@ApiModelProperty(value = "Nombre de la persona juridica", required = true)
	private List<String> nombres;
	
	@ApiModelProperty(value = "Cantidad de vehículos que pasan por hora", required = true)
	private List<FrecuenciaRutaResponse> frecuencias;
	
	@ApiModelProperty(value = "Horas de operación de las rutas", required = true)
	private List<HorarioRutaResponse> horarios;
	
	@ApiModelProperty(value = "Paraderos de las ruta", required = true)
	private List<ParaderoResponse> paraderos;
	
	@ApiModelProperty(value = "Valor de la tarifa de la ruta.", required = true)
	private Double valorTarifa;
	
	@ApiModelProperty(value = "Identificador del modo de transporte al que pertenece la línea.", required = true)
	private Long idModoRuta;
	
	@ApiModelProperty(value = "Nombre del modo de transporte al que pertenece la línea.", required = true)
	private String nombreModoRuta;
	
	@ApiModelProperty(value = "Nombre de la empresa a la que pertenece la ruta.", required = true)
	private String empresa;

	public RutaGtpcResponse (RutaGtpcDTO rutaGtpcDTO, TipoRutaDTO tipoRutaDTO, TipoSistemaRutaDTO tipoSistemaRutaDTO, List<FrecuenciaRutaDTO> frecuenciasRutasDTO, 
							TipoModoTransporteDTO tipoModoTransporte, List<HorarioRutaDTO> horariosRutaDTO, List<EmpresaTransporteDTO> empresasTransporteDTO) {
		this.idRuta = rutaGtpcDTO.getId();
		this.codigoRuta = rutaGtpcDTO.getCodigo();
		this.descripcion = rutaGtpcDTO.getDescripcion();

		String coor = rutaGtpcDTO.getCoordenadas().toString().replace(", ", ",");
		coor = coor.replace(" ", ","); 
		String coordenadas = coor.substring(12,
				(coor.length() - 1));
		this.coordenadas = coordenadas.split(",");
		
		Coordinate[] primerPunto = rutaGtpcDTO.getPrimerPunto().getCoordinates();
		this.primerPunto = new ArrayList<>();
		for(int i=0; i<primerPunto.length; i++) {
			this.primerPunto.add(primerPunto[i].x);
			this.primerPunto.add(primerPunto[i].y);
		}
		
		Coordinate[] segundoPunto = rutaGtpcDTO.getPrimerPunto().getCoordinates();
		this.ultimoPunto = new ArrayList<>();
		for(int i=0; i<segundoPunto.length; i++) {
			this.ultimoPunto.add(segundoPunto[i].x);
			this.ultimoPunto.add(segundoPunto[i].y);
		}
		this.nombreRuta = tipoRutaDTO.getNombre();
		this.nombreSistemaRuta = tipoSistemaRutaDTO.getNombre();
		this.nombres = new ArrayList<>();
		if(!empresasTransporteDTO.isEmpty()) {
			for(int i=0; i<empresasTransporteDTO.size(); i++) {
				this.nombres.add(empresasTransporteDTO.get(i).getNombre());
			}
		}
		
		
		this.frecuencias = new ArrayList<>();
		if(frecuenciasRutasDTO.size() == 0) {
			FrecuenciaRutaResponse frecuenciaRutaResponse = new FrecuenciaRutaResponse();
			frecuencias.add(frecuenciaRutaResponse);
		}else {
			for(int i = 0; i<frecuenciasRutasDTO.size(); i++) {
				FrecuenciaRutaResponse frecuenciaRutaResponse = new FrecuenciaRutaResponse(frecuenciasRutasDTO.get(i));
				frecuencias.add(frecuenciaRutaResponse);
			}
		}
		
		this.horarios = new ArrayList<>();
		if(horariosRutaDTO.size() == 0) {
			HorarioRutaResponse horarioRutaResponse = new HorarioRutaResponse();
			horarios.add(horarioRutaResponse);
		}else {
			for(int i = 0; i<horariosRutaDTO.size(); i++) {
				HorarioRutaResponse horarioRutaResponse = new HorarioRutaResponse(horariosRutaDTO.get(i));
				horarios.add(horarioRutaResponse);
			}
		}		
		
		this.valorTarifa = Utils.isNull(rutaGtpcDTO.getValorTarifa()) ? null : rutaGtpcDTO.getValorTarifa().doubleValue();
		this.idModoRuta = Long.valueOf(rutaGtpcDTO.getIdModoRuta().indice());
		this.nombreModoRuta = tipoModoTransporte.getNombre();
	}
	
	public RutaGtpcResponse(RutaMetroDTO rutaMetroDTO) {
		
		this.idRuta = rutaMetroDTO.getId();
		this.codigoRuta = rutaMetroDTO.getCodigo();
		this.descripcion = rutaMetroDTO.getDescripcion();
		this.valorTarifa = rutaMetroDTO.getValorTarifa();
		
		Coordinate[] arrayCoordenadas = rutaMetroDTO.getCoordenadas().getCoordinates();
		this.coordenadas = GeometryUtil.toArrayString(arrayCoordenadas);
		this.primerPunto = Lists.newArrayList(arrayCoordenadas[0].x, arrayCoordenadas[0].y);
		this.ultimoPunto = Lists.newArrayList(arrayCoordenadas[arrayCoordenadas.length - 1].x,
				arrayCoordenadas[arrayCoordenadas.length - 1].y);
		
		this.idModoRuta = Long.valueOf(rutaMetroDTO.getModoRutaDTO().indice());
		this.nombreModoRuta = rutaMetroDTO.getModoRutaDTO().name();				
		this.nombreRuta = Utils.isNull(rutaMetroDTO.getTipoRutaDTO()) ? null
				: rutaMetroDTO.getTipoRutaDTO().getNombre();
		//this.nombreSistemaRuta = rutaMetroDTO.getTipoSistemaRutaDTO().getNombre();
		this.nombres = rutaMetroDTO.getEmpresasTransporteDTO().stream().map(EmpresaTransporteDTO::getNombre).collect(Collectors.toList());
		this.frecuencias = rutaMetroDTO.getFrecuenciasDTO().stream().map(i -> new FrecuenciaRutaResponse(i)).collect(Collectors.toList());
		this.horarios = rutaMetroDTO.getHorariosDTO().stream().map(i -> new HorarioRutaResponse(i)).collect(Collectors.toList());
		this.paraderos = rutaMetroDTO.getParaderosDTO().stream().map(i -> new ParaderoResponse(i)).collect(Collectors.toList());
		this.empresa = String.join(", ", rutaMetroDTO.getEmpresasTransporteDTO().stream().map(item -> item.getNombre())
				.distinct().collect(Collectors.toList()));
	}
	
	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
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

	public String[] getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String[] coordenadas) {
		this.coordenadas = coordenadas;
	}

	public List<Double> getPrimerPunto() {
		return primerPunto;
	}

	public void setPrimerPunto(List<Double> primerPunto) {
		this.primerPunto = primerPunto;
	}

	public List<Double> getUltimoPunto() {
		return ultimoPunto;
	}

	public void setUltimoPunto(List<Double> ultimoPunto) {
		this.ultimoPunto = ultimoPunto;
	}

	public String getNombreRuta() {
		return nombreRuta;
	}

	public void setNombreRuta(String nombreRuta) {
		this.nombreRuta = nombreRuta;
	}

	public String getNombreSistemaRuta() {
		return nombreSistemaRuta;
	}

	public void setNombreSistemaRuta(String nombreSistemaRuta) {
		this.nombreSistemaRuta = nombreSistemaRuta;
	}

	public List<String> getNombres() {
		return nombres;
	}

	public void setNombres(List<String> nombres) {
		this.nombres = nombres;
	}
	
	public List<FrecuenciaRutaResponse> getFrecuencias() {
		return frecuencias;
	}

	public void setFrecuencias(List<FrecuenciaRutaResponse> frecuencias) {
		this.frecuencias = frecuencias;
	}

	public List<HorarioRutaResponse> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioRutaResponse> horarios) {
		this.horarios = horarios;
	}

	public Double getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(Double valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public Long getIdModoRuta() {
		return idModoRuta;
	}

	public void setIdModoRuta(Long idModoRuta) {
		this.idModoRuta = idModoRuta;
	}

	public String getNombreModoRuta() {
		return nombreModoRuta;
	}

	public void setNombreModoRuta(String nombreModoRuta) {
		this.nombreModoRuta = nombreModoRuta;
	}

	public List<ParaderoResponse> getParaderos() {
		return paraderos;
	}

	public void setParaderos(List<ParaderoResponse> paraderos) {
		this.paraderos = paraderos;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
}
