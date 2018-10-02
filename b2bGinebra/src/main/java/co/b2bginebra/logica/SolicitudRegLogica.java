package co.b2bginebra.logica;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.b2bginebra.dao.SolicitudRegDAO;
import co.b2bginebra.modelo.Estado;
import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.modelo.SolicitudReg;
import co.b2bginebra.modelo.Usuario;

@Stateless
public class SolicitudRegLogica
{

	@EJB
	private SolicitudRegDAO solicitudRegDAO;
	@EJB
	private EstadoLogica estadoLogica;
	@EJB
	private ParametroSistemaLogica parametroSistemaLogica;
	@EJB
	private UsuarioLogica usuarioLogica;
	@EJB
	private NegocioLogica negocioLogica;
	@EJB
	private GestionCorreosLogica gestionCorreosLogica;


	public void validarAtributos(SolicitudReg solicitudReg) throws Exception
	{
		if(solicitudReg==null)
		{
			throw new Exception("la solicitud no puede ser nula");
		}
		if(solicitudReg.getDescripcion()==null || solicitudReg.getDescripcion().equals(""))
		{
			throw new Exception("la descripcion de la solicitud es obligatoria");
		}
	}

	public void crearSolicitudReg(SolicitudReg solicitudReg) throws Exception
	{
		validarAtributos(solicitudReg);
		solicitudReg.setFechaCreacion(new Date());
		solicitudRegDAO.crear(solicitudReg);
	}

	public void modificarSolicitudReg(SolicitudReg solicitudReg) throws Exception
	{
		validarAtributos(solicitudReg);
		solicitudRegDAO.modificar(solicitudReg);

	}

	public void borrarSolicitudReg(SolicitudReg solicitudReg) throws Exception
	{
		solicitudRegDAO.borrar(solicitudReg);
	}

	public SolicitudReg consultarSolicitudReg(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return solicitudRegDAO.consultarPorId(id);
	}

	public List<SolicitudReg> consultarTodos() throws Exception 
	{
		return solicitudRegDAO.consultarTodos();
	}
	
	public List<SolicitudReg> consultarSolicitudPorEstado(long idEstado) throws Exception 
	{
		return solicitudRegDAO.consultarSolicitudPorEstado(idEstado);
	}


	public void aceptar(SolicitudReg solicitud) throws Exception
	{
		//se especifia la fecha de atencion
		solicitud.setFechaAtencion(new Date());
		
		//se cambia el estado
		solicitud.setEstado(estadoLogica.consultarEstadoPorNombre("Aceptada"));

		//se le asigna una respuesta generica almacenada en la base de datos 
		String respuesta = parametroSistemaLogica.consultarParametroPorNombre("Mensaje de solicitud aceptada").getValor();
		solicitud.setRespuesta(respuesta);

		//se cambia el estado del usuario y negocio
		Negocio negocio = solicitud.getNegocio();
		Usuario usuario = negocio.getUsuario();
		Estado estadoActivo = estadoLogica.consultarEstadoPorNombre("Activo");

		negocio.setEstado(estadoActivo);
		usuario.setEstado(estadoActivo);

		//guardar cambios en base de datos
		modificarSolicitudReg(solicitud);
		negocioLogica.modificarNegocio(negocio);
		usuarioLogica.modificarUsuario(usuario);

		//enviar correo
		gestionCorreosLogica.enviarCorreoSolicitudAceptada(solicitud);
	}

	/**
	 * IMPORTANTE: 
	 * La respuesta a la solicitud rechazada debe ser especificada por la Alcaldia. 
	 * Es necesario saber especificamente porque una solicitud fue rechazada
	 */
	public void rechazar(SolicitudReg solicitud) throws Exception
	{
		//se especifica la fecha de atencion
		solicitud.setFechaAtencion(new Date());
				
		//se cambia el estado
		solicitud.setEstado(estadoLogica.consultarEstadoPorNombre("Rechazada"));
	
		//se cambia el estado del usuario y negocio
		Negocio negocio = solicitud.getNegocio();
		Usuario usuario = negocio.getUsuario();
		Estado estadoInactivo = estadoLogica.consultarEstadoPorNombre("Inactivo");

		negocio.setEstado(estadoInactivo);
		usuario.setEstado(estadoInactivo);

		//guardar cambios en base de datos
		modificarSolicitudReg(solicitud);
		negocioLogica.modificarNegocio(negocio);
		usuarioLogica.modificarUsuario(usuario);
		
		//enviar correo
		gestionCorreosLogica.enviarCorreoSolicitudRechazada(solicitud);
	}
	
	public List<SolicitudReg> consultarSolicitudesPorNombreEstado(String nombreEstado)
	{
		return solicitudRegDAO.consultarSolicitudesPorNombreEstado(nombreEstado);
	}

}
