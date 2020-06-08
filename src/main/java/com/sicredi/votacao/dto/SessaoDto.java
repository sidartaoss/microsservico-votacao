/*
 * @(#) SessaoDto.java      1.00    08/06/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.TipoStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe DTO que representa a entidade {@link Sessao}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@ApiModel(description = "Classe DTO que representa a entidade Sessao.")
@JsonInclude(Include.NON_NULL)
public class SessaoDto {

	@ApiModelProperty(notes = "O identificador do objeto (PK).")
	private Long id;

	@ApiModelProperty(notes = "A duração da sessão (em minutos).")
	private Integer duracaoEmMinutos;

	@ApiModelProperty(notes = "O momento do início da sessão.")
	private Date momentoInicio;

	@ApiModelProperty(notes = "O momento do término da sessão.")
	private Date momentoTermino;

	@ApiModelProperty(notes = "O status da sessão: em andamento ou finalizada.")
	private TipoStatus status;

	@ApiModelProperty(notes = "A pauta da sessão.")
	@NotNull(message = "sessoes-1")
	private PautaDto pauta;

	@ApiModelProperty(notes = "O total de votos Sim (concordando com a pauta).")
	private Long totalVotosSim;

	@ApiModelProperty(notes = "O total de votos Não (discordando da pauta).")
	private Long totalVotosNao;

	public SessaoDto() {
	}

	public SessaoDto(Long id, Integer duracaoEmMinutos, Date momentoInicio, Date momentoTermino, TipoStatus status,
			PautaDto pauta, Long totalVotosSim, Long totalVotosNao) {
		this.id = id;
		this.duracaoEmMinutos = duracaoEmMinutos;
		this.momentoInicio = momentoInicio;
		this.momentoTermino = momentoTermino;
		this.status = status;
		this.pauta = pauta;
		this.totalVotosSim = totalVotosSim;
		this.totalVotosNao = totalVotosNao;
	}

	public SessaoDto(Long id, Integer duracaoEmMinutos, PautaDto pauta) {
		this.id = id;
		this.duracaoEmMinutos = duracaoEmMinutos;
		this.pauta = pauta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDuracaoEmMinutos() {
		return duracaoEmMinutos;
	}

	public void setDuracaoEmMinutos(Integer duracaoEmMinutos) {
		this.duracaoEmMinutos = duracaoEmMinutos;
	}

	public Date getMomentoInicio() {
		return momentoInicio;
	}

	public void setMomentoInicio(Date momentoInicio) {
		this.momentoInicio = momentoInicio;
	}

	public Date getMomentoTermino() {
		return momentoTermino;
	}

	public void setMomentoTermino(Date momentoTermino) {
		this.momentoTermino = momentoTermino;
	}

	public TipoStatus getStatus() {
		return status;
	}

	public void setStatus(TipoStatus status) {
		this.status = status;
	}

	public PautaDto getPauta() {
		return pauta;
	}

	public void setPauta(PautaDto pauta) {
		this.pauta = pauta;
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
		return String.format(
				"SessaoDto [id=%s, duracaoEmMinutos=%s, momentoInicio=%s, momentoTermino=%s, status=%s, pauta=%s, totalVotosSim=%s, totalVotosNao=%s]",
				id, duracaoEmMinutos, momentoInicio, momentoTermino, status, pauta, totalVotosSim, totalVotosNao);
	}

}
