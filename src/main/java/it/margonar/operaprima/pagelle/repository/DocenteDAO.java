package it.margonar.operaprima.pagelle.repository;

import it.margonar.operaprima.pagelle.model.Docente;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DocenteDAO {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public Docente save(Docente s) {
		Docente st = em.merge(s);
		em.flush();
		return st;
	}

	@Transactional(readOnly = true)
	public Docente findById(int id) {
		return em.find(Docente.class, id);
	}

	@Transactional(readOnly = true)
	public List<Docente> findAll() {
		return em.createNamedQuery("Docente.findAll", Docente.class)
				.getResultList();
	}

	@Transactional(readOnly = true)
	public List<Docente> autocomplete(String prefix) {
		return em.createNamedQuery("Docente.autocomplete", Docente.class)
				.setParameter("pref", prefix).getResultList();
	}

	@Transactional(readOnly = true)
	public List<Docente> findByCognome(String cognome) {
		return em.createQuery(
				"from Docente where cognome LIKE '" + cognome
						+ "%' order by cognome asc", Docente.class)
				.getResultList();
	}
}