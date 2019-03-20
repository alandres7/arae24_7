package co.gov.metropol.area247.gateway;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.gov.metropol.area247.gateway.impl.EnciclaServiceGatewayImpl;

public class EnciclaServiceGatewayImplTest {
	
	@Mock
	private IEnciclaServiceGateway enciclaServiceGateway;
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.enciclaServiceGateway = new EnciclaServiceGatewayImpl();
    }
	
	@Test
	public void getEnciclaEstatus(){
//		EnciclaWSDTO enclicalResponse = enciclaServiceGateway.consultarEstatusEncicla();
//		
//		assertTrue(!Utils.isNull(enclicalResponse));
	}

}
