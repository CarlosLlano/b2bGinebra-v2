package co.b2bginebra.dao;




import co.b2bginebra.dao.api.JpaDaoImpl;

import co.b2bginebra.modelo.ParametroSistema;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ParametroSistemaDAO extends JpaDaoImpl<ParametroSistema, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public ParametroSistemaDAO()
    {
    		super(ParametroSistema.class);
    }
    
    public ParametroSistema consultarParametroPorNombre(String nombre)
    {
    		
    		String jpql = "SELECT p FROM ParametroSistema p WHERE p.nombre=:nombre";
		return entityManager.createQuery(jpql, ParametroSistema.class).setParameter("nombre", nombre).getSingleResult();
    }
}
