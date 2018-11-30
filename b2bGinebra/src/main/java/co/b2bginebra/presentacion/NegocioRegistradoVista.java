package co.b2bginebra.presentacion;

import co.b2bginebra.logica.NegocioRegistradoLogica;
import co.b2bginebra.modelo.NegocioRegistrado;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import java.util.List;

@ManagedBean(name="negocioRegistradoVista")
@ViewScoped
public class NegocioRegistradoVista {

    private Part file;
    private List<NegocioRegistrado> negociosRegistrados;

    @EJB
    private NegocioRegistradoLogica negocioRegistradoLogica;


    @PostConstruct
    public void init(){
        negociosRegistrados = negocioRegistradoLogica.consultarTodos();
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<NegocioRegistrado> getNegociosRegistrados() {
        return negociosRegistrados;
    }
}
