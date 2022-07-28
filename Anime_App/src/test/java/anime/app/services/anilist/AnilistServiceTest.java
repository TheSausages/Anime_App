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
import anime.app.services.anime.anime.AnimeService;
import anime.app.services.anime.animeuserinfo.AnimeUserService;
import anime.app.utils.UserAuthorizationUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.EqualToJsonPattern;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

//Initialize the wiremock server for Anilist
@ExtendWith(MockitoExtension.class)
class AnilistServiceTest {
	private final static Locale DEFAULT_REQUEST_LOCALE = Locale.ENGLISH;

	WebClient client = WebClient.builder().baseUrl("http://localhost:9090").build();

	@Mock
	AnimeService animeService;

	@Mock
	AnimeUserService animeUserService;

	AnilistService service;

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
		request.setPreferredLocales(List.of(DEFAULT_REQUEST_LOCALE));
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		// Because we inject other things, not only mocks, and the mocks we have need to be initialized
		// We create the service once, before the fist test. We can't use BeforeAll because the service can't be static
		// (because the mocks can't be)
		if (Objects.isNull(service)) {
			service = new AnilistService(client, animeService, animeUserService);
		}
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

			@Nested
			@DisplayName("With Errors with default Locale")
			class WithErrorDefaultLocaleTests {
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
							equalTo(DEFAULT_REQUEST_LOCALE)
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
							equalTo(DEFAULT_REQUEST_LOCALE)
					));

					assertThat(exception.getLogMessage(), allOf(
							notNullValue(),
							not(equalToCompressingWhiteSpace(""))
					));
				}

				@Test
				void getTopAnimeOfAllTime_400Status_ThrowException() {
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
							equalTo(DEFAULT_REQUEST_LOCALE)
					));

					assertThat(exception.getLogMessage(), allOf(
							notNullValue(),
							not(equalToCompressingWhiteSpace(""))
					));
				}

				@Test
				void searchUsingQuery_400Status_ThrowException() {
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
							equalTo(DEFAULT_REQUEST_LOCALE)
					));

					assertThat(exception.getLogMessage(), allOf(
							notNullValue(),
							not(equalToCompressingWhiteSpace(""))
					));
				}
			}

			@Nested
			@DisplayName("With Errors but non-default Locale")
			class WithErrorDifferentLocaleTests {
				@Test
				void getTopAiring_400Status_ThrowException() {
					//given
					changeLocaleToFrance();

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
							equalTo(Locale.FRANCE)
					));

					assertThat(exception.getLogMessage(), allOf(
							notNullValue(),
							not(equalToCompressingWhiteSpace(""))
					));
				}

				@Test
				void getTopAnimeMovies_400Status_ThrowException() {
					//given
					changeLocaleToFrance();

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
							equalTo(Locale.FRANCE)
					));

					assertThat(exception.getLogMessage(), allOf(
							notNullValue(),
							not(equalToCompressingWhiteSpace(""))
					));
				}

				@Test
				void getTopAnimeOfAllTime_400Status_ThrowException() {
					//given
					changeLocaleToFrance();

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
							equalTo(Locale.FRANCE)
					));

					assertThat(exception.getLogMessage(), allOf(
							notNullValue(),
							not(equalToCompressingWhiteSpace(""))
					));
				}

				@Test
				void searchUsingQuery_400StatusDifferentLocale_ThrowException() {
					//given
					changeLocaleToFrance();

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
							equalTo(Locale.FRANCE)
					));

					assertThat(exception.getLogMessage(), allOf(
							notNullValue(),
							not(equalToCompressingWhiteSpace(""))
					));
				}
			}
		}
	}

	@Nested
	@DisplayName("Tests for getting Anime Information using it's id")
	class GetAnimeByIdTests {
		@Nested
		@DisplayName("Tests when Anilist doesnt return an error")
		class WithoutErrorTests {
			private final AnilistResponseDTOMedia anilistAnswer = AnilistResponseDTOMedia.builder()
					.data(AnilistMediaDTO.builder()
							.media(DetailedAnilistAnimeInformationDTO.builder()
									.id(1)
									.title(AnilistMediaTitle.builder()
											.english("Some English Title")
											.build())
									.duration(20)
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

			@Nested
			@DisplayName("User not logged in")
			class GetAnimeByIdUserNotLoggedInTests {
				@Test
				void getAnimeById_UserNotLoggedInNoReviews_ReturnCorrectPage() {
					//given
					long animeId = 1;
					LocalAnimeInformationDTO anime = LocalAnimeInformationDTO.builder()
							.animeId(animeId)
							.title("Some Title")
							.build();
					doReturn(anime).when(animeService).getAnimeDTOById(1L);

					doReturn(Collections.emptyList()).when(animeUserService).get5LatestReviewsForAnime(animeId);

					try (MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
						mockedUtils.when(UserAuthorizationUtils::checkIfLoggedIn).thenReturn(false);

						//when
						DetailedAnimeDTO response = service.getAnimeById(1);

						//then
						assertThat(response, allOf(
								notNullValue(),
								instanceOf(DetailedAnimeDTO.class)
						));

						assertThat(response.getExternalInformation(), allOf(
								notNullValue(),
								equalTo(anilistAnswer.getData().getMedia())
						));

						assertThat(response.getLocalAnimeInformation(), allOf(
								notNullValue(),
								equalTo(anime)
						));

						assertThat(response.getLocalAnimeReviews(), allOf(
								notNullValue(),
								emptyIterable()
						));

						assertThat(response.getLocalUserAnimeInformation(), nullValue());
					}
				}

				@Test
				void getAnimeById_UserNotLoggedInSingleReview_ReturnCorrectPage() {
					//given
					long animeId = 1;
					LocalAnimeInformationDTO anime = LocalAnimeInformationDTO.builder()
							.animeId(animeId)
							.title("Some Title")
							.build();
					doReturn(anime).when(animeService).getAnimeDTOById(1L);

					LocalSimpleAnimeReviewDTO review = LocalSimpleAnimeReviewDTO.builder()
							.id(animeId)
							.title("Some Title")
							.text("Some Text")
							.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
							.build();
					doReturn(List.of(review)).when(animeUserService).get5LatestReviewsForAnime(animeId);

					try (MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
						mockedUtils.when(UserAuthorizationUtils::checkIfLoggedIn).thenReturn(false);

						//when
						DetailedAnimeDTO response = service.getAnimeById(1);

						//then
						assertThat(response, allOf(
								notNullValue(),
								instanceOf(DetailedAnimeDTO.class)
						));

						assertThat(response.getExternalInformation(), allOf(
								notNullValue(),
								equalTo(anilistAnswer.getData().getMedia())
						));

						assertThat(response.getLocalAnimeInformation(), allOf(
								notNullValue(),
								equalTo(anime)
						));

						assertThat(response.getLocalAnimeReviews(), allOf(
								notNullValue(),
								containsInAnyOrder(review)
						));

						assertThat(response.getLocalUserAnimeInformation(), nullValue());
					}
				}

				@Test
				void getAnimeById_UserNotLoggedInMultipleReviews_ReturnCorrectPage() {
					//given
					long animeId = 1;
					LocalAnimeInformationDTO anime = LocalAnimeInformationDTO.builder()
							.animeId(animeId)
							.title("Some Title")
							.build();
					doReturn(anime).when(animeService).getAnimeDTOById(1L);

					LocalSimpleAnimeReviewDTO firstReview = LocalSimpleAnimeReviewDTO.builder()
							.id(animeId)
							.title("Some Title")
							.text("Some Text")
							.modification(LocalDateTime.now().minus(10, ChronoUnit.DAYS))
							.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
							.build();
					LocalSimpleAnimeReviewDTO secondReview = LocalSimpleAnimeReviewDTO.builder()
							.id(animeId)
							.title("Some Title")
							.text("Some Text")
							.modification(LocalDateTime.now())
							.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
							.build();
					LocalSimpleAnimeReviewDTO thirdReview = LocalSimpleAnimeReviewDTO.builder()
							.id(animeId)
							.title("Some Title")
							.text("Some Text")
							.modification(LocalDateTime.now().minus(5, ChronoUnit.MINUTES))
							.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
							.build();
					doReturn(List.of(secondReview, thirdReview, firstReview)).when(animeUserService).get5LatestReviewsForAnime(animeId);

					try (MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
						mockedUtils.when(UserAuthorizationUtils::checkIfLoggedIn).thenReturn(false);

						//when
						DetailedAnimeDTO response = service.getAnimeById(1);

						//then
						assertThat(response, allOf(
								notNullValue(),
								instanceOf(DetailedAnimeDTO.class)
						));

						assertThat(response.getExternalInformation(), allOf(
								notNullValue(),
								equalTo(anilistAnswer.getData().getMedia())
						));

						assertThat(response.getLocalAnimeInformation(), allOf(
								notNullValue(),
								equalTo(anime)
						));

						assertThat(response.getLocalAnimeReviews(), allOf(
								notNullValue(),
								containsInRelativeOrder(secondReview, thirdReview, firstReview)
						));

						assertThat(response.getLocalUserAnimeInformation(), nullValue());
					}
				}
			}

			@Nested
			@DisplayName("User logged in")
			class GetAnimeByIdUserLoggedInTests {
				@Test
				void getAnimeById_UserLoggedInNoReviews_ReturnCorrectPage() {
					//given
					long animeId = 1;
					LocalAnimeInformationDTO anime = LocalAnimeInformationDTO.builder()
							.animeId(animeId)
							.title("Some Title")
							.build();
					doReturn(anime).when(animeService).getAnimeDTOById(1L);

					doReturn(Collections.emptyList()).when(animeUserService).get5LatestReviewsForAnime(animeId);

					LocalUserAnimeInformationDTO userAnimeInfo = LocalUserAnimeInformationDTO.builder()
							.id(LocalUserAnimeInformationDTOId.builder()
									.userId(UUID.randomUUID())
									.animeId(animeId)
									.build())
							.isFavourite(false)
							.status(LocalUserAnimeInformationDTO.StatusEnum.NO_STATUS)
							.build();
					doReturn(userAnimeInfo).when(animeUserService).getCurrentUserAnimeInfo(animeId);

					try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
						mockedUtils.when(UserAuthorizationUtils::checkIfLoggedIn).thenReturn(true);

						//when
						DetailedAnimeDTO response = service.getAnimeById(1);

						//then
						assertThat(response, allOf(
								notNullValue(),
								instanceOf(DetailedAnimeDTO.class)
						));

						assertThat(response.getExternalInformation(), allOf(
								notNullValue(),
								equalTo(anilistAnswer.getData().getMedia())
						));

						assertThat(response.getLocalAnimeInformation(), allOf(
								notNullValue(),
								equalTo(anime)
						));

						assertThat(response.getLocalAnimeReviews(), allOf(
								notNullValue(),
								emptyIterable()
						));

						assertThat(response.getLocalUserAnimeInformation(), allOf(
								notNullValue(),
								equalTo(userAnimeInfo)
						));
					}
				}

				@Test
				void getAnimeById_UserLoggedInSingleReview_ReturnCorrectPage() {
					//given
					long animeId = 1;
					LocalAnimeInformationDTO anime = LocalAnimeInformationDTO.builder()
							.animeId(animeId)
							.title("Some Title")
							.build();
					doReturn(anime).when(animeService).getAnimeDTOById(1L);

					LocalSimpleAnimeReviewDTO review = LocalSimpleAnimeReviewDTO.builder()
							.id(animeId)
							.title("Some Title")
							.text("Some Text")
							.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
							.build();
					doReturn(List.of(review)).when(animeUserService).get5LatestReviewsForAnime(animeId);

					LocalUserAnimeInformationDTO userAnimeInfo = LocalUserAnimeInformationDTO.builder()
							.id(LocalUserAnimeInformationDTOId.builder()
									.userId(UUID.randomUUID())
									.animeId(animeId)
									.build())
							.isFavourite(false)
							.status(LocalUserAnimeInformationDTO.StatusEnum.NO_STATUS)
							.build();
					doReturn(userAnimeInfo).when(animeUserService).getCurrentUserAnimeInfo(animeId);

					try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
						mockedUtils.when(UserAuthorizationUtils::checkIfLoggedIn).thenReturn(true);

						//when
						DetailedAnimeDTO response = service.getAnimeById(1);

						//then
						assertThat(response, allOf(
								notNullValue(),
								instanceOf(DetailedAnimeDTO.class)
						));

						assertThat(response.getExternalInformation(), allOf(
								notNullValue(),
								equalTo(anilistAnswer.getData().getMedia())
						));

						assertThat(response.getLocalAnimeInformation(), allOf(
								notNullValue(),
								equalTo(anime)
						));

						assertThat(response.getLocalAnimeReviews(), allOf(
								notNullValue(),
								containsInAnyOrder(review)
						));

						assertThat(response.getLocalUserAnimeInformation(), allOf(
								notNullValue(),
								equalTo(userAnimeInfo)
						));
					}
				}

				@Test
				void getAnimeById_UserLoggedInMultipleReviews_ReturnCorrectPage() {
					//given
					long animeId = 1;
					LocalAnimeInformationDTO anime = LocalAnimeInformationDTO.builder()
							.animeId(animeId)
							.title("Some Title")
							.build();
					doReturn(anime).when(animeService).getAnimeDTOById(1L);

					LocalSimpleAnimeReviewDTO firstReview = LocalSimpleAnimeReviewDTO.builder()
							.id(animeId)
							.title("Some Title")
							.text("Some Text")
							.modification(LocalDateTime.now().minus(10, ChronoUnit.DAYS))
							.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
							.build();
					LocalSimpleAnimeReviewDTO secondReview = LocalSimpleAnimeReviewDTO.builder()
							.id(animeId)
							.title("Some Title")
							.text("Some Text")
							.modification(LocalDateTime.now())
							.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
							.build();
					LocalSimpleAnimeReviewDTO thirdReview = LocalSimpleAnimeReviewDTO.builder()
							.id(animeId)
							.title("Some Title")
							.text("Some Text")
							.modification(LocalDateTime.now().minus(5, ChronoUnit.MINUTES))
							.reviewType(LocalSimpleAnimeReviewDTO.ReviewTypeEnum.SIMPLEREVIEW)
							.build();
					doReturn(List.of(secondReview, thirdReview, firstReview)).when(animeUserService).get5LatestReviewsForAnime(animeId);

					LocalUserAnimeInformationDTO userAnimeInfo = LocalUserAnimeInformationDTO.builder()
							.id(LocalUserAnimeInformationDTOId.builder()
									.userId(UUID.randomUUID())
									.animeId(animeId)
									.build())
							.isFavourite(false)
							.status(LocalUserAnimeInformationDTO.StatusEnum.NO_STATUS)
							.build();
					doReturn(userAnimeInfo).when(animeUserService).getCurrentUserAnimeInfo(animeId);

					try(MockedStatic<UserAuthorizationUtils> mockedUtils = Mockito.mockStatic(UserAuthorizationUtils.class)) {
						mockedUtils.when(UserAuthorizationUtils::checkIfLoggedIn).thenReturn(true);

						//when
						DetailedAnimeDTO response = service.getAnimeById(1);

						//then
						assertThat(response, allOf(
								notNullValue(),
								instanceOf(DetailedAnimeDTO.class)
						));

						assertThat(response.getExternalInformation(), allOf(
								notNullValue(),
								equalTo(anilistAnswer.getData().getMedia())
						));

						assertThat(response.getLocalAnimeInformation(), allOf(
								notNullValue(),
								equalTo(anime)
						));

						assertThat(response.getLocalAnimeReviews(), allOf(
								notNullValue(),
								containsInRelativeOrder(secondReview, thirdReview, firstReview)
						));

						assertThat(response.getLocalUserAnimeInformation(), allOf(
								notNullValue(),
								equalTo(userAnimeInfo)
						));
					}
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
			void getAnimeById_400Status_ThrowException() {
				//given
				long animeId = 1;

				//when
				AnilistException exception = assertThrows(AnilistException.class, () -> service.getAnimeById(animeId));

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
						equalTo(DEFAULT_REQUEST_LOCALE)
				));

				assertThat(exception.getLogMessage(), allOf(
						notNullValue(),
						not(equalToCompressingWhiteSpace(""))
				));
			}

			@Test
			void getAnimeById_400StatusNonDefaultLocale_ThrowException() {
				//given
				changeLocaleToFrance();

				long animeId = 1;

				//when
				AnilistException exception = assertThrows(AnilistException.class, () -> service.getAnimeById(animeId));

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
						equalTo(Locale.FRANCE)
				));

				assertThat(exception.getLogMessage(), allOf(
						notNullValue(),
						not(equalToCompressingWhiteSpace(""))
				));
			}
		}
	}

	private void changeLocaleToFrance() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setPreferredLocales(List.of(Locale.FRANCE));
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}
}