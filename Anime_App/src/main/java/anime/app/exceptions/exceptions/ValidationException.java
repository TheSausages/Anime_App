package anime.app.exceptions.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception occurs during an object validation, when a constraint was violated.
 * @see anime.app.configuration.beans.ValidatorConfiguration.ThrowingValidator
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ValidationException extends DefaultException {
	private final static String logMessage = "Constraint Violation Exception, no message given";

	public ValidationException(@NonNull String userMessageTranslationKey) {
		super(userMessageTranslationKey, logMessage);
	}

	public ValidationException(@NonNull String userMessageTranslationKey, String logMessage) {
		super(userMessageTranslationKey, logMessage);
	}
}
