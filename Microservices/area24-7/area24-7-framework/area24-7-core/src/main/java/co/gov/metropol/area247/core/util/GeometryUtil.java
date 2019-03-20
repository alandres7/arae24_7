package co.gov.metropol.area247.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.maps.model.LatLng;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;

public final class GeometryUtil {

	static final GeometryFactory FACTORIA = new GeometryFactory();

	private GeometryUtil() {
		throw new IllegalStateException("Error en GeometryUtil");
	}

	/**
	 * Dado una coordenada nos da un punto(Point)
	 * 
	 * @param coordenada
	 *            - objeto que contiene el valor de la latitud y longitud
	 * 
	 * @return un objeto Point con la informacion del punto geometrico o null si el
	 *         objeto no contiene la información necesaria(latitud o longitud)
	 */
	// public static Point obtenerPunto(final Coordenada coordenada) {
	// if (!Utils.isNull(coordenada) && !Utils.isNull(coordenada.getLatitud())
	// && !Utils.isNull(coordenada.getLongitud())) {
	// return obtenerPunto(coordenada.getLatitud(), coordenada.getLongitud());
	// }
	// return null;
	// }

	/**
	 * Dado una coordenada nos da un punto(Point)
	 * 
	 * @param coordenada
	 *            - objeto que contiene el valor de la latitud y longitud
	 * 
	 * @return un objeto Point con la informacion del punto geometrico o null si el
	 *         objeto no contiene la información necesaria(latitud o longitud)
	 */
	// public static Point obtenerPunto(final CoordenadaWSDTO coordenada) {
	// if (!Utils.isNull(coordenada) && !Utils.isNull(coordenada.getLatitud())
	// && !Utils.isNull(coordenada.getLongitud())) {
	// return obtenerPunto(coordenada.getLatitud(), coordenada.getLongitud());
	// }
	// return null;
	// }

	/**
	 * Crea un punto geometrico a partir de una latitud y una longitud
	 * 
	 * @param latitud
	 *            - valor del eje x del punto
	 * @param longitud
	 *            - valor del eje y del punto
	 * 
	 * @return un punto geometrico (Point)
	 */
	public static Point obtenerPunto(final double latitud, final double longitud) {
		final Coordinate coordenada = new Coordinate(longitud, latitud);
		Point point = FACTORIA.createPoint(coordenada);
		return point;
	}

	/**
	 * Crea un objeto multipunto a partir de un array de coordenadas
	 * 
	 * @param coordenadas
	 *            - contiene los puntos geometricos
	 * 
	 * @return un objeto multipoint que contiene todos los puntos geometricos que
	 *         forman una linea(culquier tipo de linea)
	 */
	public static Polygon obtenerPuntosPolygon(final List<CoordenadaDto> coordenadasDto) {

		final Coordinate[] puntos = new Coordinate[coordenadasDto.size()];

		for (int i = 0; i < coordenadasDto.size(); i++) {
			Coordinate punto = new Coordinate(coordenadasDto.get(i).getLongitud(), coordenadasDto.get(i).getLatitud());
			puntos[i] = punto;
		}

		Polygon pPolygon = FACTORIA.createPolygon(puntos);
		return pPolygon;
	}

		/**
		 * Crea un objeto multipunto a partir de un array de coordenadas
		 * 
		 * @param coordenadas
		 *            - contiene los puntos geometricos
		 * 
		 * @return un objeto multipoint que contiene todos los puntos geometricos que
		 *         forman una linea(culquier tipo de linea)
		 */
		public static Polygon obtenerPolygon(final List<LatLng> coordenadasDto) {

			final Coordinate[] puntos = new Coordinate[coordenadasDto.size()];

			for (int i = 0; i < coordenadasDto.size(); i++) {
				Coordinate punto = new Coordinate(coordenadasDto.get(i).lng, coordenadasDto.get(i).lat);
				puntos[i] = punto;
			}

			Polygon pPolygon = FACTORIA.createPolygon(puntos);
			return pPolygon;

		}
		
