package anime.app.services.anilist;

import anime.app.anilist.request.query.Query;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.page.Page;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateValue;
import anime.app.anilist.request.query.parameters.media.*;
import anime.app.exceptions.exceptions.AnilistException;
import anime.app.integrationTests.config.wiremock.WireMockInitializer;
import anime.app.integrationTests.config.wiremock.WireMockPageExtension;
import anime.app.openapi.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.EqualToJsonPattern;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

//Initialize the wiremock server for Anilist
class AnilistServiceTest {
	private final static Locale REQUEST_LOCALE = Locale.ENGLISH;

	WebClient client = WebClient.builder().baseUrl("http://localhost:9090").build();

	AnilistService service = new AnilistService(client);

	//Will be autowired and is required, but without false there is an error
	static WireMockServer wireMockServer;

	@BeforeAll
	static void onStartUp() {
		wireMockServer = WireMockInitializer.WireMockFactory.getDefaultWireMock(9090);

		wireMockServer.start();
	}

	@AfterAll
	static void afterAll() {
		wireMockServer.stop();
	}

	@BeforeEach
	public void setUp() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setPreferredLocales(List.of(REQUEST_LOCALE));
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@Nested
	@DisplayName("Test for methods that return Anilist Pages")
	class AnilistReturnsPageTests {
		@Nested
		@DisplayName("Tests when Anilist doesnt return an error")
		class WithoutErrorTests {
			private final AnilistResponseDTOPage anilistAnswer = AnilistResponseDTOPage.builder()
					.data(AnilistPageCoverDTO.builder()
							.page(AnilistPageDTO.builder()
									.pageInfo(AnilistPageInfo.builder()
											.total(1)
											.currentPage(0)
											.hasNextPage(false)
											.lastPage(0)
											.perPage(49)
											.build())
									.media(List.of(AnilistBasicAnimeInformation.builder()
											.id(5)
											.title(AnilistMediaTitle.builder()
													.english("English Title")
													.build())
											.coverImage(AnilistCoverImage.builder()
													.extraLarge("extra large")
													.large("large")
													.medium("medium")
													.build())
											.build()))
									.build())
							.build())
					.build();

			@BeforeEach
			public void setUp() {
				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(WireMockInitializer.anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(anilistAnswer)
										.withTransformers(WireMockPageExtension.wireMockPageExtensionName)
								)
						);
			}

			@Test
			void getTopAiring__ReturnCorrectPage() {
				//given
				long pageNumber = 1;

				//when
				AnilistPageDTO pageDTO = service.getTopAiring(pageNumber);

				//then
				assertThat(pageDTO, allOf(
						notNullValue(),
						instanceOf(AnilistPageDTO.class),
						equalTo(anilistAnswer.getData().getPage())
				));
			}

			@Test
			void getTopAnimeMovies() {
				//given
				long pageNumber = 1;

				//when
				AnilistPageDTO pageDTO = service.getTopAnimeMovies(pageNumber);

				//then
				assertThat(pageDTO, allOf(
						notNullValue(),
						instanceOf(AnilistPageDTO.class),
						equalTo(anilistAnswer.getData().getPage())
				));
			}

			@Test
			void getTopAnimeOfAllTime() {
				//given
				long pageNumber = 1;

				//when
				AnilistPageDTO pageDTO = service.getTopAnimeOfAllTime(pageNumber);

				//then
				assertThat(pageDTO, allOf(
						notNullValue(),
						instanceOf(AnilistPageDTO.class),
						equalTo(anilistAnswer.getData().getPage())
				));
			}

			@Nested
			@TestInstance(TestInstance.Lifecycle.PER_CLASS)
			@DisplayName("Anime Query Tests")
			class AnimeQueryTests {
				private Media.MediaArgumentBuilder getBuilder() {
					return Media.getMediaArgumentBuilder(AnilistServiceInterface.defaultBasicField)
							.type(MediaType.ANIME)
							.sort(MediaSort.SCORE_DESC);
				}

				Stream<Arguments> queries() {
					LocalDate testDate = LocalDate.of(2021, 10, 10);

					return Stream.of(
							Arguments.of(
									AnimeQueryDTO.builder()
											.title("Title")
											.build(),
									getBuilder().search("Title")
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.season(AnilistMediaSeason.SPRING)
											.build(),
									getBuilder().season(MediaSeason.SPRING)
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.seasonYear(2021)
											.build(),
									getBuilder().seasonYear(2021)
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.status(AnilistMediaStatus.FINISHED)
											.build(),
									getBuilder().status(MediaStatus.FINISHED)
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.format(AnilistMediaFormat.TV)
											.build(),
									getBuilder().format(MediaFormat.TV)
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.maxStartDate(testDate)
											.build(),
									getBuilder().startDateLesser(FuzzyDateValue.getFuzzyDateValueBuilder().fromDate(testDate).build())
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.minStartDate(testDate)
											.build(),
									getBuilder().startDateGreater(FuzzyDateValue.getFuzzyDateValueBuilder().fromDate(testDate).build())
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.maxEndDate(testDate)
											.build(),
									getBuilder().endDateLesser(FuzzyDateValue.getFuzzyDateValueBuilder().fromDate(testDate).build())
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.minEndDate(testDate)
											.build(),
									getBuilder().endDateGreater(FuzzyDateValue.getFuzzyDateValueBuilder().fromDate(testDate).build())
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.minNrOfEpisodes(1)
											.build(),
									getBuilder().episodesGreater(1)
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.maxNrOfEpisodes(1)
											.build(),
									getBuilder().episodesLesser(1)
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.minAverageScore(80)
											.build(),
									getBuilder().averageScoreGreater(80)
							),
							Arguments.of(
									AnimeQueryDTO.builder()
											.maxAverageScore(80)
											.build(),
									getBuilder().averageScoreLesser(80)
							)
					);
				}

