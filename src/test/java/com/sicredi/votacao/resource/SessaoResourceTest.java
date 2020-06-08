/*
 * @(#) SessaoResourceTest.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.resource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.monitoring.cloudwatch.CloudwatchMetricsEmitter;
import com.sicredi.votacao.service.SessaoService;

/**
 * Classe de testes unitários para o recurso {@link SessaoResource}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SessaoResource.class)
public class SessaoResourceTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SessaoService sessaoService;
	
	@MockBean
	private CloudwatchMetricsEmitter metricsEmitter;
	
	
	@Test
	public void test_deve_retornar_erro_400_bad_request_quando_salvar_e_a_pauta_nao_for_informada() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/v1/sessoes")
				.accept(APPLICATION_JSON)
				.content("{ \"duracaoEmMinutos\": 10 }")
				.contentType(APPLICATION_JSON);
		
		// Action & Verification
		MvcResult result = mockMvc.perform(requestBuilder)
			.andExpect(status().isBadRequest())
			.andExpect(content().json("{\"statusCode\":400, \"errors\":[{\"code\":\"sessoes-1\"}]}"))
			.andReturn();
		assertThatExceptionIsMethodArgumentNotValid(result);
	}
	
	@Test
	public void test_deve_retornar_codigo_http_201_e_criar_uma_nova_sessao_com_sucesso() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/v1/sessoes")
				.accept(APPLICATION_JSON)
				.content("{ \"pauta\" : { \"id\": 1 }, \"duracaoEmMinutos\": 10 }")
				.contentType(APPLICATION_JSON);
		
		when(sessaoService.salvar(any(Sessao.class), any(HttpServletResponse.class))).thenReturn(
				new Sessao(1L, 10, new Pauta(1L, "Pauta A", "Pauta A sobre AB")));
		
		// Action & Verification
		mockMvc.perform(requestBuilder)
			.andExpect(status().isCreated())
			.andExpect(content().json("{ id: 1, pauta: { id: 1, nome: \"Pauta A\" } }"))
			.andReturn();
	}
	
	@Test
	public void test_deve_retornar_codigo_http_200_ok_e_a_lista_de_todas_as_sessoes() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/v1/sessoes")
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON);
		
		when(sessaoService.buscar()).thenReturn(
				Arrays.asList(new Sessao(1L, 10, new Pauta(1L, "Pauta A", "Pauta A sobre AB")),
						new Sessao(2L, 20, new Pauta(1L, "Pauta B", "Pauta B sobre BC"))));
		
		// Action & Verification
		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().json("[ { id: 1, duracaoEmMinutos: 10, \"pauta\": { \"id\": 1 } }, { id: 2, duracaoEmMinutos: 20 } ]"))
			.andReturn();
		
	}
	
	private void assertThatExceptionIsMethodArgumentNotValid(MvcResult result) {
		Optional<MethodArgumentNotValidException> methodArgumentNotValidException = Optional
				.ofNullable((MethodArgumentNotValidException) result.getResolvedException());
		methodArgumentNotValidException.ifPresent((mnve) -> assertThat(mnve, is(notNullValue())));
		methodArgumentNotValidException
				.ifPresent((mnve) -> assertThat(mnve, is(instanceOf(MethodArgumentNotValidException.class))));
	}
}