		/**
		 * Crea un objeto multipunto a partir de un array de coordenadas
		 * 
		 * @param coordenadas
		 *            - contiene los puntos geometricos
		 * 
		 * @return un objeto multipoint que contiene todos los puntos geometricos que
		 *         forman una linea(culquier tipo de linea)
		 */
		public static Polygon obtenerPolygonCoordInvert(final List<LatLng> coordenadasDto) {

			final Coordinate[] puntos = new Coordinate[coordenadasDto.size()];

			for (int i = 0; i < coordenadasDto.size(); i++) {
				Coordinate punto;
				if(coordenadasDto.get(i).lng >0)
					punto = new Coordinate(coordenadasDto.get(i).lat, coordenadasDto.get(i).lng);
				else
					punto = new Coordinate(coordenadasDto.get(i).lng, coordenadasDto.get(i).lat);
				puntos[i] = punto;
			}

			Polygon pPolygon = FACTORIA.createPolygon(puntos);
			return pPolygon;

		}

	/**
	 * Crea un objeto multipunto a partir de un array de coordenadas
	 * 
	 * @param coordenadas
	 *            - contiene los puntos geometricos
	 * 
	 * @return un objeto multipoint que contiene todos los puntos geometricos que
	 *         forman una linea(culquier tipo de linea)
	 */
	// public static MultiPoint obtenerPuntos(final Coordenada[] coordenadas) {
	//
	// final Coordinate[] puntos = new Coordinate[coordenadas.length];
	//
	// for (int i = 0; i < coordenadas.length; i++) {
	// Coordinate punto = new Coordinate(coordenadas[i].getLatitud(),
	// coordenadas[i].getLongitud());
	// puntos[i] = punto;
	// }
	//
	// return FACTORIA.createMultiPoint(puntos);
	//
	// }
	//
	// /**
	// * Crea un objeto multipunto a partir de un array de coordenadas de
	// * wsdto(objeto web service)
	// *
	// * @param coordenadas
	// * - contiene los puntos geometricos
	// *
	// * @return un objeto multipoint que contiene todos los puntos geometricos
	// * que forman una linea(culquier tipo de linea)
	// */
	// public static MultiPoint obtenerPuntos(final CoordenadaWSDTO[]
	// coordenadas) {
	//
	// final Coordinate[] puntos = new Coordinate[coordenadas.length];
	//
	// for (int i = 0; i < coordenadas.length; i++) {
	// Coordinate punto = new Coordinate(coordenadas[i].getLatitud(),
	// coordenadas[i].getLongitud());
	// puntos[i] = punto;
	// }
	// MultiPoint multipoint = FACTORIA.createMultiPoint(puntos);
	// return multipoint;
	// }
	//
	// /**
	// * obtener lineString
	// * @param coordenadas -coordenadas
	// * @return LineString
	// */
	// public static LineString obtenerLineString(final CoordenadaWSDTO[]
	// coordenadas) {
	//
	// final Coordinate[] puntos = new Coordinate[coordenadas.length];
	//
	// for (int i = 0; i < coordenadas.length; i++) {
	// Coordinate punto = new Coordinate(coordenadas[i].getLatitud(),
	// coordenadas[i].getLongitud());
	// puntos[i] = punto;
	// }
	// int SRID = 8307;
	// GeometryFactory fac = new GeometryFactory(null, SRID);
	//
	// LineString lineString = fac.createLineString(puntos);
	// //lineString.setSRID(8307);
	// return lineString;
	// }

	public static List<CoordenadaDto> conversorCoordenadaDto(LineString lineString) {

		List<CoordenadaDto> coordenadaDto = new ArrayList<CoordenadaDto>();
		try {
			coordenadaDto = (ArrayList<CoordenadaDto>) Arrays.asList(lineString.getCoordinates()).stream()
					.filter(Objects::nonNull).map(c -> new CoordenadaDto(c.y, c.x))
					.collect(Collectors.toList());
		} catch (Exception e) {

		}

		return coordenadaDto;
	}

