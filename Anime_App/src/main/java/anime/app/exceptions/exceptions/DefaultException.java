package anime.app.exceptions.exceptions;

import lombok.Getter;

/**
 * Abstract class for custom errors that don't extend another exception.
 * The {@link DefaultException#userMessageTranslationKey} must be a link to a translation.
 * The translation is then returned to the user.
 */
@Getter
public abstract class DefaultException extends RuntimeException {
	private final String logMessage;
	private final String userMessageTranslationKey;

	public DefaultException(String userMessageTranslationKey, String logMessage) {
		super(userMessageTranslationKey);
		this.userMessageTranslationKey = userMessageTranslationKey;
		this.logMessage = logMessage;
	}
}
