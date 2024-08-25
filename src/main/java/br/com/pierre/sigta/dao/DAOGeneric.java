package br.com.pierre.sigta.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;




public class DAOGeneric<T> {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sigta");
	
	public T salvar(T entidade) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(entidade);
		tx.commit();
		em.close();
		return entidade;
	}

	public T atualizar(T entidade) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(entidade);
		tx.commit();
		em.close();
		return entidade;
	}

	public void deletar(T entidade) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(entidade);
		tx.commit();
		em.close();
	}

	@SuppressWarnings("unchecked")
	public List<T> listar(Class<T> entidade) {
		EntityManager em = emf.createEntityManager();
		List<T> lista = em.createQuery("from " + entidade.getName()).getResultList();
		em.close();
        return lista;
    }
	
	public T buscarPorId(Class<T> entidade, Long id) {
		EntityManager em = emf.createEntityManager();
		T obj = em.find(entidade, id);
		em.close();
		return obj;
	}
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}


}
