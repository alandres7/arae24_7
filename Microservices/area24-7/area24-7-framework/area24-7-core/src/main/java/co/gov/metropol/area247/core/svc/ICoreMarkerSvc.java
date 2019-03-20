package co.gov.metropol.area247.core.svc;

import com.google.maps.model.LatLng;

public interface ICoreMarkerSvc {
	
	LatLng markerConvertSpatialReference(double lat, double lng);
	
}
