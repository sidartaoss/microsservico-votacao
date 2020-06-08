/*
 * @(#) PautaResource.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.resource;

import static com.sicredi.votacao.mapper.PautaMapper.MAPPER;
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

import com.sicredi.votacao.dto.PautaDto;
import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.service.PautaService;

import io.swagger.annotations.ApiOperation;

/**
 * Classe REST Controller que representa o recurso {@link PautaResource}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RestController
public class PautaResource {

	@Autowired
	private PautaService pautaService;

	/**
	 * Método responsável por salvar uma nova Pauta.
	 * 
	 * @param pauta o objeto {@link PautaDto}.
	 * @return a pauta criada.
	 */
	@ApiOperation("Método responsável por salvar uma nova Pauta.")
	@PostMapping("/v1/pautas")
	@ResponseStatus(CREATED)
	public PautaDto criar(@Valid @RequestBody PautaDto pautaDto, HttpServletResponse response) {
		Pauta pauta = MAPPER.toPauta(pautaDto);
		Pauta pautaSalvo = pautaService.salvar(pauta, response);
		return MAPPER.toPautaDto(pautaSalvo);
	}

	/**
	 * Método responsável por buscar a listagem de todas as Pautas.
	 * 
	 * @return a listagem de todas as pautas.
	 */
	@ApiOperation("Método responsável por buscar a listagem de todas as Pautas.")
	@GetMapping("/v1/pautas")
	public List<PautaDto> buscar() {
		return this.pautaService.buscar().stream().map(MAPPER::toPautaDto).collect(Collectors.toList());
	}

	/**
	 * Método responsável por buscar a Pauta pelo código.
	 * 
	 * @param id o código da Pauta.
	 * @return o objeto {@link PautaDto} caso existir; caso não exitir, retorna o
	 *         código Http 404.
	 */
	@ApiOperation("Método responsável por buscar a Pauta pelo código.")
	@GetMapping("/v1/pautas/{id}")
	public ResponseEntity<PautaDto> buscarPorId(@PathVariable Long id) {
		Optional<Pauta> pauta = this.pautaService.buscarPorId(id);
		return pauta.isPresent() ? ResponseEntity.ok(MAPPER.toPautaDto(pauta.get()))
				: ResponseEntity.notFound().build();
	}
}
