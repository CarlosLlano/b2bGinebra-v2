package co.b2bginebra.presentacion;

import java.util.ArrayList;

import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.omnifaces.util.Ajax;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.SelectEvent;

import co.b2bginebra.logica.CategoriaProdLogica;
import co.b2bginebra.logica.NegocioLogica;
import co.b2bginebra.logica.TipoNegocioLogica;
import co.b2bginebra.modelo.CategoriaProd;
import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.modelo.TipoNegocio;
import net.bootsfaces.component.inputText.InputText;



/**
 * representa la vista destinada a mostrar todos los negocios
 * La vista contiene un filtro de busqueda por tipo de negocio
 */

@ManagedBean(name="directorioVista")
@ViewScoped
public class DirectorioVista 
{

	
	private SelectOneMenu somTipoNegocio;
	private SelectOneMenu somCategoriaProd;
	private InputText txtBuscar;
	private List<SelectItem> tipoNegocios;
	private List<SelectItem> categoriasProd;

	//visibles
	private List<Negocio> negocios;
	//todos
	private List<Negocio> todosLosNegocios;

	@EJB
	private TipoNegocioLogica tipoNegocioLogica;
	@EJB
	private CategoriaProdLogica categoriaProdLogica;
	@EJB
	private NegocioLogica negocioLogica;
	
	private Negocio negocioSeleccionado;

	
	public void somTipoNegocioOnChange()
	{
		try 
		{
			long id = Long.parseLong(somTipoNegocio.getValue().toString());
			if(id < 0)
			{
				List<CategoriaProd> categoriasProd = categoriaProdLogica.consultarTodos();
				actualizarCategoriaProd(categoriasProd);
			}
			else
			{
				TipoNegocio tipoNegocio = tipoNegocioLogica.consultarTipoNegocio(id);
				List<CategoriaProd> categoriasProd = categoriaProdLogica.consultarCategoriaProdPorTipoNegocio(tipoNegocio.getIdTipoNegocio());
				actualizarCategoriaProd(categoriasProd);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	
	}
	
	public void actualizarCategoriaProd(List<CategoriaProd> pcategorias)
	{
		categoriasProd = new ArrayList<SelectItem>();
		for (CategoriaProd categoriaProd : pcategorias) 
		{
			SelectItem item = new SelectItem(categoriaProd.getIdCategoria(), categoriaProd.getNombre());
			categoriasProd.add(item);
		}	

	}
	
	public void buscarAction()
	{
		try 
		{
			long idTipoNegocio = Long.parseLong(somTipoNegocio.getValue().toString());
			long idCategoriaProd = Long.parseLong(somCategoriaProd.getValue().toString());
			String nombre = txtBuscar.getValue().toString().trim();
			
			negocios = negocioLogica.consultarPorTipoCategoriaYNombre(idTipoNegocio, idCategoriaProd, nombre);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void limpiarAction()
	{
		somTipoNegocio.setValue("-1");
		somCategoriaProd.setValue("-1");
		txtBuscar.resetValue();
		try 
		{
			negocios = negocioLogica.consultarNegociosPorEstado("Activo");
		} 
		catch (Exception e) 
		{	
			e.printStackTrace();
		}
	}

	public String getImage(Long idNegocio) 
	{
		try 
		{
			Negocio negocio = negocioLogica.consultarNegocio(Long.valueOf(idNegocio));
			String encoded = Base64.getEncoder().encodeToString(negocio.getImgPrincipal());
			String ruta = "data:image/png;base64," + encoded;
			return ruta;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
		
	
	public List<SelectItem> getTipoNegocios() 
	{
		try 
		{
			if(tipoNegocios==null)
			{
				List<TipoNegocio> ptiposNegocios = tipoNegocioLogica.consultarTodos();
				tipoNegocios = new ArrayList<SelectItem>();
				for (TipoNegocio tipoNegocio : ptiposNegocios) 
				{
					SelectItem item = new SelectItem(tipoNegocio.getIdTipoNegocio(), tipoNegocio.getNombre());
					tipoNegocios.add(item);
				}	
			}
		} 
		catch (Exception e) 
		{
			//handle error
		}
		return tipoNegocios;
	}
	
	public void setTipoNegocios(List<SelectItem> tipoNegocios) {
		this.tipoNegocios = tipoNegocios;
	}
	
	public List<Negocio> getNegocios() 
	{
		try 
		{
			if(negocios==null)
			{
				negocios = negocioLogica.consultarNegociosPorEstado("Activo");
				todosLosNegocios = new ArrayList<>();
				todosLosNegocios.addAll(negocios);
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
	
	public void seleccionarNegocio(SelectEvent event)
	{
		negocioSeleccionado = (Negocio)event.getObject();
	}
	
	public Negocio getNegocioSeleccionado() {
		return negocioSeleccionado;
	}


	public void setNegocioSeleccionado(Negocio negocioSeleccionado) {
		this.negocioSeleccionado = negocioSeleccionado;
	}


	public SelectOneMenu getSomTipoNegocio() {
		return somTipoNegocio;
	}


	public void setSomTipoNegocio(SelectOneMenu somTipoNegocio) {
		this.somTipoNegocio = somTipoNegocio;
	}


	public List<SelectItem> getCategoriasProd() 
	{
		try 
		{
			if(categoriasProd==null)
			{
				List<CategoriaProd> pcategoriasProd = categoriaProdLogica.consultarTodos();
				categoriasProd = new ArrayList<SelectItem>();
				for (CategoriaProd categoriaProd : pcategoriasProd) 
				{
					SelectItem item = new SelectItem(categoriaProd.getIdCategoria(), categoriaProd.getNombre());
					categoriasProd.add(item);
				}	
			}
		} 
		catch (Exception e) 
		{
			//handle error
		}
		return categoriasProd;
	}


	public void setCategoriasProd(List<SelectItem> categoriasProd) {
		this.categoriasProd = categoriasProd;
	}


	public SelectOneMenu getSomCategoriaProd() {
		return somCategoriaProd;
	}


	public void setSomCategoriaProd(SelectOneMenu somCategoriaProd) {
		this.somCategoriaProd = somCategoriaProd;
	}

	public InputText getTxtBuscar() 
	{
		return txtBuscar;
	}

	public void setTxtBuscar(InputText txtBuscar) {
		this.txtBuscar = txtBuscar;
	}


	public List<String> getAutoCompleteValues()
	{
		getNegocios();
		List<String> values = new ArrayList<>();
		for (Negocio negocio: todosLosNegocios) {
			values.add(negocio.getRazonSocial());
		}
		return values;
	}

	

	
	
	
	
	




}
