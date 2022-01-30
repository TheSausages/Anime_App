package anime.app.anilist.request.query.parameters.connections.media;

import anime.app.anilist.request.query.parameters.media.MediaSort;
import anime.app.anilist.request.query.parameters.media.MediaType;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static anime.app.anilist.request.utils.QueryArgumentMatcher.containsAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaArgumentsTest {

	@Test
	void getMediaArgumentsBuilder__ReturnValidBuilder() {
		//given

		//when
		MediaArguments.MediaArgumentsBuilder builder = MediaArguments.getMediaArgumentsBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(MediaArguments.MediaArgumentsBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media Arguments Builder")
	class MediaArgumentsBuilderTest {
		@Test
		void mediaArgumentsBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> MediaArguments.getMediaArgumentsBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaArgumentsBuilder_SortBySingle_ReturnCorrectString() {
			//given
			MediaSort[] sorts = new MediaSort[] {MediaSort.ID};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("sort: " + Arrays.toString(sorts));

			//when
			MediaArguments actualArguments = MediaArguments.getMediaArgumentsBuilder()
					.sortBy(sorts)
					.build();

			//then
			assertThat(actualArguments.getMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaArgumentsBuilder_SortByMany_ReturnCorrectString() {
			//given
			MediaSort[] sorts = new MediaSort[] {MediaSort.ID, MediaSort.CHAPTERS};
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("sort: " + Arrays.toString(sorts));

			//when
			MediaArguments actualArguments = MediaArguments.getMediaArgumentsBuilder()
					.sortBy(sorts)
					.build();

			//then
			assertThat(actualArguments.getMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaArgumentsBuilder_Type_ReturnCorrectString() {
			//given
			MediaType type = MediaType.ANIME;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("type: " + type.name());

			//when
			MediaArguments actualArguments = MediaArguments.getMediaArgumentsBuilder()
					.type(type)
					.build();

			//then
			assertThat(actualArguments.getMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaArgumentsBuilder_OnListWithoutArgument_ReturnCorrectString() {
			//given
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("onList: true");

			//when
			MediaArguments actualArguments = MediaArguments.getMediaArgumentsBuilder()
					.onList()
					.build();

			//then
			assertThat(actualArguments.getMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaArgumentsBuilder_OnListWithArgument_ReturnCorrectString() {
			//given
			boolean onList = false;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("onList: " + onList);

			//when
			MediaArguments actualArguments = MediaArguments.getMediaArgumentsBuilder()
					.onList(onList)
					.build();

			//then
			assertThat(actualArguments.getMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaArgumentsBuilder_Page_ReturnCorrectString() {
			//given
			int page = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("page: " + page);

			//when
			MediaArguments actualArguments = MediaArguments.getMediaArgumentsBuilder()
					.page(page)
					.build();

			//then
			assertThat(actualArguments.getMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaArgumentsBuilder_PerPage_ReturnCorrectString() {
			//given
			int perPage = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet("perPage: " + perPage);

			//when
			MediaArguments actualArguments = MediaArguments.getMediaArgumentsBuilder()
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}

		@Test
		void mediaArgumentsBuilder_AllParameters_ReturnCorrectString() {
			//given
			MediaSort[] sorts = new MediaSort[] {MediaSort.ID, MediaSort.CHAPTERS};
			MediaType type = MediaType.ANIME;
			int page  = 1;
			int perPage = 1;
			List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
					"sort: " + Arrays.toString(sorts),
					"type: " + type.name(),
					"onList: true",
					"page: " + page,
					"perPage: " + perPage
			);

			//when
			MediaArguments actualArguments = MediaArguments.getMediaArgumentsBuilder()
					.sortBy(sorts)
					.type(type)
					.onList()
					.page(page)
					.perPage(perPage)
					.build();

			//then
			assertThat(actualArguments.getMediaArgumentsString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsAllSetElements(expectedArguments)
			));
		}
	}
}