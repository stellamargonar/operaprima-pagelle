package it.margonar.operaprima.pagelle.repository;

import it.margonar.operaprima.pagelle.model.Laboratorio;
import it.margonar.operaprima.pagelle.model.Orchestra;
import it.margonar.operaprima.pagelle.model.Strumento;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CorsiDAO {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public <T> T save(T l) {
		T st = em.merge(l);
		em.flush();
		return st;
	}

	@Transactional(readOnly = true)
	public <T> T findById(int id, Class<T> c) {
		if (c.equals(Laboratorio.class) || c.equals(Orchestra.class)
				|| c.equals(Strumento.class)) return em.find(c, id);
		else return null;
	}

	@Transactional(readOnly = true)
	public <T> List<T> findAll(Class<T> c) {
		if (c.equals(Laboratorio.class) || c.equals(Orchestra.class)
				|| c.equals(Strumento.class)) return em.createQuery(
				"select c from " + c.getSimpleName() + " c", c).getResultList();
		else return null;
	}

	@Transactional(readOnly = true)
	public <T> List<T> findByName(String nome, Class<T> c) {
		if (c.equals(Laboratorio.class) || c.equals(Orchestra.class)
				|| c.equals(Strumento.class)) return em
				.createQuery(
						"select c from " + c.getSimpleName()
								+ " c where nome = :nome", c)
				.setParameter("nome", nome).getResultList();
		else return null;
	}
}
