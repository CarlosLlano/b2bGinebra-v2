package co.b2bginebra.logica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;


import co.b2bginebra.dao.TipoEstadoDAO;
import co.b2bginebra.modelo.TipoEstado;

@Stateless
public class TipoEstadoLogica
{
	
	@EJB
	private TipoEstadoDAO tipoEstadoDAO;
	
	
	public void validarAtributos(TipoEstado tipoEstado) throws Exception
	{
		if(tipoEstado==null)
		{
			throw new Exception("El tipo de estado no puede ser nulo");
		}
		if(tipoEstado.getNombre()==null || tipoEstado.getNombre().equals(""))
		{
			throw new Exception("El nombre es obligatorio");
		}
		if(tipoEstado.getDescripcion()==null || tipoEstado.getDescripcion().equals(""))
		{
			throw new Exception("La descripcion es obligatoria");
		}
		
	}
	
	public void crearTipoEstado(TipoEstado tipoEstado) throws Exception
	{
		validarAtributos(tipoEstado);
		tipoEstadoDAO.crear(tipoEstado);
	}
	
	public void modificarTipoEstado(TipoEstado tipoEstado) throws Exception
	{
		validarAtributos(tipoEstado);
		tipoEstadoDAO.modificar(tipoEstado);
	}
	
	public void borrarTipoEstado(TipoEstado tipoEstado) throws Exception
	{
		tipoEstadoDAO.borrar(tipoEstado);
	}
	
	public TipoEstado consultarTipoEstado(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return tipoEstadoDAO.consultarPorId(id);
		
	}
	
	public List<TipoEstado> consultarTodos() throws Exception 
	{
		return tipoEstadoDAO.consultarTodos();
	}

}
