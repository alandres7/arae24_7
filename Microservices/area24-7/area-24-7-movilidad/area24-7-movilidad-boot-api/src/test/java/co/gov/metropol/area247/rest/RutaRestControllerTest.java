package co.gov.metropol.area247.rest;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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
public class RutaRestControllerTest {

	private static final Date HOY = Calendar.getInstance().getTime();
//	private static final double LATITUD = 6.249435170811115;
//	private static final double LONGITUD = -75.56619644165039;
	private static final double LATITUD = 6.253304099999999;
	private static final double LONGITUD = -75.58782480000002;

	// 6.15290203194, -75.6262557128

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
	public void obtenerRutasCercanasTest() throws Exception {
		// para lineas 0,6

		String modosRecorrido = "9,2,5";
		float radio = 1;
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		String hoy = formateador.format(HOY);

		String uri = "/rutas/viajes/{fecha}/{latitudOrigen}/{longitudOrigen}/{modosTransporte}/{radio}";
		mockMvc.perform(get(uri, hoy, 6.2206623, -75.5753760999997, modosRecorrido, radio)).andExpect(status().isOk()).andExpect(content().contentType(contentType));

	}
	
	@Test
	public void disponibilidadDeBicicletasEstacionEnciclaTest() throws Exception {
//		long id = 6700;
//		String uri = "/rutas/encicla/disponibilidad/{id-estacion}";
//		mockMvc.perform(get(uri, id)).andExpect(status().isOk()).andExpect(content().contentType(contentType));
		
	}
	
	@Test
	public void obtenerRutasOrLineasTest() throws Exception {
		String linea1 = "L1";
		String uri = "/rutas/lineas/{parametro}";
		mockMvc.perform(get(uri, linea1)).andExpect(status().isOk()).andExpect(content().contentType(contentType));
	}

}
