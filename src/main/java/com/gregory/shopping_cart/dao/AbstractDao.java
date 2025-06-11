package com.gregory.shopping_cart.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public interface AbstractDao<T> {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("shoppingcart");
	EntityManager em = emf.createEntityManager();
	
	default void create(T object) {
		em.getTransaction().begin();
		em.persist(object);
		em.getTransaction().commit();
		close();
	};

	default T getById(Class<T> dao ,Long id) {
		T obj = em.find(dao,id);
		close();
		return obj;
	};
	
	public List<T> getAll();
	
	public void update();
	
	default void delete(T object) {
		em.getTransaction().begin();
		em.remove(object);
		em.getTransaction().commit();
		close();
	};
	
	default void close() {
		emf.close();
		em.close();
	};
	
}
