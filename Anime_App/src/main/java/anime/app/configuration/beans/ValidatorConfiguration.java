package anime.app.configuration.beans;

import anime.app.exceptions.exceptions.ValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.util.Set;

/**
 * Configure an app-wide {@link Validator}.
 * In the current implementation, the validator throws a {@link ValidationException} on constraint violations.
 */
@Configuration
public class ValidatorConfiguration {

	@Bean
	Validator getValidator() {
		return ValidatorConfiguration.ValidatorFactory.getNewValidator();
	}

	/**
	 * Small helper class. Used to always get a Validator with default settings,
	 * even when a new one must be created, ex. in static methods.
	 * @see Validation#buildDefaultValidatorFactory()
	 * @see ThrowingValidator
	 */
	public static class ValidatorFactory {
		public static Validator getNewValidator() {
			return new ThrowingValidator(Validation
					.buildDefaultValidatorFactory()
					.getValidator());
		}
	}

	/**
	 * Small custom validator. Uses the decorator pattern to add automatic {@link ValidationException} throwing during validation.
	 * Rest of the methods are not changes.
	 */
	public static class ThrowingValidator implements Validator {
		private final Validator validator;

		ThrowingValidator(Validator validator) {
			this.validator = validator;
		}

		@Override
		public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
			Set<ConstraintViolation<T>> constraints = validator.validate(object, groups);

			if (!constraints.isEmpty()) {
				throw new ValidationException(
						"general.server-error",
						"The violations are:" + constraints.toString()
				);
			}

			return constraints;
		}

		@Override
		public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
			return validator.validateProperty(object, propertyName, groups);
		}

		@Override
		public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
			return validator.validateValue(beanType, propertyName, value, groups);
		}

		@Override
		public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
			return validator.getConstraintsForClass(clazz);
		}

		@Override
		public <T> T unwrap(Class<T> type) {
			return validator.unwrap(type);
		}

		@Override
		public ExecutableValidator forExecutables() {
			return validator.forExecutables();
		}
	}
}
