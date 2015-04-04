package it.margonar.operaprima.pagelle.model;

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
@Table(name = "pag_altro")
@SequenceGenerator(name = "pag_altro_seq", sequenceName = "pag_altro_id_seq")
public class PagellaAltro {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pag_altro_seq")
	private int id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "anno")
	private int anno;

	@Column(name = "sede")
	private String sede;

	@Column(name = "repertorio", columnDefinition = "VARCHAR(500)")
	private String repertorio;

	@Column(name = "note", columnDefinition = "VARCHAR(500)")
	private String note;

	@ManyToOne
	@JoinColumn(name = "idstudente", nullable = false)
	private Studente studente;

	@ManyToOne
	@JoinColumn(name = "iddocente", nullable = false)
	private Docente docente;

	@Column(name = "assenze")
	private Integer assenze;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((studente == null) ? 0 : studente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PagellaAltro other = (PagellaAltro) obj;
		if (nome == null) {
			if (other.nome != null) return false;
		} else if (!nome.equals(other.nome)) return false;
		if (studente == null) {
			if (other.studente != null) return false;
		} else if (!studente.equals(other.studente)) return false;
		return true;
	}

	public Integer getAssenze() {
		return assenze;
	}

	public void setAssenze(Integer assenze) {
		this.assenze = assenze;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
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

}
