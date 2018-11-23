package co.b2bginebra.dao;




import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.b2bginebra.dao.api.JpaDaoImpl;
import co.b2bginebra.modelo.Oferta;

import java.util.List;


@Stateless
public class OfertaDAO extends JpaDaoImpl<Oferta, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public OfertaDAO()
	{
		super(Oferta.class);
	}

    public List<Oferta> consultarOfertasPorNegocio(Long idNegocio) {
        String jpql = "SELECT o FROM Oferta o WHERE o.negocio.idNegocio=:idNegocio";
        return entityManager.createQuery(jpql, Oferta.class).setParameter("idNegocio", idNegocio).getResultList();
    }
}
