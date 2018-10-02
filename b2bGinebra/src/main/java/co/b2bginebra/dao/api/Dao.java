package co.b2bginebra.dao.api;

import java.io.Serializable;


import java.util.List;


public interface Dao<T, PK extends Serializable> {
    
	void crear(T newEntity);

    T consultarPorId(PK id);

    void modificar(T entity);

    void borrar(T entity);

    List<T> consultarTodos();

}
