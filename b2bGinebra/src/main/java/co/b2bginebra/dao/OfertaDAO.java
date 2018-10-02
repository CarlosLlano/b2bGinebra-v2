package co.b2bginebra.dao;




import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.b2bginebra.dao.api.JpaDaoImpl;
import co.b2bginebra.modelo.Oferta;


@Stateless
public class OfertaDAO extends JpaDaoImpl<Oferta, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public OfertaDAO()
	{
		super(Oferta.class);
	}
}
