/*
 * @(#) SessaoResource.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.resource;

import static com.sicredi.votacao.mapper.SessaoMapper.MAPPER;
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

import com.sicredi.votacao.dto.SessaoDto;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.monitoring.cloudwatch.CloudwatchMetricsEmitter;
import com.sicredi.votacao.service.SessaoService;

import io.swagger.annotations.ApiOperation;

/**
 * Classe REST Controller que representa o recurso Sessao.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RestController
public class SessaoResource {

	private static final String SERVICE_NAMESPACE = "ServicoSessao";

	private static final String ENDPOINT_LATENCY_METRIC_NAME_TEMPLATE = "%s-Latencia";
	private static final String ENDPOINT_REQUESTS_METRIC_NAME_TEMPLATE = "%s-Requests";

	private static final String BUSCAR_SESSAO_ENDPOINT_NAME = "BuscarSessao";
	private static final String SALVAR_SESSAO_ENDPOINT_NAME = "SalvarSessao";

	@Autowired
	private SessaoService sessaoService;

	@Autowired
	private CloudwatchMetricsEmitter metricsEmitter;

	/**
	 * Método responsável por salvar uma nova Sessão.
	 * 
	 * @param sessao o objeto sessao.
	 * @return o objeto criado.
	 */
	@ApiOperation("Método responsável por salvar uma nova Sessão.")
	@PostMapping("/v1/sessoes")
	@ResponseStatus(CREATED)
	public SessaoDto criar(@Valid @RequestBody SessaoDto sessaoDto, HttpServletResponse response) {
		emitEndpointRequest(SALVAR_SESSAO_ENDPOINT_NAME);
		final long startTimestamp = System.currentTimeMillis();

		Sessao sessao = MAPPER.toSessao(sessaoDto);
		Sessao sessaoSalva = sessaoService.salvar(sessao, response);

		final long endTimestamp = System.currentTimeMillis();
		emitLatencyMetric(SALVAR_SESSAO_ENDPOINT_NAME, endTimestamp - startTimestamp);
		return MAPPER.toSessaoDto(sessaoSalva);
	}

	/**
	 * Método responsável por buscar a listagem de todas as Sessões.
	 * 
	 * @return a listagem de todas as sessoes.
	 */
	@ApiOperation("Método responsável por buscar a listagem de todos as Sessões.")
	@GetMapping("/v1/sessoes")
	public List<SessaoDto> buscar() {
		return this.sessaoService.buscar().stream().map(MAPPER::toSessaoDto).collect(Collectors.toList());
	}

	/**
	 * Método responsável por buscar a Sessão pelo código.
	 * 
	 * @param id o código do sessao.
	 * @return o objeto {@link SessaoDto} caso existir; caso não exitir, retorna o
	 *         código Http 404.
	 */
	@ApiOperation("Método responsável por buscar a Sessão pelo código.")
	@GetMapping("/v1/sessoes/{id}")
	public ResponseEntity<SessaoDto> buscarPorId(@PathVariable Long id) {
		emitEndpointRequest(BUSCAR_SESSAO_ENDPOINT_NAME);
		final long startTimestamp = System.currentTimeMillis();

		Optional<Sessao> sessao = this.sessaoService.buscarPorId(id);

		final long endTimestamp = System.currentTimeMillis();
		emitLatencyMetric(BUSCAR_SESSAO_ENDPOINT_NAME, endTimestamp - startTimestamp);
		return sessao.isPresent() ? ResponseEntity.ok(MAPPER.toSessaoDto(sessao.get()))
				: ResponseEntity.notFound().build();
	}

	/**
	 * Método responsável por emitir métrica de porcentagem de latência.
	 * 
	 * @param endpointName o nome do endpoint.
	 * @param value        o valor.
	 */
	private void emitLatencyMetric(final String endpointName, final long value) {
		final String metricName = String.format(ENDPOINT_LATENCY_METRIC_NAME_TEMPLATE, endpointName);
		metricsEmitter.emitMetric(SERVICE_NAMESPACE, metricName, value);
	}

	/**
	 * Método responsável por emitir métrica de quantidade de requests.
	 * 
	 * @param endpointName o nome do endpoint.
	 */
	private void emitEndpointRequest(final String endpointName) {
		final String metricName = String.format(ENDPOINT_REQUESTS_METRIC_NAME_TEMPLATE, endpointName);
		metricsEmitter.emitMetric(SERVICE_NAMESPACE, metricName, 1.0);
	}
}
