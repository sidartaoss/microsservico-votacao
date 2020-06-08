/*
 * @(#) PautaMapper.java      1.00    08/06/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sicredi.votacao.dto.PautaDto;
import com.sicredi.votacao.model.Pauta;

/**
 * Interface mapper para objetos {@link Pauta}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Mapper
public interface PautaMapper {

	PautaMapper MAPPER = Mappers.getMapper(PautaMapper.class);

	/**
	 * Método responsável por converter um objeto {@link Pauta} para
	 * {@link PautaDto}.
	 * 
	 * @param pauta o objeto {@link Pauta}.
	 * @return o objeto {@link PautaDto}.
	 */
	@Mapping(source = "id", target = "id")
	@Mapping(source = "nome", target = "nome")
	@Mapping(source = "descricao", target = "descricao")
	PautaDto toPautaDto(Pauta pauta);

	/**
	 * Método responsável por converter um objeto {@link PautaDto} para
	 * {@link Pauta}.
	 * 
	 * @param pautaDto o objeto {@link PautaDto}.
	 * @return o objeto {@link Pauta}.
	 */
	@Mapping(source = "id", target = "id")
	@Mapping(source = "nome", target = "nome")
	@Mapping(source = "descricao", target = "descricao")
	Pauta toPauta(PautaDto pautaDto);
}
