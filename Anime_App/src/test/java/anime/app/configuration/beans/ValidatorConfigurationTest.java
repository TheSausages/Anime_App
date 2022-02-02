package anime.app.configuration.beans;

import anime.app.exceptions.exceptions.ValidationException;
import anime.app.openapi.model.TagDTO;
import anime.app.openapi.model.TagImportance;
import anime.app.utils.SpringBootTestWithoutDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTestWithoutDatabase
class ValidatorConfigurationTest {

	private final Validator validator;

	@Autowired
	ValidatorConfigurationTest(Validator validator) {
		this.validator = validator;
	}

	@Test
	void validator_ObjectWithViolations_ThrowException() {
		//given
		TagDTO tag = TagDTO.builder()
				.id(-1L)
				.build();

		//when
		ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(tag));

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(ValidationException.class)
		));

		assertThat(exception.getLogMessage(), allOf(
				notNullValue(),
				not(equalToCompressingWhiteSpace(""))
		));

		assertThat(exception.getUserMessageTranslationKey(), allOf(
				notNullValue(),
				not(equalToCompressingWhiteSpace(""))
		));
	}

	@Test
	void validator_ObjectWithoutViolations_NoException() {
		//given
		TagDTO tag = TagDTO.builder()
				.id(1L)
				.name("name")
				.importance(TagImportance.LOW)
				.color("rgb(111, 222, 333)")
				.build();

		//when
		Set<ConstraintViolation<TagDTO>> constraints = validator.validate(tag);

		//then
		assertThat(constraints, allOf(
				notNullValue(),
				emptyIterable()
		));
	}
}