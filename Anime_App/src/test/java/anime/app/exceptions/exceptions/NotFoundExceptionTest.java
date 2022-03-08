package anime.app.exceptions.exceptions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotFoundExceptionTest {
    @Test
    void notFoundException_NullTranslationKey_Exception() {
        //given
        String translationKey = null;

        //when
        Exception exception = assertThrows(NullPointerException.class, () ->
                NotFoundException.builder()
                        .userMessageTranslationKey(translationKey)
                        .build()
        );

        //then
        assertThat(exception, allOf(
                notNullValue(),
                instanceOf(NullPointerException.class)
        ));
    }

    @Test
    void notFoundException_NoLogMessageNoParameters_CorrectExceptionCreation() {
        //given
        String translationKey = "Some key";

        //when
        NotFoundException exception = NotFoundException.builder()
                .userMessageTranslationKey(translationKey)
                .build();

        //then
        assertThat(exception, allOf(
                notNullValue(),
                instanceOf(NotFoundException.class)
        ));
        assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
        assertThat(exception.getLogMessage(), equalTo("Object not found in the database, no message given"));
    }

    @Test
    void notFoundException_NoLogMessageWithParameters_CorrectExceptionCreation() {
        //given
        String translationKey = "Some key";
        String param1 = "Param1";
        int param2 = 1;

        //when
        NotFoundException exception = NotFoundException.builder()
                .userMessageTranslationKey(translationKey)
                .translationParameter(param2)
                .translationParameter(param1)
                .build();

        //then
        assertThat(exception, allOf(
                notNullValue(),
                instanceOf(NotFoundException.class)
        ));
        assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
        assertThat(exception.getLogMessage(), equalTo("Object not found in the database, no message given"));
        assertThat(exception.getTranslationParameters(), allOf(
                notNullValue(),
                instanceOf(List.class),
                containsInAnyOrder(param2, param1)
        ));
    }

    @Test
    void notFoundException_WithLogMessageNoParameters_CorrectExceptionCreation() {
        //given
        String translationKey = "Some key";
        String logMessage = "Log Message";

        //when
        NotFoundException exception = NotFoundException.builder()
                .userMessageTranslationKey(translationKey)
                .logMessage(logMessage)
                .build();

        //then
        assertThat(exception, allOf(
                notNullValue(),
                instanceOf(NotFoundException.class)
        ));
        assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
        assertThat(exception.getLogMessage(), equalTo(logMessage));
    }

    @Test
    void notFoundException_WithLogMessageWithParameters_CorrectExceptionCreation() {
        //given
        String translationKey = "Some key";
        String logMessage = "Log Message";
        String param1 = "Param1";
        int param2 = 1;

        //when
        NotFoundException exception = NotFoundException.builder()
                .userMessageTranslationKey(translationKey)
                .logMessage(logMessage)
                .translationParameter(param2)
                .translationParameter(param1)
                .build();

        //then
        assertThat(exception, allOf(
                notNullValue(),
                instanceOf(NotFoundException.class)
        ));
        assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
        assertThat(exception.getLogMessage(), equalTo(logMessage));
        assertThat(exception.getTranslationParameters(), allOf(
                notNullValue(),
                instanceOf(List.class),
                containsInAnyOrder(param2, param1)
        ));
    }
}