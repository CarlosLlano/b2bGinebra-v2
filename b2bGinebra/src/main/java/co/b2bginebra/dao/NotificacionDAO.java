package co.b2bginebra.dao;



import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.b2bginebra.dao.api.JpaDaoImpl;
import co.b2bginebra.modelo.Notificacion;


@Stateless
public class NotificacionDAO extends JpaDaoImpl<Notificacion, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public NotificacionDAO()
	{
		super(Notificacion.class);
	}
    
    public List<Notificacion> consultarNotificacionPorNegocio(Long idNegocio)
    {
    	
    		String jpql = "SELECT n FROM Notificacion n WHERE n.negocio.idNegocio=:idNegocio";
		return entityManager.createQuery(jpql, Notificacion.class).setParameter("idNegocio", idNegocio).getResultList();
    }
    
}
