package co.b2bginebra.seguridad;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import co.b2bginebra.modelo.Usuario;

@ManagedBean(name="jsfSecurityTools")
@RequestScoped
public class JsfSecurityTools 
{
	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static void putinSession(String name, Object o) 
	{

		SecurityUtils.getSubject().getSession().setAttribute(name,o);


	}

	public static Object getfromSession(String name) {

		return SecurityUtils.getSubject().getSession().getAttribute(name);

	}

	public Usuario getUsuario()
	{
		return (Usuario) SecurityUtils.getSubject().getSession().getAttribute("usuario");
	}





}
