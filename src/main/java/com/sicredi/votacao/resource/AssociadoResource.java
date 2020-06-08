/*
 * @(#) AssociadoResource.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.resource;

import static com.sicredi.votacao.mapper.AssociadoMapper.MAPPER;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.votacao.dto.AssociadoDto;
import com.sicredi.votacao.model.Associado;
import com.sicredi.votacao.service.AssociadoService;

import io.swagger.annotations.ApiOperation;

/**
 * Classe REST Controller que representa o recurso {@link AssociadoResource}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RestController
public class AssociadoResource {

	@Autowired
	private AssociadoService associadoService;

	/**
	 * Método responsável por salvar um novo Associado.
	 * 
	 * @param associado o objeto {@link AssociadoDto}.
	 * @return o objeto criado.
	 */
	@ApiOperation("Método responsável por salvar um novo Associado.")
	@PostMapping("/v1/associados")
	@ResponseStatus(CREATED)
	public AssociadoDto criar(@Valid @RequestBody AssociadoDto associadoDto, HttpServletResponse response) {
		Associado associado = MAPPER.toAssociado(associadoDto);
		Associado associadoSalvo = associadoService.salvar(associado, response);
		return MAPPER.toAssociadoDto(associadoSalvo);
	}

	/**
	 * Método responsável por buscar a listagem de todos os Associados.
	 * 
	 * @return a listagem de todas as associados.
	 */
	@ApiOperation("Método responsável por buscar a listagem de todos os Associados.")
	@GetMapping("/v1/associados")
	public List<AssociadoDto> buscar() {
		return this.associadoService.buscar().stream().map(MAPPER::toAssociadoDto).collect(Collectors.toList());
	}

	/**
	 * Método responsável por buscar o Associado pelo código.
	 * 
	 * @param id o código do Associado.
	 * @return o objeto {@link AssociadoDto} caso existir; caso não exitir, retorna
	 *         o código Http 404.
	 */
	@ApiOperation("Método responsável por buscar o Associado pelo código.")
	@GetMapping("/v1/associados/{id}")
	public ResponseEntity<AssociadoDto> buscarPorId(@PathVariable Long id) {
		Optional<Associado> associado = this.associadoService.buscarPorId(id);
		return associado.isPresent() ? ResponseEntity.ok(MAPPER.toAssociadoDto(associado.get()))
				: ResponseEntity.notFound().build();
	}
}
