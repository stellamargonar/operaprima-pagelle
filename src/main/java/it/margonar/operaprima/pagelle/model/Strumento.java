package it.margonar.operaprima.pagelle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "strumento")
@SequenceGenerator(name = "strumento_seq", sequenceName = "strumento_id_seq")
// @NamedQueries({
// @NamedQuery(name = "Strumento.findAll", query = "from Strumento"),
// @NamedQuery(name = "Strumento.byName", query =
// "from Strumento where lower(nome)=:nome")
//
// })
public class Strumento {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "strumento_seq")
	private int id;
	@Column(name = "nome")
	private String nome;

	public Strumento() {}

	public Strumento(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Strumento other = (Strumento) obj;
		if (nome == null) {
			if (other.nome != null) return false;
		} else if (!nome.equals(other.nome)) return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
}
