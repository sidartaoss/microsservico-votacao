/*
 * @(#) PautaDto.java      1.00    08/06/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sicredi.votacao.model.Pauta;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe DTO para a entidade {@link Pauta}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@ApiModel(description = "Classe DTO que representa a entidade Pauta.")
@JsonInclude(Include.NON_NULL)
public class PautaDto {

	@ApiModelProperty(notes = "O identificador do objeto (PK).")
	private Long id;

	@ApiModelProperty(notes = "O nome da pauta.")
	@Size(min = 3, max = 50, message = "pautas-2")
	@NotBlank(message = "pautas-1")
	private String nome;

	@ApiModelProperty(notes = "A descrição da pauta.")
	@Size(min = 10, max = 150, message = "pautas-4")
	@NotBlank(message = "pautas-3")
	private String descricao;

	public PautaDto() {
	}

	public PautaDto(Long id, String nome, String descricao) {
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

	@Override
	public String toString() {
		return String.format("PautaDto [id=%s, nome=%s, descricao=%s]", id, nome, descricao);
	}

}
