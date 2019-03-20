package co.gov.metropol.area247.seguridad.restcontroller;


//import static org.mockito.BDDMockito.*;

/*

@RunWith(SpringRunner.class)
@WebMvcTest(value = UsuarioRestController.class, secure = false)
@ContextConfiguration(classes = Area247SeguridadApplication.class)
//@Transactional
 */


public class UsuarioRestControllerTest {
	/*	
	UsuarioDto usuario;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ISeguridadUsuarioService usuarioService;
	
	@Test
	public void registroTest() throws Exception
	{
		
		this.usuario.setUsername("usuariotest@test.com");
		this.usuario.setContrasena("password");
		this.usuario.setFuenteRegistro("AP");
		
		given(usuarioService.usuarioCrear(usuario)).willReturn(true);
		
		mockMvc.perform(get("/api/registro").accept(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated()).andExpect(content().string("Usuario creado exitosamente"));
		
		verify(usuarioService).usuarioCrear(usuario);
	}
	
	
	/* ---------------------------------------------------*/

//	@Autowired 
//	ISeguridadUsuarioService usuarioService;
//	
//	@Before
//	public void setup()
//	{
//		
//	}
//	
//	@After
//	public void tearDown()
//	{
//		
//	}
//	
//	@Test
//	public void testUsuarioListarTodos()
//	{
//		List<Usuario> usuarios = usuarioService.usuarioListarTodos();
//		
//		//numero de usuarios inscritos
//		Assert.assertEquals(1, usuarios.size());
//		//Podría ser incluso vacío más no nulo
//		Assert.assertNotNull(usuarios);
//		
//	}
	
	
	
}
