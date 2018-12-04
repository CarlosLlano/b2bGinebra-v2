package co.b2bginebra.presentacion;

import co.b2bginebra.logica.NegocioRegistradoLogica;
import co.b2bginebra.modelo.NegocioRegistrado;
import net.bootsfaces.utils.FacesMessages;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import java.util.List;

@ManagedBean(name="negocioRegistradoVista")
@ViewScoped
public class NegocioRegistradoVista {


    @EJB
    private NegocioRegistradoLogica negocioRegistradoLogica;

    private List<NegocioRegistrado> negociosRegistrados;
    private Part file;


    public void cargarNegociosRegistrados(){
        try {
            List<NegocioRegistrado> negociosCargados = negocioRegistradoLogica.cargarArchivo(file);
            negocioRegistradoLogica.validarAtributos(negociosCargados);
            negocioRegistradoLogica.borrarTodosNegocioRegistrado();
            for(NegocioRegistrado negocioRegistrado : negociosCargados){
                negocioRegistradoLogica.crearNegocioRegistrado(negocioRegistrado);
            }
            negociosRegistrados = negociosCargados;
            mostrarMensaje("Archivo cargado exitosamente");
        } catch (Exception e) {
            mostrarMensaje(e.getMessage());
        }
    }

    public void mostrarMensaje(String mensaje)
    {
        FacesMessages.info(mensaje);
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<NegocioRegistrado> getNegociosRegistrados() {
        if(negociosRegistrados == null){
            negociosRegistrados =  negocioRegistradoLogica.consultarTodos();
        }
        return negociosRegistrados;
    }
}
