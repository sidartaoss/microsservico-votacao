/*
 * @(#) CreatedResourceListener.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.event.listener;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.sicredi.votacao.event.CreatedResourceEvent;

/**
 * Classe ouvinte da aplicação que escuta o evento de criação de um novo
 * recurso.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Component
public class CreatedResourceListener implements ApplicationListener<CreatedResourceEvent> {

	/**
	 * Método responsável por ouvir o evento {@link CreatedResourceEvent}.
	 * 
	 * @param o objeto {@link CreatedResourceEvent};
	 */
	@Override
	public void onApplicationEvent(CreatedResourceEvent event) {
		HttpServletResponse response = event.getResponse();
		Long id = event.getId();
		this.addHeaderLocation(response, id);
	}

	/**
	 * Método responsável por adicionar o {@code id} ao atributo {@code Location} no
	 * header do response.
	 * 
	 * @param response o objeto {@link HttpServletResponse}.
	 * @param id       o {@code id} do recurso criado.
	 */
	private void addHeaderLocation(HttpServletResponse response, Long id) {
		URI uri = fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
