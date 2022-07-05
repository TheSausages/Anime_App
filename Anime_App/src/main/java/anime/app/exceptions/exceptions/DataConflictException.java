package anime.app.exceptions.exceptions;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Error occurs when some data conflicts with others.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DataConflictException extends DefaultException {
    private final static String logMessage = "Object not found in the database, no message given";

    @Builder
    public DataConflictException(String logMessage, @NonNull String userMessageTranslationKey, @Singular List<Object> translationParameters) {
        super(logMessage, userMessageTranslationKey, translationParameters);
    }

    @Override
    protected String getDefaultLogMessage() {
        return logMessage;
    }
}
