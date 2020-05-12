package co.gov.metropol.area247.covid19.providers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.springframework.stereotype.Component;

import com.socrata.api.Soda2Consumer;
import com.socrata.builders.SoqlQueryBuilder;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.ConditionalExpression;
import com.socrata.model.soql.SoqlQuery;

import co.gov.metropol.area247.covid19.model.C19Case;

@Component
public class MarkersProvider {

	private static final int VALUE_SR = 4326;
	private static final int LIMITE_INALCANZABLE = 100000;
	
	public List<C19Case> getPayloadCasosCovid(String schemaAuthority, 
												String resourceId) throws SodaError, 
																			InterruptedException, 
																			UnsupportedEncodingException{
		SoqlQuery query = new SoqlQueryBuilder()
                .setWhereClause(new ConditionalExpression("ciudad_de_ubicaci_n IN ('BARBOSA','BELLO','CALDAS','COPACABANA','ENVIGADO','GIRARDOTA','ITAGUI','LA ESTRELLA','MEDELLIN','SABANETA') AND departamento='ANTIOQUIA'"))
                .setLimit(Integer.valueOf(LIMITE_INALCANZABLE))
                .build();
    	Soda2Consumer consumer = Soda2Consumer.newConsumer(schemaAuthority);
    	return consumer.query(resourceId, query, new GenericType<List<C19Case>>() {});
	}
	
	public List<C19Case> getPayloadCasosCovid(String schemaAuthority, 
												String resourceId,
												String conditions) throws SodaError, 
																			InterruptedException, 
																			UnsupportedEncodingException{
		SoqlQuery query = new SoqlQueryBuilder()
                .setWhereClause(new ConditionalExpression(conditions))
                .setLimit(Integer.valueOf(LIMITE_INALCANZABLE))
                .build();
    	Soda2Consumer consumer = Soda2Consumer.newConsumer(schemaAuthority);
    	return consumer.query(resourceId, query, new GenericType<List<C19Case>>() {});
	}
			
			
	
}
