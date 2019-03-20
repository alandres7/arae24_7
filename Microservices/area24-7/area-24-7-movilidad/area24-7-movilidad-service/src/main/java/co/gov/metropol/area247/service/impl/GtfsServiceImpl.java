package co.gov.metropol.area247.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;

import co.gov.metropol.area247.service.IGtfsService;

public class GtfsServiceImpl implements IGtfsService {

	private GtfsMutableRelationalDao dao;

	private Map<Route, List<ShapePoint>> shapePointsByRoute;
	private Map<Route, List<Stop>> stopsByRoute;
	private Map<Route, List<StopTime>> stopTimesByRoute;
	private Map<Route, Map<ServiceCalendar, List<StopTime>>> stopTimesByRouteAndService;
	private Map<Route, Map<ServiceCalendar, Map<Trip, List<StopTime>>>> stopTimesByRouteServiceAndTrip;

	public GtfsServiceImpl(GtfsMutableRelationalDao dao) {
		this.dao = dao;

		shapePointsByRoute = new HashMap<>();
		stopsByRoute = new HashMap<>();
		stopTimesByRoute = new HashMap<>();
		stopTimesByRouteAndService = new HashMap<>();
		stopTimesByRouteServiceAndTrip = new HashMap<>();

	}

	@Override
	public List<ShapePoint> findShapePointsByRoute(Route route) {
		/**
		 * Si en la base de conocimiento ya se encuentra la ruta con la
		 * información deseada retorna esta informacion
		 */
		if (shapePointsByRoute.containsKey(route)) {
			return shapePointsByRoute.get(route);
		} else {
			/**
			 * Sino encuentra informacion busca en todas las coordenadas por
			 * diferentes criterios de busqueda como el id de la ruta o nombre
			 * de ruta o el id de la coordenda
			 */
			List<ShapePoint> shapePoints = new ArrayList<>();

			dao.getAllShapePoints().iterator().forEachRemaining(shapePoint -> {
				if (shapePoint.getShapeId().getId().contains(route.getId().getId())
						|| shapePoint.getShapeId().getId().equalsIgnoreCase(route.getLongName())
						|| shapePoint.getShapeId().equals(dao.getTripsForRoute(route).get(0).getShapeId())) {
					shapePoints.add(shapePoint);
				}
			});
			/**
			 * Y si no encuentra informacion por los dos procesos anteriores
			 * entonces obtiene la información a partir de las estaciones de la
			 * ruta y por ultimo almacena esta informaion en la base de
			 * conocimiento
			 */
			if (shapePoints.isEmpty()) {
				findStopsByRoute(route).iterator().forEachRemaining(stop -> {
					ShapePoint shapePoint = new ShapePoint();
					shapePoint.setShapeId(route.getId());
					shapePoint.setLat(stop.getLat());
					shapePoint.setLon(stop.getLon());
					shapePoints.add(shapePoint);
				});
			}

			shapePointsByRoute.put(route, shapePoints);

			return shapePoints;
		}
	}

	@Override
	public List<Stop> findStopsByRoute(Route route) {
		/**
		 * Si en la base de conocimiento ya se encuentra la ruta con la
		 * información deseada retorna esta informacion
		 */
		if (stopsByRoute.containsKey(route)) {
			return stopsByRoute.get(route);
		} else {
			/**
			 * Sino encuentra informacion entonces busca en todos los paraderos
			 * filtrando por la ruta definida
			 */
			List<Stop> stops = new ArrayList<>();
			final List<Trip> trips = dao.getTripsForRoute(route);

			if (trips != null && !trips.isEmpty()) {
				final Trip trip = trips.get(0);
				dao.getStopTimesForTrip(trip).iterator().forEachRemaining(stopTime -> {
					stops.add(stopTime.getStop());
				});
			}

			stopsByRoute.put(route, stops);

			return stops;
		}
	}

	@Override
	public StopTime getStopTimeByRouteAndServiceAndArrivalTime(Route route, ServiceCalendar service, int arrivalTime,
			Integer sequence) {
		List<StopTime> stopTimes = findStopTimeByRouteAndService(route, service);
		if (sequence == null) {
			return approximateToArrivalTime(arrivalTime, stopTimes);
		}
		return approximateToArrivalTime(arrivalTime, stopTimes, sequence);
	}

	private StopTime approximateToArrivalTime(int arrivalTime, List<StopTime> stopTimes) {
		int temp = 0;
		int pos = 0;
		if (arrivalTime <= 0) {
			temp = stopTimes.get(0).getArrivalTime();
			for (int i = 0; i < stopTimes.size(); i++) {
				int arrivalTimeAux = stopTimes.get(i).getArrivalTime();
				if (arrivalTimeAux >= arrivalTime && arrivalTimeAux <= temp) {
					temp = arrivalTimeAux;
					pos = i;
				}
			}
		} else {
			for (int i = 0; i < stopTimes.size(); i++) {
				int arrivalTimeAux = stopTimes.get(i).getArrivalTime();
				if (arrivalTimeAux <= arrivalTime && arrivalTimeAux >= temp) {
					temp = arrivalTimeAux;
					pos = i;
				}
			}
		}
		return stopTimes.get(pos);
	}

