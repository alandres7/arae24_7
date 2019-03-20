package co.gov.metropol.area247.core.svc.impl;


import java.util.ArrayList;
import java.util.List;

import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.LatLng;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.core.gateway.MarkersProvider;
import co.gov.metropol.area247.core.gateway.conversor.dto.GeometriesContainer;
import co.gov.metropol.area247.core.gateway.conversor.dto.Geometry;
import co.gov.metropol.area247.core.gateway.conversor.dto.RequestGeometriesContainer;
import co.gov.metropol.area247.core.svc.ICoreMarkerSvc;
import co.gov.metropol.area247.core.util.GeometryUtil;

@Service("markerConverter")
public class ICoreMarkerConverterSvcImpl implements ICoreMarkerSvc {
	
	@Autowired
	MarkersProvider providerMarkers;
	
	private static final int MAGNA_SPATIAL_REFERENCE = 3116;
	private static final int BOGOTA_1975_SPATIAL_REFERENCE = 4218;
	private static final String MAGNA_CRS = "EPSG:3116";
	private static final String BOGOTA_1975_CRS = "EPSG:4218";
	private static final String WS_URL_CRS_CONVERTER = "http://tasks.arcgisonline.com/ArcGIS/rest/services/Geometry/GeometryServer/project";
	private static final String GEOMETRY_TYPE = "esriGeometryPoint";
	
	/**
	 * Esta operación está pendiente de refinamiento falta más info para 
	 * construir un objeto con otro sistema de referencia espacial
	 * una de mis hipótesis considera que se necesita una data "WKT"
	 * o una especie de datasheet con autorizaciones
	 * 
	 * @param lat
	 * @param lng
	 * @return
	 * @throws NoSuchAuthorityCodeException
	 * @throws FactoryException
	 * @throws MismatchedDimensionException
	 * @throws TransformException
	 */
	protected LatLng markerConvertSpatialReferenceV2(double lat, double lng) throws NoSuchAuthorityCodeException, FactoryException, MismatchedDimensionException, TransformException {
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
		Coordinate coordinate = new Coordinate(lng, lat);
		Point point = geometryFactory.createPoint(coordinate);
		point.setSRID(MAGNA_SPATIAL_REFERENCE);
		CoordinateReferenceSystem sourceCRS = CRS.decode(MAGNA_CRS, Boolean.TRUE);
		CoordinateReferenceSystem targetCRS = CRS.decode(BOGOTA_1975_CRS, Boolean.TRUE);
		MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, Boolean.TRUE);
		Point pointTransform = (Point) JTS.transform(point, transform);
		pointTransform.setSRID(BOGOTA_1975_SPATIAL_REFERENCE);
		return GeometryUtil.conversorCoordenadasMaps(pointTransform); 
	}
	
	@Override
	public LatLng markerConvertSpatialReference(double lat, double lng)  {
		List<Geometry> requestGeometries = new ArrayList<>();
		LatLng latLng = null;
		Geometry geometry = new Geometry(lng, lat);
		requestGeometries.add(geometry);
		GeometriesContainer geometriesContainer = new GeometriesContainer(GEOMETRY_TYPE, requestGeometries);
		RequestGeometriesContainer request = new RequestGeometriesContainer(MAGNA_SPATIAL_REFERENCE, BOGOTA_1975_SPATIAL_REFERENCE, geometriesContainer);
		List<Geometry> responseGeometries;
		try {
			responseGeometries = providerMarkers.getGeometries(WS_URL_CRS_CONVERTER, request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseGeometries =  new ArrayList<>();
		}
		if(!responseGeometries.isEmpty()) {
			geometry = responseGeometries.get(0);
			latLng = new LatLng(geometry.getY(), geometry.getX());
		}
		return latLng;
	}
	
	

}
