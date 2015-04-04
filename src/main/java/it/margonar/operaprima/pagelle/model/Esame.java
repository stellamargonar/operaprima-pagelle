package it.margonar.operaprima.pagelle.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "esami")
@SequenceGenerator(name = "esame_seq", sequenceName = "esame_id_seq")
@NamedQueries({
		@NamedQuery(name = "Esame.findAll", query = "select e from Esame e"),
		@NamedQuery(name = "Esame.byStudente", query = "select e from Esame e where e.studente = :studente")

})
public class Esame {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "esame_seq")
	private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "IDstudente", nullable = false)
	private Studente studente;

	@Column(name = "data")
	private Date data;

	@Override
	public String toString() {
		return "Esame " + data + ", " + studente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((studente == null) ? 0 : studente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Esame other = (Esame) obj;
		if (studente == null) {
			if (other.studente != null) return false;
		} else if (!studente.equals(other.studente)) return false;
		return true;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
