package co.b2bginebra.dao;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.b2bginebra.dao.api.JpaDaoImpl;
import co.b2bginebra.modelo.NegocioRegistrado;


@Stateless
public class NegocioRegistradoDAO extends JpaDaoImpl<NegocioRegistrado, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public NegocioRegistradoDAO()
	{
		super(NegocioRegistrado.class);
	}
    
    public boolean estaRegistradoNegocioConUsuario(String razonSocial, String docRepr)
    {
    		boolean respuesta;
    		String jpql = "SELECT n FROM NegocioRegistrado n WHERE n.razonSocial=:razonSocial AND n.docRepr=:docRepr" ;
		List<NegocioRegistrado> negReg =  entityManager.createQuery(jpql, NegocioRegistrado.class).setParameter("razonSocial", razonSocial)
				.setParameter("docRepr", docRepr).getResultList();
		
		if(negReg.isEmpty() == true)
		{
			respuesta = false;  //La alcaldia no tiene constancia de la existencia de dicho negocio
		}
		else
		{
			respuesta = true; //La alcaldia si tiene constancia de la existencia de dicho negocio
		}
		
		return respuesta;
    }

    public void borrarTodos(){
		String jpql = "DELETE FROM NegocioRegistrado";
    	entityManager.createQuery(jpql).executeUpdate();
	}
}
