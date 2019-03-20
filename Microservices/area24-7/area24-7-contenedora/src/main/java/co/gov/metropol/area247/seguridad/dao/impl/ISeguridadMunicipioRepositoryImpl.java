package co.gov.metropol.area247.seguridad.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.seguridad.dao.ISeguridadMunicipioRepositoryCustom;
import co.gov.metropol.area247.seguridad.model.Municipio;

@Repository("municipioDao")
@Transactional
public class ISeguridadMunicipioRepositoryImpl implements ISeguridadMunicipioRepositoryCustom {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Municipio> coordenadaInterceptoMunicipio(double lat, double lng) {
		List<Municipio> municipios;
		try {
			Query query = sessionFactory.getCurrentSession()
					.createSQLQuery("SELECT P.ID, P.S_NOMBRE, P.ID_DEPARTAMENTO, P.CLB_POLYGON, P.N_VALLE_ABURRA, P.CLB_ZERO_POINT "
							+ "      FROM (SELECT VA.*, "
							+ "            SDO_GEOM.RELATE(VA.CLB_POLYGON,'determine',"
							+ "            MDSYS.SDO_GEOMETRY(2001, 8307, "
							+ "            MDSYS.SDO_POINT_TYPE("+lng+","+lat+", NULL), NULL, NULL), 0.005) RELATIONSHIP "
							+ "            FROM ( "
							+ "                 SELECT MU.* "
							+ "                 FROM D247SEG_MUNICIPIO MU "
							+ "                 WHERE MU.CLB_POLYGON IS NOT NULL)VA) P " 
							+ "       WHERE P.RELATIONSHIP <> 'DISJOINT'")
					.addEntity(Municipio.class);
			municipios = query.list();
		}catch(Exception e) {
			municipios = new ArrayList<>();
		}
		return municipios;
	}

}
