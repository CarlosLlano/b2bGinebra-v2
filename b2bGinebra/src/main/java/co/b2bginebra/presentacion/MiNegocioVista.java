package co.b2bginebra.presentacion;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import co.b2bginebra.utils.Mensajes;
import org.omnifaces.util.Ajax;
import org.primefaces.context.RequestContext;

import co.b2bginebra.logica.CategoriaProdLogica;
import co.b2bginebra.logica.HorarioAtencionLogica;
import co.b2bginebra.logica.ImagenLogica;
import co.b2bginebra.logica.NegocioLogica;
import co.b2bginebra.logica.TipoNegocioLogica;
import co.b2bginebra.logica.UsuarioLogica;
import co.b2bginebra.modelo.CategoriaProd;
import co.b2bginebra.modelo.HorarioAtencion;
import co.b2bginebra.modelo.Imagen;
import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.modelo.Oferta;
import co.b2bginebra.modelo.TipoNegocio;
import co.b2bginebra.modelo.Usuario;
import co.b2bginebra.seguridad.JsfSecurityTools;
import net.bootsfaces.component.dateTimePicker.DateTimePicker;
import net.bootsfaces.component.inputTextarea.InputTextarea;
import net.bootsfaces.component.selectOneMenu.SelectOneMenu;
import net.bootsfaces.utils.FacesMessages;

/**
 * 
 * representa la vista de configuracion de los atributos de un negocio
 *
 */

@ManagedBean(name="miNegocioVista")
@ViewScoped
public class MiNegocioVista 
{
	
	/**
	 * Elemento visuales
	 */
	private SelectOneMenu somTipoNegocio;
	private List<SelectItem> tipoNegocios;
	//imagen principal
	private Part file;  
	private byte[] imagen;
	//horarios
	private SelectOneMenu somDiaHorario;
	private List<SelectItem> dias;
	private DateTimePicker pickerDesde;
	private DateTimePicker pickerHasta;
	private HorarioAtencion horarioSeleccionado;
	//galeria
	private Part fileGaleria;  
	private byte[] imagenGaleria;
	private String txtIdentificadorImagen;
	//catalogo
	private SelectOneMenu somCategoriaProd;
	private List<SelectItem> categoriasProd;
	private InputTextarea txtAreaDescripcion;
	
	
	/**
	 * Datos del negocio
	 */
	private List<HorarioAtencion> horarios;
	private List<Imagen> imagenes;
	private List<Oferta> ofertas;
	private Negocio negocioSeleccionado;
	
	/**
	 * Ejbs
	 */
	@EJB
	private NegocioLogica negocioLogica;
	@EJB
	private HorarioAtencionLogica horariosLogica;
	@EJB
	private ImagenLogica imagenLogica;
	@EJB
	private TipoNegocioLogica tipoNegocioLogica;
	@EJB
	private CategoriaProdLogica categoriaProdLogica;
	@EJB
	private UsuarioLogica usuarioLogica;
	
	@PostConstruct
    public void postConstruct() 
	{
		try 
		{
			Usuario usuario = (Usuario) JsfSecurityTools.getfromSession("usuario");
			Negocio negocio = usuario.getNegocios().get(0);
			
			negocioSeleccionado = clonar(negocio);
			
			
			horarios = horariosLogica.consultarHorariosPorNegocio(negocioSeleccionado.getIdNegocio());
			imagenes = imagenLogica.consultarImagenesPorNegocio(negocioSeleccionado.getIdNegocio());
			ofertas = negocioSeleccionado.getOfertas();
			
			
		} 
		catch (Exception e) 
		{
			
		}
		
    	
    }
	
