/*
 * @(#) ErrorResponse.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.error;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * Classe utilitária para carregar dados de erro.
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
public class ErrorResponse {

	private final int statusCode;
	private final List<ApiError> errors;

	private ErrorResponse(int statusCode, List<ApiError> errors) {
		this.statusCode = statusCode;
		this.errors = errors;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public List<ApiError> getErrors() {
		return errors;
	}

	static ErrorResponse of(HttpStatus status, List<ApiError> errors) {
		return new ErrorResponse(status.value(), errors);
	}

	static ErrorResponse of(HttpStatus status, ApiError error) {
		return of(status, Collections.singletonList(error));
	}

	static class ApiError {
		private final String code;
		private final String message;

		public ApiError(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

	}

}

