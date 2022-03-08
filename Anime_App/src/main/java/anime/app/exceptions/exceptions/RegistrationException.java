package anime.app.exceptions.exceptions;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

/**
 * This error only occurs during user registration. Should not be used anywhere else!
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class RegistrationException extends DefaultLocaleException {
	private static final String logMessage = "Registration Exception, no message given";

	@Builder
	public RegistrationException(String logMessage, @NonNull String userMessageTranslationKey, @Singular List<Object> translationParameters, @NonNull Locale originalLocale) {
		super(logMessage, userMessageTranslationKey, translationParameters, originalLocale);
	}

	@Override
	protected String getDefaultLogMessage() {
		return logMessage;
	}
}
