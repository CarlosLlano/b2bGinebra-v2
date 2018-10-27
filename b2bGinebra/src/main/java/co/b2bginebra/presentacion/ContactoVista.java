package co.b2bginebra.presentacion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import co.b2bginebra.utils.Mensajes;
import org.primefaces.context.RequestContext;

import co.b2bginebra.logica.GestionCorreosLogica;


@ManagedBean(name = "contactoVista")
@ViewScoped
public class ContactoVista 
{
	@EJB
	private GestionCorreosLogica gestionCorreosLogica;

	private String nombres;
	private String apellidos;
	private String telefono;
	private String correo;
	private String mensaje;
	
	private String modalText;


	public void enviarCorreoAction()
	{
		if(nombres.trim().equals("") || apellidos.trim().equals("") || telefono.trim().equals("") || correo.trim().equals("") || mensaje.trim().equals(""))
		{	
			mostrarMensaje(Mensajes.ERROR_MISSING_FIELDS);
		}
		else
		{
			String mensajeCorreo = "Mensaje enviado por: " + nombres + " " + apellidos + "\n"
					+ "Contacto: " + telefono + "\n" +
					"Correo: " + correo + "\n" + 
					"Mensaje:" + "\n" + "\n" + 
					mensaje;
			try {
				gestionCorreosLogica.enviarCorreoContacto(mensajeCorreo);
				limpiar();
				mostrarMensaje(Mensajes.SUCCESS_MESSAGE);
			} catch (Exception e) {
				limpiar();
				mostrarMensaje(Mensajes.ERROR_MESSAGE);
			}


		}
	}
	
	public void mostrarMensaje(String mensaje)
	{
		RequestContext context = RequestContext.getCurrentInstance();
		setModalText(mensaje);
		String codigo = "$('.modalPseudoClass').modal()";
		context.execute(codigo);
	}
	
	public void limpiar()
	{
		nombres="";
		apellidos="";
		telefono="";
		correo="";
		mensaje="";
		
	}


	public String getNombres() {
		return nombres;
	}


	public void setNombres(String nombres) {
		this.nombres = nombres;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
	
	public String getModalText() {
		return modalText;
	}

	public void setModalText(String modalText) {
		this.modalText = modalText;
	}
	

}
