/*
 * @(#) AssociadoResourceTest.java      1.00    28/05/2020
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

import com.sicredi.votacao.model.Associado;
import com.sicredi.votacao.service.AssociadoService;

/**
 * Classe de testes unitários para o recurso {@link AssociadoResource}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AssociadoResource.class)
public class AssociadoResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AssociadoService associadoService;

	@Test
	public void test_deve_retornar_erro_400_bad_request_quando_salvar_e_cpf_nao_for_informado() throws Exception {
		// Scenario
		String requestBody = "{ \"nome\" : \"Danilo Ferreira\" }";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/associados")
				.accept(APPLICATION_JSON)
				.content(requestBody)
				.contentType(APPLICATION_JSON);

		// Action & Verification
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"statusCode\":400, \"errors\":[{\"code\":\"associados-1\"}]}"))
				.andReturn();
		assertThatExceptionIsMethodArgumentNotValid(result);
	}
	
	@Test
	public void test_deve_retornar_erro_400_bad_request_quando_salvar_e_o_tamanho_do_cpf_for_maior_que_11_caracteres() throws Exception {
		// Scenario
		String requestBody = "{ \"nome\" : \"Danilo Ferreira\", \"cpf\": \"0874131901099\" }";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/associados")
				.accept(APPLICATION_JSON)
				.content(requestBody)
				.contentType(APPLICATION_JSON);

		// Action & Verification
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"statusCode\":400, \"errors\":[{\"code\":\"associados-2\"}]}"))
				.andReturn();
		assertThatExceptionIsMethodArgumentNotValid(result);
	}
	
	@Test
	public void test_deve_retornar_erro_400_bad_request_quando_salvar_e_o_nome_nao_for_informado() throws Exception {
		// Scenario
		String requestBody = "{ \"cpf\": \"08741319010\" }";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/associados")
				.accept(APPLICATION_JSON)
				.content(requestBody)
				.contentType(APPLICATION_JSON);

		// Action & Verification
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"statusCode\":400, \"errors\":[{\"code\":\"associados-3\"}]}"))
				.andReturn();
		assertThatExceptionIsMethodArgumentNotValid(result);
	}
	
	@Test
	public void test_deve_retornar_erro_400_bad_request_quando_salvar_e_o_tamanho_do_nome_for_menor_que_3_caracteres() throws Exception {
		// Scenario
		String requestBody = "{ \"nome\": \"Jo\", \"cpf\": \"08741319010\" }";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/associados")
				.accept(APPLICATION_JSON)
				.content(requestBody)
				.contentType(APPLICATION_JSON);

		// Action & Verification
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"statusCode\":400, \"errors\":[{\"code\":\"associados-4\"}]}"))
				.andReturn();
		assertThatExceptionIsMethodArgumentNotValid(result);
	}

	@Test
	public void test_deve_retornar_201_e_criar_um_novo_associado_com_sucesso() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/associados")
				.accept(APPLICATION_JSON)
				.content("{ \"nome\" : \"Danilo Ferreira\", \"cpf\" : \"72850104647\" }")
				.contentType(APPLICATION_JSON);

		when(associadoService.salvar(any(Associado.class), any(HttpServletResponse.class)))
				.thenReturn(new Associado(1L, "72850104647", "Danilo Ferreira"));

		// Action & Verification
		mockMvc.perform(requestBuilder)
				.andExpect(status().isCreated())
				.andExpect(content().json("{ id: 1, cpf: \"72850104647\", nome: \"Danilo Ferreira\" }"))	
				.andReturn();

	}

	@Test
	public void test_deve_retornar_a_lista_de_todos_os_associados() throws Exception {
		// Scenario
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/associados")
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON);

		when(associadoService.buscar()).thenReturn(
				Arrays.asList(new Associado(1L, "72850104647", "Danilo Ferreira"),
								new Associado(2L, "55546671394", "Diogo Dias")));

		// Action & Verification
		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json("[{ id: 1, cpf: \"72850104647\" }, { id: 2, cpf: \"55546671394\" }]"))
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
