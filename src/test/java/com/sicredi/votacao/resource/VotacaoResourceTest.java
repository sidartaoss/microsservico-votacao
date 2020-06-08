/*
 * @(#) VotacaoResourceTest.java      1.00    29/05/2020
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

import com.sicredi.votacao.model.TipoVoto;
import com.sicredi.votacao.model.Votacao;
import com.sicredi.votacao.model.VotacaoPk;
import com.sicredi.votacao.service.VotacaoService;

/**
 * Classe de testes unitários para o recurso {@link VotacaoResource}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(SpringRunner.class)
@WebMvcTest(VotacaoResource.class)
public class VotacaoResourceTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private VotacaoService votacaoService;
	
	@Test
	public void test_deve_retornar_erro_400_bad_request_quando_salvar_e_associado_id_nao_for_informado() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/v1/votacoes")
				.accept(APPLICATION_JSON)
				.content("{ \"sessaoId\": 2, \"voto\": \"N\" }")
				.contentType(APPLICATION_JSON);
		
		// Action & Verification
		MvcResult result = mockMvc.perform(requestBuilder)
			.andExpect(status().isBadRequest())
			.andExpect(content().json("{ \"statusCode\":400, \"errors\":[{\"code\":\"votacoes-2\"}] }"))
			.andReturn();
		assertThatExceptionIsMethodArgumentNotValid(result);
		
	}
	
	@Test
	public void test_deve_retornar_erro_400_bad_request_quando_salvar_e_sessao_id_nao_for_informado() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/v1/votacoes")
				.accept(APPLICATION_JSON)
				.content("{ \"associadoId\": 1, \"voto\": \"N\" }")
				.contentType(APPLICATION_JSON);
		
		// Action & Verification
		MvcResult result = mockMvc.perform(requestBuilder)
			.andExpect(status().isBadRequest())
			.andExpect(content().json("{ \"statusCode\":400, \"errors\":[{\"code\":\"votacoes-3\"}] }"))
			.andReturn();
		assertThatExceptionIsMethodArgumentNotValid(result);
		
	}
	
	@Test
	public void test_deve_retornar_erro_400_bad_request_quando_salvar_e_associado_id_e_sessao_id_nao_forem_informados() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/v1/votacoes")
				.accept(APPLICATION_JSON)
				.content("{ \"voto\": \"N\" }")
				.contentType(APPLICATION_JSON);
		
		// Action & Verification
		MvcResult result = mockMvc.perform(requestBuilder)
			.andExpect(status().isBadRequest())
			.andExpect(content().json("{ \"statusCode\":400, \"errors\":[{\"code\":\"votacoes-2\"}, {\"code\":\"votacoes-3\"}] }"))
			.andReturn();
		assertThatExceptionIsMethodArgumentNotValid(result);
		
	}
	
	@Test
	public void test_deve_retornar_erro_400_bad_request_quando_salvar_e_o_voto_nao_for_informado() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/v1/votacoes")
				.accept(APPLICATION_JSON)
				.content("{ \"associadoId\": 1, \"sessaoId\": 2 }")
				.contentType(APPLICATION_JSON);
		
		// Action & Verification
		MvcResult result = mockMvc.perform(requestBuilder)
			.andExpect(status().isBadRequest())
			.andExpect(content().json("{\"statusCode\":400, \"errors\":[{\"code\":\"votacoes-4\"}]}"))
			.andReturn();
		assertThatExceptionIsMethodArgumentNotValid(result);
		
	}

	@Test
	public void test_deve_retornar_codigo_http_201_e_salvar_um_novo_voto_com_sucesso() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/v1/votacoes")
				.accept(APPLICATION_JSON)
				.content("{ \"associadoId\": 1, \"sessaoId\": 2, \"voto\": \"N\" }")
				.contentType(APPLICATION_JSON);
		
		when(votacaoService.salvar(any(Votacao.class), any(HttpServletResponse.class))).thenReturn(
				new Votacao(new VotacaoPk(1L, 2L), TipoVoto.N));
		
		// Action & Verification
		mockMvc.perform(requestBuilder)
			.andExpect(status().isCreated())
			.andExpect(content().json("{ associadoId: 1, sessaoId: 2 , voto: \"N\" }"))
			.andReturn();
		
	}
	
	@Test
	public void test_deve_retornar_a_lista_de_todos_os_votos() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/v1/votacoes")
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON);
		
		when(votacaoService.buscar()).thenReturn(
				Arrays.asList(new Votacao(new VotacaoPk(1L, 2L), TipoVoto.S),
						new Votacao(new VotacaoPk(2L, 2L), TipoVoto.N)));
		
		// Action & Verification
		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().json("[ { associadoId: 1, sessaoId: 2, voto: \"S\" }, { associadoId: 2, sessaoId: 2, voto: \"N\" } ]"))
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
