	private StopTime approximateToArrivalTime(int arrivalTime, List<StopTime> stopTimes, Integer sequence) {
		int temp = 0;
		int pos = 0;
		for (int i = 0; i < stopTimes.size(); i++) {
			StopTime stopTime = stopTimes.get(i);
			if (stopTime.getStopSequence() == sequence) {
				int arrivalTimeAux = stopTimes.get(i).getArrivalTime();
				if (arrivalTimeAux <= arrivalTime & arrivalTimeAux >= temp) {
					temp = arrivalTimeAux;
					pos = i;
				}
			}
		}
		return stopTimes.get(pos);
	}

	@Override
	public List<StopTime> findStopTimeByRoute(Route route) {
		List<StopTime> stopTimes = new ArrayList<>();
		dao.getTripsForRoute(route).iterator().forEachRemaining(trip -> {
			stopTimes.addAll(dao.getStopTimesForTrip(trip));
		});
		stopTimesByRoute.put(route, stopTimes);
		return stopTimes;
	}

	@Override
	public List<StopTime> findStopTimeByRouteAndService(Route route, ServiceCalendar service) {
		/**
		 * Si tiene la informacion en la base de conocimiento retorna la
		 * informacion solicitada.
		 */
		if (stopTimesByRouteAndService.containsKey(route)) {
			Map<ServiceCalendar, List<StopTime>> stopTimeByService = stopTimesByRouteAndService.get(route);
			if (stopTimeByService.containsKey(service)) {
				return stopTimeByService.get(service);
			} else {
				List<StopTime> stopTimes = new ArrayList<>();
				findStopTimeByRoute(route).iterator().forEachRemaining(stopTime -> {
					if (stopTime.getTrip().getServiceId().equals(service.getServiceId())) {
						stopTimes.add(stopTime);
					}
				});
				stopTimeByService.put(service, stopTimes);
				stopTimesByRouteAndService.put(route, stopTimeByService);
				return stopTimes;
			}
		}
		/**
		 * Sino busca la informacion la almacena en la base de conocimiento y
		 * retorna la informacion solicitada.
		 **/
		else {
			List<StopTime> stopTimes = new ArrayList<>();
			findStopTimeByRoute(route).iterator().forEachRemaining(stopTime -> {
				if (stopTime.getTrip().getServiceId().equals(service.getServiceId())) {
					stopTimesByRouteServiceAndTrip
						.computeIfAbsent(route, k -> new HashMap<ServiceCalendar, Map<Trip, List<StopTime>>>())
						.computeIfAbsent(service, k -> new HashMap<Trip, List<StopTime>>())
						.computeIfAbsent(stopTime.getTrip(), k -> new ArrayList<StopTime>()).add(stopTime);
					stopTimes.add(stopTime);
				}
			});
			stopTimesByRouteAndService.computeIfAbsent(route, k -> new HashMap<ServiceCalendar, List<StopTime>>())
					.computeIfAbsent(service, k -> new ArrayList<StopTime>()).addAll(stopTimes);
			return stopTimes;
		}
	}

	@Override
	public List<StopTime> findStopTimeByRouteServiceAndTrip(Route route, ServiceCalendar service, Trip trip) {

		List<StopTime> stopTimes = new ArrayList<>();
		findStopTimeByRouteAndService(route, service).iterator().forEachRemaining(stopTime -> {
			if (stopTime.getTrip().equals(trip))
				stopTimes.add(stopTime);
		});

		stopTimesByRouteServiceAndTrip
				.computeIfAbsent(route, k -> new HashMap<ServiceCalendar, Map<Trip, List<StopTime>>>())
				.computeIfAbsent(service, k -> new HashMap<Trip, List<StopTime>>())
				.computeIfAbsent(trip, k -> new ArrayList<StopTime>()).addAll(stopTimes);

		return stopTimes;
	}
	
	public Set<Trip> findTripsByRouteAndService(Route route, ServiceCalendar service) {
		Map<Trip, List<StopTime>> map = stopTimesByRouteServiceAndTrip
				.computeIfAbsent(route, k -> new HashMap<ServiceCalendar, Map<Trip, List<StopTime>>>())
				.computeIfAbsent(service, k -> new HashMap<Trip, List<StopTime>>());
		
		return map.keySet();
	}
	
	public Map<Trip, List<StopTime>> findTripsAndStopsTimeByRouteAndService(Route route, ServiceCalendar service) {
		return stopTimesByRouteServiceAndTrip
				.computeIfAbsent(route, k -> new HashMap<ServiceCalendar, Map<Trip, List<StopTime>>>())
				.computeIfAbsent(service, k -> new HashMap<Trip, List<StopTime>>());
	}
}
