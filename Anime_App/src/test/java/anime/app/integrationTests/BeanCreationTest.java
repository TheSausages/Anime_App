package anime.app.integrationTests;

import anime.app.configuration.beans.ValidatorConfiguration;
import anime.app.configuration.beans.WebClientsConfiguration;
import anime.app.integrationTests.config.BaseIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.validation.Validator;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BeanCreationTest extends BaseIntegrationTest {

	private final Keycloak keycloak;
	private final LocaleResolver resolver;
	private final WebClient anilistWebClient;
	private final WebClient keycloakWebClient;
	private final Validator validator;

	@Autowired
	BeanCreationTest(Keycloak keycloak, LocaleResolver resolver, Validator validator,
	                 @Qualifier(WebClientsConfiguration.anilistWebClientBeanName) WebClient anilistWebClient,
	                 @Qualifier(WebClientsConfiguration.keycloakWebClientBeanName) WebClient keycloakWebClient) {
		this.keycloak = keycloak;
		this.resolver = resolver;
		this.validator = validator;
		this.anilistWebClient = anilistWebClient;
		this.keycloakWebClient = keycloakWebClient;
	}

	@Test
	void validator__CheckIfCorrectBeanCreated() {
		//given

		//when

		//then
		assertThat(validator, allOf(
				notNullValue(),
				instanceOf(Validator.class),
				instanceOf(ValidatorConfiguration.ThrowingValidator.class)
		));
	}

	@Test
	void anilistWebClient__CheckIfCorrectBeanCreated() {
		//given

		//when

		//then
		assertThat(anilistWebClient, allOf(
				notNullValue(),
				instanceOf(WebClient.class)
		));
	}

	@Test
	void keycloakWebClient__CheckIfCorrectBeanCreated() {
		//given

		//when

		//then
		assertThat(keycloakWebClient, allOf(
				notNullValue(),
				instanceOf(WebClient.class)
		));
	}

	@Test
	void objectMapper__CheckIfCorrectBeanCreated() {
		//given

		//when

		//then
		assertThat(mapper, allOf(
				notNullValue(),
				instanceOf(ObjectMapper.class)
		));

		assertThat(mapper.getDateFormat(), equalTo(new SimpleDateFormat("yyyy-MM-dd")));
		assertThat(mapper.getRegisteredModuleIds(), contains(new JavaTimeModule().getTypeId()));
		assertThat(mapper.isEnabled(SerializationFeature.INDENT_OUTPUT), equalTo(true));
	}

	@Test
	void customLocaleResolver__CheckIfCorrectBeanCreated() {
		//given

		//when

		//then
		assertThat(resolver, allOf(
				notNullValue(),
				instanceOf(AcceptHeaderLocaleResolver.class)
		));

		assertThat(((AcceptHeaderLocaleResolver) resolver).getDefaultLocale(), equalTo(Locale.UK));
	}

	@Test
	void keycloak__CheckIfCorrectBeanCreated() {
		//given

		//when

		//then
		assertThat(keycloak, allOf(
				notNullValue(),
				instanceOf(Keycloak.class)
		));
	}
}