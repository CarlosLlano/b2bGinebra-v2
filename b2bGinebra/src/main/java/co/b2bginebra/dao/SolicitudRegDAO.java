package co.b2bginebra.dao;




import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.b2bginebra.dao.api.JpaDaoImpl;
import co.b2bginebra.modelo.SolicitudReg;


@Stateless
public class SolicitudRegDAO extends JpaDaoImpl<SolicitudReg, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public SolicitudRegDAO()
	{
		super(SolicitudReg.class);
	}
    
    public List<SolicitudReg> consultarSolicitudPorEstado(long idEstado)
    {
    		String jpql = "SELECT s FROM SolicitudReg s WHERE s.estado.idEstado=:idEstado";
    		return entityManager.createQuery(jpql, SolicitudReg.class).setParameter("idEstado", idEstado).getResultList();
  
    }
    
    public List<SolicitudReg> consultarSolicitudesPorNombreEstado(String nombreEstado)
	{
    		String jpql = "SELECT s FROM SolicitudReg s WHERE s.estado.nombre=:nombreEstado";
		return entityManager.createQuery(jpql, SolicitudReg.class).setParameter("nombreEstado", nombreEstado).getResultList();
	}
}
