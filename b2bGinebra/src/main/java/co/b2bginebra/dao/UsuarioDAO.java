package co.b2bginebra.dao;




import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.b2bginebra.dao.api.JpaDaoImpl;
import co.b2bginebra.modelo.Usuario;


@Stateless
public class UsuarioDAO extends JpaDaoImpl<Usuario, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    
    public UsuarioDAO()
   	{
   		super(Usuario.class);
   	}
    
    public Usuario consultarUsuarioPorIdentificacion(String identificacion)
    {
    		String jpql = "SELECT usu FROM Usuario usu WHERE usu.identificacion=:identificacion";
    		List<Usuario> usuarios = entityManager.createQuery(jpql, Usuario.class).setParameter("identificacion", identificacion).getResultList();
    		if(usuarios.isEmpty())
    		{
    			return null;
    		}
    		else
    		{
    			return usuarios.get(0);
    		}
		
    }
    
    public Usuario consultarUsuarioPorCorreo(String correo)
    {
    		
		String jpql = "SELECT usu FROM Usuario usu WHERE usu.correo=:correo";
    		List<Usuario> usuarios = entityManager.createQuery(jpql, Usuario.class).setParameter("correo", correo).getResultList();
    		if(usuarios.isEmpty())
    		{
    			return null;
    		}
    		else
    		{
    			return usuarios.get(0);
    		}
    }
    
    public List<Usuario> consultarUsuariosPorEstado(String nombreEstado)
	{
    		String jpql = "SELECT usu FROM Usuario usu WHERE usu.estado.nombre=:nombreEstado";
		List<Usuario> usuarios = entityManager.createQuery(jpql, Usuario.class).setParameter("nombreEstado", nombreEstado).getResultList();
		return usuarios;
	}
    
	public List<Usuario> consultarUsuariosPorIdEstado(long idEstado)
	{
		String jpql = "SELECT usu FROM Usuario usu WHERE usu.estado.idEstado=:idEstado";
		List<Usuario> usuarios = entityManager.createQuery(jpql, Usuario.class).setParameter("idEstado", idEstado).getResultList();
		return usuarios;
	}
}
