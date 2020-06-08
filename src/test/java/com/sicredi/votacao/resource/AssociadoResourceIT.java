/*
 * @(#) AssociadoResourceIT.java      1.00    29/05/2020
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

/**
 * Classe de testes integrados para o recurso {@link AssociadoResource}.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssociadoResourceIT {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void test_1_salvar_uma_associado() throws JSONException {
		String expectedResponse = "{ id: 1, nome: \"Davi Gomes\" }";
		HttpEntity<AssociadoDto> request = new HttpEntity<>(new AssociadoDto(null, "64076065433", "Davi Gomes"));
		ResponseEntity<String> actualResponse = this.restTemplate.postForEntity("/v1/associados", request,
				String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse.getBody(), false);
	}

	@Test
	public void test_2_buscar_associados() throws JSONException {
		String expectedResponse = "[ { id: 1, nome: \"Davi Gomes\", cpf: \"64076065433\" } ]";
		String actualResponse = this.restTemplate.getForObject("/v1/associados", String.class);
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
	}

}
