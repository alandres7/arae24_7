package co.gov.metropol.area247.security.service;

import static java.util.Collections.emptyList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import co.gov.metropol.area247.seguridad.model.enums.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticacionService {
	
	public static String agregarAutenticacion(String username) throws ParseException
	{

		String dateTokenString = Token.TIEMPO_EXPIRACION.getValor();
		SimpleDateFormat format = new SimpleDateFormat("ss");
		Date dateToken = format.parse(dateTokenString);
		long milisegundos = dateToken.getTime() / 1000;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis() + milisegundos);
		
		String jsonWebToken = Jwts.builder()
				.setSubject(username)
				.setExpiration(calendar.getTime())
				.signWith(SignatureAlgorithm.HS512, Token.SECRETO.getValor())
				.compact();
		
		return (Token.PREFIJO_TOKEN.getValor() + " " + jsonWebToken);
		
	}
	
	public static Authentication obtenerAutenticacion(HttpServletRequest request){
		
		String jsonWebToken = request.getHeader(Token.HEADER.getValor());
		
		if (jsonWebToken !=null)
		{
			String usuario = Jwts.parser()
					.setSigningKey(Token.SECRETO.getValor())
					.parseClaimsJws(jsonWebToken.replace(Token.PREFIJO_TOKEN.getValor(), " "))
					.getBody()
					.getSubject();
			
			return usuario !=null ? new UsernamePasswordAuthenticationToken(usuario, null, emptyList()) : null;
					
		}
		
		return null;
		
	}

}
