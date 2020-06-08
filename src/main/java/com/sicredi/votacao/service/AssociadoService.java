/*
 * @(#) AssociadoService.java      1.00    29/05/2020
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sicredi.votacao.event.CreatedResourceEvent;
import com.sicredi.votacao.model.Associado;
import com.sicredi.votacao.repository.AssociadoRepository;
import com.sicredi.votacao.service.exception.BusinessException;

/**
 * Classe de negócio que representa o componente de serviço Associado.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Service
public class AssociadoService {

	private static final String SERVICO_EXTERNO_CPF = "https://user-info.herokuapp.com/users/{cpf}";

	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Método responsável por salvar um objeto persistente {@link Associado}.
	 * @param associado o objeto {@link Associado}.
	 * @return o objeto {@link Associado} criado.
	 */
	public Associado salvar(Associado associado, HttpServletResponse response) {
		final String cpf = associado.getCpf();
		if (cpf == null || cpf.trim().isEmpty()) {
			throw new BusinessException("associados-1", HttpStatus.BAD_REQUEST);
		}
		if (cpf.length() > 11) {
			throw new BusinessException("associados-2", HttpStatus.BAD_REQUEST);
		}
		final String nome = associado.getNome();
		if (nome == null || nome.trim().isEmpty()) {
			throw new BusinessException("associados-3", HttpStatus.BAD_REQUEST);
		}
		if (nome.length() < 3 || nome.length() > 60) {
			throw new BusinessException("associados-4", HttpStatus.BAD_REQUEST);
		}
		String cpfServiceResponse = null;
		try {
			cpfServiceResponse = restTemplate.getForObject(SERVICO_EXTERNO_CPF, String.class, cpf);
		} catch (HttpClientErrorException e) {
			throw new BusinessException("associados-5", HttpStatus.BAD_REQUEST);
		}
		if (cpfServiceResponse.contains("UNABLE_TO_VOTE")) {
			throw new BusinessException("associados-6", HttpStatus.BAD_REQUEST);
		}
		final Associado associadoSalvo = this.associadoRepository.save(associado);
		this.publisher.publishEvent(new CreatedResourceEvent(this, response, associadoSalvo.getId()));
		return associadoSalvo;
	}

	/**
	 * Método responsável por buscar todas as instâncias do objeto persistente {@link Associado}.
	 * @return a lista de todas as instâncias do objeto {@link Associado}.
	 */
	public List<Associado> buscar() {
		return this.associadoRepository.findAll();
	}

	/**
	 * Método responsável por obter uma instância do objeto persistente {@link Associado}.
	 * @param id o código do associado.
	 * @return uma instância do objeto persistente {@link Associado}.
	 */
	public Optional<Associado> buscarPorId(Long id) {
		return this.associadoRepository.findById(id);
	}

}
