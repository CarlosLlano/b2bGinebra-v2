package co.b2bginebra.presentacion;

import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import co.b2bginebra.logica.HorarioAtencionLogica;
import co.b2bginebra.logica.ImagenLogica;
import co.b2bginebra.logica.NegocioLogica;
import co.b2bginebra.modelo.HorarioAtencion;
import co.b2bginebra.modelo.Imagen;
import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.modelo.Oferta;

/**
 * 
 * representa la vista con los detalles del negocio
 *
 */

@ManagedBean(name="negocioVista")
@ViewScoped
public class NegocioVista 
{
	
	private List<HorarioAtencion> horarios;
	private List<Imagen> imagenes;
	private List<Oferta> ofertas;
	
	private Negocio negocioSeleccionado;
	
	@EJB
	private NegocioLogica negocioLogica;
	@EJB
	private HorarioAtencionLogica horariosLogica;
	@EJB
	private ImagenLogica imagenLogica;
	
	@PostConstruct
    public void postConstruct() 
	{
		try 
		{
			Long id = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("negocio"));
			negocioSeleccionado = negocioLogica.consultarNegocio(id);

			if(negocioSeleccionado==null){
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect("home.xhtml");
			}else{
				horarios = horariosLogica.consultarHorariosPorNegocio(negocioSeleccionado.getIdNegocio());
				imagenes = imagenLogica.consultarImagenesPorNegocio(negocioSeleccionado.getIdNegocio());
				ofertas = negocioSeleccionado.getOfertas();
			}
			
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
	
	
	
	
	
	
	
	
}
