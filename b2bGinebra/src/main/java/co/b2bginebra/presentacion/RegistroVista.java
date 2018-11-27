package co.b2bginebra.presentacion;

import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import co.b2bginebra.modelo.NegocioRegistrado;
import co.b2bginebra.utils.Mensajes;
import org.omnifaces.util.Ajax;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;

import co.b2bginebra.logica.EstadoLogica;
import co.b2bginebra.logica.GestionCorreosLogica;
import co.b2bginebra.logica.NegocioLogica;
import co.b2bginebra.logica.NegocioRegistradoLogica;
import co.b2bginebra.logica.SolicitudRegLogica;
import co.b2bginebra.logica.TipoNegocioLogica;
import co.b2bginebra.logica.UsuarioLogica;
import co.b2bginebra.modelo.Estado;
import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.modelo.SolicitudReg;
import co.b2bginebra.modelo.TipoNegocio;
import co.b2bginebra.modelo.Usuario;
import net.bootsfaces.utils.FacesMessages;


/**
 * representa la vista para realizar el registro en la aplicacion
 *
 */

@ManagedBean(name="registroVista")
@ViewScoped
public class RegistroVista
{
	//Datos del usuario
	private String txtUsuNombre;
	private String txtUsuIdentificacion;
	private String txtUsuTelefono;
	private String txtUsuDireccion;
	private String txtUsuCorreo;
	private String txtUsuPassword;
	private String txtUsuPasswordConfirmar;

	//Datos del negocio
	private String txtNegRazonSocial;
	private String txtNegDireccion;
	private String txtNegTelefono;
	private String txtNegSitioWeb;
	private String txtNegCorreo;
	private String txtNegDescripcion;
	private SelectOneMenu somTipoNegocio;
	private List<SelectItem> tipoNegocios;
	private Part file;
	private byte[] imagen;
	
	
	//logica de negocio
	@EJB
	private UsuarioLogica usuarioLogica;
	@EJB
	private NegocioLogica negocioLogica;
	@EJB
	private TipoNegocioLogica tipoNegocioLogica;
	@EJB
	private SolicitudRegLogica solicitudRegLogica;
	@EJB
	private NegocioRegistradoLogica negocioRegistradoLogica;
	@EJB
	private EstadoLogica estadoLogica;
	@EJB
	private GestionCorreosLogica gestionCorreosLogica;

	private List<NegocioRegistrado> negocioRegistrados;

	@PostConstruct
	public void init()
	{
		//Datos del usuario
		txtUsuNombre = "";
		txtUsuIdentificacion = "";
		txtUsuTelefono= "";;
		txtUsuDireccion= "";
		txtUsuCorreo= "";
		txtUsuPassword= "";
		txtUsuPasswordConfirmar= "";

		//Datos del negocio
		txtNegRazonSocial= "";
		txtNegDireccion= "";
		txtNegTelefono= "";
		txtNegSitioWeb= "";
		txtNegCorreo= "";
		txtNegDescripcion= "";

		negocioRegistrados = negocioRegistradoLogica.consultarTodos();
	}
	
	public void registrar()
	{
		try
		{
			
			Usuario usuNuevo = crearUsuario();
			Negocio negocioNuevo = crearNegocio();
			
			//validacion
			usuarioLogica.validarAtributos(usuNuevo);
			negocioLogica.validarAtributos(negocioNuevo);
			
			
			negocioNuevo.setUsuario(usuNuevo);

			//se busca usuario-negocio en la tabla de negocios registrados
			boolean estaRegistrado = negocioRegistradoLogica.estaRegistradoNegocioConUsuario(usuNuevo, negocioNuevo);

			if(estaRegistrado == false)
			{
				//el usuario y el negocio quedan como inactivos
				Estado estado = estadoLogica.consultarEstadoPorNombre("Inactivo");
				usuNuevo.setEstado(estado);
				negocioNuevo.setEstado(estado);

				
				//crea el negocio y el usuario asociado
				negocioLogica.crearNegocio(negocioNuevo);

				//se crea solicitud de registro
				SolicitudReg solicitudReg = new SolicitudReg();
				solicitudReg.setDescripcion("Datos de Usuario: \n" + usuNuevo.toString() + "\n\n" + "Datos de Negocio: \n" + negocioNuevo.toString());
				solicitudReg.setFechaCreacion(new Date());
				solicitudReg.setNegocio(negocioNuevo);
				solicitudReg.setEstado(estadoLogica.consultarEstadoPorNombre("Enviada"));
				solicitudRegLogica.crearSolicitudReg(solicitudReg);			

				//Se manda correo a la Alcaldia informando de que se creo una solicitud de registro
				gestionCorreosLogica.enviarCorreoSolicitudCreada(solicitudReg);
				
				limpiar();
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				mostrarMensaje(Mensajes.SUCCESS_COMPLETED_REGISTRATION);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	            externalContext.redirect("registro.xhtml?faces-redirect=true");
				
				
				
				
				
			}
			else
			{
				//No se manda solicitud de registro. El usuario y el negocio quedan en estado activo
				Estado estado = estadoLogica.consultarEstadoPorNombre("Activo");
				usuNuevo.setEstado(estado);
				negocioNuevo.setEstado(estado);

				//crea el negocio y el usuario asociado
				negocioLogica.crearNegocio(negocioNuevo);

				limpiar();
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				mostrarMensaje(Mensajes.SUCCESS_ACCOUNT_CREATED);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	            externalContext.redirect("registro.xhtml?faces-redirect=true");
				

			}


		}
		catch(Exception e)
		{
			mostrarMensaje(e.getMessage());
		}
	}

