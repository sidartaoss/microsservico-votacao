/*
 * @(#) VotacaoNotifier.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.event;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.sicredi.votacao.config.JsonConfig;
import com.sicredi.votacao.vo.ResultadoVotacao;

/**
 * Classe de evento para a notificação do resultado da votação.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
public class VotacaoNotifier {

	private AmazonSQS sqsClient;
	private String queueName;

	public VotacaoNotifier() {
	}

	public VotacaoNotifier(AmazonSQS sqsClient, String queueName) {
		this.sqsClient = sqsClient;
		this.queueName = queueName;
	}

	/**
	 * Método responsável por enviar uma mensagem para a fila SQS com o resultado da
	 * votação.
	 * 
	 * @param resultadoVotacao o objeto {@link ResultadoVotacao}.
	 */
	public void onResultadoVotacaoSalvo(final ResultadoVotacao resultadoVotacao) {
		final SendMessageRequest messageRequest = new SendMessageRequest(queueName,
				JsonConfig.asJsonString(resultadoVotacao));
		sqsClient.sendMessage(messageRequest);
	}

}
