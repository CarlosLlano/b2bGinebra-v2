package co.b2bginebra.dao;

import co.b2bginebra.modelo.CategoriaProd;
import co.b2bginebra.dao.api.JpaDaoImpl;


import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class CategoriaProdDAO extends JpaDaoImpl<CategoriaProd, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    public CategoriaProdDAO()
    {
    		super(CategoriaProd.class);
    }

    public List<CategoriaProd> consultarCategoriaProdPorTipoNegocio(long idTipoNegocio) {
        String jpql = "SELECT c FROM CategoriaProd c WHERE c.tipoNegocio.idTipoNegocio=:idTipoNegocio";
        return entityManager.createQuery(jpql, CategoriaProd.class).setParameter("idTipoNegocio", idTipoNegocio).getResultList();
    }
}
