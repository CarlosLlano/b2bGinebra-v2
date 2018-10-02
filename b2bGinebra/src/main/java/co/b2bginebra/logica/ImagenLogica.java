package co.b2bginebra.logica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.b2bginebra.dao.ImagenDAO;
import co.b2bginebra.modelo.Imagen;

@Stateless
public class ImagenLogica
{
	
	@EJB
	private ImagenDAO imagenDAO;
	
	
	public void validarAtributos(Imagen imagen) throws Exception
	{
		if(imagen==null)
		{
			throw new Exception("la imagen no puede ser nula");
		}
		if(imagen.getImagen()==null)
		{
			throw new Exception("La imagen no puede ser nula");
		}
	}
	
	public void crearImagen(Imagen imagen) throws Exception
	{
		validarAtributos(imagen);
		imagenDAO.crear(imagen);
	}
	
	public void modificarImagen(Imagen imagen) throws Exception
	{
		validarAtributos(imagen);
		imagenDAO.modificar(imagen);
	}
	
	public void borrarImagen(Imagen imagen) throws Exception
	{
		imagenDAO.borrar(imagen);
	}
	
	public Imagen consultarImagen(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return imagenDAO.consultarPorId(id);
	}
	
	public List<Imagen> consultarTodos() throws Exception 
	{
		return imagenDAO.consultarTodos();
	}
	
	public List<Imagen> consultarImagenesPorNegocio(Long idNegocio)
    {
		return imagenDAO.consultarImagenesPorNegocio(idNegocio);
    }

}
