/*
 * @(#) Pauta.java      1.00    28/05/2020
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe persistente que representa a entidade Pauta.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Entity
@Table(name = "pauta")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Pauta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cd_pauta", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false)
	private String nome;

	@Column(length = 150, nullable = false)
	private String descricao;

	@Version
	@Column(name = "dt_modificacao")
	private Timestamp version;

	public Pauta() {
	}

	public Pauta(Long id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Timestamp getVersion() {
		return version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Pauta [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", version=" + version + "]";
	}

}
