package anime.app.utils;


import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Small utility class containing methods connected to Error creation.
 */
public interface ErrorUtils {
    /**
     * Small method to create an error message from its parts.
     * @return Correctly formatted complete error log message
     */
    static String getErrorMessage(String message, LocalDateTime occurrence, UUID errorCode) {
        return message + "\t|\tOccurrence Time: " + occurrence + "\t|\tError Code: " + errorCode;
    }
}
