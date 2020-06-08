/*
 * @(#) AssociadoServiceTest.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.client.RestTemplate;

import com.sicredi.votacao.model.Associado;
import com.sicredi.votacao.repository.AssociadoRepository;
import com.sicredi.votacao.service.exception.BusinessException;

/**
 * Classe de testes unitários para o componente de serviço Associado.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(MockitoJUnitRunner.class)
public class AssociadoServiceTest {

	@InjectMocks
	private AssociadoService associadoService;
	
	@Mock
	private AssociadoRepository associadoRepository;
	
	@Mock
	private ApplicationEventPublisher publisher;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_cpf_nao_for_informado() {
		// Scenario
		Associado associado = new Associado(3L, null, "Isaias Haydon");
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.associadoService.salvar(associado, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_o_tamanho_do_cpf_for_maior_que_11_caracteres() {
		// Scenario
		Associado associado = new Associado(3L, "8670728400299", "Isaias Haydon");
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.associadoService.salvar(associado, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_nome_nao_for_informado() {
		// Scenario
		Associado associado = mock(Associado.class);
		when(associado.getCpf()).thenReturn("86707284002");
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.associadoService.salvar(associado, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_o_tamanho_do_nome_for_menor_que_3_caracteres() {
		// Scenario
		Associado associado = new Associado(3L, "86707284002", "Is");
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.associadoService.salvar(associado, response);
	}
	
	@Test
	public void test_deve_salvar_associado_com_sucesso() {
		// Scenario
		Associado expected = new Associado(1L, "70725860677", "Sergio Davis");
		
		when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString())).thenReturn("{\"status\": \"ABLE_TO_VOTE\"}");
		
		when(this.associadoRepository.save(Mockito.any(Associado.class))).thenReturn(expected);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Action
		Associado actual = this.associadoService.salvar(expected, response);
		
		// Verification
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getCpf(), actual.getCpf());
		assertEquals(expected.getNome(), actual.getNome());
	}
	
	@Test
	public void test_deve_deve_retornar_um_associado_pelo_codigo() {
		// Scenario
		Optional<Associado> expected = Optional.of(new Associado(1L, "45432804310", "Mateus Cardoso"));
		
		when(this.associadoRepository.findById(Mockito.anyLong())).thenReturn(expected);
		
		// Action
		Optional<Associado> actual = this.associadoService.buscarPorId(1L);
		
		// Verification
		assertTrue(actual.isPresent());
		assertEquals(expected.get().getId(), actual.get().getId());
		assertEquals(expected.get().getCpf(), actual.get().getCpf());
		assertEquals(expected.get().getNome(), actual.get().getNome());
	}
	
	@Test
	public void test_deve_deve_retornar_todos_os_associados() {
		// Scenario
		when(this.associadoRepository.findAll()).thenReturn(Arrays.asList(
				new Associado(1L, "19279191420", "Cauã Almeida"),
				new Associado(2L, "90762616288", "Paulo Costa")));
		
		// Action
		List<Associado> actual = this.associadoService.buscar();
		
		// Verification
		assertThat(actual).isNotNull().isNotEmpty().hasSize(2);

		assertThat(actual).extracting("id", "cpf", "nome")
				.contains(tuple(1L, "19279191420", "Cauã Almeida"),
						tuple(2L, "90762616288", "Paulo Costa"));
	}
	
}
