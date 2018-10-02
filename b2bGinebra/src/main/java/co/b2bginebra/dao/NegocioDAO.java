package co.b2bginebra.dao;


import co.b2bginebra.modelo.Negocio;
import co.b2bginebra.dao.api.JpaDaoImpl;


import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class NegocioDAO extends JpaDaoImpl<Negocio, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public NegocioDAO()
	{
		super(Negocio.class);
	}
    
}
