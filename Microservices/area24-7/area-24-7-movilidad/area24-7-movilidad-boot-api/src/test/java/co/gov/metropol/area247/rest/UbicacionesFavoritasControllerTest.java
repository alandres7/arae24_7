package co.gov.metropol.area247.rest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import co.gov.metropol.area247.Area247BootApiApplication;
import co.gov.metropol.area247.model.ConDireccionFavoritaDTO;
import co.gov.metropol.area247.service.IConDireccionFavoritaService;
import co.gov.metropol.area247.util.Utils;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Area247BootApiApplication.class)
@WebAppConfiguration
public class UbicacionesFavoritasControllerTest {
	
	private static final String NOMBRE_UBICACION = "Mi Abu";
	private static final Long ID_USUARIO = 1L;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
	private IConDireccionFavoritaService service;

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

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
	
	
	
	@Test
	public void createDireccionFavoritaTest() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("id", "1");
		params.add("nombre", NOMBRE_UBICACION);
		params.add("descripcion", "Calle 1 # 2 - 3");
		params.add("idUsuario", ID_USUARIO.toString());
		params.add("latitud", "6.249435170811115");
		params.add("longitud", "-75.56619644165039");
		params.add("idCategoria", "1");

		mockMvc.perform(post("/ubicaciones/add/")
				.contentType(contentType)
				.params(params))
				.andExpect(status().isCreated());
		
	}
	
	@Test
	public void informacionDeUbicacionFavoritaTest() throws Exception {
		String uri = String.format("/ubicaciones/%s/%s", NOMBRE_UBICACION, ID_USUARIO);
		mockMvc.perform(get(uri)).andExpect(status().isOk());
	}
	
	
	@Test
	public void updateUbicacionFavoritaTest() throws Exception {
		
		ConDireccionFavoritaDTO ubicacion = service.findByNombreAndIdUsuario(NOMBRE_UBICACION, ID_USUARIO);
		
		if (!Utils.isNull(ubicacion) && !Utils.isNull(ubicacion.getId())) {
			String uri = "/ubicaciones/update";
			mockMvc.perform(put(uri).param("id", ubicacion.getId().toString())
					.param("nombre", NOMBRE_UBICACION)
					.param("descripcion", "Carrera 1 # 2 - 3")
					.param("idUsuario", ID_USUARIO.toString())
					.param("latitud", "6.249435170811115")
					.param("longitud", "-75.56619644165039")
					.param("idCategoria", "1"))
					.andExpect(jsonPath("$.descripcion", is("Carrera 1 # 2 - 3")))
					.andExpect(status().isOk());
		}
	}
	
	
	@Test
	public void deleteUserTest() throws Exception {
		
		ConDireccionFavoritaDTO ubicacion = service.findByNombreAndIdUsuario(NOMBRE_UBICACION, ID_USUARIO);
		
		if (!Utils.isNull(ubicacion) && !Utils.isNull(ubicacion.getId())) {
			mockMvc.perform(delete("/ubicaciones/delete/{id}", ubicacion.getId())).andExpect(status().isOk());
		}
	}
	
	
}
