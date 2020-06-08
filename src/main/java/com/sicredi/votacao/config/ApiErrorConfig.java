/*
 * @(#) ApiErrorConfig.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Classe de configuração responsável por mapear api_errors.properties no path
 * da aplicação.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Configuration
public class ApiErrorConfig {

	@Bean
	public MessageSource apiErrorMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/api_errors");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
