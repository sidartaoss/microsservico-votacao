/*
 * @(#) Votacao.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.model;

import static javax.persistence.EnumType.STRING;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Classe persistente que representa a entidade {@link Votacao}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Entity
@Table(name = "votacao")
public class Votacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VotacaoPk id;

	@Enumerated(STRING)
	@Column(nullable = false)
	private TipoVoto voto;

	@Version
	@Column(name = "dt_modificacao")
	private Timestamp version;

	public Votacao() {
	}

	public Votacao(VotacaoPk id, TipoVoto voto) {
		this.id = id;
		this.voto = voto;
	}

	public VotacaoPk getId() {
		return id;
	}

	public void setId(VotacaoPk id) {
		this.id = id;
	}

	public TipoVoto getVoto() {
		return voto;
	}

	public void setVoto(TipoVoto voto) {
		this.voto = voto;
	}

	public Timestamp getVersion() {
		return version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Votacao [id=" + id + ", voto=" + voto + ", version=" + version + "]";
	}

}
