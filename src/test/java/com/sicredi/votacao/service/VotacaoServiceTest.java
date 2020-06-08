/*
 * @(#) VotacaoServiceTest.java      1.00    29/05/2020
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
import java.util.Calendar;
import java.util.Date;
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

import com.sicredi.votacao.model.Associado;
import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.TipoVoto;
import com.sicredi.votacao.model.Votacao;
import com.sicredi.votacao.model.VotacaoPk;
import com.sicredi.votacao.repository.VotacaoRepository;
import com.sicredi.votacao.service.exception.BusinessException;

/**
 * Classe de testes unitários para o componente de serviço Votacao.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(MockitoJUnitRunner.class)
public class VotacaoServiceTest {

	@InjectMocks
	private VotacaoService votacaoService;
	
	@Mock
	private AssociadoService associadoService;
	
	@Mock
	private SessaoService sessaoService;
	
	@Mock
	private ApplicationEventPublisher publisher;
	
	@Mock
	private VotacaoRepository votacaoRepository;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_associado_id_nao_for_informado() {
		// Scenario
		Votacao votacao = new Votacao(new VotacaoPk(null, 2L), TipoVoto.N);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.votacaoService.salvar(votacao, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_sessao_id_nao_for_informado() {
		// Scenario
		Votacao votacao = new Votacao(new VotacaoPk(1L, null), TipoVoto.S);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.votacaoService.salvar(votacao, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_associado_id_e_sessao_id_nao_forem_informados() {
		// Scenario
		Votacao votacao = new Votacao(null, TipoVoto.N);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.votacaoService.salvar(votacao, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_voto_nao_foi_informado() {
		// Scenario
		Votacao votacao = new Votacao(new VotacaoPk(1L, null), TipoVoto.S);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.votacaoService.salvar(votacao, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_sessao_informada_nao_existir_na_base_de_dados() {
		// Scenario
		Votacao expected = new Votacao(new VotacaoPk(11L, 100L), TipoVoto.S);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.votacaoService.salvar(expected, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_sessao_informada_ja_estiver_finalizada() {
		// Scenario
		Votacao expected = new Votacao(new VotacaoPk(11L, 100L), TipoVoto.S);
		
		Calendar cal = Calendar.getInstance();
		Date momentoInicio = cal.getTime();
		cal.add(Calendar.MINUTE, 10);
		Date momentoTermino = cal.getTime();
		
		when(this.sessaoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.of(new Sessao(100L, 10, new Pauta(1L, "Pauta A", "Pauta sobre AB"), momentoInicio, momentoTermino)));
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.votacaoService.salvar(expected, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_associado_informado_nao_existir_na_base_de_dados() {
		// Scenario
		Votacao expected = new Votacao(new VotacaoPk(11L, 100L), TipoVoto.S);
		
		when(this.sessaoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.of(new Sessao(100L, 10, new Pauta(1L, "Pauta A", "Pauta sobre AB"))));
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.votacaoService.salvar(expected, response);
	}
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_voto_ja_existir_na_base_de_dados() {
		// Scenario
		Votacao expected = new Votacao(new VotacaoPk(11L, 100L), TipoVoto.S);

		when(this.associadoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.of(new Associado(11L, "44799375792", "Arthur Fernandes")));
		when(this.sessaoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.of(new Sessao(100L, 10, new Pauta(1L, "Pauta A", "Pauta sobre AB"))));	
		
		when(this.votacaoService.buscarPorId(Mockito.any(VotacaoPk.class))).thenReturn(Optional.of(expected));
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.votacaoService.salvar(expected, response);
	}
	
	@Test
	public void test_deve_salvar_votacao_com_sucesso() {
		// Scenario
		Votacao expected = new Votacao(new VotacaoPk(1L, 2L), TipoVoto.N);
		
		when(this.associadoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.of(new Associado(11L, "44799375792", "Arthur Fernandes")));
		when(this.sessaoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.of(new Sessao(100L, 10, new Pauta(1L, "Pauta A", "Pauta sobre AB"))));
		
		when(this.votacaoRepository.save(Mockito.any(Votacao.class))).thenReturn(expected);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Action
		Votacao actual = this.votacaoService.salvar(expected, response);
		
		// Verification
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		
		assertEquals(expected.getId().getAssociadoId(), actual.getId().getAssociadoId());
		assertEquals(expected.getId().getSessaoId(), actual.getId().getSessaoId());
		assertEquals(expected.getVoto(), actual.getVoto());
	}
	
	@Test
	public void test_deve_deve_retornar_uma_votacao_pelo_codigo() {
		// Scenario
		VotacaoPk pk = new VotacaoPk(10L, 20L);
		
		Optional<Votacao> expected = Optional.of(new Votacao(pk, TipoVoto.N));
		
		when(this.votacaoRepository.findById(Mockito.any(VotacaoPk.class))).thenReturn(expected);
		
		// Action
		Optional<Votacao> actual = this.votacaoService.buscarPorId(pk);
		
		// Verification
		assertTrue(actual.isPresent());
		assertThat(actual.get().getId()).isNotNull();
		
		assertEquals(expected.get().getId().getAssociadoId(), actual.get().getId().getAssociadoId());
		assertEquals(expected.get().getId().getSessaoId(), actual.get().getId().getSessaoId());
		assertEquals(expected.get().getVoto(), actual.get().getVoto());
	}
	
	@Test
	public void test_deve_deve_retornar_todas_as_votacoes() {
		// Scenario
		VotacaoPk pk1 = new VotacaoPk(10L, 20L);
		VotacaoPk pk2 = new VotacaoPk(30L, 20L);

		when(this.votacaoRepository.findAll()).thenReturn(Arrays.asList(
				new Votacao(pk1, TipoVoto.N),
				new Votacao(pk2, TipoVoto.S)));
		
		// Action
		List<Votacao> actual = this.votacaoService.buscar();
		
		// Verification
		assertThat(actual).isNotNull().isNotEmpty().hasSize(2);

		assertThat(actual).extracting("id", "voto")
				.contains(tuple(pk1, TipoVoto.N),
						tuple(pk2, TipoVoto.S));
	}
	
}
