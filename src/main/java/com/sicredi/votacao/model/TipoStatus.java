/*
 * @(#) StatusSessao.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.model;

import io.swagger.annotations.ApiModel;

/**
 * Classe enum que representa os status da Sessão: Em Andamento ou Finalizada.
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@ApiModel(description = "Classe enum que representa os status da Sessão.")
public enum TipoStatus {

	/**
	 * Em Andamento
	 */
	EM_ANDAMENTO,
	/**
	 * Finalizada
	 */
	FINALIZADA
}
