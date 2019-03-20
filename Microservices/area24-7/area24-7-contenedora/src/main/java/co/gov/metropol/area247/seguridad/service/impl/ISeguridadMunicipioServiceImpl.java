package co.gov.metropol.area247.seguridad.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

import co.gov.metropol.area247.core.domain.marker.dto.Town;
import co.gov.metropol.area247.core.domain.marker.dto.TownPolygon;
import co.gov.metropol.area247.core.util.GeometryUtil;
import co.gov.metropol.area247.seguridad.dao.ISeguridadMunicipioRepository;
import co.gov.metropol.area247.seguridad.dao.ISeguridadMunicipioRepositoryCustom;
import co.gov.metropol.area247.seguridad.model.Municipio;
import co.gov.metropol.area247.seguridad.model.dto.MunicipioDto;
import co.gov.metropol.area247.seguridad.service.ISeguridadMunicipioService;

@Service
public class ISeguridadMunicipioServiceImpl implements ISeguridadMunicipioService {

	@Autowired
	ISeguridadMunicipioRepository municipioDao;
	
	@Autowired
	@Qualifier("municipioDao")
	private ISeguridadMunicipioRepositoryCustom municipioDaoHbnte;
	
	@Override
	public Municipio municipioObtenerPorId(Long municipioId) {
		return municipioDao.findOne(municipioId);
	}

	@Override
	public Municipio municipioObtenerPorNombre(String municipioNombre) {
		return municipioDao.findByNombre(municipioNombre);
	}

	@Override
	public List<Municipio> municipioListarTodos() {
		return (List<Municipio>) municipioDao.findAll();
	}
	
	@Override
	public Municipio getByNombre(String nomMunicipio, String nomDepartamento) {
		MunicipioDto municipio = municipioDao.municipioByNombre(nomMunicipio, nomDepartamento);
		if(municipio != null) {
			return municipioObtenerPorId(municipio.getId());
		}
		return null;
	}
	
	@Override
	public List<Municipio> getByValleAburra(){
		return municipioDao.findByValleAburra(Boolean.TRUE);
	}
	
	@Override
	public boolean guardarMunicipio(Municipio municipio) {
		try {
			municipioDao.save(municipio);
			return true;
		}catch(Exception e) {
			
		}
		return false;
		
	}
	
	@Override
	public TownPolygon getPolygonMunicipio(Long id) {
		Municipio municipioAux = municipioDao.findOne(id);
		return new TownPolygon(municipioAux.getNombre(), 
				municipioAux.getPolygon());
	}
	
	@Override
	public List<LatLng> getPolygonAMVA(){
		List<Municipio> municipios = municipioDao.findByValleAburra(Boolean.TRUE);
		List<LatLng> coordenadasAMVA = new ArrayList<>(); 
		Polygon[] polygonsAMVA = municipios.stream().map(municipio->municipio.getPolygon()).toArray(Polygon[]::new);
		MultiPolygon polygonAMVA = new MultiPolygon(polygonsAMVA, new GeometryFactory());
		municipios.forEach(municipio->coordenadasAMVA.addAll(GeometryUtil.conversorCoordenadasMaps(municipio.getPolygon())));
		return new ArrayList<>(new HashSet<>(coordenadasAMVA));
	}
	
	@Override
	public List<Town> getTowns(){
		List<Municipio> municipios = municipioDao.findByValleAburra(Boolean.TRUE);
		//updateZeroPoint(municipios);
		List<Town> towns = new ArrayList<>();
		municipios.forEach(municipio->{
			towns.add(new Town(
					municipio.getId(),
					municipio.getNombre(),
					PolylineEncoding.encode(GeometryUtil.conversorCoordenadasMaps(municipio.getPolygon())),
					GeometryUtil.conversorCoordenadasMaps(municipio.getZeroPoint())
					));
		});
		return towns;
	}
	
	@Override
	public String municipioXUbicacion(double lat, double lng){
		List<Municipio> municipiosIntercepto =  municipioDaoHbnte.coordenadaInterceptoMunicipio(lat, lng);
		if(municipiosIntercepto.isEmpty()) {
			return "Fuera del AMVA";
		}else {
			return municipiosIntercepto.get(0).getNombre();
		}
	}
	
	private boolean updateZeroPoint(List<Municipio> municipios) {
		try {
			municipios.forEach(municipio -> {
				municipio.setZeroPoint(municipio.getPolygon().getCentroid());
				municipioDao.save(municipio);
			});
			municipioDao.save(municipios);
			return Boolean.TRUE;
		} catch (Exception e) {

		}
		return Boolean.FALSE;
	}

}
