package anime.app.exceptions.exceptions;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

/**
 * This exception occurs when a request is not authenticated or doesn't have a given role.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends DefaultLocaleException {
	private final static String logMessage = "Authentication Exception, no message given";

	@Builder
	public AuthenticationException(String logMessage, @NonNull String userMessageTranslationKey, @Singular List<Object> translationParameters, @NonNull Locale originalLocale) {
		super(logMessage, userMessageTranslationKey, translationParameters, originalLocale);
	}

	@Override
	protected String getDefaultLogMessage() {
		return logMessage;
	}
}
