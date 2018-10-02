package co.b2bginebra.logica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;



import co.b2bginebra.dao.OfertaDAO;
import co.b2bginebra.modelo.Oferta;

@Stateless
public class OfertaLogica
{
	
	@EJB
	private OfertaDAO ofertaDAO;
	
	
	public void validarAtributos(Oferta oferta) throws Exception
	{
		if(oferta == null)
		{
			throw new Exception("La oferta no puede ser nula");
		}
		if(oferta.getDescripcion()==null || oferta.getDescripcion().equals(""))
		{
			throw new Exception("La oferta debe tener una descripcion");
		}
		
		
	}
	
	public void crearOferta(Oferta oferta) throws Exception
	{
		validarAtributos(oferta);
		ofertaDAO.crear(oferta);
	}
	
	public void modificarOferta(Oferta oferta) throws Exception
	{
		validarAtributos(oferta);
		ofertaDAO.modificar(oferta);
	}
	
	public void borrarOferta(Oferta oferta) throws Exception
	{
		ofertaDAO.borrar(oferta);
	}
	
	public Oferta consultarOferta(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return ofertaDAO.consultarPorId(id);
	}
	
	public List<Oferta> consultarTodos() throws Exception 
	{
		return ofertaDAO.consultarTodos();
	}

}
