package anime.app.integrationTests.config;

import anime.app.services.i18n.I18nService;
import com.fasterxml.jackson.databind.ObjectMapper;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.startupcheck.MinimumDurationRunningStartupCheckStrategy;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

/**
 * Integration tests use Mockmvc and {@link com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication}
 * to simulate a logged-in user. The Keycloak container is used to test keycloak service.
 *
 * Example integration test:
 * <pre>{@code
 *      public class IntTest extends BaseIntegrationTest {
 *         @Test
 *         @KeycloakPrincipalByUserId("SomeStringValue")
 *         public void test() {
 *             //given
 *
 *             //when
 *             WebTestClient.ResponseSpec spec = webTestClient
 *             		.get()
 *             	    .uri("/uri")
 *             	    .exchange()
 *             (Note! dont use the authorization header here)
 *
 *             //then
 *             ...
 *         }
 *         ...
 *     }
 * }</pre>
 *
 * Example integration test when the user needs to be set inside the test:
 * <pre>{@code
 *      public class IntTest extends BaseIntegrationTest {
 *         @Test
 *         @WithMockAuthentication(authType = KeycloakAuthenticationToken.class)
 *         public void test() {
 *             //given
 *             changeLoggedInUserTo("userId");
 *
 *             //when
 *             WebTestClient.ResponseSpec spec = webTestClient
 *             		.get()
 *             	    .uri("/uri")
 *             	    .exchange()
 *             (Note! dont use the authorization header here)
 *
 *             //then
 *             ...
 *         }
 *         ...
 *     }
 * }</pre>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@Testcontainers
@ComponentScan(basePackageClasses = { KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class })
@Sql(scripts = {"classpath:schema.sql", "classpath:data-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class BaseIntegrationTest {
	private final static Slf4jLogConsumer containerLogger = new Slf4jLogConsumer(LoggerFactory.getLogger(BaseIntegrationTest.class));

	@Autowired
	protected ObjectMapper mapper;

	@Autowired
	protected WebApplicationContext context;

	@Autowired
	protected I18nService service;

	protected WebTestClient webTestClient;

	protected static final PostgreSQLContainer<?> postreg = new PostgreSQLContainer<>("postgres:14.1-alpine")
			.withDatabaseName("animeApp")
				.withUsername("appUser")
				.withPassword("appPassword1")
				.withExposedPorts(5432)
			.withStartupCheckStrategy(
					new MinimumDurationRunningStartupCheckStrategy(Duration.ofSeconds(5))
			)
			.withLogConsumer(containerLogger)
			.withReuse(true);

	protected static final KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:15.0.0")
			.withRealmImportFile("./keycloak/realm/realm-export.json")
				.withAdminUsername("admin")
				.withAdminPassword("Password1")
				.withExposedPorts(8080)
			.withStartupCheckStrategy(
					new MinimumDurationRunningStartupCheckStrategy(Duration.ofSeconds(5))
			)
			.withLogConsumer(containerLogger)
			.withReuse(true);

	static {
		postreg.start();
		keycloak.start();
	}

	@BeforeEach
	public void setup() {
		MockMvc mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				//.apply(springSecurity())
				.build();

		this.webTestClient = MockMvcWebTestClient
				.bindTo(mockMvc)
				.build();

		assertThat(keycloak.isRunning(), is(true));
		assertThat(postreg.isRunning(), is(true));
	}

	/**
	 * Add container information for properties
	 * @param registry The registry holding the properties
	 */
	@DynamicPropertySource
	static void registerContainerProperties(DynamicPropertyRegistry registry) {
		//The anilist api should be set here to the wireMock address
		registry.add("anilist.apiUrl", () -> "http://localhost:9090");
		registry.add("keycloak.auth-server-url", keycloak::getAuthServerUrl);
		registry.add("keycloakrealm.master.url", keycloak::getAuthServerUrl);
		registry.add("keycloakrealm.userserver.url", () -> String.format("%s/realms/Keycloak_Instance/protocol/openid-connect", keycloak.getAuthServerUrl()));
		registry.add("spring.datasource.url", postreg::getJdbcUrl);
	}

	protected static <T> Matcher<T> hasGraph(String graphPath, Matcher<T> matcher) {
		List<String> properties = Arrays.asList(graphPath.split("\\."));
		ListIterator<String> iterator =
				properties.listIterator(properties.size());

		Matcher<T> ret = matcher;
		while (iterator.hasPrevious()) {
			ret = hasProperty(iterator.previous(), ret);
		}
		return ret;
	}

	protected void changeLoggedInUserTo(String userId) {
		final var auth = (KeycloakAuthenticationToken) TestSecurityContextHolder.getContext().getAuthentication();
		when(auth.getPrincipal()).thenReturn(userId);
	}
}
