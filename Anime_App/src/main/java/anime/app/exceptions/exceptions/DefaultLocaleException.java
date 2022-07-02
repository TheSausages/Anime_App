package anime.app.exceptions.exceptions;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DefaultLocaleException extends DefaultException {
    private final static String logMessage = "Unknown exception with locale";

    @Builder.Default
    private Locale originalLocale = LocaleContextHolder.getLocale();

    @Builder(builderMethodName = "defaultLocaleExceptionBuilder")
    public DefaultLocaleException(String logMessage, @NonNull String userMessageTranslationKey, @Singular List<Object> translationParameters, Locale originalLocale) {
        super(logMessage, userMessageTranslationKey, translationParameters);
        this.originalLocale = Objects.nonNull(originalLocale) ? originalLocale : LocaleContextHolder.getLocale();
    }

    public @NonNull Locale getOriginalLocale() {
        return originalLocale;
    }

    @Override
    protected String getDefaultLogMessage() {
        return logMessage;
    }
}
