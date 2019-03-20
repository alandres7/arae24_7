package co.gov.metropol.area247.centrocontrol.security.repository.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.Rol;
import co.gov.metropol.area247.centrocontrol.model.Usuario;
import co.gov.metropol.area247.centrocontrol.security.repository.SecurityRepository;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadRolService;
import co.gov.metropol.area247.centrocontrol.service.CentroControlExchange;

@Scope("prototype")
@Repository
//public class SecurityRepositoryImpl extends Dao implements SecurityRepository{
public class SecurityRepositoryImpl implements SecurityRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityRepositoryImpl.class);

    @Autowired
    private CentroControlExchange centroControlExchange;

    @Autowired
    private ISeguridadRolService servicioRoles;

    @Autowired
    private Environment enviro;

    @Override
    public Usuario obtenerUsuarioPorUsername(String username, String password) throws SQLException {
        try {
            boolean isConnect = validarLDAP(username.replace("@ada.co", ""), password);

            if (isConnect) {
                Usuario usuario = centroControlExchange.obtenerUsuarioByUsername(username);

                /*Rol rol = new Rol();
				rol.setId(2l);
				rol.setNombre("USER");
				rol.setDescripcion("Rol para usuarios de la aplicación");
				rol.setActivo(true);
				usuario.setRol(rol);*/
                return usuario;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

@Override
    public List<Rol> obtenerRoles() throws SQLException {
        return servicioRoles.rolListarTodos();
    }

    // username: area247@ada.co
    // password: AdA2017@
    /**
     * Método encargado de validar si un usuario existe en el directorio activo
     */
    public Boolean validarLDAP(String username, String password) {
//        try {
//            Hashtable<String, String> env = new Hashtable<String, String>();
//
//            env.put(Context.INITIAL_CONTEXT_FACTORY, enviro.getProperty("initial.context.factory"));
//            env.put(Context.PROVIDER_URL, enviro.getProperty("provider.url"));    // LDAP_URL
//            env.put(Context.SECURITY_AUTHENTICATION, enviro.getProperty("security.authentication"));
//            String base_DN = enviro.getProperty("base.dn");
//            base_DN = base_DN.replaceAll("user", username);
//            env.put(Context.SECURITY_PRINCIPAL, base_DN);
//            env.put(Context.SECURITY_CREDENTIALS, password);
//
//            DirContext ctx = new InitialDirContext(env);
//            ctx.close();
//            return true;
//        } catch (NamingException e) {
//            System.out.println("Error >>> " + e);
//            return false;
//        }
        return true;
    }

}
