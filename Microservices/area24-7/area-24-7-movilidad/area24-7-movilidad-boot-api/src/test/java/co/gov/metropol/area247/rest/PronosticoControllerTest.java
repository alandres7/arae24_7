package co.gov.metropol.area247.rest;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import co.gov.metropol.area247.Area247BootApiApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Area247BootApiApplication.class)
@WebAppConfiguration
public class PronosticoControllerTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hcm -> hcm instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("El convertidor de mensajes JSON no puede ser nulo", this.mappingJackson2HttpMessageConverter);

	}
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void informacionDeRutaTest() throws Exception {
		
		Calendar hoy = Calendar.getInstance();
		String fechaOrigen = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(hoy.getTime());
		hoy.add(Calendar.HOUR_OF_DAY, 2);
		String fechaDestino = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(hoy.getTime());
		
		double latitudO = 6.2325414131870325;
		double longitudO = -75.59228897094727;
		
		double latitudD = 6.248411322225335;
		double longitudD = -75.57031631469727;
		
		String uri = "/pronostico/{fecha}/{fechaOrigen}/{longitudOrigen}/{latitudOrigen}/{fechaDestino}/{longitudDestino}/{latitudDestino}";
		mockMvc.perform(get(uri, fechaOrigen, fechaOrigen, latitudO, longitudO, fechaDestino, latitudD, longitudD))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType));
		
	}

}
