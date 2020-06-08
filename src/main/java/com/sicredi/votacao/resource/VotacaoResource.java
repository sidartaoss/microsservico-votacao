/*
 * @(#) VotacaoResource.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.resource;

import static com.sicredi.votacao.mapper.VotacaoMapper.MAPPER;
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

import com.sicredi.votacao.dto.VotacaoDto;
import com.sicredi.votacao.model.Votacao;
import com.sicredi.votacao.model.VotacaoPk;
import com.sicredi.votacao.service.VotacaoService;

import io.swagger.annotations.ApiOperation;

/**
 * Classe REST Controller que representa o recurso {@link VotacaoResource}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RestController
public class VotacaoResource {

	@Autowired
	private VotacaoService votacaoService;

	/**
	 * Método responsável por salvar um novo voto.
	 * 
	 * @param votacaoDto o objeto {@link VotacaoDto}.
	 * @return o objeto {@link VotacaoDto} salvo.
	 */
	@ApiOperation("Método responsável por salvar um novo voto.")
	@PostMapping("/v1/votacoes")
	@ResponseStatus(CREATED)
	public VotacaoDto votar(@Valid @RequestBody VotacaoDto votacaoDto, HttpServletResponse response) {
		Votacao votacao = MAPPER.toVotacao(votacaoDto);
		Votacao votacaoSalvo = votacaoService.salvar(votacao, response);
		return MAPPER.toVotacaoDto(votacaoSalvo);
	}

	/**
	 * Método responsável por buscar a listagem de todos os votos.
	 * 
	 * @return a listagem de todas os votos.
	 */
	@ApiOperation("Método responsável por buscar a listagem de todos os votos.")
	@GetMapping("/v1/votacoes")
	public List<VotacaoDto> buscar() {
		return this.votacaoService.buscar().stream().map(MAPPER::toVotacaoDto).collect(Collectors.toList());
	}

	/**
	 * Método responsável por buscar o voto pelo código do associado e pelo código
	 * da sessão.
	 * 
	 * @param associadoId o código de identificação do associado.
	 * @param sessaoId    o código de identificação da sessão.
	 * @return o objeto {@link VotacaoDto} caso existir; caso não exitir, retorna o
	 *         código Http 404.
	 */
	@ApiOperation("Método responsável por buscar o voto pelo código do associado e pelo código da sessão.")
	@GetMapping("/v1/votacoes/{associadoId}/{sessaoId}")
	public ResponseEntity<VotacaoDto> buscarPorId(@PathVariable Long associadoId, @PathVariable Long sessaoId) {
		Optional<Votacao> votacao = this.votacaoService.buscarPorId(new VotacaoPk(associadoId, sessaoId));
		return votacao.isPresent() ? ResponseEntity.ok(MAPPER.toVotacaoDto(votacao.get()))
				: ResponseEntity.notFound().build();
	}
}
