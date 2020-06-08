/*
 * @(#) SessaoResourceIT.java      1.00    29/05/2020
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

import com.sicredi.votacao.dto.PautaDto;
import com.sicredi.votacao.dto.SessaoDto;

/**
 * Classe de testes integrados para o recurso {@link SessaoResource}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SessaoResourceIT {

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
		HttpEntity<SessaoDto> request = new HttpEntity<>(new SessaoDto(null, 10, new PautaDto(1L, null, null)));
		ResponseEntity<String> actualResponse = this.restTemplate.postForEntity("/v1/sessoes", request, String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse.getBody(), false);
	}

	@Test
	public void test_3_deve_buscar_a_lista_de_sessoes() throws JSONException {
		String expectedResponse = "[ { id: 1, duracaoEmMinutos: 10, pauta: { id: 1, nome: \"Pauta D\" } } ]";
		String actualResponse = this.restTemplate.getForObject("/v1/sessoes", String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
	}

}
