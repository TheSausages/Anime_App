package anime.app.exceptions.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

/**
 * This error only occurs during user registration. Should not be used anywhere else!
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class RegistrationException extends DefaultException {
	private static final String logMessage = "Registration Exception, no message given";

	private final Locale originalLocale;

	public RegistrationException(String userMessageTranslationKey, @NonNull Locale originalLocale) {
		super(userMessageTranslationKey, logMessage);
		this.originalLocale = originalLocale;
	}

	public RegistrationException(String userMessageTranslationKey, @NonNull Locale originalLocale, String logMessage) {
		super(userMessageTranslationKey, logMessage);
		this.originalLocale = originalLocale;
	}

	public Locale getOriginalLocale() {
		return originalLocale;
	}
}