	public static List<CoordenadaDto> conversorCoordenadaDto(Polygon polygon) {

		List<CoordenadaDto> coordenadaDto = new ArrayList<CoordenadaDto>();
		try {
			coordenadaDto = (ArrayList<CoordenadaDto>) Arrays.asList(polygon.getCoordinates()).stream()
					.filter(Objects::nonNull).map(c -> new CoordenadaDto(c.y, c.x))
					.collect(Collectors.toList());
		} catch (Exception e) {

		}

		return coordenadaDto;
	}

	public static CoordenadaDto conversorCoordenadaDto(Point point) {
		CoordenadaDto coordenadaDto = null;
		try {
			Coordinate c = point.getCoordinate();
			coordenadaDto = new CoordenadaDto(c.y, c.x);
		} catch (Exception e) {

		}
		return coordenadaDto;
	}

	public static List<String> conversorCoordenada(LineString lineString) {

		ArrayList<String> arreglo = new ArrayList<String>();

		try {
			Arrays.asList(lineString.getCoordinates()).stream().filter(Objects::nonNull)
					.map(c -> new CoordenadaDto(c.y, c.x))
					.forEach(e -> {
						arreglo.add(""+e.getLatitud());
						arreglo.add(""+e.getLongitud());
					});
		} catch (Exception e) {

		}

		return arreglo;
	}
	
	public static List<LatLng> conversorCoordenadasMaps(LineString lineString) {

		ArrayList<LatLng> arreglo = new ArrayList<>();

		try {
			Arrays.asList(lineString.getCoordinates()).stream().filter(Objects::nonNull)
					.map(c -> new CoordenadaDto(c.y, c.x))
					.forEach(e -> {
						LatLng latLng = new LatLng(e.getLatitud(), e.getLongitud());
						arreglo.add(latLng);
					});
		} catch (Exception e) {

		}

		return arreglo;
	}
	
	

	public static List<String> conversorCoordenada(Polygon polygon) {

		ArrayList<String> arreglo = new ArrayList<String>();
		try {
			Arrays.asList(polygon.getCoordinates()).stream().filter(Objects::nonNull)
					.map(c -> new CoordenadaDto(c.y, c.x))
					.forEach(e -> {
						arreglo.add(""+e.getLatitud());
						arreglo.add(""+e.getLongitud());
					});
		} catch (Exception e) {

		}

		return arreglo;
	}
	
	public static List<LatLng> conversorCoordenadasMaps(Polygon polygon) {

		ArrayList<LatLng> arreglo = new ArrayList<>();

		try {
			Arrays.asList(polygon.getCoordinates()).stream().filter(Objects::nonNull)
					.map(c -> new CoordenadaDto(c.y, c.x))
					.forEach(e -> {
						LatLng latLng = new LatLng(e.getLatitud(), e.getLongitud());
						arreglo.add(latLng);
					});
		} catch (Exception e) {

		}

		return arreglo;
	}

	public static List<String> conversorCoordenada(Point point) {
		ArrayList<String> arreglo = new ArrayList<String>();
		try {
			Coordinate c = point.getCoordinate();
			arreglo.add(""+c.y);
			arreglo.add(""+c.x);
		} catch (Exception e) {

		}
		return arreglo;
	}
	
	public static LatLng conversorCoordenadasMaps(Point point) {
		LatLng marker = new LatLng(0, 0);

		try {
			Coordinate c = point.getCoordinate();
			marker = new LatLng(c.y, c.x);
		} catch (Exception e) {

		}

		return marker;
	}
	
	
	public static LineString conversorPolyLineS(Polygon poligono) {
		final LineString lineString = poligono.getExteriorRing();
		return lineString;
	}
	

}
