/*
 * @(#) AmazonServicesConfig.java      1.00    30/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.sicredi.votacao.event.VotacaoNotifier;
import com.sicredi.votacao.monitoring.cloudwatch.CloudwatchMetricsEmitter;

/**
 * Classe de configuração para serviços Amazon.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Configuration
public class AmazonServicesConfig {

    private static final String PUBLISHED_RESULTADO_VOTACAO_QUEUE = "published_resultado_votacao_queue";

    @Bean
    public VotacaoNotifier votacaoNotifier() {
        return new VotacaoNotifier(
                AmazonSQSClientBuilder.standard().
                        withRegion(Regions.US_EAST_1.getName()).build(),
                PUBLISHED_RESULTADO_VOTACAO_QUEUE
        );
    }
    
    @Bean
    public CloudwatchMetricsEmitter metricsEmitter() {
        final String region = Regions.US_EAST_1.getName();
        final String stage = "prod";

        AmazonCloudWatch cloudwatchClient = AmazonCloudWatchClientBuilder.standard()
                .withRegion(region)
                .build();

        return new CloudwatchMetricsEmitter(cloudwatchClient, stage);
    }

}
