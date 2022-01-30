package anime.app.exceptions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

/**
 * This exception occurs when a request to the Anilist Api returns an error.
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class AnilistException extends DefaultException {
	private final static String logMessage = "Anilist Exception, no message given";
	private final Locale originalLocale;

	public AnilistException(String userMessageTranslationKey, Locale originalLocale) {
		super(userMessageTranslationKey, logMessage);
		this.originalLocale = originalLocale;
	}

	public AnilistException(String userMessageTranslationKey, Locale originalLocale, String logMessage) {
		super(userMessageTranslationKey, logMessage);
		this.originalLocale = originalLocale;
	}

	public Locale getOriginalLocale() {
		return originalLocale;
	}
}