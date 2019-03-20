package co.gov.metropol.area247.geometry;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.linearref.LinearLocation;
import com.vividsolutions.jts.linearref.LocationIndexedLine;

import co.gov.metropol.area247.services.rest.metro.CoordenadaWSDTO;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

public final class GeometryUtil {

	private static final GeometryFactory FACTORIA = new GeometryFactory();

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
	public static Point obtenerPunto(final Coordenada coordenada) {
		if (!Utils.isNull(coordenada) && !Utils.isNull(coordenada.getLatitud())
				&& !Utils.isNull(coordenada.getLongitud())) {
			return obtenerPunto(coordenada.getLatitud(), coordenada.getLongitud());
		}
		return null;
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
	public static Point obtenerPunto(final CoordenadaWSDTO coordenada) {
		if (!Utils.isNull(coordenada) && !Utils.isNull(coordenada.getLatitud())
				&& !Utils.isNull(coordenada.getLongitud())) {
			return obtenerPunto(coordenada.getLatitud(), coordenada.getLongitud());
		}
		return null;
	}

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
		final Coordinate coordenada = new Coordinate(latitud, longitud);
		return FACTORIA.createPoint(coordenada);
	}
	
	/**
	 * Crea un punto geometrico a partir de una latitud y una longitud
	 * 
	 * @param punto
	 * @return un punto geometrico (Point)
	 */
	public static Point obtenerPunto(String[] punto) {
		final Coordinate coordenada = new Coordinate(Double.parseDouble(punto[0]), Double.parseDouble(punto[1]));
		return FACTORIA.createPoint(coordenada);
	}
	
	/**
	 * Crea un punto geometrico a partir de una latitud y una longitud
	 * 
	 * @param punto - punto
	 * @param invertir - para invertir latitud por longitud
	 * @return un punto geometrico (Point)
	 */
	public static Point obtenerPunto(String[] punto, boolean invertir) {
		if (invertir) {
			String[] puntoInvertido = {punto[1], punto[0]};
			return obtenerPunto(puntoInvertido);
		} else {
			return obtenerPunto(punto);
		}
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
	public static MultiPoint obtenerPuntos(final Coordenada[] coordenadas) {

		final Coordinate[] puntos = new Coordinate[coordenadas.length];

		for (int i = 0; i < coordenadas.length; i++) {
			Coordinate punto = new Coordinate(coordenadas[i].getLatitud(), coordenadas[i].getLongitud());
			puntos[i] = punto;
		}

		return FACTORIA.createMultiPoint(puntos);

	}

	/**
	 * Crea un objeto multipunto a partir de un array de coordenadas de wsdto(objeto
	 * web service)
	 * 
	 * @param coordenadas
	 *            - contiene los puntos geometricos
	 * 
	 * @return un objeto multipoint que contiene todos los puntos geometricos que
	 *         forman una linea(culquier tipo de linea)
	 */
	public static MultiPoint obtenerPuntos(final CoordenadaWSDTO[] coordenadas) {

		final Coordinate[] puntos = new Coordinate[coordenadas.length];

		for (int i = 0; i < coordenadas.length; i++) {
			Coordinate punto = new Coordinate(coordenadas[i].getLatitud(), coordenadas[i].getLongitud());
			puntos[i] = punto;
		}
		return FACTORIA.createMultiPoint(puntos);
	}

	/**
	 * obtener lineString
	 * 
	 * @param coordenadas
	 *            -coordenadas
	 * @return LineString
	 */
	public static LineString obtenerLineString(final CoordenadaWSDTO[] coordenadas) {

		final Coordinate[] puntos = new Coordinate[coordenadas.length];

		for (int i = 0; i < coordenadas.length; i++) {
			Coordinate punto = new Coordinate(coordenadas[i].getLatitud(), coordenadas[i].getLongitud());
			puntos[i] = punto;
		}
		
		return obtenerLineString(puntos);
	}
	
	/**
	 * obtener lineString a partir de un vector de sting con las coordenadas,
	 * se utiliza para obtener las coordenadas de GTPC
	 * 
	 * @param coordenadas
	 *            -coordenadas
	 * @param invertir
	 *            - cambia de posicion la latitud y longitd, en caso de que las
	 *            coordenadas esten definidas como Longitud Latitud se debe
	 *            marcar como <code>true</code>
	 * @return LineString
	 */
	public static LineString obtenerLineString(String[] coordenadas, boolean invertir) {
		try {
			List<Coordinate> puntos = new ArrayList<>();

			for (int i = 0; i < coordenadas.length; i+=3) {
				Coordinate punto;
				double x = Double.parseDouble(coordenadas[i]);
				double y = Double.parseDouble(coordenadas[i+1]);
				if (invertir) {
					punto = new Coordinate(y, x);
				} else {
					punto = new Coordinate(x, y);
				}
				puntos.add(punto);
			}
			
			Coordinate[] coordenadasArray = new Coordinate[puntos.size()];
			coordenadasArray = puntos.toArray(coordenadasArray);
			return obtenerLineString(coordenadasArray);
		} catch (Exception e) {
			throw new Area247Exception("Error al obtener las coordenadas", e.getCause());
		}
		
	}
	
	/**
	 * obtener lineString
	 * 
	 * @param coordenadas
	 *            -coordenadas
	 * @return LineString
	 */
	public static LineString obtenerLineString(final List<Coordenada> coordenadas) {

		final Coordinate[] puntos = new Coordinate[coordenadas.size()];

		for (int i = 0; i < coordenadas.size(); i++) {
			Coordinate punto = new Coordinate(coordenadas.get(i).getLatitud(), coordenadas.get(i).getLongitud());
			puntos[i] = punto;
		}
		
		return obtenerLineString(puntos);
	}
	
	public static LineString obtenerLineString(final Coordinate[] coordenadas) {
		final int srid = 8307;
		GeometryFactory fac = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), srid);

