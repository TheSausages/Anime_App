package anime.app.exceptions.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;
import java.util.Objects;

/**
 * Class for custom errors that don't extend another exception. The {@link lombok.experimental.SuperBuilder} annotation cannot be used because
 * the class extends the {@link RuntimeException} class.
 * The {@link DefaultException#userMessageTranslationKey} must be a link to a translation.
 * The translation is then returned to the user.
 */
@Getter
public class DefaultException extends RuntimeException {
	private final String logMessage;

	@NonNull
	private final String userMessageTranslationKey;

	@Singular
	private final List<Object> translationParameters;

	@Builder(builderMethodName = "defaultExceptionBuilder")
	public DefaultException(String logMessage, @NonNull String userMessageTranslationKey, List<Object> translationParameters) {
		super(userMessageTranslationKey);
		this.userMessageTranslationKey = userMessageTranslationKey;
		this.translationParameters = translationParameters;
		this.logMessage = Objects.isNull(logMessage) ? getDefaultLogMessage() : logMessage;
	}

	/**
	 * Method that returns the default log message for an error.
	 * This method should be overwritten
	 */
	protected String getDefaultLogMessage() {
		return "Unknown error occurred";
	}

	@Override
	public String toString() {
		return "DefaultException{" +
				"logMessage='" + logMessage + '\'' +
				", userMessageTranslationKey='" + userMessageTranslationKey + '\'' +
				", translationParameters=" + translationParameters +
				'}';
	}
}
