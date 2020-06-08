/*
 * @(#) CloudwatchMetricsEmitter.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.monitoring.cloudwatch;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

/**
 * Classe componente para emitir métricas no Amazon CloudWatch.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
public class CloudwatchMetricsEmitter {

	private static final String ENVIRONMENT_DIMENSION_NAME = "ENV";

	private String environmentName;

	public CloudwatchMetricsEmitter(final AmazonCloudWatch cloudwatchClient, final String environmentName) {
		this.cloudWatchClient = cloudwatchClient;
		this.environmentName = environmentName;
	}

	private AmazonCloudWatch cloudWatchClient;

	/**
	 * Método responsável por emitir métricas para o serviço CloudWatch.
	 * 
	 * @param metricNamespace o namespace da métrica.
	 * @param metricName      o nome da métrica.
	 * @param value           o valor da métrica.
	 */
	public void emitMetric(final String metricNamespace, final String metricName, double value) {
		Dimension dimension = new Dimension().withName(ENVIRONMENT_DIMENSION_NAME).withValue(environmentName);

		MetricDatum datum = new MetricDatum().withMetricName(metricName).withUnit(StandardUnit.None).withValue(value)
				.withDimensions(dimension);

		PutMetricDataRequest request = new PutMetricDataRequest().withNamespace(metricNamespace).withMetricData(datum);

		cloudWatchClient.putMetricData(request);
	}
}
