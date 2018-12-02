package co.b2bginebra.logica;

import co.b2bginebra.dao.NegocioRegistradoDAO;
import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.modelo.NegocioRegistrado;
import co.b2bginebra.modelo.Usuario;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
			throw new Exception("El negocio registrado debe especificar la razón social");
		}
        if(negocio.getDireccion()==null || negocio.getDireccion().equals(""))
        {
            throw new Exception("El negocio registrado debe especificar la dirección");
        }
        if(negocio.getNombreRepr()==null || negocio.getNombreRepr().equals(""))
        {
            throw new Exception("El negocio registrado debe especificar el nombre del representante legal");
        }
	}

	public void validarAtributos(List<NegocioRegistrado> negocioRegistrados) throws Exception{
	    for (int i = 0; i < negocioRegistrados.size(); i++){
            try {
                NegocioRegistrado negocioRegistrado = negocioRegistrados.get(i);
                validarAtributos(negocioRegistrado);
            } catch (Exception e) {
                String mensaje = "El negocio número %s del archivo no cumple con el formato por la siguiente razón: %s";
                throw new Exception(String.format(mensaje, i+1, e.getMessage()));
            }
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

	public void borrarTodosNegocioRegistrado(){
		negocioRegistradoDAO.borrarTodos();
	}
	
	public NegocioRegistrado consultarNegocioRegistrado(Long id) throws Exception
	{
		if(id==0)
		{
			throw new Exception("el id es obligatorio");
		}
		return negocioRegistradoDAO.consultarPorId(id);
	}
	
	public List<NegocioRegistrado> consultarTodos()
	{
		return negocioRegistradoDAO.consultarTodos();
	}
	
	public boolean estaRegistradoNegocioConUsuario(Usuario usuario, Negocio negocio) throws Exception
	{
		return negocioRegistradoDAO.estaRegistradoNegocioConUsuario(negocio.getRazonSocial(), usuario.getIdentificacion());
	}

	public List<NegocioRegistrado> cargarArchivo(Part file) throws Exception{
		List<NegocioRegistrado> negociosRegistrados = new ArrayList<>();
		try {
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet){
			    if(row.getRowNum() > 0){
                    NegocioRegistrado negocioRegistrado = new NegocioRegistrado();
                    for(Cell cell : row){
                        if(cell.getColumnIndex() == 1){
                            negocioRegistrado.setRazonSocial(leerCelda(cell));
                        }
                        if(cell.getColumnIndex() == 2){
                            negocioRegistrado.setDireccion(leerCelda(cell));
                        }
                        if(cell.getColumnIndex() == 3){
                            negocioRegistrado.setDocRepr(leerCelda(cell));
                        }
                        if(cell.getColumnIndex() == 4){
                            negocioRegistrado.setNombreRepr(leerCelda(cell));
                        }
                    }
                    if(Stream.of(negocioRegistrado.getRazonSocial(),
                                negocioRegistrado.getDireccion(),
                                negocioRegistrado.getDocRepr(),
                                negocioRegistrado.getNombreRepr()).allMatch(Objects::nonNull)){
                        negociosRegistrados.add(negocioRegistrado);
                    }
                }
			}
			return negociosRegistrados;
		} catch (Exception e) {
			throw new Exception("Ocurrió un error al cargar el archivo. Recuerde que debe ser un excel (.xls, .xlsx)");
		}
	}


	private String leerCelda(Cell cell){
	    String valor = "";
	    switch (cell.getCellTypeEnum()){
            case STRING:
                valor = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                valor = (int) cell.getNumericCellValue() + "";
                break;
        }
        return valor;
    }

}
