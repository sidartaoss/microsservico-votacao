/*
 * @(#) ApiExceptionHandler.java      1.00    29/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.error;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sicredi.votacao.error.ErrorResponse.ApiError;
import com.sicredi.votacao.service.exception.BusinessException;

/**
 * Classe de componentes responsável pelo tratamento de exceções dos erros da
 * aplicação.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@RestControllerAdvice
public class ApiExceptionHandler {

	private static final String NENHUMA_MENSAGEM_DISPONIVEL = "Nenhuma mensagem disponivel";
	private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

	private final MessageSource apiErrorMessageSource;

	public ApiExceptionHandler(MessageSource apiErrorMessageSource) {
		this.apiErrorMessageSource = apiErrorMessageSource;
	}

	/**
	 * Método responsável pelo tratamento de exceções do tipo
	 * {@link MethodArgumentNotValidException}.
	 * 
	 * @param exception o objeto {@link MethodArgumentNotValidException}.
	 * @param locale    o objeto {@link Locale}.
	 * @return o objeto {@link ResponseEntity}.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException exception,
			Locale locale) {
		Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();

		List<ApiError> apiErrors = errors.map(ObjectError::getDefaultMessage).map(code -> toApiError(code, locale))
				.collect(toList());

		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	/**
	 * Método responsável pelo tratamento de exceções do tipo
	 * {@link BusinessException}.
	 * 
	 * @param exception o objeto {@link BusinessException}.
	 * @param locale    o objeto {@link Locale}.
	 * @return o objeto {@link ResponseEntity}.
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception, Locale locale) {
		final String errorCode = exception.getCode();
		final HttpStatus status = exception.getStatus();

		final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale));
		return ResponseEntity.badRequest().body(errorResponse);
	}

	/**
	 * Método responsável pelo tratamento de exceções do tipo {@link Exception}.
	 * 
	 * @param exception o objeto {@link Exception}.
	 * @param locale    o objeto {@link Locale}.
	 * @return o objeto {@link ResponseEntity}.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handlerInternalServerError(Exception exception, Locale locale) {
		LOG.error("Erro nao esperado", exception);
		final String errorCode = "error-0";
		final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale));
		return ResponseEntity.status(status).body(errorResponse);
	}

	/**
	 * Método responsável pelo tratamento de exceções do tipo
	 * {@link HttpMessageNotReadableException}.
	 * 
	 * @param exception o objeto {@link HttpMessageNotReadableException}.
	 * @param locale    o objeto {@link Locale}.
	 * @return o objeto {@link ResponseEntity}.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException exception, Locale locale) {
		final String errorCode = "error-1";
		final HttpStatus status = HttpStatus.BAD_REQUEST;
		final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale));
		return ResponseEntity.badRequest().body(errorResponse);
	}

	/**
	 * Método responsável por instanciar um objeto {@link ApiError}.
	 * 
	 * @param code   o código do erro.
	 * @param locale o Locale da mensagem.
	 * @param args   o array de argumentos.
	 * @return um objeto {@link ApiError}.
	 */
	private ApiError toApiError(String code, Locale locale, Object... args) {
		String message;
		try {
			message = apiErrorMessageSource.getMessage(code, args, locale);
		} catch (NoSuchMessageException e) {
			LOG.error("Não foi possível encontrar mensagem para o código {} no locale {}", code, locale);
			message = NENHUMA_MENSAGEM_DISPONIVEL;
		}
		return new ApiError(code, message);
	}
}