	public Usuario crearUsuario() throws Exception
	{
		if(txtUsuPassword.equals(txtUsuPasswordConfirmar)==false)
		{
			throw new Exception(Mensajes.INVALID_PASSWORD);
			
		}
		
		usuarioLogica.existeUsuario(txtUsuIdentificacion, txtUsuCorreo);
		
		Usuario usuNuevo = new Usuario();
		usuNuevo.setNombre(txtUsuNombre.trim());
		usuNuevo.setIdentificacion(txtUsuIdentificacion.trim());
		usuNuevo.setTelefono(txtUsuTelefono.trim());
		usuNuevo.setDireccion(txtUsuDireccion.trim());
		usuNuevo.setCorreo(txtUsuCorreo.trim());
		usuNuevo.setPassword(txtUsuPassword.trim());
		return usuNuevo;
		
		
		
		
	}

	public Negocio crearNegocio() throws Exception
	{
		
		Negocio negocioNuevo = new Negocio();
		negocioNuevo.setRazonSocial(txtNegRazonSocial.trim());
		negocioNuevo.setDireccion(txtNegDireccion.trim());
		negocioNuevo.setTelefono(txtNegTelefono.trim());
		negocioNuevo.setSitioWeb(txtNegSitioWeb.trim());
		negocioNuevo.setCorreo(txtNegCorreo.trim());
		negocioNuevo.setDescripcion(txtNegDescripcion.trim());
		uploadImagen();
		negocioNuevo.setImgPrincipal(imagen);
		long id = Long.parseLong(somTipoNegocio.getValue().toString());
		if(id < 0)
		{
			throw new Exception("Debe escoger un tipo de negocio");
		}
		else
		{
			TipoNegocio tipoNegocio = tipoNegocioLogica.consultarTipoNegocio(id);
			negocioNuevo.setTipoNegocio(tipoNegocio);
		}

		return negocioNuevo;
	}

	public void uploadImagen() 
	{
		
		imagen = null;
		if(file != null)
		{
			try 
			{
				//validar
				if(validateFile(file))
				{
					//leer
					Scanner scanner = new Scanner(file.getInputStream());
					
					imagen = new byte[(int)file.getSize()];
					InputStream in = file.getInputStream();
					in.read(imagen); 
					
					scanner.close();
					
					
				}
				else
				{
					mostrarMensaje(Mensajes.INVALID_IMAGE);
				}
			} 
			catch (IOException e) 
			{
				mostrarMensaje(Mensajes.ERROR_UPLOADING_IMAGE);
			}
		}
		else
		{
			mostrarMensaje(Mensajes.MISSING_IMAGE);
		}
		
		
	}
	public boolean validateFile(Part file) 
	{
		if(file!= null)
		{
			if (file.getContentType().equals("image/jpeg")==false) 
			{
				return false;
			}
		}
		return true;
	
	}

	//Por si se considera mostrar la imagen
	public String getRuta() 
	{
		String encoded = Base64.getEncoder().encodeToString(imagen);
		String ruta = "data:image/png;base64," + encoded;
		return ruta;
	}
	
	
	public void mostrarMensaje(String mensaje)
	{
		FacesMessages.info(mensaje);
        RequestContext.getCurrentInstance().scrollTo("formulario:top");
    }

	public void onChangeTxtUsuIdentificacion(){

	    List<NegocioRegistrado> negocioRegistrado = negocioRegistrados.stream()
                .filter((negocio) -> negocio.getDocRepr().equals(txtUsuIdentificacion))
                .collect(Collectors.toList());
	    if(!negocioRegistrado.isEmpty()){
            txtNegRazonSocial = negocioRegistrado.get(0).getRazonSocial();
            txtNegDireccion = negocioRegistrado.get(0).getDireccion();
	        txtUsuNombre = negocioRegistrado.get(0).getNombreRepr();

            Ajax.update("formulario:txtNegRazonSocial");
            Ajax.update("formulario:direccion");
            Ajax.update("formulario:txtUsuNombre");

            mostrarMensaje(Mensajes.AUTOCOMPLETE_TEXT);
            Ajax.update("formulario:growl");

        }
        else{
            txtNegRazonSocial = "";
            txtNegDireccion = "";
            txtUsuNombre = "";
            Ajax.update("formulario:txtNegRazonSocial");
            Ajax.update("formulario:txtNegDireccion");
            Ajax.update("formulario:txtUsuNombre");
        }
    }
	
	

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public SelectOneMenu getSomTipoNegocio() {
		return somTipoNegocio;
	}

