/*
 * @(#) ResultadoVotacao.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe Value Object (VO) para os dados do resultado da votação.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
public class ResultadoVotacao {

	@JsonProperty("sessao_id")
	private Long sessaoId;

	@JsonProperty("total_votos_sim")
	private Long totalVotosSim;

	@JsonProperty("total_votos_nao")
	private Long totalVotosNao;

	public ResultadoVotacao() {
	}

	public ResultadoVotacao(Long sessaoId, Long totalVotosSim, Long totalVotosNao) {
		this.sessaoId = sessaoId;
		this.totalVotosSim = totalVotosSim;
		this.totalVotosNao = totalVotosNao;
	}

	public Long getSessaoId() {
		return sessaoId;
	}

	public void setSessaoId(Long sessaoId) {
		this.sessaoId = sessaoId;
	}

	public Long getTotalVotosSim() {
		return totalVotosSim;
	}

	public void setTotalVotosSim(Long totalVotosSim) {
		this.totalVotosSim = totalVotosSim;
	}

	public Long getTotalVotosNao() {
		return totalVotosNao;
	}

	public void setTotalVotosNao(Long totalVotosNao) {
		this.totalVotosNao = totalVotosNao;
	}

	@Override
	public String toString() {
		return String.format("ResultadoVotacao [sessaoId=%s, totalVotosSim=%s, totalVotosNao=%s]", sessaoId,
				totalVotosSim, totalVotosNao);
	}

}
