/*
 * @(#) VotacaoService.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.service;

import static com.sicredi.votacao.model.TipoStatus.FINALIZADA;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sicredi.votacao.event.CreatedResourceEvent;
import com.sicredi.votacao.model.Associado;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.Votacao;
import com.sicredi.votacao.model.VotacaoPk;
import com.sicredi.votacao.repository.VotacaoRepository;
import com.sicredi.votacao.service.exception.BusinessException;

/**
 * Classe de negócio que representa o componente de serviço
 * {@link VotacaoService}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Service
public class VotacaoService {

	@Autowired
	private VotacaoRepository votacaoRepository;

	@Autowired
	private AssociadoService associadoService;

	@Autowired
	private SessaoService sessaoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * Método responsável por salvar um objeto persistente {@link Votacao}.
	 * 
	 * @param votacao o objeto {@link Votacao}.
	 * @return o objeto {@link Votacao} criado.
	 */
	public Votacao salvar(Votacao votacao, HttpServletResponse response) {
		final VotacaoPk pk = votacao.getId();
		if (pk == null || pk.getAssociadoId() == null || pk.getSessaoId() == null) {
			throw new BusinessException("votacoes-1", HttpStatus.BAD_REQUEST);
		}
		final Long associadoId = pk.getAssociadoId();
		if (associadoId == null) {
			throw new BusinessException("votacoes-2", HttpStatus.BAD_REQUEST);
		}
		final Long sessaoId = pk.getSessaoId();
		if (sessaoId == null) {
			throw new BusinessException("votacoes-3", HttpStatus.BAD_REQUEST);
		}
		if (votacao.getVoto() == null) {
			throw new BusinessException("votacoes-4", HttpStatus.BAD_REQUEST);
		}
		final Optional<Sessao> sessao = this.sessaoService.buscarPorId(pk.getSessaoId());
		if (!sessao.isPresent()) {
			throw new BusinessException("votacoes-5", HttpStatus.BAD_REQUEST);
		}
		if (sessao.get().getStatus() == FINALIZADA) {
			throw new BusinessException("votacoes-6", HttpStatus.BAD_REQUEST);
		}
		final Optional<Associado> associado = this.associadoService.buscarPorId(pk.getAssociadoId());
		if (!associado.isPresent()) {
			throw new BusinessException("votacoes-7", HttpStatus.BAD_REQUEST);
		}
		Optional<Votacao> voto = this.buscarPorId(votacao.getId());
		if (voto.isPresent()) {
			throw new BusinessException("votacoes-8", HttpStatus.BAD_REQUEST);
		}
		Votacao votacaoSalva = this.votacaoRepository.save(votacao);
		this.publisher.publishEvent(new CreatedResourceEvent(this, response, votacaoSalva.getId()));
		return votacaoSalva;
	}

	/**
	 * Método responsável por buscar todas as instâncias do objeto persistente
	 * {@link Votacao}.
	 * 
	 * @return a lista de todas as instâncias do objeto {@link Votacao}.
	 */
	public List<Votacao> buscar() {
		return this.votacaoRepository.findAll();
	}

	/**
	 * Método responsável por obter uma instância do objeto persistente
	 * {@link Votacao}.
	 * 
	 * @param votacaoPk o objeto {@link VotacaoPk}.
	 * @return uma instância do objeto persistente {@link Votacao}.
	 */
	public Optional<Votacao> buscarPorId(VotacaoPk votacaoPk) {
		return this.votacaoRepository.findById(votacaoPk);
	}

	/**
	 * Método responsável por buscar o total de votos Sim para a sessão informada.
	 * 
	 * @param sessaoId o id da sessão.
	 * @return o total de votos Sim para a sessão informada.
	 */
	public Long buscarTotalVotosSimPorSessaoId(Long sessaoId) {
		return this.votacaoRepository.findTotalVotosSimBySessaoId(sessaoId);
	}

	/**
	 * Método responsável por buscar o total de votos Não para a sessão informada.
	 * 
	 * @param sessaoId o id da sessão.
	 * @return o total de votos Não para a sessão informada.
	 */
	public Long buscarTotalVotosNaoPorSessaoId(Long sessaoId) {
		return this.votacaoRepository.findTotalVotosNaoBySessaoId(sessaoId);
	}

}
