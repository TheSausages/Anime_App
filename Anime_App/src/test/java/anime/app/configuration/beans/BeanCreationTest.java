package anime.app.configuration.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class BeanCreationTest {

	private final Keycloak keycloak;
	private final LocaleResolver resolver;
	private final ObjectMapper mapper;
	private final WebClient anilistWebClient;
	private final WebClient keycloakWebClient;

	@Autowired
	BeanCreationTest(Keycloak keycloak, LocaleResolver resolver, ObjectMapper mapper,
	                 @Qualifier("anilistWebClient") WebClient anilistWebClient,
	                 @Qualifier("keycloakWebClient") WebClient keycloakWebClient) {
		this.keycloak = keycloak;
		this.resolver = resolver;
		this.anilistWebClient = anilistWebClient;
		this.mapper = mapper;
		this.keycloakWebClient = keycloakWebClient;
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

		assertThat(((AcceptHeaderLocaleResolver) resolver).getDefaultLocale(), equalTo(Locale.ENGLISH));
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