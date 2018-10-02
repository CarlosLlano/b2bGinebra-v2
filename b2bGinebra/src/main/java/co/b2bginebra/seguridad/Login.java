package co.b2bginebra.seguridad;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import co.b2bginebra.logica.UsuarioLogica;
import co.b2bginebra.modelo.Usuario;

@ManagedBean(name="login")
@ViewScoped
public class Login extends AuthorizingRealm
{

	private Usuario usuLogueado;

	
	private UsuarioLogica usuarioLogica;
	
	//permisos
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {

		if(usuLogueado != null)
		{
			
			if(usuLogueado.getIdentificacion().equals("admin"))
			{
				Set<String> roles = new HashSet<String>();
				roles.add("root");
				return new SimpleAuthorizationInfo(roles);
			}
			else
			{
				Set<String> roles = new HashSet<String>();
				roles.add("Users");
				return new SimpleAuthorizationInfo(roles);
			}
			
		}
		else
		{
			return null;
		}

	}

	//autenticacion
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException 
	{
		//validar si es null
		try
		{
			//se obtiene las credenciales
			UsernamePasswordToken token = (UsernamePasswordToken) arg0;		
			String userName = token.getUsername();
			String password = new String(token.getPassword());

			
			//se inyecta manualmente el ejb
			usuarioLogica = (UsuarioLogica) new InitialContext().lookup("java:module/UsuarioLogica");;
			
			//se validan las credenciales
			Usuario usuario = usuarioLogica.validarUsuario(userName, password);
					
			if(usuario != null)
			{
				SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(userName, password, userName); 
				
				//Para establecer los roles
				usuLogueado = usuario;		
				//Poner al usuario en la session
				JsfSecurityTools.putinSession("usuario",usuario);

				return ai;
			}
			else
			{
				return new SimpleAuthenticationInfo();
			}
		}
		catch(Exception e)
		{

			return new SimpleAuthenticationInfo();
		}
		
	}
	
	public void checkAuthentication() throws IOException 
	{
	   
	    if (JsfSecurityTools.getfromSession("usuario") != null) 
	    {
	    	    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("home.xhtml?faces-redirect=true");
	    }
	}
	
	
	
}