	public void setSomTipoNegocio(SelectOneMenu somTipoNegocio) {
		this.somTipoNegocio = somTipoNegocio;
	}

	public List<SelectItem> getTipoNegocios() 
	{
		try 
		{
			if(tipoNegocios==null)
			{
				List<TipoNegocio> ptiposNegocios = tipoNegocioLogica.consultarTodos();
				tipoNegocios = new ArrayList<SelectItem>();
				for (TipoNegocio tipoNegocio : ptiposNegocios) 
				{
					SelectItem item = new SelectItem(tipoNegocio.getIdTipoNegocio(), tipoNegocio.getNombre());
					tipoNegocios.add(item);
				}	
			}
		} 
		catch (Exception e) 
		{
			
		}
		return tipoNegocios;
	}
	
	public void setTipoNegocios(List<SelectItem> tipoNegocios) {
		this.tipoNegocios = tipoNegocios;
	}

	public String getTxtUsuNombre() {
		return txtUsuNombre;
	}

	public void setTxtUsuNombre(String txtUsuNombre) {
		this.txtUsuNombre = txtUsuNombre;
	}

	public String getTxtUsuIdentificacion() {
		return txtUsuIdentificacion;
	}

	public void setTxtUsuIdentificacion(String txtUsuIdentificacion) {
		this.txtUsuIdentificacion = txtUsuIdentificacion;
	}

	public String getTxtUsuTelefono() {
		return txtUsuTelefono;
	}

	public void setTxtUsuTelefono(String txtUsuTelefono) {
		this.txtUsuTelefono = txtUsuTelefono;
	}

	public String getTxtUsuDireccion() {
		return txtUsuDireccion;
	}

	public void setTxtUsuDireccion(String txtUsuDireccion) {
		this.txtUsuDireccion = txtUsuDireccion;
	}

	public String getTxtUsuCorreo() {
		return txtUsuCorreo;
	}

	public void setTxtUsuCorreo(String txtUsuCorreo) {
		this.txtUsuCorreo = txtUsuCorreo;
	}

	public String getTxtUsuPassword() {
		return txtUsuPassword;
	}

	public void setTxtUsuPassword(String txtUsuPassword) {
		this.txtUsuPassword = txtUsuPassword;
	}
	
	public String getTxtUsuPasswordConfirmar() {
		return txtUsuPasswordConfirmar;
	}
	
	public void setTxtUsuPasswordConfirmar(String txtUsuPasswordConfirmar) {
		this.txtUsuPasswordConfirmar = txtUsuPasswordConfirmar;
	}

	public String getTxtNegRazonSocial() {
		return txtNegRazonSocial;
	}

	public void setTxtNegRazonSocial(String txtNegRazonSocial) {
		this.txtNegRazonSocial = txtNegRazonSocial;
	}

	public String getTxtNegDireccion() {
		return txtNegDireccion;
	}

	public void setTxtNegDireccion(String txtNegDireccion) {
		this.txtNegDireccion = txtNegDireccion;
	}

	public String getTxtNegTelefono() {
		return txtNegTelefono;
	}

	public void setTxtNegTelefono(String txtNegTelefono) {
		this.txtNegTelefono = txtNegTelefono;
	}

	public String getTxtNegSitioWeb() {
		return txtNegSitioWeb;
	}

	public void setTxtNegSitioWeb(String txtNegSitioWeb) {
		this.txtNegSitioWeb = txtNegSitioWeb;
	}

	public String getTxtNegCorreo() {
		return txtNegCorreo;
	}

	public void setTxtNegCorreo(String txtNegCorreo) {
		this.txtNegCorreo = txtNegCorreo;
	}

	public String getTxtNegDescripcion() {
		return txtNegDescripcion;
	}

	public void setTxtNegDescripcion(String txtNegDescripcion) {
		this.txtNegDescripcion = txtNegDescripcion;
	}

	public void limpiar()
	{
		txtUsuNombre = ""; 
		txtUsuIdentificacion = ""; 
		txtUsuTelefono = ""; 
		txtUsuDireccion = ""; 
		txtUsuCorreo = ""; 
		txtUsuPassword = ""; 
		txtNegRazonSocial = ""; 
		txtNegDireccion = ""; 
		txtNegTelefono = ""; 
		txtNegSitioWeb = ""; 
		txtNegCorreo = ""; 
		txtNegDescripcion = "";
	
	}
	
	
	
}
