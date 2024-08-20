package br.com.pierre.sigta.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import br.com.pierre.sigta.util.HibernateUtil;



public class DAOGeneric<T> {
	private static EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();

	public T salvar(T entidade) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(entidade);
		tx.commit();
		return entidade;
	}

	public T atualizar(T entidade) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(entidade);
		tx.commit();
		return entidade;
	}

	public void deletar(T entidade) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(entidade);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	public List<T> listar(Class<T> entidade) {
        return em.createQuery("from " + entidade.getName()).getResultList();
    }
	
	public T buscarPorId(Class<T> entidade, Long id) {
		return em.find(entidade, id);
	}
	
	public EntityManager getEntityManager() {
		return em;
	}

	public void closeEntityManager() {
		em.close();
	}
}
