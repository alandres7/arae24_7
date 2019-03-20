package co.gov.metropol.area247.seguridad.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import co.gov.metropol.area247.security.service.TokenAuthenticacionService;

public class JwtAuthenticationFilter extends GenericFilterBean {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		Authentication authentication = TokenAuthenticacionService.obtenerAutenticacion((HttpServletRequest) request);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		handleRequest(request, response);
		filterChain.doFilter(request, response);
		
	}

	public void handleRequest(ServletRequest request, ServletResponse response) throws IOException {

		//PrintWriter out = response.getWriter();

		//response.setContentType("text/plain");

		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {

			String paramName = parameterNames.nextElement();

			System.out.println("request paramName " + paramName); 

			String[] paramValues = request.getParameterValues(paramName);

			for (int i = 0; i < paramValues.length; i++) {

				String paramValue = paramValues[i];

				System.out.println("parametros iterador" + paramValue);

			}

		}

	}

}
