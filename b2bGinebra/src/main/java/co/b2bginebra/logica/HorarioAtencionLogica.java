package co.b2bginebra.logica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.b2bginebra.dao.HorarioAtencionDAO;
import co.b2bginebra.modelo.HorarioAtencion;

@Stateless
public class HorarioAtencionLogica
{
	
	@EJB
	private HorarioAtencionDAO horarioAtencionDAO;
	
	
	public void validarAtributos(HorarioAtencion horarioAtencion) throws Exception
	{
		if(horarioAtencion==null)
		{
			throw new Exception("el horario no puede ser nulo");
		}
		if(horarioAtencion.getDia()==null || horarioAtencion.getDia().equals(""))
		{
			throw new Exception("el dia es obligatorio");
		}
		if(horarioAtencion.getFechaInicio()==null || horarioAtencion.getFechaInicio().equals(""))
		{
			throw new Exception("la hora de inicio es obligatoria");
		}
		if(horarioAtencion.getFechaFin()==null || horarioAtencion.getFechaFin().equals(""))
		{
			throw new Exception("la hora de finalizacion es obligatoria");
		}
	}
	
	public void crearHorarioAtencion(HorarioAtencion horarioAtencion) throws Exception
	{
		validarAtributos(horarioAtencion);
		horarioAtencionDAO.crear(horarioAtencion);
		
	}
	
	public void modificarHorarioAtencion(HorarioAtencion horarioAtencion) throws Exception
	{
		validarAtributos(horarioAtencion);
		horarioAtencionDAO.modificar(horarioAtencion);
	}
	
	public void borrarHorarioAtencion(HorarioAtencion horarioAtencion) throws Exception
	{
		horarioAtencionDAO.borrar(horarioAtencion);
	}
	
	public HorarioAtencion consultarHorarioAtencion(Long id) throws Exception
	{

		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return horarioAtencionDAO.consultarPorId(id);
	}
	
	public List<HorarioAtencion> consultarTodos() throws Exception 
	{
		return horarioAtencionDAO.consultarTodos();
	}
	
	public List<HorarioAtencion> consultarHorariosPorNegocio(Long idNegocio)
    {
		return horarioAtencionDAO.consultarHorariosPorNegocio(idNegocio);
    }

}
