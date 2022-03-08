package anime.app.exceptions.exceptions;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * This exception occurs during an object validation, when a constraint was violated.
 * @see anime.app.configuration.beans.ValidatorConfiguration.ThrowingValidator
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ValidationException extends DefaultException {
	private final static String logMessage = "Constraint Violation Exception, no message given";

	@Builder
	public ValidationException(String logMessage, @NonNull String userMessageTranslationKey, @Singular List<Object> translationParameters) {
		super(logMessage, userMessageTranslationKey, translationParameters);
	}

	@Override
	protected String getDefaultLogMessage() {
		return logMessage;
	}
}
