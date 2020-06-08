/*
 * @(#) Associado.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Classe persistente que representa a entidade {@link Associado}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Entity
@Table(name = "associado")
public class Associado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cd_associado", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 11, nullable = false)
	private String cpf;

	@Column(name = "nome", length = 60, nullable = false)
	private String nome;

	@Version
	@Column(name = "dt_modificacao")
	private Timestamp version;

	public Associado() {
	}

	public Associado(Long id, String cpf, String nome) {
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Timestamp getVersion() {
		return version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Associado [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", version=" + version + "]";
	}

}
