package co.b2bginebra.logica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;


import co.b2bginebra.dao.NegocioRegistradoDAO;
import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.modelo.NegocioRegistrado;
import co.b2bginebra.modelo.Usuario;

@Stateless
public class NegocioRegistradoLogica
{
	
	@EJB
	private NegocioRegistradoDAO negocioRegistradoDAO;
	
	
	public void validarAtributos(NegocioRegistrado negocio) throws Exception
	{
		if(negocio==null)
		{
			throw new Exception("El negocio registrado no puede ser nulo");
		}
		if(negocio.getDocRepr()==null || negocio.getDocRepr().equals(""))
		{
			throw new Exception("El negocio registrado debe especificar el numero de documento del representante legal");
		}
		if(negocio.getRazonSocial()==null || negocio.getRazonSocial().equals(""))
		{
			throw new Exception("El negocio registrado debe especificar la razon social");
		}
	}
	
	public void crearNegocioRegistrado(NegocioRegistrado negocio) throws Exception
	{
		validarAtributos(negocio);
		negocioRegistradoDAO.crear(negocio);
	}
	
	public void modificarNegocioRegistrado(NegocioRegistrado negocio) throws Exception
	{
		validarAtributos(negocio);
		negocioRegistradoDAO.modificar(negocio);
	}
	
	public void borrarNegocioRegistrado(NegocioRegistrado negocio) throws Exception
	{
		negocioRegistradoDAO.borrar(negocio);
	}
	
	public NegocioRegistrado consultarNegocioRegistrado(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return negocioRegistradoDAO.consultarPorId(id);
	}
	
	public List<NegocioRegistrado> consultarTodos() throws Exception 
	{
		return negocioRegistradoDAO.consultarTodos();
	}
	
	public boolean estaRegistradoNegocioConUsuario(Usuario usuario, Negocio negocio) throws Exception
	{
		return negocioRegistradoDAO.estaRegistradoNegocioConUsuario(negocio.getRazonSocial(), usuario.getIdentificacion());
	}

}
