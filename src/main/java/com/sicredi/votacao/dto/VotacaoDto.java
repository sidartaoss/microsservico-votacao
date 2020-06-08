/*
 * @(#) VotacaoDto.java      1.00    08/06/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.dto;

import javax.validation.constraints.NotNull;

import com.sicredi.votacao.model.TipoVoto;
import com.sicredi.votacao.model.Votacao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe DTO que representa a entidade {@link Votacao}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@ApiModel(description = "Classe DTO que representa a entidade Votacao.")
public class VotacaoDto {

	@ApiModelProperty(notes = "O código do associado.")
	@NotNull(message = "votacoes-2")
	private Long associadoId;

	@ApiModelProperty(notes = "O código da sessão.")
	@NotNull(message = "votacoes-3")
	private Long sessaoId;

	@ApiModelProperty(notes = "O voto do associado para a pauta.")
	@NotNull(message = "votacoes-4")
	private TipoVoto voto;

	public VotacaoDto(Long associadoId, Long sessaoId, TipoVoto voto) {
		this.associadoId = associadoId;
		this.sessaoId = sessaoId;
		this.voto = voto;
	}

	public VotacaoDto() {
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

	public TipoVoto getVoto() {
		return voto;
	}

	public void setVoto(TipoVoto voto) {
		this.voto = voto;
	}

	@Override
	public String toString() {
		return String.format("VotacaoDto [associadoId=%s, sessaoId=%s, voto=%s]", associadoId, sessaoId, voto);
	}

}
