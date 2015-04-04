package it.margonar.operaprima.pagelle.repository;

import it.margonar.operaprima.pagelle.model.Esame;
import it.margonar.operaprima.pagelle.model.PagellaAltro;
import it.margonar.operaprima.pagelle.model.PagellaCoro;
import it.margonar.operaprima.pagelle.model.PagellaFormazione;
import it.margonar.operaprima.pagelle.model.PagellaLaboratorio;
import it.margonar.operaprima.pagelle.model.PagellaOrchestra;
import it.margonar.operaprima.pagelle.model.PagellaStrumento;
import it.margonar.operaprima.pagelle.model.Studente;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class StudenteDAO {

	@PersistenceContext
	EntityManager em;
	@Autowired
	PagellaDAO pagellaDAO;

	@Transactional
	public Studente save(Studente s) {
		Studente st = em.merge(s);
		em.flush();
		return st;
	}

	@Transactional(readOnly = true)
	public Studente findById(int id) {
		return em.find(Studente.class, id);
	}

	@Transactional(readOnly = true)
	public List<Studente> findAll() {
		return em.createNamedQuery("Studente.findAll", Studente.class)
				.getResultList();
	}

	@Transactional(readOnly = true)
	public List<Studente> autocomplete(String prefix) {
		return em.createNamedQuery("Studente.autocomplete", Studente.class)
				.setParameter("pref", prefix).getResultList();
	}

	@Transactional
	public void delete(Studente s) {
		PagellaStrumento ps = pagellaDAO.findByStudente(s,
				PagellaStrumento.class);
		if (ps != null) pagellaDAO.delete(ps);

		PagellaFormazione pf = pagellaDAO.findByStudente(s,
				PagellaFormazione.class);
		if (pf != null) pagellaDAO.delete(pf);

		PagellaAltro pa = pagellaDAO.findByStudente(s, PagellaAltro.class);
		if (pa != null) pagellaDAO.delete(pa);

		PagellaLaboratorio pl = pagellaDAO.findByStudente(s,
				PagellaLaboratorio.class);
		if (pl != null) pagellaDAO.delete(pl);

		PagellaOrchestra po = pagellaDAO.findByStudente(s,
				PagellaOrchestra.class);
		if (po != null) pagellaDAO.delete(po);

		PagellaCoro pc = pagellaDAO.findByStudente(s, PagellaCoro.class);
		if (pc != null) pagellaDAO.delete(pc);

		Esame e = pagellaDAO.findByStudente(s, Esame.class);
		if (e != null) pagellaDAO.delete(e);

		Studente toRemove = em.merge(s);
		em.remove(toRemove);
		em.flush();
	}
}
