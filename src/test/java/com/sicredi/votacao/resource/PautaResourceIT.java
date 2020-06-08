/*
 * @(#) PautaResourceIT.java      1.00    29/05/2020
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

/**
 * Classe de testes integrados para o recurso {@link PautaResource}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PautaResourceIT {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void test_1_salvar_uma_pauta() throws JSONException {
		String expectedResponse = "{ id: 1, nome: \"Pauta A\" }";
		HttpEntity<PautaDto> request = new HttpEntity<>(new PautaDto(null, "Pauta A", "Pauta A sobre AB"));
		ResponseEntity<String> actualResponse = this.restTemplate.postForEntity("/v1/pautas", request, String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse.getBody(), false);
	}

	@Test
	public void test_2_buscar_a_lista_de_pautas() throws JSONException {
		String expectedResponse = "[ { id: 1, nome: \"Pauta A\", descricao: \"Pauta A sobre AB\" } ]";
		String actualResponse = this.restTemplate.getForObject("/v1/pautas", String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
	}

}
