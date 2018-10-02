package co.b2bginebra.dao;




import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.b2bginebra.dao.api.JpaDaoImpl;
import co.b2bginebra.modelo.TipoNegocio;


@Stateless
public class TipoNegocioDAO extends JpaDaoImpl<TipoNegocio, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public TipoNegocioDAO()
   	{
   		super(TipoNegocio.class);
   	}
    
}