		return fac.createLineString(coordenadas);
	}
	
	/**
	 * Tenemos una serie de coordenadas que forman un camino y damos un punto
	 * cualquiera, esta funcion nos trae el punto mas cercano o semejante de la
	 * serie de coordenadas al punto dado.
	 * 
	 * @param punto
	 *            - punto a comparar
	 * @param coordenadas
	 *            - contiene todos los puntos contra los que se comparara el
	 *            punto dado
	 * @return el punto mas cercano o semejante al punto dado
	 */
	public static Coordinate obtenerPuntoMasCercano(Coordinate punto, LineString coordenadas) {
		LocationIndexedLine linea = new LocationIndexedLine(coordenadas);
		LinearLocation loc = linea.project(punto);
		return loc.getCoordinate(coordenadas);
	}
	
	/**
	 * Este metodo obtiene un segmento de ruta dado un punto origen y un punto
	 * destino.
	 * 
	 * @param desde
	 *            - punto origen
	 * @param hasta
	 *            - punto destino
	 * @param coordenadas
	 *            - puntos de toda la ruta o linea
	 * @return LineString que contiene el segmento de coordenadas entre los
	 *         puntos dados
	 */
	public static LineString obtenerSegmento(Coordinate desde, Coordinate hasta, LineString coordenadas) {
		
		LocationIndexedLine linea = new LocationIndexedLine(coordenadas);
		int indexInicial = linea.indexOf(desde).getSegmentIndex();
		int indexFinal = linea.indexOf(hasta).getSegmentIndex();
		
		if (indexInicial == 0)
			indexFinal++;
		
		Coordinate[] arrayCoordenadas = coordenadas.getCoordinates();
		Coordinate[] tramo = new Coordinate[Math.abs(indexFinal - indexInicial)];
		
		int pos = 0;
		
		for (int i = indexInicial; i < indexFinal; i++) {
			tramo[pos] = arrayCoordenadas[i];
			pos++;
		}
		
		return obtenerLineString(tramo);
	}
	
	public static List<Coordinate> obtenerSegmento(Coordinate desde, Coordinate hasta, List<Coordinate> coordenadas) {
		LineString coordenadasLS = obtenerLineString(coordenadas.toArray(new Coordinate[]{}));
		return Arrays.asList(obtenerSegmento(desde, hasta, coordenadasLS).getCoordinates());
	}
	
	public static String[] toArrayString(LineString coordenadas) {
		
		if (!Utils.isNull(coordenadas)) {
			return toArrayString(coordenadas.getCoordinates());
		}
		
		return new String[0];
	}
	
	public static String[] toArrayString(Coordinate[] coordenadas) {
		
		if (null != coordenadas) {
			
			String[] resultado = new String[coordenadas.length * 2];
			int contador = 0;
			
			for (Coordinate coordinate : coordenadas) {
				resultado[contador++] = String.valueOf(coordinate.x);
				resultado[contador++] = String.valueOf(coordinate.y);
			}
			
			return resultado;
		}
		
		return new String[0];
	}

	/**
	 * Calcula la distancia entre dos puntos coordenada
	 * Creado 18/07/2018 02:40:10 p.m
	 * @param punto1 - punto coordenada
	 * @param punto2 - punto coordenada
	 * @return la distancia entre los dos puntos
	 */
	public static double calcularDistanciaPuntos(Coordinate punto1, Coordinate punto2) {
		double cateto1 = punto1.x - punto2.x;
		double cateto2 = punto1.y - punto2.y;
		return Math.sqrt(cateto1 * cateto1 + cateto2 * cateto2);
	}

	public static List<Coordinate> decodificarPolilinea(String encoded) {
	
		List<Coordinate> poly = new ArrayList<Coordinate>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;
		
		while (index < len) {
			int b, shift = 0, result = 0;
			do {
			
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
				
			} while (b >= 0x20);
				
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
				
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
						
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;
			Coordinate p = new Coordinate((((double) lat / 1E5)),(((double) lng / 1E5)));
			poly.add(p);
			
		}
			return poly;
	}
}
