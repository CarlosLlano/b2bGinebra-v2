package co.b2bginebra.presentacion;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import co.b2bginebra.logica.EstadoLogica;
import co.b2bginebra.logica.NegocioLogica;
import co.b2bginebra.logica.NegocioRegistradoLogica;
import co.b2bginebra.logica.SolicitudRegLogica;
import co.b2bginebra.modelo.Estado;
import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.modelo.SolicitudReg;
import co.b2bginebra.modelo.Usuario;
import net.bootsfaces.component.selectOneMenu.SelectOneMenu;
import net.bootsfaces.utils.FacesMessages;

/**
 * representa la vista para la gestion de solicitudes
 *
 */

@ManagedBean(name="solicitudesVista")
@ViewScoped
public class SolicitudesVista 
{

	private List<SolicitudReg> solicitudes;

	@EJB
	private SolicitudRegLogica solicitudRegLogica;
	@EJB
	private NegocioRegistradoLogica negocioRegistradoLogica;
	@EJB
	private NegocioLogica negocioLogica;
	@EJB
	private EstadoLogica estadoLogica;

	private SolicitudReg solicitudRegSeleccionada;
	
	private String txtRespuesta;
	private SelectOneMenu somEstado;
	private List<SelectItem> estadosSolicitudes;


	@PostConstruct
	public void init()
	{
		txtRespuesta = "";
	}
	/**
	 * Comprueba si el usuario y el negocio de la solicitud seleccionada ya se encuentran registrados para aceptar inmediatamente.
	 */
	public void verificarSolicitud()
	{
		Negocio negocio = solicitudRegSeleccionada.getNegocio();
		Usuario usuario = negocio.getUsuario();
		
		try 
		{
			boolean estaRegistrado = negocioRegistradoLogica.estaRegistradoNegocioConUsuario(usuario, negocio);
			
			
			if(estaRegistrado)
			{
				mostrarMensaje("La solicitud puede ser aceptada");
			}
			else
			{
				mostrarMensaje("Es necesario verificar los datos");		
			}
		} 
		catch (Exception e) 
		{
			mostrarMensaje("Ocurrio un error");
		}
		
		
	}

	public void aceptarSolicitud()
	{
		
		try 
		{
			solicitudRegLogica.aceptar(solicitudRegSeleccionada);
			mostrarMensaje("Solicitud aceptada correctamente");
			
		} 
		catch (Exception e) 
		{
			mostrarMensaje("Ocurrio un error");
		}		
	}

	public void rechazarSolicitud()
	{
		try 
		{
			if(txtRespuesta.trim().equals(""))
			{
				mostrarMensaje("Se debe especificar una respuesta");
			}
			else
			{
				solicitudRegSeleccionada.setRespuesta(txtRespuesta);
				solicitudRegLogica.rechazar(solicitudRegSeleccionada);
				
				mostrarMensaje("Solicitud rechazada correctamente");
			}
			
		} 
		catch (Exception e) 
		{
			
			mostrarMensaje("Ocurrio un error");
		}
	}

	public List<SolicitudReg> getSolicitudes() {

		try 
		{
			if(solicitudes==null)
			{
				solicitudes = solicitudRegLogica.consultarTodos();
			}
		} 
		catch (Exception e) 
		{

		}
		return solicitudes;
	}

	public void setSolicitudes(List<SolicitudReg> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public SolicitudReg getSolicitudRegSeleccionada() {
		return solicitudRegSeleccionada;
	}

	public void setSolicitudRegSeleccionada(SolicitudReg solicitudRegSeleccionada) {
		this.solicitudRegSeleccionada = solicitudRegSeleccionada;
	}
	
	public void seleccionarSolicitud(SolicitudReg solicitudReg)
	{
		
		solicitudRegSeleccionada = solicitudReg;
		
	}
	
	public String getTxtRespuesta() {
		return txtRespuesta;
	}

	public void setTxtRespuesta(String txtRespuesta) {
		this.txtRespuesta = txtRespuesta;
	}
	
	public String getImage(Long idNegocio) 
	{
		
		try 
		{

			Negocio negocio = negocioLogica.consultarNegocio(Long.valueOf(idNegocio));
			String encoded = Base64.getEncoder().encodeToString(negocio.getImgPrincipal());
			String ruta = "data:image/png;base64," + encoded;
			return ruta;

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
		
	}
	
	

	public void mostrarMensaje(String mensaje)
	{
		try
		{
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			FacesMessages.info(mensaje);		
	        RequestContext.getCurrentInstance().scrollTo("formulario:top");
				
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	        externalContext.redirect("alcaldiaSolicitudes.xhtml?faces-redirect=true");
		}
		catch(Exception e)
		{
			
		}
		
	

	}
	public SelectOneMenu getSomEstado() {
		return somEstado;
	}
	public void setSomEstado(SelectOneMenu somEstado) {
		this.somEstado = somEstado;
	}
	public List<SelectItem> getEstadosSolicitudes() 
	{
		try 
		{
			if(estadosSolicitudes==null)
			{
				List<Estado> estados = estadoLogica.consultarTodos();
				estadosSolicitudes = new ArrayList<SelectItem>();
				for (Estado est : estados) 
				{
					if(est.getTipoEstado().getNombre().equals("Solicitud de registro"))
					{
						SelectItem item = new SelectItem(est.getIdEstado(), est.getNombre());
						estadosSolicitudes.add(item);
					}
					
				}	
			}
		} 
		catch (Exception e) 
		{
			//handle error
		}
		
		return estadosSolicitudes;
	}
	public void setEstadosSolicitudes(List<SelectItem> estadosSolicitudes) {
		this.estadosSolicitudes = estadosSolicitudes;
	}
	
	public void somEstadoOnChange()
	{
		try 
		{
			long id = Long.parseLong(somEstado.getValue().toString());
			if(id < 0)
			{
				solicitudes = solicitudRegLogica.consultarTodos();
			}
			else
			{
				
				solicitudes = solicitudRegLogica.consultarSolicitudPorEstado(id);
				
			}
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
	
	}
	
	
	
	
	
	
	
	
}
