package anime.app.exceptions.exceptions;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

/**
 * This exception occurs when a request to the Anilist Api returns an error.
 * @see anime.app.services.anilist.AnilistService
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class AnilistException extends DefaultLocaleException {
	private final static String logMessage = "Anilist Exception, no message given";


	@Builder
	public AnilistException(String logMessage, @NonNull String userMessageTranslationKey, @Singular List<Object> translationParameters, Locale originalLocale) {
		super(logMessage, userMessageTranslationKey, translationParameters, originalLocale);
	}

	@Override
	protected String getDefaultLogMessage() {
		return logMessage;
	}
}