				@ParameterizedTest(name = "Search using queries, test {index}: {argumentsWithNames}")
				@MethodSource("queries")
				void searchUsingQuery(AnimeQueryDTO query, Media.MediaArgumentBuilder builder) {
					//given
					long pageNumber = 1;
					JsonNode request = Query.buildQuery(Page.fromMediaAndPageInfo(
							pageNumber, 49,
							builder.build(),
							AnilistServiceInterface.defaultPageInfo
					));

					//when
					AnilistPageDTO pageDTO = service.searchUsingQuery(query, pageNumber);

					//then
					assertThat(pageDTO, allOf(
							notNullValue(),
							instanceOf(AnilistPageDTO.class),
							equalTo(anilistAnswer.getData().getPage())
					));

					//Check the request
					wireMockServer.verify(RequestPatternBuilder.newRequestPattern()
							.withRequestBody(new EqualToJsonPattern(
									request,
									true,
									false)
							)
					);
				}
			}
		}

		@Nested
		@DisplayName("Tests when Anilist returns an error")
		class WithErrorTests {
			@BeforeEach
			public void setUp() {
				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(WireMockInitializer.anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.like(
												ResponseDefinitionBuilder.jsonResponse(
														"Error", 400
												)
										)
								)
						);
			}

			@Test
			void getTopAiring_400Status_ThrowException() {
				//given
				long pageNumber = 1;

				//when
				AnilistException exception = assertThrows(AnilistException.class, () -> service.getTopAiring(pageNumber));

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(AnilistException.class)
				));

				assertThat(exception.getMessage(), allOf(
						notNullValue(),
						not(equalToCompressingWhiteSpace(""))
				));

				assertThat(exception.getOriginalLocale(), allOf(
						notNullValue(),
						equalTo(REQUEST_LOCALE)
				));

				assertThat(exception.getLogMessage(), allOf(
						notNullValue(),
						not(equalToCompressingWhiteSpace(""))
				));
			}

			@Test
			void getTopAnimeMovies_400Status_ThrowException() {
				//given
				long pageNumber = 1;

				//when
				AnilistException exception = assertThrows(AnilistException.class, () -> service.getTopAnimeMovies(pageNumber));

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(AnilistException.class)
				));

				assertThat(exception.getMessage(), allOf(
						notNullValue(),
						not(equalToCompressingWhiteSpace(""))
				));

				assertThat(exception.getOriginalLocale(), allOf(
						notNullValue(),
						equalTo(REQUEST_LOCALE)
				));

				assertThat(exception.getLogMessage(), allOf(
						notNullValue(),
						not(equalToCompressingWhiteSpace(""))
				));
			}

			@Test
			void getTopAnimeOfAllTime() {
				//given
				long pageNumber = 1;

				//when
				AnilistException exception = assertThrows(AnilistException.class, () -> service.getTopAnimeOfAllTime(pageNumber));

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(AnilistException.class)
				));

				assertThat(exception.getMessage(), allOf(
						notNullValue(),
						not(equalToCompressingWhiteSpace(""))
				));

				assertThat(exception.getOriginalLocale(), allOf(
						notNullValue(),
						equalTo(REQUEST_LOCALE)
				));

				assertThat(exception.getLogMessage(), allOf(
						notNullValue(),
						not(equalToCompressingWhiteSpace(""))
				));
			}

			@Test
			void searchUsingQuery() {
				//given
				long pageNumber = 1;
				AnimeQueryDTO queryDTO = AnimeQueryDTO.builder()
						.format(AnilistMediaFormat.TV)
						.build();

				//when
				AnilistException exception = assertThrows(AnilistException.class, () -> service.searchUsingQuery(queryDTO, pageNumber));

				//then
				assertThat(exception, allOf(
						notNullValue(),
						instanceOf(AnilistException.class)
				));

				assertThat(exception.getMessage(), allOf(
						notNullValue(),
						not(equalToCompressingWhiteSpace(""))
				));

				assertThat(exception.getOriginalLocale(), allOf(
						notNullValue(),
						equalTo(REQUEST_LOCALE)
				));

				assertThat(exception.getLogMessage(), allOf(
						notNullValue(),
						not(equalToCompressingWhiteSpace(""))
				));
			}
		}
	}

	@Test
	@Disabled("Disabled until rest of repositories are done")
	void getAnimeById() {
	}
}