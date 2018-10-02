package co.b2bginebra.dao.api;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public class JpaDaoImpl<T, PK extends Serializable> implements Dao<T, PK> {

	private Class<T> entityClass;

	@PersistenceContext
	private EntityManager entityManager;


	public JpaDaoImpl(Class<T> entityClass) 
	{
		this.entityClass = entityClass;

	}


	@Override
	public void crear(T newEntity) {
		entityManager.persist(newEntity);

	}


	@Override
	public T consultarPorId(PK id) {

		return (T) entityManager.find(entityClass, id);
	}


	@Override
	public void modificar(T entity) {
		entityManager.merge(entity);

	}


	@Override
	public void borrar(T entity) {
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));

	}


	@Override
	public List<T> consultarTodos() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> root = cq.from(entityClass);

		return entityManager.createQuery(cq.select(root)).getResultList();
	}
}
