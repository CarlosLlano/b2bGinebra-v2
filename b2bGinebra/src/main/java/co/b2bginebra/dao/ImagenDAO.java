package co.b2bginebra.dao;


import java.util.List;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.b2bginebra.dao.api.JpaDaoImpl;
import co.b2bginebra.modelo.Imagen;


@Stateless
public class ImagenDAO extends JpaDaoImpl<Imagen, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public ImagenDAO()
	{
		super(Imagen.class);
	}
    
    public List<Imagen> consultarImagenesPorNegocio(Long idNegocio)
    {
    	
    		String jpql = "SELECT i FROM Imagen i WHERE i.negocio.idNegocio=:idNegocio";
		return entityManager.createQuery(jpql, Imagen.class).setParameter("idNegocio", idNegocio).getResultList();
    }
    
}
