package anime.app.anilist.request.query.parameters.connections.trends;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static anime.app.anilist.request.utils.QueryArgumentMatcher.containsAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaTrendsArgumentsTest {

	@Test
	void getMediaTrendsArgumentsBuilder__ReturnValidBuilder() {
		//given

		//when
		MediaTrendsArguments.MediaTrendsArgumentsBuilder builder = MediaTrendsArguments.getMediaTrendsArgumentsBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(MediaTrendsArguments.MediaTrendsArgumentsBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media Trends Arguments Builder")
	class MediaTrendsArgumentsBuilderTest {
		@Test
		void mediaTrendsArgumentsBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> MediaTrendsArguments.getMediaTrendsArgumentsBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaTrendsArgumentsBuilder_MediaTrendSortSingle_ReturnCorrectString() {
			//given
			MediaTrendSort[] sorts = new MediaTrendSort[] {MediaTrendSort.ID};
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments("sort: " + Arrays.toString(sorts));

			//when
			MediaTrendsArguments actualArguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getMediaTrendsArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaTrendsArgumentsBuilder_MediaTrendSortMany_ReturnCorrectString() {
			//given
			MediaTrendSort[] sorts = new MediaTrendSort[] {MediaTrendSort.ID, MediaTrendSort.MEDIA_ID};
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments("sort: " + Arrays.toString(sorts));

			//when
			MediaTrendsArguments actualArguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getMediaTrendsArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaTrendsArgumentsBuilder_ReleasingNoArgument_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments("releasing: true");

			//when
			MediaTrendsArguments actualArguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
					.releasing()
					.build();

			//then
			assertThat(actualArguments.getMediaTrendsArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaTrendsArgumentsBuilder_ReleasingWithArgument_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments("releasing: false");

			//when
			MediaTrendsArguments actualArguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
					.releasing(false)
					.build();

			//then
			assertThat(actualArguments.getMediaTrendsArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaTrendsArgumentsBuilder_Page_ReturnCorrectString() {
			//given
			int page = 1;
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments("page: " + page);

			//when
			MediaTrendsArguments actualArguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
					.page(page)
					.build();

			//then
			assertThat(actualArguments.getMediaTrendsArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaTrendsArgumentsBuilder_PerPage_ReturnCorrectString() {
			//given
			int perPage = 30;
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments("perPage: " + perPage);

			//when
			MediaTrendsArguments actualArguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getMediaTrendsArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaTrendsArgumentsBuilder_AllParameters_ReturnCorrectString() {
			//given
			int page = 1;
			int perPage =  30;
			MediaTrendSort[] sorts = new MediaTrendSort[] {MediaTrendSort.ID, MediaTrendSort.MEDIA_ID};
			Set<ParameterString> expectedArguments = TestUtils.getParameterStringSetArguments(
					"releasing: true",
					"page: " + page,
					"perPage: " + perPage,
					"sort: " + Arrays.toString(sorts)
			);

			//when
			MediaTrendsArguments actualArguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
					.releasing()
					.page(page)
					.perPage(perPage)
					.sort(sorts)
					.build();

			//then
			assertThat(actualArguments.getMediaTrendsArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}
	}
}