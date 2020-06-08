/*
 * @(#) SessaoService.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.sicredi.votacao.event.CreatedResourceEvent;
import com.sicredi.votacao.event.VotacaoNotifier;
import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.repository.SessaoRepository;
import com.sicredi.votacao.service.exception.BusinessException;
import com.sicredi.votacao.vo.ResultadoVotacao;

/**
 * Classe de negócio que representa o componente de serviço Sessao.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Service
public class SessaoService {

	private static final Logger LOG = LoggerFactory.getLogger(SessaoService.class);

	@Autowired
	private SessaoRepository sessaoRepository;

	@Autowired
	private PautaService pautaService;

	@Autowired
	private VotacaoService votacaoService;

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private VotacaoNotifier votacaoNotifier;

	/**
	 * Método responsável por salvar um objeto persistente {@link Sessao}.
	 * 
	 * @param sessao o objeto {@link Sessao}.
	 * @return o objeto {@link Sessao} criado.
	 */
	public Sessao salvar(Sessao sessao, HttpServletResponse response) {
		if (sessao.getPauta() == null || sessao.getPauta().getId() == null) {
			throw new BusinessException("sessoes-1", HttpStatus.BAD_REQUEST);
		}
		Optional<Pauta> optionalPauta = this.pautaService.buscarPorId(sessao.getPauta().getId());
		if (!optionalPauta.isPresent()) {
			throw new BusinessException("sessoes-2", HttpStatus.BAD_REQUEST);
		}
		sessao.setPauta(optionalPauta.get());
		if (sessao.getDuracaoEmMinutos() == null || sessao.getDuracaoEmMinutos().intValue() == 0) {
			sessao.setDuracaoEmMinutos(Integer.valueOf(1));
		}
		sessao.setMomentoInicio(new Date());
		Sessao sessaoSalva = this.sessaoRepository.save(sessao);
		taskScheduler.schedule(() -> {
			Optional<Sessao> sessaoSalvaOptional = sessaoRepository.findById(sessaoSalva.getId());
			if (!sessaoSalvaOptional.isPresent()) {
				throw new BusinessException("sessoes-3", HttpStatus.BAD_REQUEST);
			}
			final Sessao sessaoAtualiza = sessaoSalvaOptional.get();

			Calendar cal = Calendar.getInstance();
			cal.setTime(sessao.getMomentoInicio());
			cal.add(Calendar.MINUTE, sessao.getDuracaoEmMinutos());
			sessaoAtualiza.setMomentoTermino(cal.getTime());

			sessaoAtualiza.setTotalVotosSim(votacaoService.buscarTotalVotosSimPorSessaoId(sessaoSalva.getId()));
			sessaoAtualiza.setTotalVotosNao(votacaoService.buscarTotalVotosNaoPorSessaoId(sessaoSalva.getId()));
			sessaoRepository.save(sessaoAtualiza);

			LOG.info("Notificando fila SQS. Sessao ID: {}, Total de Votos Sim: {}, Total de Votos Nao: {}",
					sessaoAtualiza.getId(), sessaoAtualiza.getTotalVotosSim(), sessaoAtualiza.getTotalVotosNao());
			votacaoNotifier.onResultadoVotacaoSalvo(
					new ResultadoVotacao(
							sessaoAtualiza.getId(),
							sessaoAtualiza.getTotalVotosSim(), 
							sessaoAtualiza.getTotalVotosNao()));
		}, new Date(System.currentTimeMillis() + (sessao.getDuracaoEmMinutos() * (60 * 1000))));
		this.publisher.publishEvent(new CreatedResourceEvent(this, response, sessaoSalva.getId()));
		return sessaoSalva;
	}

	/**
	 * Método responsável por buscar todas as instâncias do objeto persistente
	 * {@link Sessao}.
	 * 
	 * @return a lista de todas as instâncias do objeto {@link Sessao}.
	 */
	public List<Sessao> buscar() {
		return this.sessaoRepository.findAll();
	}

	/**
	 * Método responsável por obter uma instância do objeto persistente
	 * {@link Sessao}.
	 * 
	 * @param id o código do sessao.
	 * @return uma instância do objeto persistente {@link Sessao}.
	 */
	public Optional<Sessao> buscarPorId(Long id) {
		return this.sessaoRepository.findById(id);
	}

}
