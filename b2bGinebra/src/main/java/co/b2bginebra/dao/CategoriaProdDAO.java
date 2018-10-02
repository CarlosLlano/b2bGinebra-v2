package co.b2bginebra.dao;

import co.b2bginebra.modelo.CategoriaProd;
import co.b2bginebra.dao.api.JpaDaoImpl;


import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class CategoriaProdDAO extends JpaDaoImpl<CategoriaProd, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public CategoriaProdDAO()
    {
    		super(CategoriaProd.class);
    }
}
