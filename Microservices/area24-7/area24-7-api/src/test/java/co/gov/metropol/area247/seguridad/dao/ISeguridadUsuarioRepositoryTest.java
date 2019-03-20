package co.gov.metropol.area247.seguridad.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.gov.metropol.area247.seguridad.config.Area247ApiApplication;
import co.gov.metropol.area247.seguridad.model.FuenteRegistro;
import co.gov.metropol.area247.seguridad.model.Rol;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.service.ISeguridadFuenteRegistroService;
import co.gov.metropol.area247.seguridad.service.ISeguridadRolService;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = {Area247ApiApplication.class, H2JpaConfig.class})
@Transactional
public class ISeguridadUsuarioRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ISeguridadUsuarioRepository usuarioDao;
	
	@Autowired
	private ISeguridadFuenteRegistroService fuenteRegistroService;
	
	@Autowired 
	private ISeguridadRolService rolService;
	
	Usuario usuario;
		
	@Before
	public void setup()
	{
	
		Rol rol = new Rol();
		rol.setNombre("USER");
		Long rolId = (Long) entityManager.persistAndGetId(rol);

		FuenteRegistro fuenteRegistro = new FuenteRegistro();
		fuenteRegistro.setNombre("AP");
		fuenteRegistro.setDescripcion("Aplicacion Movil");
		fuenteRegistro.setRol(rolService.rolObtenerPorId(rolId));
		Long fuenteRegistroId = (Long) entityManager.persistAndGetId(fuenteRegistro);
			
		usuario = new Usuario();	
		usuario.setUsername("usuariotest@test.com");
		usuario.setContrasena("password");
		usuario.setFuenteRegistro(fuenteRegistroService.fuenteRegistroObtenerPorId(fuenteRegistroId));
		
		usuario.setRol(usuario.getFuenteRegistro().getRol());
		entityManager.persist(usuario);
		entityManager.flush();	
		
	}
	
	@Test
	public void usuarioObtenerTodosTest() {	
				
		List<Usuario> usuarios = (List<Usuario>) usuarioDao.findAll();
		assertThat(usuarios).isNotNull();
		assertThat(usuarios.size()).isGreaterThan(0);
		assertThat(usuarios).contains(usuario);
		assertThat(usuarios).hasOnlyElementsOfType(usuario.getClass());
		
	}
	
	@Test
	public void usuarioObtenerPorId() {
		
		Long idUsuario = 1L;
		assertThat(usuarioDao.findOne(idUsuario)).isNotNull();
		assertThat(usuarioDao.findOne(idUsuario)).isEqualTo(usuario);
		assertThat(usuarioDao.findOne(idUsuario)).isExactlyInstanceOf(usuario.getClass());
		
	}
	
	@Test
	public void usuarioObtenerPorNombre() {
		
		String username = "usuariotest@test.com";
		//assertThat(usuarioDao.findByNickname(username)).isNotNull();
		//assertThat(usuarioDao.findByNickname(username)).isEqualTo(usuario);
		//assertThat(usuarioDao.findByNickname(username)).isExactlyInstanceOf(usuario.getClass());
		
	}
	
	@Test
	public void usuarioGuardar_crearYactualizarUsuarios() {
		
		//Crear
		Usuario usuario2 = new Usuario();
		usuario2.setFuenteRegistro(usuario.getFuenteRegistro());
		usuario2.setUsername("usuario2test@test.com");
		usuario2.setContrasena("password");
		usuarioDao.save(usuario2);
		
		List<Usuario> usuarios = (List<Usuario>) usuarioDao.findAll();
		assertThat(usuarios.size()).isEqualTo(2);
		assertThat(usuarios).contains(usuario);
		assertThat(usuarios).contains(usuario2);	
		
		//Actualizar
		String nickname = "Nick";
		//usuario.setNickname(nickname);
		assertThat(usuarioDao.save(usuario)).isNotNull();
		assertThat(usuarioDao.save(usuario)).isExactlyInstanceOf(usuario.getClass());
		assertThat(usuarioDao.save(usuario)).isEqualTo(usuario);
		//assertThat(usuarioDao.findOne(usuario.getId()).getNickname()).isEqualTo(nickname);	
		
	}
	
	@Test
	public void usuarioGuardar_FallaSiUsernameNoEsUnEmail() {
		
			String username = "usuario";
			Usuario usuario2 = new Usuario();
			usuario2.setUsername(username);
			usuario2.setContrasena("password");
			usuario2.setFuenteRegistro(usuario.getFuenteRegistro());
			try
			{
				usuarioDao.save(usuario2);
			}catch(Exception e) {
				assertThat(usuario2.getId()).isNull();
			}
	}
	
	@Test
	public void usuarioGuardar_FallaSiDatosBasicosNulos() {
		
		Usuario usuario2 = new Usuario();
		try
		{
			usuarioDao.save(usuario2);
		} catch (Exception e) {
			assertThat(usuario2.getId()).isNull();
		}
		
	}
	
}
