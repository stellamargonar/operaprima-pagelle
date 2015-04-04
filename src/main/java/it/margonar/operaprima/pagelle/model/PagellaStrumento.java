package it.margonar.operaprima.pagelle.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "pag_strumento")
@SequenceGenerator(name = "pag_strumento_seq", sequenceName = "pag_strumento_id_seq")
public class PagellaStrumento {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pag_strumento_seq")
	private int id;

	@ManyToOne
	@JoinColumn(name = "idstudente", nullable = false)
	private Studente studente;

	@ManyToOne
	@JoinColumn(name = "iddocente", nullable = false)
	private Docente docente;

	@ManyToOne
	@JoinColumn(name = "idstrumento", nullable = false)
	private Strumento strumento;

	@Column(name = "anno")
	private int anno;

	@Column(name = "sede")
	private String sede;

	@Column(name = "durata")
	private String durata;

	@Column(name = "testi", columnDefinition = "VARCHAR(500)")
	private String testi;

	@Column(name = "repertorio", columnDefinition = "VARCHAR(500)")
	private String repertorio;

	@Column(name = "note", columnDefinition = "VARCHAR(500)")
	private String note;

	@Column(name = "assenze")
	private Integer assenze;

	@Column(name = "c1")
	private int c1;
	@Column(name = "c2")
	private int c2;
	@Column(name = "c3")
	private int c3;
	@Column(name = "c4")
	private int c4;
	@Column(name = "c5")
	private int c5;
	@Column(name = "c6")
	private int c6;
	@Column(name = "c7")
	private int c7;
	@Column(name = "c8")
	private int c8;

	@Override
	public String toString() {
		return "Pagella: " + strumento + ", " + studente;
	}

	public void setCompetenze(List<Integer> list) {
		if (list.size() != 8) { return; }
		c1 = list.get(0);
		c2 = list.get(1);
		c3 = list.get(2);
		c4 = list.get(3);
		c5 = list.get(4);
		c6 = list.get(5);
		c7 = list.get(6);
		c8 = list.get(7);
	}

	public int getCompetenza(int index) {
		switch (index) {
		case 0:
			return c1;
		case 1:
			return c2;
		case 2:
			return c3;
		case 3:
			return c4;
		case 4:
			return c5;
		case 5:
			return c6;
		case 6:
			return c7;
		case 7:
			return c8;
		default:
			return -1;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Studente getStudente() {
		return studente;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getDurata() {
		return durata;
	}

	public void setDurata(String durata) {
		this.durata = durata;
	}

	public String getTesti() {
		return testi;
	}

	public void setTesti(String testi) {
		this.testi = testi;
	}

	public String getRepertorio() {
		return repertorio;
	}

	public void setRepertorio(String repertorio) {
		this.repertorio = repertorio;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getAssenze() {
		return assenze;
	}

	public void setAssenze(Integer assenze) {
		this.assenze = assenze;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anno;
		result = prime * result
				+ (strumento == null ? 0 : strumento.hashCode());
		result = prime * result + (studente == null ? 0 : studente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		PagellaStrumento other = (PagellaStrumento) obj;
		if (anno != other.anno) { return false; }
		if (strumento == null) {
			if (other.strumento != null) { return false; }
		} else if (!strumento.equals(other.strumento)) { return false; }
		if (studente == null) {
			if (other.studente != null) { return false; }
		} else if (!studente.equals(other.studente)) { return false; }
		return true;
	}

	public Strumento getStrumento() {
		return strumento;
	}

	public void setStrumento(Strumento strumento) {
		this.strumento = strumento;
	}

	public void setCompetenze(int c1, int c2, int c3, int c4, int c5, int c6,
			int c7, int c8) {
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		this.c4 = c4;
		this.c5 = c5;
		this.c6 = c6;
		this.c7 = c7;
		this.c8 = c8;
	}

	public int getC1() {
		return c1;
	}

	public void setC1(int c1) {
		this.c1 = c1;
	}

	public int getC2() {
		return c2;
	}

	public void setC2(int c2) {
		this.c2 = c2;
	}

	public int getC3() {
		return c3;
	}

	public void setC3(int c3) {
		this.c3 = c3;
	}

	public int getC4() {
		return c4;
	}

	public void setC4(int c4) {
		this.c4 = c4;
	}

	public int getC5() {
		return c5;
	}

	public void setC5(int c5) {
		this.c5 = c5;
	}

	public int getC6() {
		return c6;
	}

	public void setC6(int c6) {
		this.c6 = c6;
	}

	public int getC7() {
		return c7;
	}

	public void setC7(int c7) {
		this.c7 = c7;
	}

	public int getC8() {
		return c8;
	}

	public void setC8(int c8) {
		this.c8 = c8;
	}

}
