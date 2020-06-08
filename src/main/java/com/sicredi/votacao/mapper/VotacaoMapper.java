/*
 * @(#) VotacaoMapper.java      1.00    08/06/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sicredi.votacao.dto.VotacaoDto;
import com.sicredi.votacao.model.Votacao;

/**
 * Interface mapper para objetos {@link Votacao}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Mapper
public interface VotacaoMapper {

	VotacaoMapper MAPPER = Mappers.getMapper(VotacaoMapper.class);

	/**
	 * Método responsável por converter um objeto {@link Votacao} para
	 * {@link VotacaoDto}.
	 * 
	 * @param votacao o objeto {@link Votacao}.
	 * @return o objeto {@link VotacaoDto}.
	 */
	@Mapping(source = "id.associadoId", target = "associadoId")
	@Mapping(source = "id.sessaoId", target = "sessaoId")
	@Mapping(source = "voto", target = "voto")
	VotacaoDto toVotacaoDto(Votacao votacao);

	/**
	 * Método responsável por converter um objeto {@link VotacaoDto} para
	 * {@link Votacao}.
	 * 
	 * @param votacaoDto o objeto {@link VotacaoDto}.
	 * @return o objeto {@link Votacao}.
	 */
	@Mapping(source = "associadoId", target = "id.associadoId")
	@Mapping(source = "sessaoId", target = "id.sessaoId")
	@Mapping(source = "voto", target = "voto")
	Votacao toVotacao(VotacaoDto votacaoDto);
}
