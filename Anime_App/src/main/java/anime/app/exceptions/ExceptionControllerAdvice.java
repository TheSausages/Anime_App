package anime.app.exceptions;

import anime.app.exceptions.exceptions.AnilistException;
import anime.app.exceptions.exceptions.AuthenticationException;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.exceptions.exceptions.ValidationException;
import anime.app.openapi.model.ErrorDTO;
import anime.app.services.i18n.I18nServiceInterface;
import anime.app.utils.ErrorUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Class for handling custom and selected errors. Each custom error must have its own Exception Handler and Status.
 * The {@link anime.app.exceptions.exceptions.DefaultException#logMessage} for each should be logged here.
 */
@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
	private final I18nServiceInterface i18nService;

	@Autowired
	ExceptionControllerAdvice(I18nServiceInterface i18nService) {
		this.i18nService = i18nService;
	}

	/**
	 * Exception handler for {@link anime.app.exceptions.exceptions.AnilistException}.
	 * @param ex The exception
	 * @return A {@link anime.app.openapi.model.ErrorDTO} with {@link anime.app.exceptions.exceptions.DefaultException#userMessageTranslationKey} translation.
	 */
	@ExceptionHandler(AnilistException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	ErrorDTO anilistExceptionHandler(AnilistException ex) {
		log.error(ex.getCompleteLogMessage());

		return ErrorDTO.builder()
				.status(HttpStatus.SERVICE_UNAVAILABLE.value())
				.message(i18nService.getTranslation(ex.getUserMessageTranslationKey(), ex.getOriginalLocale(), ex.getTranslationParameters()))
				.timeStamp(ex.getErrorOccurrenceTime())
				.code(ex.getGeneratedErrorCode())
				.build();
	}

	/**
	 * Exception handler for {@link anime.app.exceptions.exceptions.AnilistException}.
	 * @param ex The exception
	 * @return A {@link anime.app.openapi.model.ErrorDTO} with {@link anime.app.exceptions.exceptions.DefaultException#userMessageTranslationKey} translation.
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ErrorDTO notFoundExceptionHandler(NotFoundException ex) {
		log.error(ex.getCompleteLogMessage());

		return ErrorDTO.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.message(i18nService.getTranslation(ex.getUserMessageTranslationKey(), ex.getTranslationParameters()))
				.timeStamp(ex.getErrorOccurrenceTime())
				.code(ex.getGeneratedErrorCode())
				.build();
	}

	/**
	 * Exception handler for {@link anime.app.exceptions.exceptions.AuthenticationException}.
	 * @param ex The exception
	 * @return A {@link anime.app.openapi.model.ErrorDTO} with {@link anime.app.exceptions.exceptions.DefaultException#userMessageTranslationKey} translation.
	 */
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	ErrorDTO authorizationExceptionHandler(AuthenticationException ex) {
		log.error(ex.getCompleteLogMessage());

		return ErrorDTO.builder()
				.status(HttpStatus.UNAUTHORIZED.value())
				.message(i18nService.getTranslation(ex.getUserMessageTranslationKey(), ex.getOriginalLocale(), ex.getTranslationParameters()))
				.timeStamp(ex.getErrorOccurrenceTime())
				.code(ex.getGeneratedErrorCode())
				.build();
	}

	/**
	 * Exception handler for {@link anime.app.exceptions.exceptions.ValidationException}.
	 * @param ex The exception
	 * @return A {@link anime.app.openapi.model.ErrorDTO} with {@link anime.app.exceptions.exceptions.DefaultException#userMessageTranslationKey} translation.
	 */
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ErrorDTO validationExceptionHandler(ValidationException ex) {
		log.error(ex.getCompleteLogMessage());

		return ErrorDTO.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(i18nService.getTranslation(ex.getUserMessageTranslationKey(), ex.getTranslationParameters()))
				.timeStamp(ex.getErrorOccurrenceTime())
				.code(ex.getGeneratedErrorCode())
				.build();
	}

	/**
	 * Custom {@link MethodArgumentNotValidException} handler. Handle the exception further, with a translated default error message returned to the user.
	 * @param ex The exception
	 * @param headers Headers of the request
	 * @param status Status of the request
	 * @param request The web request
	 * @return Further proces the exception in the default {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)} handler.
	 */
	@Override
	@NonNull
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
		LocalDateTime errorOccurrenceTime = LocalDateTime.now();
		UUID errorCode = UUID.randomUUID();
		String errorMessage = ErrorUtils.getErrorMessage(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), errorOccurrenceTime, errorCode);

		log.error(errorMessage);

		return handleExceptionInternal(
				ex,
				ErrorDTO.builder()
						.status(HttpStatus.UNPROCESSABLE_ENTITY.value())
						.message(i18nService.getTranslation("general.an-error-occurred"))
						.timeStamp(errorOccurrenceTime)
						.code(errorCode)
						.build(),
				headers,
				HttpStatus.UNPROCESSABLE_ENTITY,
				request
		);
	}

	/**
	 * Custom {@link HttpMediaTypeNotSupportedException} handler. Handle the exception further, with a translated default error message returned to the user.
	 * @param ex The exception
	 * @param headers Headers of the request
	 * @param status Status of the request
	 * @param request The web request
	 * @return Further proces the exception in the default {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)} handler.
	 */
	@Override
	@NonNull
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(@NonNull HttpMediaTypeNotSupportedException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
		LocalDateTime errorOccurrenceTime = LocalDateTime.now();
		UUID errorCode = UUID.randomUUID();
		String errorMessage = ErrorUtils.getErrorMessage(Objects.nonNull(ex.getMessage()) ? ex.getMessage() : "No message given", errorOccurrenceTime, errorCode);
		log.error("This media type is not supported: {}", errorMessage);

		return handleExceptionInternal(
				ex,
				ErrorDTO.builder()
						.status(status.value())
						.message(i18nService.getTranslation("general.an-error-occurred"))
						.timeStamp(errorOccurrenceTime)
						.code(errorCode)
						.build(),
				headers,
				status,
				request
		);
	}
}
