package co.b2bginebra.logica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;


import co.b2bginebra.dao.TipoNegocioDAO;
import co.b2bginebra.modelo.TipoNegocio;

@Stateless
public class TipoNegocioLogica
{
	
	@EJB
	private TipoNegocioDAO tipoNegocioDAO;
	
	
	public void validarAtributos(TipoNegocio tipoNegocio) throws Exception
	{
		if(tipoNegocio==null)
		{
			throw new Exception("el tipo de negocio no puede ser nulo");
		}
		if(tipoNegocio.getNombre()==null || tipoNegocio.getNombre().equals(""))
		{
			throw new Exception("El nombre es obligatorio");
		}
		
	}
	
	public void crearTipoNegocio(TipoNegocio tipoNegocio) throws Exception
	{
		validarAtributos(tipoNegocio);
		tipoNegocioDAO.crear(tipoNegocio);
	}
	
	public void modificarTipoNegocio(TipoNegocio tipoNegocio) throws Exception
	{
		validarAtributos(tipoNegocio);
		tipoNegocioDAO.modificar(tipoNegocio);
	}
	
	public void borrarTipoNegocio(TipoNegocio tipoNegocio) throws Exception
	{
		tipoNegocioDAO.borrar(tipoNegocio);
	}
	
	public TipoNegocio consultarTipoNegocio(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return tipoNegocioDAO.consultarPorId(id);
		
	}
	
	public List<TipoNegocio> consultarTodos() throws Exception 
	{
		return tipoNegocioDAO.consultarTodos();
	}

}
