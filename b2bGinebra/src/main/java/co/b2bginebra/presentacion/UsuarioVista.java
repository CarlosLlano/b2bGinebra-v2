package co.b2bginebra.presentacion;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import co.b2bginebra.utils.Mensajes;
import org.primefaces.context.RequestContext;

import co.b2bginebra.logica.UsuarioLogica;
import co.b2bginebra.modelo.Usuario;
import co.b2bginebra.seguridad.JsfSecurityTools;
import net.bootsfaces.component.inputText.InputText;

/**
 * representa la vista para la gestion de informacion de un usuario
 *
 */
@ManagedBean(name="usuarioVista")
@ViewScoped
public class UsuarioVista 
{
	
	private InputText txtNombre;
	private InputText txtDireccion;
	private InputText txtCorreo;
	private InputText txtTelefono;
	
	
	//Errores
	private String modalText;
	
	private Usuario usuLogueado;
	
	@EJB
	private UsuarioLogica usuarioLogica;
	
    @PostConstruct
    public void postConstruct() 
    {   
    	
    	    usuLogueado = (Usuario) JsfSecurityTools.getfromSession("usuario");
    	    
    	    txtNombre = new InputText();
    	    txtDireccion = new InputText();
    	    txtCorreo = new InputText();
    	    txtTelefono = new InputText();
    	   
    	   
    	    txtNombre.setValue(usuLogueado.getNombre().toString());
    	    txtDireccion.setValue(usuLogueado.getDireccion().toString());
    	    txtCorreo.setValue(usuLogueado.getCorreo().toString());
    	    txtTelefono.setValue(usuLogueado.getTelefono().toString());

    }
	
	
	public void cambiarInformacionPersonal()
	{
		usuLogueado.setNombre(txtNombre.getValue().toString());	
		usuLogueado.setDireccion(txtDireccion.getValue().toString());
		usuLogueado.setCorreo(txtCorreo.getValue().toString());
		usuLogueado.setTelefono(txtTelefono.getValue().toString());
		
		
		try
		{
			usuarioLogica.modificarUsuario(usuLogueado);
			mostrarMensaje(Mensajes.SUCCESS_INFORMATION_CHANGED);
		} 
		catch (Exception e) 
		{
			mostrarMensaje(e.getMessage());
		}
		
	}


	public InputText getTxtNombre() {
		return txtNombre;
	}


	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}


	public InputText getTxtDireccion() {
		return txtDireccion;
	}


	public void setTxtDireccion(InputText txtDireccion) {
		this.txtDireccion = txtDireccion;
	}


	public InputText getTxtCorreo() {
		return txtCorreo;
	}


	public void setTxtCorreo(InputText txtCorreo) {
		this.txtCorreo = txtCorreo;
	}


	public InputText getTxtTelefono() {
		return txtTelefono;
	}


	public void setTxtTelefono(InputText txtTelefono) {
		this.txtTelefono = txtTelefono;
	}


	


	public Usuario getUsuLogueado() {
		return usuLogueado;
	}


	public void setUsuLogueado(Usuario usuLogueado) {
		this.usuLogueado = usuLogueado;
	}
	
	public void mostrarMensaje(String mensaje)
	{
		RequestContext context = RequestContext.getCurrentInstance();
		setModalText(mensaje);
		String codigo = "$('.modalPseudoClass').modal()";
		context.execute(codigo);
	}
	
	public String getModalText() {
		return modalText;
	}
	
	public void setModalText(String modalText) {
		this.modalText = modalText;
	}
	
	

}

