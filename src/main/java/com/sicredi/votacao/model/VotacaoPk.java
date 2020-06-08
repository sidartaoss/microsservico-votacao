/*
 * @(#) VotacaoPk.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe persistente que representa a PK da entidade {@link Votacao}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Embeddable
public class VotacaoPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "cd_associado", nullable = false)
	private Long associadoId;

	@Column(name = "cd_sessao", nullable = false)
	private Long sessaoId;

	public VotacaoPk() {
	}

	public VotacaoPk(Long associadoId, Long sessaoId) {
		this.associadoId = associadoId;
		this.sessaoId = sessaoId;
	}

	public Long getAssociadoId() {
		return associadoId;
	}

	public void setAssociadoId(Long associadoId) {
		this.associadoId = associadoId;
	}

	public Long getSessaoId() {
		return sessaoId;
	}

	public void setSessaoId(Long sessaoId) {
		this.sessaoId = sessaoId;
	}

	@Override
	public String toString() {
		return String.format("VotacaoPk [associadoId=%s, sessaoId=%s]", associadoId, sessaoId);
	}

}
