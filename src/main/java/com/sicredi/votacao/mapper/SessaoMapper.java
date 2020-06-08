/*
 * @(#) SessaoMapper.java      1.00    08/06/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sicredi.votacao.dto.SessaoDto;
import com.sicredi.votacao.model.Sessao;

/**
 * Interface mapper para objetos {@link Sessao}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Mapper
public interface SessaoMapper {

	SessaoMapper MAPPER = Mappers.getMapper(SessaoMapper.class);

	/**
	 * Método responsável por converter um objeto {@link Sessao} para
	 * {@link SessaoDto}.
	 * 
	 * @param sessao o objeto {@link Sessao}.
	 * @return o objeto {@link SessaoDto}.
	 */
	@Mapping(source = "id", target = "id")
	@Mapping(source = "duracaoEmMinutos", target = "duracaoEmMinutos")
	@Mapping(source = "momentoInicio", target = "momentoInicio")
	@Mapping(source = "momentoTermino", target = "momentoTermino")
	@Mapping(source = "pauta", target = "pauta")
	@Mapping(source = "totalVotosSim", target = "totalVotosSim")
	@Mapping(source = "totalVotosNao", target = "totalVotosNao")
	SessaoDto toSessaoDto(Sessao sessao);

	/**
	 * Método responsável por converter um objeto {@link SessaoDto} para
	 * {@link Sessao}.
	 * 
	 * @param sessaoDto o objeto {@link SessaoDto}.
	 * @return o objeto {@link Sessao}.
	 */
	@Mapping(source = "id", target = "id")
	@Mapping(source = "duracaoEmMinutos", target = "duracaoEmMinutos")
	@Mapping(source = "momentoInicio", target = "momentoInicio")
	@Mapping(source = "momentoTermino", target = "momentoTermino")
	@Mapping(source = "pauta", target = "pauta")
	@Mapping(source = "totalVotosSim", target = "totalVotosSim")
	@Mapping(source = "totalVotosNao", target = "totalVotosNao")
	Sessao toSessao(SessaoDto sessaoDto);
}
