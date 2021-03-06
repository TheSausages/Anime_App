package anime.app.anilist.request.query.parameters.connections.studio;

import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.query.parameters.connections.media.MediaArguments;
import anime.app.anilist.request.query.parameters.connections.media.MediaConnection;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.query.parameters.connections.studio.Studio.STUDIO_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StudioTest {

	@Test
	void getStudioWithoutFieldName__ReturnCorrectString() {
		//given
		List<String> expectedSchedule = TestUtils.buildFieldParameterStringSet("id");

		//when
		String actualString = Studio.getStudioBuilder()
				.id()
				.build()
				.getStudioWithoutFieldName();

		//then
		assertThat(actualString, allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedSchedule)
		));
	}

	@Test
	void getStudioBuilder__ReturnValidBuilder() {
		//given

		//when
		Studio.StudioBuilder builder = Studio.getStudioBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(Studio.StudioBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Studio Builder")
	class StudioBuilderTest {
		@Test
		void studioBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Studio.getStudioBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void studioBuilder_Id_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet("id");

			//when
			Studio actualStudio = Studio.getStudioBuilder().id().build();

			//then
			assertThat(actualStudio.getStudioString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_TITLE, expectedString)
			));
		}

		@Test
		void studioBuilder_Name_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet("name");

			//when
			Studio actualStudio = Studio.getStudioBuilder().name().build();

			//then
			assertThat(actualStudio.getStudioString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_TITLE, expectedString)
			));
		}

		@Test
		void studioBuilder_IsAnimationStudio_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet("isAnimationStudio");

			//when
			Studio actualStudio = Studio.getStudioBuilder().isAnimationStudio().build();

			//then
			assertThat(actualStudio.getStudioString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_TITLE, expectedString)
			));
		}

		@Test
		void studioBuilder_SiteUrl_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet("siteUrl");

			//when
			Studio actualStudio = Studio.getStudioBuilder().siteUrl().build();

			//then
			assertThat(actualStudio.getStudioString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_TITLE, expectedString)
			));
		}

		@Test
		void studioBuilder_Favourites_ReturnCorrectString() {
			//given
			List<String> expectedString = TestUtils.buildFieldParameterStringSet("favourites");

			//when
			Studio actualStudio = Studio.getStudioBuilder().favourites().build();

			//then
			assertThat(actualStudio.getStudioString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_TITLE, expectedString)
			));
		}

		@Test
		void studioBuilder_MediaWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder().pageInfo(info).build();
			List<String> expectedString = TestUtils.buildFieldParameterStringSet(
					"media " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Studio actualStudio = Studio.getStudioBuilder().media(connection).build();

			//then
			assertThat(actualStudio.getStudioString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_TITLE, expectedString)
			));
		}

		@Test
		void studioBuilder_MediaWithArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder().pageInfo(info).build();
			MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder().onList().build();
			List<String> expectedString = TestUtils.buildFieldParameterStringSet(
					"media" + arguments.getMediaArgumentsString() + " " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Studio actualStudio = Studio.getStudioBuilder().media(connection, arguments).build();

			//then
			assertThat(actualStudio.getStudioString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_TITLE, expectedString)
			));
		}

		@Test
		void studioBuilder_AllParameters_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().perPage().build();
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder().pageInfo(info).build();
			MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder().onList().build();
			List<String> expectedString = TestUtils.buildFieldParameterStringSet(
					"id",
					"name",
					"isAnimationStudio",
					"siteUrl",
					"favourites",
					"media" + arguments.getMediaArgumentsString() + " " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Studio actualStudio = Studio.getStudioBuilder()
					.id()
					.name()
					.isAnimationStudio()
					.siteUrl()
					.favourites()
					.media(connection, arguments)
					.build();

			//then
			assertThat(actualStudio.getStudioString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_TITLE, expectedString)
			));
		}
	}
}