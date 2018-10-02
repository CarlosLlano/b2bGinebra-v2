package co.b2bginebra.presentacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import co.b2bginebra.logica.NegocioLogica;
import co.b2bginebra.logica.NegocioRegistradoLogica;
import co.b2bginebra.logica.SolicitudRegLogica;
import co.b2bginebra.logica.TipoNegocioLogica;
import co.b2bginebra.logica.UsuarioLogica;
import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.presentacion.api.PointAmountStat;

/**
 * representa la vista destinada a mostrar estadisticas al administrador
 * las estadisticas son:
 * 	1.porcentaje de negocios REGISTRADOS EN LA PLATAFORMA por cada tipo de negocio
 * 		ej: 30% hoteles y restaurantes, 20% transporte, etc
 * 	2.porcentaje de negocios REGISTRADOS EN LA BASE DE DATOS de la alcaldia por cada tipo de negocio
 * El objetivo es ayudar a determinar el uso de la plataforma y tambien tener una idea general de la distribucion economica del municipio
 */
@ManagedBean(name="dashboardVista")
@ViewScoped
public class DashboardVista 
{

	private List<Negocio> negocios;
	
	
	@EJB
	private TipoNegocioLogica tipoNegocioLogica;
	@EJB
	private NegocioLogica negocioLogica;
	@EJB
	private NegocioRegistradoLogica negocioRegistradoLogica;
	@EJB
	private UsuarioLogica usuarioLogica;
	@EJB
	private SolicitudRegLogica solicitudRegLogica;
		
	
	public List<Negocio> getNegocios() 
	{
		try 
		{
			if(negocios==null)
			{
				negocios = negocioLogica.consultarTodos();
				
			}
		} 
		catch (Exception e) 
		{
			//handle error
		}
		return negocios;

	}
	public void setNegocios(List<Negocio> negocios) {
		this.negocios = negocios;
	}

	
	public List<PointAmountStat> getEstadisticaNegocios()
	{
		List<PointAmountStat> pointAmountStats = null;
		try 
		{
			pointAmountStats = new ArrayList<PointAmountStat>();
			int cantidadActual = negocioLogica.consultarTodos().size();
			int cantidadRegistrado = negocioRegistradoLogica.consultarTodos().size();
			
			pointAmountStats.add(new PointAmountStat("Actuales", cantidadActual));
			pointAmountStats.add(new PointAmountStat("Existentes", cantidadRegistrado));
			
			
		}
		catch (Exception e) 
		{
			return null;
			
		}
		return pointAmountStats;
	}
	
	public List<PointAmountStat> getEstadisticaTipoNegocio() 
	{
		List<PointAmountStat> pointAmountStats = new ArrayList<PointAmountStat>();
		
		try
		{
			List<PointAmountStat> temp = new ArrayList<PointAmountStat>();
			List<Negocio> negocios = negocioLogica.consultarTodos();
			
			HashMap<String, Integer> hasmap = new HashMap<String, Integer>(100);
			for (Negocio neg : negocios) 
			{
				String tipoNombre = neg.getTipoNegocio().getNombre();
				int cantidad = hasmap.get(tipoNombre)==null? 0 : hasmap.get(tipoNombre);
				hasmap.put(tipoNombre, cantidad + 1);
				
			}
			
			hasmap.forEach((k,v) -> temp.add(new PointAmountStat(k, v)));
			Collections.sort(temp);
			
			for(int i = 0; i < 5; i++)
			{
				pointAmountStats.add(temp.get(i));
			}
		}
		catch(Exception e)
		{
			
		}
		
		return pointAmountStats;
	}
	
	public List<PointAmountStat> getEstadisticaUsuarios()
	{
		List<PointAmountStat> pointAmountStats = new ArrayList<PointAmountStat>();
		try 
		{
			pointAmountStats.add(new PointAmountStat("Registrados", usuarioLogica.consultarTodos().size()));
			pointAmountStats.add(new PointAmountStat("Activos", usuarioLogica.consultarUsuariosPorEstado("Activo").size()));
			pointAmountStats.add(new PointAmountStat("Inactivos", usuarioLogica.consultarUsuariosPorEstado("Inactivo").size()));
			pointAmountStats.add(new PointAmountStat("Bloqueados", usuarioLogica.consultarUsuariosPorEstado("Bloqueado").size()));
		}
		catch (Exception e) 
		{
			
			
		}
		return pointAmountStats;
	}
	
	public List<PointAmountStat> getEstadisticaSolicitudes()
	{
		List<PointAmountStat> pointAmountStats = new ArrayList<PointAmountStat>();
		try 
		{
			pointAmountStats.add(new PointAmountStat("Todas", solicitudRegLogica.consultarTodos().size()));
			pointAmountStats.add(new PointAmountStat("Enviada", solicitudRegLogica.consultarSolicitudesPorNombreEstado("Enviada").size()));
			pointAmountStats.add(new PointAmountStat("Aceptada", solicitudRegLogica.consultarSolicitudesPorNombreEstado("Aceptada").size()));
			pointAmountStats.add(new PointAmountStat("Rechazada", solicitudRegLogica.consultarSolicitudesPorNombreEstado("Rechazada").size()));
		}
		catch (Exception e) 
		{
			return null;
			
		}
		return pointAmountStats;
	}
	
	
	

	



}


