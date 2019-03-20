package co.gov.metropol.area247.mapper.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.CiclorutaDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.model.PuntoTarjetaCivicaDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ClasificacionInformacion;
import co.gov.metropol.area247.util.Utils;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PosibilidadViajeResponse {

	@ApiModelProperty(value = "Respuesta de Estaciones cercanas ", required = true)
	private List<EstacionResponse> estaciones;

	@ApiModelProperty(value = "Respuesta de ruta ciclvia ", required = true)
	private List<CicloviaResponse> ciclovias;

	@ApiModelProperty(value = "Respuesta de lineas ", required = true)
	private List<LineaMetroResponse> lineas;

	@ApiModelProperty(value = "Respuesta puntos tarjeta civica ", required = true)
	private List<TarjetaCivicaResponse> puntosTarjetaCivica;

	@ApiModelProperty(value = "Respuesta de ruta gtpc", required = true)
	private List<RutaGtpcResponse> rutas;

	@ApiModelProperty(value = "Respuesta de paraderos cercanos ", required = true)
	private List<ParaderoResponse> paraderos;

	public PosibilidadViajeResponse() {
		inicializar();
	}

	public PosibilidadViajeResponse(Map<ClasificacionInformacion, List<Object>> informacion) {

		inicializar();

		if (!Utils.isNull(informacion)) {
			
			if (informacion.containsKey(ClasificacionInformacion.RUTAS))
				clasificarInformacionRutas(informacion.get(ClasificacionInformacion.RUTAS));
			
			if (informacion.containsKey(ClasificacionInformacion.ESTACIONES))
				clasificarInformacionEstaciones(informacion.get(ClasificacionInformacion.ESTACIONES));
			
			if (informacion.containsKey(ClasificacionInformacion.RECARGAS))
				clasificarInformacionRecargas(informacion.get(ClasificacionInformacion.RECARGAS));
		}
	}

	public List<LineaMetroResponse> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaMetroResponse> lineas) {
		this.lineas = lineas;
	}

	public List<TarjetaCivicaResponse> getPuntosTarjetaCivica() {
		return puntosTarjetaCivica;
	}

	public void setPuntosTarjetaCivica(List<TarjetaCivicaResponse> puntosTarjetaCivica) {
		this.puntosTarjetaCivica = puntosTarjetaCivica;
	}

	public List<EstacionResponse> getEstaciones() {
		return estaciones;
	}

	public void setEstaciones(List<EstacionResponse> estaciones) {

		if (this.estaciones.size() > 0) {
			this.estaciones.addAll(estaciones);
		} else {
			this.estaciones = estaciones;
		}
	}

	public List<CicloviaResponse> getCiclovias() {
		return ciclovias;
	}

	public void setCiclovias(List<CicloviaResponse> ciclovias) {
		this.ciclovias = ciclovias;
	}

	public List<ParaderoResponse> getParaderos() {
		return paraderos;
	}

	public void setParaderos(List<ParaderoResponse> paraderos) {
		this.paraderos = paraderos;
	}

	public List<RutaGtpcResponse> getRutas() {
		return rutas;
	}

	public void setRutas(List<RutaGtpcResponse> rutas) {
		this.rutas = rutas;
	}

	private void clasificarInformacionRutas(List<Object> infoRutas) {
		infoRutas.iterator().forEachRemaining(ruta -> {
			if (ruta instanceof LineaMetroDTO)
				lineas.add(new LineaMetroResponse((LineaMetroDTO) ruta));
			if (ruta instanceof RutaMetroDTO)
				rutas.add(new RutaGtpcResponse((RutaMetroDTO) ruta));
			if (ruta instanceof CiclorutaDTO)
				ciclovias.add(new CicloviaResponse((CiclorutaDTO) ruta));
		});
	}

	private void clasificarInformacionEstaciones(List<Object> infoEstaciones) {
		infoEstaciones.iterator().forEachRemaining(estacion -> {
			if (estacion instanceof EstacionLineaMetroDTO) 
				adicionarEstacionLineaMetroDTO(((EstacionLineaMetroDTO) estacion), estaciones);							
			if (estacion instanceof ParaderoRutaMetroDTO)
				adicionarParaderoRutaMetroDTO(((ParaderoRutaMetroDTO) estacion), paraderos);
			if (estacion instanceof TareviaEstacionEnciclaDTO)
				estaciones.add(new EstacionResponse((TareviaEstacionEnciclaDTO) estacion));
		});
	}

	private void clasificarInformacionRecargas(List<Object> infoRecargas) {
		infoRecargas.iterator().forEachRemaining(recarga -> {
			if (recarga instanceof PuntoTarjetaCivicaDTO)
				puntosTarjetaCivica.add(new TarjetaCivicaResponse((PuntoTarjetaCivicaDTO) recarga));

		});
	}
	
	/**
	 * Verifica que la estacion no se encuentre ya repetido, validanco el codigo
	 * y descripcion de la estacionLineaMetroDTO en la lista de estaciones, y en
	 * caso de que se encuentre este adiciona la linea que tenga el
	 * estacionLineaMetroDTO.
	 * 
	 * @param estacionLineaMetroDTO
	 *            - nueva estacion,, objeto a validar
	 * @param estaciones
	 *            - lista de estaciones no repetida, lista contra la cual se
	 *            valida las estaciones
	 */
	private void adicionarEstacionLineaMetroDTO(EstacionLineaMetroDTO estacionLineaMetroDTO, List<EstacionResponse> estaciones) {
		final String nombre = estacionLineaMetroDTO.getCodigo();
		final String descripcion = estacionLineaMetroDTO.getDescripcion();
		boolean adicionarLinea = false;
		int indexAdicionar = 0;
		for (EstacionResponse estacionResponse : estaciones) {
			if (estacionResponse.getNombre().equals(nombre) && estacionResponse.getDescripcion().equals(descripcion)) {
				indexAdicionar = estaciones.indexOf(estacionResponse);
				adicionarLinea = true;
				break;
			}
		}
		
		if (adicionarLinea) {
			estaciones.get(indexAdicionar).getLineas()
			.add(new RutaInfoBasicaResponse(estacionLineaMetroDTO.getLineaDTO()));
		} else {
			estaciones.add(new EstacionResponse(estacionLineaMetroDTO));
		}
	}
	
	/**
	 * Cumple la misma funcion que el metodo
	 * {@link #adicionarEstacionLineaMetro(EstacionLineaMetroDTO, List)}
	 * 
	 * @param paraderoRutaMetroDTO
	 *            - nuevo paradero, objeto a validar
	 * @param paraderos
	 *            - lista de paraderos no repetidos, lista contra la cual se
	 *            valida los nuevos paraderos
	 */
	private void adicionarParaderoRutaMetroDTO(ParaderoRutaMetroDTO paraderoRutaMetroDTO, List<ParaderoResponse> paraderos) {
		final String codigo = paraderoRutaMetroDTO.getCodigo();
		final String descripcion = paraderoRutaMetroDTO.getDescripcion();
		boolean adicionarRuta = false;
		int indexAdicionar = 0;
		for (ParaderoResponse paraderoResponse : paraderos) {
			if (paraderoResponse.getCodigoParadero().equals(codigo) && paraderoResponse.getDescripcion().equals(descripcion)) {
				indexAdicionar = paraderos.indexOf(paraderoResponse);
				adicionarRuta = true;
				break;
			}
		}
		
		if (adicionarRuta) {
			paraderos.get(indexAdicionar).getRutas()
			.add(new RutaInfoBasicaResponse(paraderoRutaMetroDTO.getRutaDTO()));
		} else {
			paraderos.add(new ParaderoResponse(paraderoRutaMetroDTO));
		}
	}

	private void inicializar() {
		this.estaciones = new ArrayList<>();
		this.ciclovias = new ArrayList<>();
		this.paraderos = new ArrayList<>();
		this.rutas = new ArrayList<>();
		this.puntosTarjetaCivica = new ArrayList<>();
		this.lineas = new ArrayList<>();
	}

}
