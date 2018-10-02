package co.b2bginebra.logica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.b2bginebra.dao.EstadoDAO;
import co.b2bginebra.modelo.Estado;

@Stateless
public class EstadoLogica
{
	
	@EJB
	private EstadoDAO estadoDAO;
	
	
	public void validarAtributos(Estado estado) throws Exception
	{
		if(estado==null)
		{
			throw new Exception("El estado no puede ser nulo");
		}
		if(estado.getNombre()==null || estado.getNombre().equals(""))
		{
			throw new Exception("El nombre del estado es obligatorio");
		}
		if(estado.getDescripcion()==null || estado.getDescripcion().equals(""))
		{
			throw new Exception("La descripcion del estado es obligatorio");
		}
	}
	
	public void crearEstado(Estado estado) throws Exception
	{
		validarAtributos(estado);
		estadoDAO.crear(estado);
	}
	
	public void modificarEstado(Estado estado) throws Exception
	{
		validarAtributos(estado);
		estadoDAO.modificar(estado);
	}
	
	public void borrarEstado(Estado estado) throws Exception
	{
		estadoDAO.borrar(estado);
	}
	
	public Estado consultarEstado(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return estadoDAO.consultarPorId(id);
	}
	
	public Estado consultarEstadoPorNombre(String nombreEstado) throws Exception
	{
		return estadoDAO.consultarEstadoPorNombre(nombreEstado);
	}
	
	public List<Estado> consultarTodos() throws Exception 
	{
		return estadoDAO.consultarTodos();
	}

}
