/*
 * @(#) PautaService.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sicredi.votacao.event.CreatedResourceEvent;
import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.repository.PautaRepository;
import com.sicredi.votacao.service.exception.BusinessException;

/**
 * Classe de negócio que representa o componente de serviço Pauta.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * Método responsável por salvar um objeto persistente {@link Pauta}.
	 * @param pauta o objeto {@link Pauta}.
	 * @return o objeto {@link Pauta} criado.
	 */
	public Pauta salvar(Pauta pauta, HttpServletResponse response) {
		final String nome = pauta.getNome();
		if (nome == null || nome.trim().isEmpty()) {
			throw new BusinessException("pautas-1", HttpStatus.BAD_REQUEST);
		}
		if (nome.length() < 3 || nome.length() > 50) {
			throw new BusinessException("pautas-2", HttpStatus.BAD_REQUEST);
		}
		final String descricao = pauta.getDescricao();
		if (descricao == null || descricao.trim().isEmpty()) {
			throw new BusinessException("pautas-3", HttpStatus.BAD_REQUEST);
		}
		if (descricao.length() < 10 || descricao.length() > 150) {
			throw new BusinessException("pautas-4", HttpStatus.BAD_REQUEST);
		}
		Pauta pautaSalva = this.pautaRepository.save(pauta);
		this.publisher.publishEvent(new CreatedResourceEvent(this, response, pautaSalva.getId()));
		return pautaSalva;
	}

	/**
	 * Método responsável por buscar todas as instâncias do objeto persistente {@link Pauta}.
	 * @return a lista de todas as instâncias do objeto {@link Pauta}.
	 */
	public List<Pauta> buscar() {
		return this.pautaRepository.findAll();
	}

	/**
	 * Método responsável por obter uma instância do objeto persistente {@link Pauta}.
	 * @param id o código da pauta.
	 * @return uma instância do objeto persistente {@link Pauta}.
	 */
	public Optional<Pauta> buscarPorId(Long id) {
		return this.pautaRepository.findById(id);
	}

}
