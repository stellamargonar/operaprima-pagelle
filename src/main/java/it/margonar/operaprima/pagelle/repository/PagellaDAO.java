package it.margonar.operaprima.pagelle.repository;

import it.margonar.operaprima.pagelle.model.Docente;
import it.margonar.operaprima.pagelle.model.Studente;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PagellaDAO {

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
		if (condition(c)) return em.find(c, id);
		else return null;
	}

	@Transactional(readOnly = true)
	public <T> List<T> findAll(Class<T> c) {
		if (condition(c)) return em.createQuery(
				"select p from " + c.getSimpleName() + " p", c).getResultList();
		else return null;
	}

	@Transactional(readOnly = true)
	public <T> T findByStudente(Studente studente, Class<T> c) {
		if (condition(c)) {
			List<T> list = em
					.createQuery(
							"select p from " + c.getSimpleName()
									+ " p where studente = :studente", c)
					.setParameter("studente", studente).getResultList();
			if (list.isEmpty()) return null;
			else return list.get(0);
		} else return null;
	}

	@Transactional(readOnly = true)
	public <T> List<T> findByDocente(Docente docente, Class<T> c) {
		if (condition(c)) return em
				.createQuery(
						"select p from " + c.getSimpleName()
								+ " p where docente = :docente", c)
				.setParameter("docente", docente).getResultList();
		else return null;
	}

	@Transactional
	public <T> void delete(T pagella) {
		T instance = em.merge(pagella);
		em.remove(instance);
		em.flush();
	}

	private boolean condition(Class c) {
		return (c.getSimpleName().startsWith("Pagella") || c.getSimpleName()
				.startsWith("Esame"));
	}
}
