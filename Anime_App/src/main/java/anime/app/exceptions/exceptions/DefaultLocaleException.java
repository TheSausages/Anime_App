package anime.app.exceptions.exceptions;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;
import java.util.Locale;

public class DefaultLocaleException extends DefaultException {
    private final static String logMessage = "Unknown exception with locale";

    private final Locale originalLocale;

    @Builder(builderMethodName = "defaultLocaleExceptionBuilder")
    public DefaultLocaleException(String logMessage, @NonNull String userMessageTranslationKey, @Singular List<Object> translationParameters, @NonNull Locale originalLocale) {
        super(logMessage, userMessageTranslationKey, translationParameters);
        this.originalLocale = originalLocale;
    }

    public @NonNull Locale getOriginalLocale() {
        return originalLocale;
    }

    @Override
    protected String getDefaultLogMessage() {
        return logMessage;
    }
}
