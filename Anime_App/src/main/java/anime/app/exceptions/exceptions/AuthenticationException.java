package anime.app.exceptions.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

/**
 * This exception occurs when a request is not authenticated or doesn't have a given role.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends DefaultException {
	private final static String logMessage = "Authentication Exception, no message given";
	private final Locale originalLocale;

	public AuthenticationException(String userMessageTranslationKey, @NonNull Locale originalLocale) {
		super(userMessageTranslationKey, logMessage);
		this.originalLocale = originalLocale;
	}

	public AuthenticationException(String userMessageTranslationKey, @NonNull Locale originalLocale, String logMessage) {
		super(userMessageTranslationKey, logMessage);
		this.originalLocale = originalLocale;
	}

	public Locale getOriginalLocale() {
		return originalLocale;
	}
}
