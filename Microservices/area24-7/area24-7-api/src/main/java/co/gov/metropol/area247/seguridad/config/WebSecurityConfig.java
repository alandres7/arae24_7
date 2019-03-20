package co.gov.metropol.area247.seguridad.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String URI_PATH = "/";
	private static final String URI_LOGIN = "/api/login";
	private static final String URI_LOGIN_CCONTROL = "/api/login-ccontrol";
	private static final String URI_RESET_PASS = "/api/usuario-restablecer-pass";
	private static final String URI_EXIST_USER = "/api/existeUsuario";
	private static final String URI_PAISES = "/api/paises";
	private static final String URI_REGISTER = "/api/registro";
	private static final String URI_REGISTER_CONFIRM = "/api/confirmar-registro";
	private static final String URI_MENU_APP =  "/api/aplicacion/menu";	
	private static final String URI_WEB_APP_IDTIPOCAPA = "/api/aplicacion/obtenerPorIdOTipoCapa";
	private static final String URI_WEB_APP_TODAS = "/api/aplicacion/obtenerTodas";	
	private static final String URI_ICONO = "/api/icono/*";
	private static final String URI_MULTIMEDIA = "/api/multimedia/*";
	private static final String URI_API_DOCS = "/v2/api-docs";
	private static final String URI_UI_CONFIG = "/configuration/ui";
	private static final String URI_RSRC_SWAGGER = "/swagger-resources/**";
	private static final String URI_CONFIG_SECURITY = "/configuration/security";
	private static final String URI_UI_SWAGGER = "/swagger-ui.html";
	private static final String URI_RSRC = "/webjars/**";
	private static final String URI_MENSAJE = "/api/mensaje/nombreIdentificador/{nombreIdentificador}";
	
	@Autowired
	  UserDetailsService userDetailsService;
	
	 @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	 }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable().authorizeRequests()
		.antMatchers(URI_PATH).permitAll()
		.antMatchers(HttpMethod.POST, URI_LOGIN).permitAll()
		.antMatchers(HttpMethod.POST, URI_LOGIN_CCONTROL).permitAll()
		.antMatchers(HttpMethod.PUT, URI_RESET_PASS).permitAll()
		.antMatchers(HttpMethod.POST, URI_EXIST_USER).permitAll()
		.antMatchers(HttpMethod.GET, URI_PAISES).permitAll()
		.antMatchers(HttpMethod.POST, URI_REGISTER).permitAll()
		.antMatchers(HttpMethod.GET, URI_REGISTER_CONFIRM).permitAll()
		.antMatchers(HttpMethod.GET, URI_MENU_APP).permitAll()
		.antMatchers(HttpMethod.GET, URI_WEB_APP_IDTIPOCAPA).permitAll()
		.antMatchers(HttpMethod.GET, URI_WEB_APP_TODAS).permitAll()
		.antMatchers(HttpMethod.GET, URI_ICONO).permitAll()
		.antMatchers(HttpMethod.GET, URI_MULTIMEDIA).permitAll()
		.antMatchers(HttpMethod.GET, URI_MENSAJE).permitAll()
//		.antMatchers(HttpMethod.POST, "/api/**").permitAll()
		//.antMatchers(HttpMethod.POST, "/api/icono*").permitAll()
		.antMatchers(URI_API_DOCS, URI_UI_CONFIG, URI_RSRC_SWAGGER, URI_CONFIG_SECURITY, URI_UI_SWAGGER, URI_RSRC).permitAll()
		.anyRequest().authenticated()
		.and()
		.cors().configurationSource(corsConfigurationSource()).and()
		.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;        
	}
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
