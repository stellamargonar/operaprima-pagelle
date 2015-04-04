package it.margonar.operaprima.pagelle.model;

import java.util.Date;

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
@Table(name = "studente")
@SequenceGenerator(name = "studente_seq", sequenceName = "studente_id_seq")
@NamedQueries({
		@NamedQuery(name = "Studente.findAll", query = "select s from Studente s"),
		@NamedQuery(name = "Studente.autocomplete", query = "select s from Studente s where lower(s.cognome) like '%:pref%' or lower(s.nome) like '%:pref%'")

})
public class Studente implements Comparable<Studente> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studente_seq")
	private int id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;
	@Column(name = "data_nascita")
	private Date dataNascita;
	@Column(name = "data_iscrizione")
	private Date dataIscrizione;

	public Studente() {}

	public Studente(String nome, String cognome) {
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

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataIscrizione() {
		return dataIscrizione;
	}

	public void setDataIscrizione(Date dataIscrizione) {
		this.dataIscrizione = dataIscrizione;
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
		Studente other = (Studente) obj;
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
		return cognome + " " + nome;
	}

	public int compareTo(Studente o) {
		if (o != null) {
			String original = cognome + nome;
			String other = o.getCognome() + o.getNome();
			return original.compareTo(other);
		} else return 1;
	}

}
