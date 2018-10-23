package co.b2bginebra.presentacion;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import co.b2bginebra.logica.GestionCorreosLogica;
import co.b2bginebra.utils.Mensajes;
import org.primefaces.context.RequestContext;

import co.b2bginebra.logica.UsuarioLogica;
import co.b2bginebra.modelo.Usuario;
import co.b2bginebra.seguridad.JsfSecurityTools;
import net.bootsfaces.component.inputText.InputText;

import java.util.*;
import java.util.stream.Stream;

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

	@EJB
	private GestionCorreosLogica gestionCorreosLogica;
	
    @PostConstruct
    public void postConstruct() 
    {   
    	
    	    usuLogueado = (Usuario) JsfSecurityTools.getfromSession("usuario");
    	    
    	    txtNombre = new InputText();
    	    txtDireccion = new InputText();
    	    txtCorreo = new InputText();
    	    txtTelefono = new InputText();
    	   
    	   
    	    txtNombre.setValue(usuLogueado.getNombre());
    	    txtDireccion.setValue(usuLogueado.getDireccion());
    	    txtCorreo.setValue(usuLogueado.getCorreo());
    	    txtTelefono.setValue(usuLogueado.getTelefono());

    }
	
	
	public void cambiarInformacionPersonal()
	{
		try
		{
			validarYNotificarCambios();
		} 
		catch (Exception e) 
		{
			mostrarMensaje(e.getMessage());
		}
		
	}

	public void validarYNotificarCambios() throws Exception{

		String cambios = "";
		if(!usuLogueado.getNombre().equals(txtNombre.getValue().toString()))
		{
			cambios+= String.format("Nombre: %s --> %s %s",usuLogueado.getNombre(),txtNombre.getValue().toString(),System.lineSeparator());
			usuLogueado.setNombre(txtNombre.getValue().toString());
		}
		if(!usuLogueado.getCorreo().equals(txtCorreo.getValue().toString()))
		{
			cambios+= String.format("Correo: %s --> %s %s",usuLogueado.getCorreo(),txtCorreo.getValue().toString(),System.lineSeparator());
			usuLogueado.setCorreo(txtCorreo.getValue().toString());
		}
		if(!usuLogueado.getDireccion().equals(txtDireccion.getValue().toString()))
		{
			cambios+= String.format("Direccion: %s --> %s %s",usuLogueado.getDireccion(),txtDireccion.getValue().toString(),System.lineSeparator());
			usuLogueado.setDireccion(txtDireccion.getValue().toString());
		}
		if(!usuLogueado.getTelefono().equals(txtTelefono.getValue().toString()))
		{
			cambios+= String.format("Telefono: %s --> %s %s",usuLogueado.getTelefono(),txtTelefono.getValue().toString(),System.lineSeparator());
			usuLogueado.setTelefono(txtTelefono.getValue().toString());
		}
		if(!cambios.isEmpty()){
			String resumen = String.format("El usuario idenficado con CC: %s ha actualizado sus datos personales: %s", usuLogueado.getIdentificacion(), System.lineSeparator()) + cambios;
			usuarioLogica.modificarUsuario(usuLogueado);
			gestionCorreosLogica.enviarCorreoCambioInformacionPersonal(resumen);
			mostrarMensaje(Mensajes.SUCCESS_INFORMATION_CHANGED);
		}
		else{
			mostrarMensaje(Mensajes.INFO_INFORMATION_NOT_CHANGED);
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

