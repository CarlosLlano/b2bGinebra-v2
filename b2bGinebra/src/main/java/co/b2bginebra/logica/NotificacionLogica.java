package co.b2bginebra.logica;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.b2bginebra.dao.NotificacionDAO;
import co.b2bginebra.modelo.Notificacion;

@Stateless
public class NotificacionLogica
{
	
	@EJB
	private NotificacionDAO notificacionDAO;
	
	
	public void validarAtributos(Notificacion notificacion) throws Exception
	{
		if(notificacion==null)
		{
			throw new Exception("La notificacion no puede ser nula");
		}
		if(notificacion.getNombre()==null || notificacion.getNombre().equals(""))
		{
			throw new Exception("El nombre es obligatorio");
		}
		if(notificacion.getDescripcion()==null || notificacion.getDescripcion().equals(""))
		{
			throw new Exception("La descripcion es obligatoria");
		}
		if(notificacion.getImagen()==null)
		{
			throw new Exception("La imagen es obligatoria");
		}
		if(notificacion.getFechaTerminacion()==null)
		{
			throw new Exception("La fecha de terminacion es obligatoria");
		}
		if(notificacion.getNegocio()==null)
		{
			throw new Exception("El negocio es obligatorio");
		}
		if(notificacion.getCategoriaProd()==null)
		{
			throw new Exception("La categoria de producto es obligatoria");
		}
		if(notificacion.getTipoNot()==null)
		{
			throw new Exception("El tipo de notificacion es obligatorio");
		}
		
	}
	
	public void crearNotificacion(Notificacion notificacion) throws Exception
	{
		validarAtributos(notificacion);
		notificacionDAO.crear(notificacion);
	}
	
	public void modificarNotificacion(Notificacion notificacion) throws Exception
	{
		validarAtributos(notificacion);
		notificacionDAO.modificar(notificacion);
	}
	
	public void borrarNotificacion(Notificacion notificacion) throws Exception
	{
		notificacionDAO.borrar(notificacion);
	}
	
	public Notificacion consultarNotificacion(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return notificacionDAO.consultarPorId(id);
	}
	
	public List<Notificacion> consultarTodos() throws Exception 
	{
		return notificacionDAO.consultarTodos();
	}

	public List<Notificacion> consultarNotificacionPorNegocio(Long idNegocio)
    {
		return notificacionDAO.consultarNotificacionPorNegocio(idNegocio);
    }
}
