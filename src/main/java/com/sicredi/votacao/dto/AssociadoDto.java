/*
 * @(#) AssociadoDto.java      1.00    06/06/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sicredi.votacao.model.Associado;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe DTO para a entidade {@link Associado}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@ApiModel(description = "Classe DTO que representa a entidade Associado.")
@JsonInclude(Include.NON_NULL)
public class AssociadoDto {

	@ApiModelProperty(notes = "O identificador do objeto (PK).")
	private Long id;

	@ApiModelProperty(notes = "O CPF do associado.")
	@Size(max = 11, message = "associados-2")
	@NotBlank(message = "associados-1")
	private String cpf;

	@ApiModelProperty(notes = "O nome do associado.")
	@Size(min = 3, max = 60, message = "associados-4")
	@NotBlank(message = "associados-3")
	private String nome;

	public AssociadoDto() {
	}

	public AssociadoDto(Long id, String cpf, String nome) {
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

	@Override
	public String toString() {
		return String.format("AssociadoDto [id=%s, cpf=%s, nome=%s]", id, cpf, nome);
	}

}
