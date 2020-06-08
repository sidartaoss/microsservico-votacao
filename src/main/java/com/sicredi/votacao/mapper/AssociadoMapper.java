/*
 * @(#) AssociadoMapper.java      1.00    07/06/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sicredi.votacao.dto.AssociadoDto;
import com.sicredi.votacao.model.Associado;

/**
 * Interface mapper para objetos {@link Associado}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Mapper
public interface AssociadoMapper {

	AssociadoMapper MAPPER = Mappers.getMapper(AssociadoMapper.class);

	/**
	 * Método responsável por converter um objeto {@link Associado} para
	 * {@link AssociadoDto}.
	 * 
	 * @param associado o objeto {@link Associado}.
	 * @return o objeto {@link AssociadoDto}.
	 */
	@Mapping(source = "id", target = "id")
	@Mapping(source = "cpf", target = "cpf")
	@Mapping(source = "nome", target = "nome")
	AssociadoDto toAssociadoDto(Associado associado);

	/**
	 * Método responsável por converter um objeto {@link AssociadoDto} para
	 * {@link Associado}.
	 * 
	 * @param associadoDto o objeto {@link AssociadoDto}.
	 * @return o objeto {@link Associado}.
	 */
	@Mapping(source = "id", target = "id")
	@Mapping(source = "cpf", target = "cpf")
	@Mapping(source = "nome", target = "nome")
	Associado toAssociado(AssociadoDto associadoDto);
}
