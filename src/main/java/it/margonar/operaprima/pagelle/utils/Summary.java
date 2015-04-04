package it.margonar.operaprima.pagelle.utils;

import it.margonar.operaprima.pagelle.model.Esame;
import it.margonar.operaprima.pagelle.model.PagellaAltro;
import it.margonar.operaprima.pagelle.model.PagellaCoro;
import it.margonar.operaprima.pagelle.model.PagellaFormazione;
import it.margonar.operaprima.pagelle.model.PagellaLaboratorio;
import it.margonar.operaprima.pagelle.model.PagellaOrchestra;
import it.margonar.operaprima.pagelle.model.PagellaStrumento;
import it.margonar.operaprima.pagelle.model.Studente;

public class Summary {

	private Studente s;
	private PagellaStrumento ps;
	private PagellaFormazione pf;
	private PagellaLaboratorio pl;
	private PagellaOrchestra po;
	private PagellaCoro pc;
	private PagellaAltro pa;
	private Esame esame;

	public Studente getStudente() {
		return s;
	}

	public void setStudente(Studente s) {
		this.s = s;
	}

	public PagellaStrumento getPs() {
		return ps;
	}

	public void setPs(PagellaStrumento ps) {
		this.ps = ps;
	}

	public PagellaFormazione getPf() {
		return pf;
	}

	public void setPf(PagellaFormazione pf) {
		this.pf = pf;
	}

	public PagellaLaboratorio getPl() {
		return pl;
	}

	public void setPl(PagellaLaboratorio pl) {
		this.pl = pl;
	}

	public PagellaOrchestra getPo() {
		return po;
	}

	public void setPo(PagellaOrchestra po) {
		this.po = po;
	}

	public PagellaCoro getPc() {
		return pc;
	}

	public void setPc(PagellaCoro pc) {
		this.pc = pc;
	}

	public void setPa(PagellaAltro pa) {
		this.pa = pa;
	}

	public PagellaAltro getPa() {
		return pa;
	}

	public Esame getEsame() {
		return esame;
	}

	public void setEsame(Esame esame) {
		this.esame = esame;
	}
}
