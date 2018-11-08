package co.b2bginebra.presentacion;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import co.b2bginebra.utils.Mensajes;
import org.omnifaces.util.Ajax;
import org.primefaces.context.RequestContext;

import co.b2bginebra.logica.CategoriaProdLogica;
import co.b2bginebra.logica.NotificacionLogica;
import co.b2bginebra.logica.TipoNotLogica;
import co.b2bginebra.modelo.CategoriaProd;
import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.modelo.Notificacion;
import co.b2bginebra.modelo.TipoNot;
import co.b2bginebra.modelo.Usuario;
import co.b2bginebra.seguridad.JsfSecurityTools;
import net.bootsfaces.component.selectOneMenu.SelectOneMenu;
import net.bootsfaces.utils.FacesMessages;

/**
 * representa la vista usada para gestionar notificaciones (crear, modificar, borrar, ver todas)
 *
 */
@ManagedBean(name="notificacionVista")
@ViewScoped
public class NotificacionVista 
{
	private String txtNombre;
	private String txtDescripcion;
	private Part file; 
	private byte[] imagen;
	private Date fechaTerminacion;
	private SelectOneMenu somTipoNotificacion;
	private org.primefaces.component.selectonemenu.SelectOneMenu somCategoriaProd;
	
	private List<SelectItem> losItemsTipoNotificacion;
	private List<SelectItem> categoriasProd;
	
	
	//negocio del usuario logueado
	private Negocio negocio;
	
	
	@EJB
	private NotificacionLogica notificacionLogica;
	@EJB
	private TipoNotLogica tipoNotLogica;
	@EJB
	private CategoriaProdLogica categoriaProdLogica;
	
	@PostConstruct
	public void init()
	{
		Usuario usuario = (Usuario) JsfSecurityTools.getfromSession("usuario");
		negocio = usuario.getNegocios().get(0);

	}
	
	public void crear()
	{
		try 
		{
			Notificacion notificacion = new Notificacion();
			notificacion.setNombre(txtNombre);
			notificacion.setDescripcion(txtDescripcion);
			notificacion.setImagen(imagen);
			notificacion.setFechaCreacion(new Date());
			notificacion.setFechaTerminacion(fechaTerminacion);
			notificacion.setNegocio(negocio);
			notificacion.setCategoriaProd(categoriaProdLogica.consultarCategoriaProd(Long.parseLong(somCategoriaProd.getValue().toString())));
			notificacion.setTipoNot(tipoNotLogica.consultarTipoNot(Long.parseLong(somTipoNotificacion.getValue().toString())));	
			
			notificacionLogica.crearNotificacion(notificacion);
			limpiar();
			Ajax.update("formulario:notificaciones");
			Ajax.update("formulario:infoNot");
			RequestContext.getCurrentInstance().scrollTo("formulario:bottom");
		} 
		catch (Exception e) 
		{
			mostrarMensaje(e.getMessage());
		}
			
	}

	public void borrar(Notificacion notificacion)
	{
		try 
		{
			
			notificacionLogica.borrarNotificacion(notificacion);
			Ajax.update("formulario:notificaciones");
		} 
		catch (Exception e) 
		{
			mostrarMensaje(e.getMessage());
		}
		
	}
	
	public void limpiar()
	{
		txtNombre = "";
		txtDescripcion = "";
		somCategoriaProd.resetValue();
		somTipoNotificacion.resetValue();
		imagen = null;
		fechaTerminacion = null;
		
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}
	
	public void uploadImagen() 
	{
		System.out.println("ENTRO 1");
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
					
					
					Ajax.update("formulario:panelImagen");
					System.out.println("ENTRO 3");

				}
				else
				{
					System.out.println("ENTRO 4");

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
		System.out.println("ENTRO 2");

		if(file!= null)
		{
			if (file.getContentType().equals("image/jpeg")==false) 
			{
				return false;
			}
		}
		return true;
	
	}
	
	public void mostrarMensaje(String mensaje)
	{
		System.out.println("ENTRO 5");

		FacesMessages.info(mensaje);		
	}

	public String getImagen() 
	{
		try 
		{
			String encoded = Base64.getEncoder().encodeToString(imagen);
			String ruta = "data:image/png;base64," + encoded;
			return ruta;

		} 
		catch (Exception e) 
		{
			
		}
		return null;
	}

	public String getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(String txtNombre) {
		this.txtNombre = txtNombre;
	}

	public String getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(String txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
	}

	public Date getFechaTerminacion() {
		return fechaTerminacion;
	}

	public void setFechaTerminacion(Date fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
	}

	public org.primefaces.component.selectonemenu.SelectOneMenu getSomCategoriaProd() {
		return somCategoriaProd;
	}
	
	public void setSomCategoriaProd(org.primefaces.component.selectonemenu.SelectOneMenu somCategoriaProd) {
		this.somCategoriaProd = somCategoriaProd;
	}

	public List<SelectItem> getCategoriasProd() {
		try 
		{
			if(categoriasProd==null)
			{
				List<CategoriaProd> pcategoriasProd = categoriaProdLogica.consultarTodos();
				categoriasProd = new ArrayList<SelectItem>();
				for (CategoriaProd categoriaProd : pcategoriasProd) 
				{
					SelectItem item = new SelectItem(categoriaProd.getIdCategoria(), categoriaProd.getNombre());
					categoriasProd.add(item);
				}	
			}
		} 
		catch (Exception e) 
		{
			//handle error
		}
		return categoriasProd;
	}

	public void setCategoriasProd(List<SelectItem> categoriasProd) {
		this.categoriasProd = categoriasProd;
	}

	public List<SelectItem> getLosItemsTipoNotificacion()
	{
		try
		{
			if(losItemsTipoNotificacion==null)
			{
				List<TipoNot> lista = tipoNotLogica.consultarTodos();
				losItemsTipoNotificacion = new ArrayList<SelectItem>();
				for (TipoNot tipoNot : lista) 
				{
					SelectItem selectItem = new SelectItem(tipoNot.getIdTipoNot(), tipoNot.getNombre());
					losItemsTipoNotificacion.add(selectItem);
				}
				
			}
		} 
		catch (Exception e) {
			
		}
		
		return losItemsTipoNotificacion;
	}

	public void setLosItemsTipoNotificacion(List<SelectItem> losItemsTipoNotificacion) {
		this.losItemsTipoNotificacion = losItemsTipoNotificacion;
	}
	
	

	public SelectOneMenu getSomTipoNotificacion() {
		return somTipoNotificacion;
	}

	public void setSomTipoNotificacion(SelectOneMenu somTipoNotificacion) {
		this.somTipoNotificacion = somTipoNotificacion;
	}

	public List<Notificacion> getNotificaciones() 
	{
		return notificacionLogica.consultarNotificacionPorNegocio(negocio.getIdNegocio());
	}
	
	public List<Notificacion> getTodasNotificaciones()
	{
		try 
		{
			List<Notificacion> notificaciones = notificacionLogica.consultarTodos();
			return notificaciones;
		} 
		catch (Exception e) 
		{
			return null;
		}
		
	}
	
	public String getImage(Long idNotificacion) 
	{
		
		try 
		{

			Notificacion not = notificacionLogica.consultarNotificacion(idNotificacion);
			String encoded = Base64.getEncoder().encodeToString(not.getImagen());
			String ruta = "data:image/png;base64," + encoded;
			return ruta;

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
		
	}
	
	

}
