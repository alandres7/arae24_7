package co.gov.metropol.area247.centrocontrol.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import co.gov.metropol.area247.centrocontrol.model.PermisoRol;
import co.gov.metropol.area247.centrocontrol.security.context.CentroControlContextHolder;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadPermisoRolService;

@Component
public class GenericFilter extends GenericFilterBean {

	private static Logger LOGGER = LoggerFactory.getLogger(GenericFilter.class);

	private final String publicPages = "/login";

	private final ISeguridadPermisoRolService iSeguridadPermisoRolService;

	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public GenericFilter(ISeguridadPermisoRolService iSeguridadPermisoRolService) {
		this.iSeguridadPermisoRolService = iSeguridadPermisoRolService;
	}

	@SuppressWarnings("unused")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		try {
			String url = null;
			if (request instanceof HttpServletRequest) {
				url = ((HttpServletRequest) request).getRequestURL().toString();
			}

			PermisoRol permisoRol = null;
			
			System.out.println(url);

			Long idObjeto = 0L;

			if (!url.contains(".")) {
				if (!obtenerNombreRecurso(url).contains(publicPages)) {
					if (CentroControlContextHolder.isAuthenticate()) {
						System.out.println("is authenticate");
						
						idObjeto = containNumbers(obtenerNombreRecurso(url));
						
						if(idObjeto != 0) {
							System.out.println(idObjeto);
							permisoRol = iSeguridadPermisoRolService.permisoRolObtenerPermisoPorRolPorObjeto(1l, idObjeto);
							System.out.println(permisoRol);
							
						}
						
						if (permisoRol != null) {
							System.out.println("tiene permisos");
							request.setAttribute("puede_adicionar", permisoRol.getPuedeAdicionar());
							request.setAttribute("puede_borrar", permisoRol.getPuedeBorrar());
							request.setAttribute("puede_consultar", permisoRol.getPuedeConsultar());
							request.setAttribute("puede_editar", permisoRol.getPuedeEditar());
							request.setAttribute("puede_imprimir", permisoRol.getPuedeImprimir());
						} else {
							System.out.println("no tiene permisos");
						}
					} else {
						System.out.println("not authenticate");
					}
				}
			}


			filterChain.doFilter(request, response);
		} catch (Exception e) {
			LOGGER.error("fail intercepting end-points", e);
		}

	}

	private String obtenerNombreRecurso(String url) {
		String[] values = url.split("/");
		int number = values.length;
		String nameResouce = values[--number];
		return nameResouce;
	}
	
	private long containNumbers(String nameResource) {
		try {
			System.out.println("mesagggggggggggggggge"+nameResource);
			return Long.parseLong(nameResource);
		}catch(Exception e) {
			return 0l;
		}
	}
	
	private long obtenerIdObjeto(String url) {
		String[] values = url.split("/");
		
		if(values[1].equals("adminFormularios")) {
			
		}		
		int number = values.length;
		String nameResouce = values[--number];
		//return nameResouce;		
		return 0L;

	}

}
