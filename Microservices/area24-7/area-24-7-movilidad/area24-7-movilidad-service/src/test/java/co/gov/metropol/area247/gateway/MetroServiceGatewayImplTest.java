package co.gov.metropol.area247.gateway;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import co.gov.metropol.area247.gateway.impl.MetroServiceGatewayImpl;

public class MetroServiceGatewayImplTest {

	@InjectMocks
	private IMetroServiceGateway metroServiceGateway = new MetroServiceGatewayImpl();
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        this.metroServiceGateway = new MetroServiceGatewayImpl();
    }
	
	@Test
	public void consultarDatosMetroTest(){
		// MetroWSDTO metroResponse = metroServiceGateway.consultarDatosMetro();
		// assertTrue(!Utils.isNull(metroResponse));
	}

}
