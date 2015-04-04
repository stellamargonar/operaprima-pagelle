package it.margonar.operaprima.pagelle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "laboratorio")
@SequenceGenerator(name = "laboratorio_seq", sequenceName = "laboratorio_id_seq")
// @NamedQueries({
// @NamedQuery(name = "Laboratorio.findAll", query = "from Laboratorio"),
// @NamedQuery(name = "Laboratorio.byName", query =
// "from Laboratorio where lower(laboratorio) = :nome")
//
// })
public class Laboratorio {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "laboratorio_seq")
	private int id;
	@Column(name = "nome")
	private String nome;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
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
		Laboratorio other = (Laboratorio) obj;
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
