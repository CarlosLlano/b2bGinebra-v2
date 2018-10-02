package co.b2bginebra.seguridad;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;

@ManagedBean(name="logout")
@RequestScoped 
public class Logout {
 
    /**
     * Shiro logout for the current user
     */
    public void submit() throws IOException 
    {
    		
        if (SecurityUtils.getSubject().hasRole("root") || SecurityUtils.getSubject().hasRole("Users") ) 
        {
            final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            SecurityUtils.getSubject().logout();
            externalContext.invalidateSession();  // cleanup user related session state
            externalContext.redirect("inicio.xhtml?faces-redirect=true");
            
        }
        
    }
}