	public List<Oferta> getOfertas() {
		return ofertas;
	}
	
	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}
	
	public String getImagenPrincipal() 
	{
		try 
		{
			String encoded = Base64.getEncoder().encodeToString(negocioSeleccionado.getImgPrincipal());
			String ruta = "data:image/png;base64," + encoded;
			return ruta;

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public String getImage(Imagen imagen) 
	{		
		try 
		{
			String encoded = Base64.getEncoder().encodeToString(imagen.getImagen());
			String ruta = "data:image/png;base64," + encoded;
			return ruta;

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
		
	}
	
	public List<Imagen> getImagenes() {
		return imagenes;
	}
	
	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public List<HorarioAtencion> getHorarios() {
		return horarios;
	}



	public void setHorarios(List<HorarioAtencion> horarios) {
		this.horarios = horarios;
	}



	public Negocio getNegocioSeleccionado() {
		return negocioSeleccionado;
	}

	public void setNegocioSeleccionado(Negocio negocioSeleccionado) {
		this.negocioSeleccionado = negocioSeleccionado;
	}
	
	public boolean getHayImagenes()
	{
		if(imagenes != null)
		{
			return imagenes.size() > 0;
		}
		else
		{
			return false;
		}
		
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
				somTipoNegocio.setValue(negocioSeleccionado.getTipoNegocio().getIdTipoNegocio());
				
			}
		} 
		catch (Exception e) 
		{
			//handle error
		}
		return tipoNegocios;
	}

	public void setTipoNegocios(List<SelectItem> tipoNegocios) {
		this.tipoNegocios = tipoNegocios;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}
	
	public void uploadImagen() 
	{
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
					
					negocioSeleccionado.setImgPrincipal(imagen);
					Ajax.update("formulario:panelImagen");
					
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
	public void mostrarMensaje(String mensaje)
	{
	
		FacesMessages.info(mensaje);		
        RequestContext.getCurrentInstance().scrollTo("formulario:top");
       

	}
	
	public Negocio clonar(Negocio negocio)
	{
		Negocio respuesta = new Negocio();
		
		respuesta.setIdNegocio(negocio.getIdNegocio());
		respuesta.setRazonSocial(negocio.getRazonSocial());
		respuesta.setDireccion(negocio.getDireccion());
		respuesta.setTelefono(negocio.getTelefono());
		respuesta.setSitioWeb(negocio.getSitioWeb());
		respuesta.setCorreo(negocio.getCorreo());
		respuesta.setDescripcion(negocio.getDescripcion());
		respuesta.setTipoNegocio(negocio.getTipoNegocio());
		respuesta.setEstado(negocio.getEstado());
		respuesta.setImgPrincipal(negocio.getImgPrincipal());
		respuesta.setHorarioAtencions(negocio.getHorarioAtencions());
		respuesta.setImagens(negocio.getImagens());
		respuesta.setNotificacions(negocio.getNotificacions());
		respuesta.setOfertas(negocio.getOfertas());
		respuesta.setUsuario(negocio.getUsuario());
		
		
		return respuesta;
	}

	public Part getFileGaleria() {
		return fileGaleria;
	}

	public void setFileGaleria(Part fileGaleria) {
		this.fileGaleria = fileGaleria;
	}

	public byte[] getImagenGaleria() {
		return imagenGaleria;
	}

	public void setImagenGaleria(byte[] imagenGaleria) {
		this.imagenGaleria = imagenGaleria;
	}
	
	public void uploadImagenGaleria() 
	{
		
		if(fileGaleria != null)
		{
			try 
			{
				//validar
				if(validateFile(fileGaleria))
				{
					//leer
					Scanner scanner = new Scanner(fileGaleria.getInputStream());
					imagenGaleria = new byte[(int)fileGaleria.getSize()];
					InputStream in = fileGaleria.getInputStream();
					in.read(imagenGaleria); 
					scanner.close();	
					
					Imagen imagen = new Imagen();
					imagen.setImagen(imagenGaleria);
					imagen.setNegocio(negocioSeleccionado);
					imagenes.add(imagen);
					negocioSeleccionado.setImagens(imagenes);
					
					
					Ajax.update("formulario:galeria");
					
					
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

	public SelectOneMenu getSomDiaHorario() {
		return somDiaHorario;
	}

	public void setSomDiaHorario(SelectOneMenu somDiaHorario) {
		this.somDiaHorario = somDiaHorario;
	}

	
	
	public void agregarHorario()
	{
		Date dateDesde = null;
		Date dateHasta = null;
		if(pickerDesde.getValue() == null)
		{
			mostrarMensaje(Mensajes.MISSING_OPEN_SCHEDULE);
		}
		else if(pickerHasta.getValue() == null)
		{
			mostrarMensaje(Mensajes.MISSING_CLOSED_SCHEDULE);
		}
		else
		{
			dateDesde = (Date) pickerDesde.getValue();
			dateHasta = (Date) pickerHasta.getValue();
			
			String inicio = dateToString(dateDesde);
			String fin = dateToString(dateHasta);
			int id = Integer.parseInt(somDiaHorario.getValue().toString());
			String dia = dias.get(id).getLabel();
			
			HorarioAtencion horarioAtencion = new HorarioAtencion();
			horarioAtencion.setDia(dia);
			horarioAtencion.setFechaInicio(inicio);
			horarioAtencion.setFechaFin(fin);
			horarioAtencion.setNegocio(negocioSeleccionado);
			
			horarios.add(horarioAtencion);
			negocioSeleccionado.setHorarioAtencions(horarios);
			
			Ajax.update("formulario:horarios");
		}
	}
	
	public void borrar(HorarioAtencion horario)
	{
		horarios.remove(horario);
		Ajax.update("formulario:horarios");
	}
	
	public void agregarProducto()
	{
		try
		{
			long id = Long.parseLong(somCategoriaProd.getValue().toString());
			if(id < 0)
			{
				mostrarMensaje(Mensajes.MISSING_PRODUCT_CATEGORY);
			}
			else
			{
				CategoriaProd categoriaProd = categoriaProdLogica.consultarCategoriaProd(id);
				
				if(txtAreaDescripcion.getValue() == null || txtAreaDescripcion.getValue().toString().trim().equals(""))
				{
					mostrarMensaje(Mensajes.MISSING_PRODUCT_DESCRIPTION);
				}
				else 
				{
					String descripcion = txtAreaDescripcion.getValue().toString().trim();
					Oferta oferta = new Oferta();
					oferta.setCategoriaProd(categoriaProd);
					oferta.setDescripcion(descripcion);
					oferta.setNegocio(negocioSeleccionado);
					
					ofertas.add(oferta);
					negocioSeleccionado.setOfertas(ofertas);
					somCategoriaProd.resetValue();
					txtAreaDescripcion.resetValue();
					Ajax.update("formulario:catalogo");
					RequestContext.getCurrentInstance().scrollTo("formulario:bottom");
				}
			}
		}
		catch(Exception e)
		{
			mostrarMensaje(Mensajes.ERROR_MESSAGE);
		}
	}
	
	public void borrarOferta(Oferta oferta)
	{
		ofertas.remove(oferta);
		Ajax.update("formulario:catalogo");
	}
	
	public DateTimePicker getPickerDesde() {
		return pickerDesde;
	}
	public void setPickerDesde(DateTimePicker pickerDesde) {
		this.pickerDesde = pickerDesde;
	}
	
	public DateTimePicker getPickerHasta() {
		return pickerHasta;
	}
	public void setPickerHasta(DateTimePicker pickerHasta) {
		this.pickerHasta = pickerHasta;
	}
	
	public List<SelectItem> getDias()
	{
		try 
		{
			if(dias==null)
			{
				dias = new ArrayList<SelectItem>();
				ArrayList<String> diasDeLaSemana = new ArrayList<String>();
				diasDeLaSemana.add("Lunes");
				diasDeLaSemana.add("Martes");
				diasDeLaSemana.add("Miercoles");
				diasDeLaSemana.add("Jueves");
				diasDeLaSemana.add("Viernes");
				diasDeLaSemana.add("Sabado");
				diasDeLaSemana.add("Domingo");
				for(int i = 0; i < diasDeLaSemana.size(); i++)
				{
					SelectItem item = new SelectItem(i, diasDeLaSemana.get(i));
					dias.add(item);
				}
			}
		} 
		catch (Exception e) 
		{
			//handle error
		}
		return dias;
	}
	
	public String dateToString(Date date)
	{
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date 
		String hora = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		int minutos = calendar.get(Calendar.MINUTE);
		String min = minutos < 10? minutos + "0":String.valueOf(minutos);
		String tiempo = hora + ":" + min;
		
		return tiempo;
	}
	
	public void seleccionarHorario(HorarioAtencion horarioAtencion)
	{
		horarioSeleccionado = horarioAtencion;
	}
	
	public HorarioAtencion getHorarioSeleccionado() {
		return horarioSeleccionado;
	}
	public void setHorarioSeleccionado(HorarioAtencion horarioSeleccionado) {
		this.horarioSeleccionado = horarioSeleccionado;
	}

	public SelectOneMenu getSomCategoriaProd() {
		return somCategoriaProd;
	}

	public void setSomCategoriaProd(SelectOneMenu somCategoriaProd) {
		this.somCategoriaProd = somCategoriaProd;
	}

	public List<SelectItem> getCategoriasProd() 
	{
		try 
		{
			if(categoriasProd==null)
			{
				List<CategoriaProd> pcategoriasProd = negocioSeleccionado.getTipoNegocio().getCategoriaProds();
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
	
	public InputTextarea getTxtAreaDescripcion() {
		return txtAreaDescripcion;
	}
	
	public void setTxtAreaDescripcion(InputTextarea txtAreaDescripcion) {
		this.txtAreaDescripcion = txtAreaDescripcion;
	}
	
	
	
	
	public void guardarCambios()
	{
		
        try 
        {
        		//actualizar atributos del negocio
			long id = Long.parseLong(somTipoNegocio.getValue().toString());
			TipoNegocio tipoNegocio = tipoNegocioLogica.consultarTipoNegocio(id);
			negocioSeleccionado.setTipoNegocio(tipoNegocio);
			
			negocioSeleccionado.setHorarioAtencions(horarios);
			negocioSeleccionado.setImagens(imagenes);
			negocioSeleccionado.setOfertas(ofertas);
		
			negocioLogica.modificarNegocio(negocioSeleccionado);

    			
    			//actualizacion de la informacion del usuario en sesion
    			Usuario usuario = (Usuario) JsfSecurityTools.getfromSession("usuario");
    			Usuario usu = usuarioLogica.consultarUsuario(usuario.getIdUsuario());
    			JsfSecurityTools.putinSession("usuario", usu);
    			
    			//recargar pagina
        		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        		mostrarMensaje(Mensajes.SUCCESS_CHANGES_MADE);
    			
			externalContext.redirect("miNegocio.xhtml?faces-redirect=true");
			
			
			
			
			
		} 
        catch (Exception e) 
        {
			mostrarMensaje(e.getMessage());
			
			e.printStackTrace();
		}
	}
	
	public int getPosicionImagen(Imagen imagen)
	{
		return imagenes.indexOf(imagen);
	}
	
	public void eliminarImagenGaleria()
	{
		try
		{
			int pos = Integer.parseInt(txtIdentificadorImagen.trim());
			if(pos < 0 || pos >= imagenes.size())
			{
				mostrarMensaje(Mensajes.INVALID_IMAGE_ID);
			}
			else
			{
				txtIdentificadorImagen="";
				imagenes.remove(pos);
				Ajax.update("formulario:galeria");
			}
		}
		catch(Exception e)
		{
			mostrarMensaje(Mensajes.INVALID_IMAGE_ID);
		}
		
	}

	public String getTxtIdentificadorImagen() {
		return txtIdentificadorImagen;
	}

	public void setTxtIdentificadorImagen(String txtIdentificadorImagen) {
		this.txtIdentificadorImagen = txtIdentificadorImagen;
	}
	
	
	

}
