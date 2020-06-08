/*
 * @(#) PautaServiceTest.java      1.00    29/05/2020
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

import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.repository.PautaRepository;
import com.sicredi.votacao.service.exception.BusinessException;

/**
 * Classe de testes unitários para o componente de serviço {@link PautaService}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(MockitoJUnitRunner.class)
public class PautaServiceTest {

	@InjectMocks
	private PautaService pautaService;
	
	@Mock
	private PautaRepository pautaRepository;
	
	@Mock
	private ApplicationEventPublisher publisher;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_nome_nao_for_informado() {
		// Scenario
		Pauta pauta = new Pauta(3L, null, "Pauta C sobre CD");
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.pautaService.salvar(pauta, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_o_tamanho_do_nome_for_menor_que_3_caracteres() {
		// Scenario
		Pauta pauta = new Pauta(3L, "Sa", "Pauta Sa sobre Sociedade Anonima");
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.pautaService.salvar(pauta, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_descricao_nao_for_informado() {
		// Scenario
		Pauta pauta = mock(Pauta.class);
		when(pauta.getNome()).thenReturn("Pauta C");
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.pautaService.salvar(pauta, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_o_tamanho_da_descricao_for_menor_que_10_caracteres() {
		// Scenario
		Pauta pauta = new Pauta(3L, "Sociedade Anonima", "Pauta Sa");
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.pautaService.salvar(pauta, response);
	}
	
	@Test
	public void test_deve_salvar_pauta_com_sucesso() {
		// Scenario
		Pauta expected = new Pauta(1L, "Pauta A", "Pauta sobre CDE");
		
		when(this.pautaRepository.save(Mockito.any(Pauta.class))).thenReturn(expected);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Action
		Pauta actual = this.pautaService.salvar(expected, response);
		
		// Verification
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getNome(), actual.getNome());
		assertEquals(expected.getDescricao(), actual.getDescricao());
	}
	
	@Test
	public void test_deve_deve_retornar_uma_pauta_pelo_codigo() {
		// Scenario
		Optional<Pauta> expected = Optional.of(new Pauta(1L, "Pauta A", "Pauta sobre CDE"));
		
		when(this.pautaRepository.findById(Mockito.anyLong())).thenReturn(expected);
		
		// Action
		Optional<Pauta> actual = this.pautaService.buscarPorId(1L);
		
		// Verification
		assertTrue(actual.isPresent());
		assertEquals(expected.get().getId(), actual.get().getId());
		assertEquals(expected.get().getNome(), actual.get().getNome());
		assertEquals(expected.get().getDescricao(), actual.get().getDescricao());
	}
	
	@Test
	public void test_deve_deve_retornar_todas_as_pautas() {
		// Scenario
		when(this.pautaRepository.findAll()).thenReturn(Arrays.asList(
				new Pauta(1L, "Pauta A", "Pauta A sobre AB"),
				new Pauta(2L, "Pauta B", "Pauta B sobre BC")));
		
		// Action
		List<Pauta> actual = this.pautaService.buscar();
		
		// Verification
		assertThat(actual).isNotNull().isNotEmpty().hasSize(2);

		assertThat(actual).extracting("id", "nome", "descricao")
				.contains(tuple(1L, "Pauta A", "Pauta A sobre AB"),
						tuple(2L, "Pauta B", "Pauta B sobre BC"));
	}
	
}
