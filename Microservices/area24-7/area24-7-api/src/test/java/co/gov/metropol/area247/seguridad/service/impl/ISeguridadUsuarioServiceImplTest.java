//package co.gov.metropol.area247.seguridad.service.impl;
//
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import co.gov.metropol.area247.seguridad.dao.ISeguridadUsuarioRepository;
//import co.gov.metropol.area247.seguridad.model.FuenteRegistro;
//import co.gov.metropol.area247.seguridad.model.Rol;
//import co.gov.metropol.area247.seguridad.model.Usuario;
//import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;
//
//@RunWith(SpringRunner.class)
//public class ISeguridadUsuarioServiceImplTest {
//	
////	@TestConfiguration
////	static class ISeguridadUsuarioServiceImplTestContextConfigurarion{
////		@Bean
////		public ISeguridadUsuarioService ISeguridadUsuarioService()
////		{
////			return new ISeguridadUsuarioServiceImpl();
////		}
////	}
////
////	@Autowired
////	private ISeguridadUsuarioService usuarioService;
////	
////	@MockBean
////	private ISeguridadUsuarioRepository usuarioDao;
////	
////	@Before
////	public void setup(){
//		
////		Rol rol = new Rol();
////		rol.setId(1L);
////		rol.setNombre("USER");
////
////		FuenteRegistro fuenteRegistro = new FuenteRegistro();
////		fuenteRegistro.setNombre("AP");
////		fuenteRegistro.setDescripcion("Aplicacion Movil");
////			
////		usuario = new Usuario();	
////		usuario.setUsername("usuariotest@test.com");
////		usuario.setContrasena("password");
////		usuario.setFuenteRegistro(fuenteRegistroService.fuenteRegistroObtenerPorId(fuenteRegistroId));
////		
////		usuario.setRoles(usuario.getFuenteRegistro().getRol());
////		
////		
////		Usuario usuario = new Usuario();
////		usuario.setUsername(username);
////	}
//		
//}
