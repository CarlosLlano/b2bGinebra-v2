package co.b2bginebra.logica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.b2bginebra.dao.TipoNotDAO;
import co.b2bginebra.modelo.TipoNot;

@Stateless
public class TipoNotLogica
{
	
	@EJB
	private TipoNotDAO tipoNotDAO;
	
	
	public void validarAtributos(TipoNot tipoNot) throws Exception
	{
		if(tipoNot==null)
		{
			throw new Exception("el tipo de notificacion no puede ser nulo");
		}
		if(tipoNot.getNombre()==null || tipoNot.getNombre().equals(""))
		{
			throw new Exception("El nombre es obligatorio");
		}
		if(tipoNot.getDescripcion()==null || tipoNot.getDescripcion().equals(""))
		{
			throw new Exception("La descripcion es obligatoria");
		}
	}
	
	public void crearTipoNot(TipoNot tipoNot) throws Exception
	{
		validarAtributos(tipoNot);
		tipoNotDAO.crear(tipoNot);
	}
	
	public void modificarTipoNot(TipoNot tipoNot) throws Exception
	{
		validarAtributos(tipoNot);
		tipoNotDAO.modificar(tipoNot);
	}
	
	public void borrarTipoNot(TipoNot tipoNot) throws Exception
	{
		tipoNotDAO.borrar(tipoNot);
	}
	
	public TipoNot consultarTipoNot(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return tipoNotDAO.consultarPorId(id);
	}
	
	public List<TipoNot> consultarTodos() throws Exception 
	{
		return tipoNotDAO.consultarTodos();
	}

}
