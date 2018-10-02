package co.b2bginebra.logica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;


import co.b2bginebra.dao.ParametroSistemaDAO;
import co.b2bginebra.modelo.ParametroSistema;

@Stateless
public class ParametroSistemaLogica
{
	
	@EJB
	private ParametroSistemaDAO parametrosSistemaDAO;
	
	
	public void validarAtributos(ParametroSistema parametroSistema) throws Exception
	{
		if(parametroSistema==null)
		{
			throw new Exception("El parametro no puede ser nulo");
		}
		if(parametroSistema.getNombre()==null || parametroSistema.getNombre().equals(""))
		{
			throw new Exception("El nombre del parametro es obligatorio");
		}
		if(parametroSistema.getValor()==null || parametroSistema.getValor().equals(""))
		{
			throw new Exception("El valor del parametro es obligatorio");
		}
	}
	
	public void crearParametroSistema(ParametroSistema parametroSistema) throws Exception
	{
		validarAtributos(parametroSistema);
		parametrosSistemaDAO.crear(parametroSistema);
	}
	
	public void modificarParametroSistema(ParametroSistema parametroSistema) throws Exception
	{
		validarAtributos(parametroSistema);
		parametrosSistemaDAO.modificar(parametroSistema);
	}
	
	public void borrarParametroSistema(ParametroSistema parametroSistema) throws Exception
	{
		parametrosSistemaDAO.borrar(parametroSistema);
	}
	
	public ParametroSistema consultarParametroSistema(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return parametrosSistemaDAO.consultarPorId(id);
	}
	
	public List<ParametroSistema> consultarTodos() throws Exception 
	{
		return parametrosSistemaDAO.consultarTodos();
	}
	
	public ParametroSistema consultarParametroPorNombre(String nombre) throws Exception
	{
		return parametrosSistemaDAO.consultarParametroPorNombre(nombre);
	}

}
