package anime.app.exceptions.exceptions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataConflictExceptionTest {
    @Test
    void dataConflictException_NullTranslationKey_Exception() {
        //given
        String translationKey = null;

        //when
        Exception exception = assertThrows(NullPointerException.class, () ->
                DataConflictException.builder()
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
    void dataConflictException_NoLogMessageNoParameters_CorrectExceptionCreation() {
        //given
        String translationKey = "Some key";

        //when
        DataConflictException exception = DataConflictException.builder()
                .userMessageTranslationKey(translationKey)
                .build();

        //then
        assertThat(exception, allOf(
                notNullValue(),
                instanceOf(DataConflictException.class)
        ));
        assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
        assertThat(exception.getLogMessage(), equalTo("Some conflict found in data, no message given"));
    }

    @Test
    void dataConflictException_NoLogMessageWithParameters_CorrectExceptionCreation() {
        //given
        String translationKey = "Some key";
        String param1 = "Param1";
        int param2 = 1;

        //when
        DataConflictException exception = DataConflictException.builder()
                .userMessageTranslationKey(translationKey)
                .translationParameter(param2)
                .translationParameter(param1)
                .build();

        //then
        assertThat(exception, allOf(
                notNullValue(),
                instanceOf(DataConflictException.class)
        ));
        assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
        assertThat(exception.getLogMessage(), equalTo("Some conflict found in data, no message given"));
        assertThat(exception.getTranslationParameters(), allOf(
                notNullValue(),
                instanceOf(List.class),
                containsInAnyOrder(param2, param1)
        ));
    }

    @Test
    void dataConflictException_WithLogMessageNoParameters_CorrectExceptionCreation() {
        //given
        String translationKey = "Some key";
        String logMessage = "Log Message";

        //when
        DataConflictException exception = DataConflictException.builder()
                .userMessageTranslationKey(translationKey)
                .logMessage(logMessage)
                .build();

        //then
        assertThat(exception, allOf(
                notNullValue(),
                instanceOf(DataConflictException.class)
        ));
        assertThat(exception.getUserMessageTranslationKey(), equalTo(translationKey));
        assertThat(exception.getLogMessage(), equalTo(logMessage));
    }

    @Test
    void dataConflictEException_WithLogMessageWithParameters_CorrectExceptionCreation() {
        //given
        String translationKey = "Some key";
        String logMessage = "Log Message";
        String param1 = "Param1";
        int param2 = 1;

        //when
        DataConflictException exception = DataConflictException.builder()
                .userMessageTranslationKey(translationKey)
                .logMessage(logMessage)
                .translationParameter(param2)
                .translationParameter(param1)
                .build();

        //then
        assertThat(exception, allOf(
                notNullValue(),
                instanceOf(DataConflictException.class)
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