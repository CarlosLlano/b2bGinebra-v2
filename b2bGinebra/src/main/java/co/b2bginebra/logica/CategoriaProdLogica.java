package co.b2bginebra.logica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;


import co.b2bginebra.dao.CategoriaProdDAO;
import co.b2bginebra.modelo.CategoriaProd;

@Stateless
public class CategoriaProdLogica
{
	
	@EJB
	private CategoriaProdDAO categoriaProdDAO;
	
	
	public void validarAtributos(CategoriaProd categoriaProd) throws Exception
	{
		if(categoriaProd==null)
		{
			throw new Exception("La categoria de producto no puede ser nula");
		}
		if(categoriaProd.getNombre()==null || categoriaProd.getNombre().equals(""))
		{
			throw new Exception("El nombre de la categoria es obligatorio");
		}
	}
	
	public void crearCategoriaProd(CategoriaProd categoriaProd) throws Exception
	{
		validarAtributos(categoriaProd);
		categoriaProdDAO.crear(categoriaProd);
	}
	
	public void modificarCategoriaProd(CategoriaProd categoriaProd) throws Exception
	{
		validarAtributos(categoriaProd);
		categoriaProdDAO.modificar(categoriaProd);
	}
	
	public void borrarCategoriaProd(CategoriaProd categoriaProd) throws Exception
	{
		categoriaProdDAO.borrar(categoriaProd);
	}
	
	public CategoriaProd consultarCategoriaProd(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return categoriaProdDAO.consultarPorId(id);
	}
	
	public List<CategoriaProd> consultarTodos() throws Exception 
	{
		return categoriaProdDAO.consultarTodos();
	}

}
