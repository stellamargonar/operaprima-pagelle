package it.margonar.operaprima.pagelle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "docente")
@SequenceGenerator(name = "docente_seq", sequenceName = "docente_id_seq")
@NamedQueries({
		@NamedQuery(name = "Docente.findAll", query = "select d from Docente d"),
		@NamedQuery(name = "Docente.autocomplete", query = "select d from Docente d where lower(d.cognome) like '%:pref%' or lower(d.nome) like '%:pref%'")

})
public class Docente {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "docente_seq")
	private int id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;

	public Docente() {}

	public Docente(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Docente other = (Docente) obj;
		if (cognome == null) {
			if (other.cognome != null) return false;
		} else if (!cognome.equals(other.cognome)) return false;
		if (nome == null) {
			if (other.nome != null) return false;
		} else if (!nome.equals(other.nome)) return false;
		return true;
	}

	@Override
	public String toString() {
		return nome + " " + cognome;
	}

}
