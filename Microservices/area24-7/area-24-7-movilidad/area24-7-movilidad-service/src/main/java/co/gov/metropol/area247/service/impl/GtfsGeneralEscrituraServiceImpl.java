package co.gov.metropol.area247.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.GtfsWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.service.IGtfsEscrituraService;
import co.gov.metropol.area247.service.ITipoParametroService;
import co.gov.metropol.area247.util.constantes.Constantes.TipoParametro;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service("gtfsGeneralEscrituraService")
public class GtfsGeneralEscrituraServiceImpl implements IGtfsEscrituraService {

	@Autowired
	@Qualifier("gtfsMetroArchivoEscrituraService")
	private IGtfsEscrituraService gtfsMetroArchivoEscrituraService;

	@Autowired
	@Qualifier("gtfsRutaGtpcEscrituraService")
	private IGtfsEscrituraService gtfsRutaGtpcEscrituraService;

	@Autowired
	private ITipoParametroService tipoParametroService;

	@Override
	public void escribirGTFS() {

		try {

			GtfsWriter writer = new GtfsWriter();

			String url = tipoParametroService.obtenerValorCadena(TipoParametro.URL_FOLDER_GTFS_GENERAL);
			String nombreGtfs = tipoParametroService.obtenerValorCadena(TipoParametro.NAME_GTFS_GENERAL);

			String urlAbsoluta = url.concat(nombreGtfs);

			writer.setOutputLocation(new File(urlAbsoluta));

			obtenerCalendarios().iterator().forEachRemaining(calendario -> {
				writer.handleEntity(calendario);
			});

			obtenerAgencias().iterator().forEachRemaining(agencia -> {
				writer.handleEntity(agencia);
			});

			obtenerRutas().iterator().forEachRemaining(ruta -> writer.handleEntity(ruta));

			obtenerParaderos().iterator().forEachRemaining(paradero -> {
				writer.handleEntity(paradero);
			});

			obtenerCoordenadas().iterator().forEachRemaining(coordenada -> {
				writer.handleEntity(coordenada);
			});

			obtenerViajes().iterator().forEachRemaining(viaje -> {
				writer.handleEntity(viaje);
			});

			obtenerTiemposDeParada().iterator().forEachRemaining(tiemposParada -> {
				writer.handleEntity(tiemposParada);
			});

			writer.close();
		} catch (IOException e) {
			throw new Area247Exception("Error al generar GTFS con la informacion de las rutas del metro y GTPC");
		}

	}

	@Override
	public Set<Route> obtenerRutas() {
		Set<Route> rutas = new HashSet<>();
		rutas.addAll(gtfsMetroArchivoEscrituraService.obtenerRutas());
		rutas.addAll(gtfsRutaGtpcEscrituraService.obtenerRutas());
		return rutas;
	}

	@Override
	public Set<Stop> obtenerParaderos() {
		Set<Stop> paraderos = new HashSet<>();
		paraderos.addAll(gtfsMetroArchivoEscrituraService.obtenerParaderos());
		paraderos.addAll(gtfsRutaGtpcEscrituraService.obtenerParaderos());
		return paraderos;
	}

	@Override
	public Collection<ShapePoint> obtenerCoordenadas() {
		List<ShapePoint> coordenadas = new ArrayList<>();
		coordenadas.addAll(gtfsMetroArchivoEscrituraService.obtenerCoordenadas());
		coordenadas.addAll(gtfsRutaGtpcEscrituraService.obtenerCoordenadas());
		return coordenadas;
	}

	@Override
	public Map<Trip, List<StopTime>> obtenerViajesConTiemposParada() {
		return null;
	}

	@Override
	public Set<Agency> obtenerAgencias() {
		Set<Agency> agencias = new HashSet<>();
		agencias.addAll(gtfsMetroArchivoEscrituraService.obtenerAgencias());
		agencias.addAll(gtfsRutaGtpcEscrituraService.obtenerAgencias());
		return agencias;
	}

	@Override
	public Set<ServiceCalendar> obtenerCalendarios() {
		Set<ServiceCalendar> calendarios = new HashSet<>();
		calendarios.addAll(gtfsMetroArchivoEscrituraService.obtenerCalendarios());
		calendarios.addAll(gtfsRutaGtpcEscrituraService.obtenerCalendarios());
		return calendarios;
	}

	@Override
	public Set<Trip> obtenerViajes() {
		Set<Trip> viajes = new HashSet<>();
		viajes.addAll(gtfsMetroArchivoEscrituraService.obtenerViajes());
		viajes.addAll(gtfsRutaGtpcEscrituraService.obtenerViajes());
		return viajes;
	}

	@Override
	public Collection<StopTime> obtenerTiemposDeParada() {
		List<StopTime> tiemposDeParada = new ArrayList<>();
		tiemposDeParada.addAll(gtfsMetroArchivoEscrituraService.obtenerTiemposDeParada());
		tiemposDeParada.addAll(gtfsRutaGtpcEscrituraService.obtenerTiemposDeParada());
		return tiemposDeParada;
	}

}
