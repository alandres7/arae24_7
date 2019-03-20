package co.gov.metropol.area247.centrocontrol.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import co.gov.metropol.area247.centrocontrol.security.filter.GenericFilter;
import co.gov.metropol.area247.centrocontrol.security.provider.SecurityAuthenticationProvider;
import co.gov.metropol.area247.centrocontrol.seguridad.dao.ISeguridadPermisoRolRepository;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadPermisoRolService;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class Area247SecurityApplication extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SecurityAuthenticationProvider authenticationProvider;

	@Autowired
    private Environment enviro;
	
	@Autowired
	private ISeguridadPermisoRolService iSeguridadPermisoRolService;
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	};
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		    .authorizeRequests()
		    .antMatchers("/css/**","/js/**","/assets/**","/fonts/**","/fragments/**","/assets/kml/**").permitAll()
		    .antMatchers("/validar-registro").permitAll()
		    .antMatchers("/inicio").permitAll()
		    .and().formLogin().loginPage("/login").defaultSuccessUrl("/inicio").loginProcessingUrl("/j_spring_security_check")
			.failureUrl("/login?error=t").usernameParameter("j_username").passwordParameter("j_password").permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login").permitAll();
		
		http.headers().frameOptions().disable();
			
	}
	

}