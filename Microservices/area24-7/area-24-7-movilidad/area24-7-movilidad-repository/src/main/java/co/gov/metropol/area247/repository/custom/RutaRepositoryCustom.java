package co.gov.metropol.area247.repository.custom;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.vividsolutions.jts.geom.Coordinate;

import co.gov.metropol.area247.repository.domain.LineaMetro;
import co.gov.metropol.area247.repository.domain.Ruta;

public interface RutaRepositoryCustom {
	
	/**
	 * Obtiene las coordenadas entre dos puntos dados, las coordenadas pueden
	 * ser de una linea, ruta o cicloruta(falta por implementar).<br>
	 * <br>
	 * <b>Ejemplo:</b> Vease una linea de metro que recorre de sur a norte la
	 * ciudad de medellin y tiene almacenadas todas las coordenadas del
	 * recorrido de sur a norte, pero usted solo necesita las coordenadas del
	 * punto sur al centro de la ciudad, en este metodo define el codigo de la
	 * ruta o linea, el punto de origen, destino y el tipo de ruta <b>(R: Ruta,
	 * M: Linea de metro, C: Cicloruta)</b>
	 * 
	 * @param codigo
	 *            - codigo de la ruta <i>(e.j 'LA' : hace referenia a la linea
	 *            A)</i>
	 * @param latitudO
	 *            - latitud del punto de origen <i>(e.j 6.247132)</i>
	 * @param longitudO
	 *            - longitud del punto de origen <i>(e.j -75.569828)</i>
	 * @param latitudD
	 *            - latitud del punto de destino <i>(e.j 6.290361)</i>
	 * @param longitudD
	 *            - longitud del punto de origen <i>(e.j -75.564716)</i>
	 * @param tipoRuta
	 *            - tipo de ruta <i>(R: Ruta, M: Linea de metro, C:
	 *            Cicloruta)</i>
	 * 
	 * @return un array de objetos de tipo {@link Coordinate} que contiene el
	 *         segmento de coordenadas entre los dos puntos dados.
	 *         <br>
	 *         <br>
	 *         <b>NOTA: la comunicacion con la bd esta implementada con el jdbc,
	 *         posiblemente esta forma se cambie</b>
	 */
	Coordinate[] obtenerRutaEntreCoordenadas(@Param("codigo") final String codigo,
			@Param("latitudO") final double latitudO, @Param("longitudO") final double longitudO,
			@Param("latitudD") final double latitudD, @Param("longitudD") final double longitudD, @Param("tipoRuta") final String tipoRuta);
	
	/**
	 * Obtiene informacion de rutas y lineas como el id, codigo, nombre y tipo
	 * ('L' para linea o 'R' para Ruta) segun el parametro indicado como
	 * argumento
	 * 
	 * @param parametro - filtro de busqueda
	 * @return una lista de objetos (pueden ser {@link Ruta} o {@link LineaMetro})
	 */
	List<Object> findInfoLineasAndRutasByCodigoOrDescripcion(String parametro);
}
