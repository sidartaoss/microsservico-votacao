/*
 * @(#) SessaoServiceTest.java      1.00    29/05/2020
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
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.TipoStatus;
import com.sicredi.votacao.repository.SessaoRepository;
import com.sicredi.votacao.service.exception.BusinessException;

/**
 * Classe de testes unitários para o componente de serviço {@link SessaoService}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(MockitoJUnitRunner.class)
public class SessaoServiceTest {

	@InjectMocks
	private SessaoService sessaoService;
	
	@Mock
	private PautaService pautaService;
	
	@Mock
	private SessaoRepository sessaoRepository;
	
	@Mock
	private ApplicationEventPublisher publisher;
	
	@Mock
	private ThreadPoolTaskScheduler taskScheduler;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test_deve_disparar_excecao_quando_salvar_e_pauta_nao_for_informada() {
		// Scenario
		Sessao sessao = new Sessao(1L, 15, null);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Verification
		exception.expect(BusinessException.class);
		
		// Action
		this.sessaoService.salvar(sessao, response);
	}
	
	@Test
	public void test_deve_salvar_sessao_com_sucesso() {
		// Scenario
		Optional<Pauta> pauta = Optional.of(new Pauta(2L, "Pauta D", "Pauta sobre DEF"));
		when(this.pautaService.buscarPorId(Mockito.anyLong())).thenReturn(pauta);
		
		Sessao expected = new Sessao(1L, 15, pauta.get(), new Date());
		
		when(this.sessaoRepository.save(Mockito.any(Sessao.class))).thenReturn(expected);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		// Action
		Sessao actual = this.sessaoService.salvar(expected, response);
		
		// Verification
		assertThat(actual).isNotNull();
		assertThat(actual.getPauta()).isNotNull();
		
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getPauta().getId(), actual.getPauta().getId());
		assertEquals(expected.getPauta().getNome(), actual.getPauta().getNome());
		assertEquals(expected.getPauta().getDescricao(), actual.getPauta().getDescricao());
		assertEquals(expected.getMomentoInicio(), actual.getMomentoInicio());
		assertEquals(expected.getDuracaoEmMinutos(), actual.getDuracaoEmMinutos());
		assertEquals(expected.getStatus(), actual.getStatus());
	}
	
	@Test
	public void test_deve_deve_retornar_uma_sessao_em_andamento_pelo_codigo() {
		// Scenario
		Integer duracaoEmMinutos = Integer.valueOf(15);
		
		Calendar cal = Calendar.getInstance();
		Date momentoInicio = cal.getTime();
		
		Sessao expected = new Sessao(1L, duracaoEmMinutos, new Pauta(2L, "Pauta D", "Pauta sobre DEF"), momentoInicio);
		when(this.sessaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
		
		// Action
		Optional<Sessao> actual = this.sessaoService.buscarPorId(1L);
		
		// Verification
		assertTrue(actual.isPresent());
		assertThat(actual.get().getPauta()).isNotNull();
		
		assertEquals(expected.getId(), actual.get().getId());
		assertEquals(expected.getPauta().getId(), actual.get().getPauta().getId());
		assertEquals(expected.getPauta().getNome(), actual.get().getPauta().getNome());
		assertEquals(expected.getPauta().getDescricao(), actual.get().getPauta().getDescricao());
		assertEquals(expected.getMomentoInicio(), actual.get().getMomentoInicio());
		assertEquals(expected.getDuracaoEmMinutos(), actual.get().getDuracaoEmMinutos());
		assertEquals(TipoStatus.EM_ANDAMENTO, actual.get().getStatus());
	}
	
	@Test
	public void test_deve_deve_retornar_uma_sessao_finalizada_pelo_codigo() {
		// Scenario
		Integer duracaoEmMinutos = Integer.valueOf(15);
		
		Calendar cal = Calendar.getInstance();
		Date momentoInicio = cal.getTime();
		cal.add(Calendar.MINUTE, duracaoEmMinutos);
		Date momentoTermino = cal.getTime();
		
		Sessao expected = new Sessao(1L, duracaoEmMinutos, new Pauta(2L, "Pauta D", "Pauta sobre DEF"), momentoInicio, momentoTermino);
		when(this.sessaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
		
		// Action
		Optional<Sessao> actual = this.sessaoService.buscarPorId(1L);
		
		// Verification
		assertTrue(actual.isPresent());
		assertThat(actual.get().getPauta()).isNotNull();
		
		assertEquals(expected.getId(), actual.get().getId());
		assertEquals(expected.getPauta().getId(), actual.get().getPauta().getId());
		assertEquals(expected.getPauta().getNome(), actual.get().getPauta().getNome());
		assertEquals(expected.getPauta().getDescricao(), actual.get().getPauta().getDescricao());
		assertEquals(expected.getMomentoInicio(), actual.get().getMomentoInicio());
		assertEquals(expected.getDuracaoEmMinutos(), actual.get().getDuracaoEmMinutos());
		assertEquals(expected.getMomentoTermino(), actual.get().getMomentoTermino());
		assertEquals(TipoStatus.FINALIZADA, actual.get().getStatus());
	}
	
	@Test
	public void test_deve_deve_retornar_a_lista_de_todas_as_sessoes() {
		// Scenario
		Integer duracaoEmMinutos = Integer.valueOf(15);
		
		Calendar cal = Calendar.getInstance();
		Date momentoInicio = cal.getTime();
		cal.add(Calendar.MINUTE, duracaoEmMinutos);
		Date momentoTermino = cal.getTime();
		
		Pauta pauta2 = new Pauta(2L, "Pauta D", "Pauta sobre DEF");
		Pauta pauta3 = new Pauta(3L, "Pauta E", "Pauta sobre EFG");
		when(this.sessaoRepository.findAll()).thenReturn(Arrays.asList(
				new Sessao(1L, duracaoEmMinutos, pauta2, momentoInicio, momentoTermino),
				new Sessao(2L, duracaoEmMinutos, pauta3, momentoInicio, momentoTermino)));
		
		// Action
		List<Sessao> actual = this.sessaoService.buscar();
		
		// Verification
		assertThat(actual).isNotNull().isNotEmpty().hasSize(2);

		assertThat(actual).extracting("id", "duracaoEmMinutos", "pauta", "momentoInicio", "momentoTermino")
				.contains(tuple(1L, duracaoEmMinutos, pauta2, momentoInicio, momentoTermino),
						tuple(2L, duracaoEmMinutos, pauta3, momentoInicio, momentoTermino));
	}
	
}
