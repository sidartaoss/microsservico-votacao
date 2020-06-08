/*
 * @(#) VotacaoResourceIT.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.resource;

import org.json.JSONException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.sicredi.votacao.dto.AssociadoDto;
import com.sicredi.votacao.dto.PautaDto;
import com.sicredi.votacao.dto.SessaoDto;
import com.sicredi.votacao.dto.VotacaoDto;
import com.sicredi.votacao.model.TipoVoto;

/**
 * Classe de testes integrados para o recurso {@link VotacaoResource}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VotacaoResourceIT {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void test_1_deve_salvar_uma_nova_pauta() throws JSONException {
		String expectedResponse = "{ id: 1, nome: \"Pauta D\" }";
		HttpEntity<PautaDto> request = new HttpEntity<>(new PautaDto(null, "Pauta D", "Pauta D sobre DEF"));
		ResponseEntity<String> actualResponse = this.restTemplate.postForEntity("/v1/pautas", request, String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse.getBody(), false);
	}

	@Test
	public void test_2_deve_salvar_uma_nova_sessao() throws JSONException {
		String expectedResponse = "{ id: 1, \"duracaoEmMinutos\": 10, \"pauta\": { id: 1 } }";
		HttpEntity<SessaoDto> request = new HttpEntity<>(new SessaoDto(1L, 10, new PautaDto(1L, null, null)));
		ResponseEntity<String> actualResponse = this.restTemplate.postForEntity("/v1/sessoes", request, String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse.getBody(), false);
	}

	@Test
	public void test_3_deve_salvar_um_novo_associado() throws JSONException {
		String expectedResponse = "{ id: 1, nome: \"Davi Gomes\" }";
		HttpEntity<AssociadoDto> request = new HttpEntity<>(new AssociadoDto(null, "64076065433", "Davi Gomes"));
		ResponseEntity<String> actualResponse = this.restTemplate.postForEntity("/v1/associados", request,
				String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse.getBody(), false);
	}

	@Test
	public void test_4_deve_salvar_um_novo_voto() throws JSONException {
		String expectedResponse = "{ associadoId: 1, sessaoId: 1, \"voto\": \"S\" }";
		HttpEntity<VotacaoDto> request = new HttpEntity<>(new VotacaoDto(1L, 1L, TipoVoto.S));
		ResponseEntity<String> actualResponse = this.restTemplate.postForEntity("/v1/votacoes", request, String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse.getBody(), false);
	}

	@Test
	public void test_5_deve_buscar_todos_os_votos() throws JSONException {
		String expectedResponse = "[ { associadoId: 1, sessaoId: 1, \"voto\": \"S\" } ]";
		String actualResponse = this.restTemplate.getForObject("/v1/votacoes", String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
	}

}
