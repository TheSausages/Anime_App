package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.query.parameters.connections.media.MediaArguments;
import anime.app.anilist.request.query.parameters.connections.media.MediaConnection;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateField;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.characters.Character.characterTitle;
import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CharacterTest {

	@Test
	void getCharacterStringWithoutFieldName() {
		//given
		Set<ParameterString> expectedSchedule = TestUtils.getParameterStringSet("id");

		//when
		String actualString = Character.getCharacterBuilder()
				.id()
				.build()
				.getCharacterStringWithoutFieldName();

		//then
		assertThat(actualString, allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedSchedule)
		));
	}

	@Test
	void getCharacterBuilder__ReturnValidBuilder() {
		//given

		//when
		Character.CharacterBuilder builder = Character.getCharacterBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(Character.CharacterBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Character Builder")
	class CharacterBuilderTest {
		@Test
		void characterBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Character.getCharacterBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void characterBuilder_Id_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet("id");

			//when
			Character character = Character.getCharacterBuilder().id().build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_Name_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"name {\nfirst\nmiddle\nlast\nfull\nnative\nalternative\nalternativeSpoiler\n}"
			);

			//when
			Character character = Character.getCharacterBuilder().name().build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_Image_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"image {\nlarge\nmedium\n}"
			);

			//when
			Character character = Character.getCharacterBuilder().image().build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_DescriptionWithoutArgument_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"description"
			);

			//when
			Character character = Character.getCharacterBuilder().description().build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_DescriptionWithArgument_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"description(asHtml: true)"
			);

			//when
			Character character = Character.getCharacterBuilder().description(true).build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_Gender_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"gender"
			);

			//when
			Character character = Character.getCharacterBuilder().gender().build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_DateOfBirth_ReturnCorrectString() {
			//given
			FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.dateOfBirth).allAndBuild();
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					FuzzyDateFieldParameter.dateOfBirth + " " + "{\nyear\nmonth\nday\n}"
			);

			//when
			Character character = Character.getCharacterBuilder().dateOfBirth(dateField).build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_Age_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"age"
			);

			//when
			Character character = Character.getCharacterBuilder().age().build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_AnilistSiteUrl_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"siteUrl"
			);

			//when
			Character character = Character.getCharacterBuilder().aniListSiteUrl().build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_Favourites_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"favourites"
			);

			//when
			Character character = Character.getCharacterBuilder().favourites().build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_ModNotes_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"modNotes"
			);

			//when
			Character character = Character.getCharacterBuilder().modNotes().build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_MediaWithoutArguments_ReturnCorrectString() {
			//given
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(PageInfo.getPageInfoBuilder().currentPage().build())
					.build();
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"media " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Character character = Character.getCharacterBuilder().media(connection).build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_MediaWithArguments_ReturnCorrectString() {
			//given
			int page = 1;
			MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder().page(page).build();
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(PageInfo.getPageInfoBuilder().currentPage().build())
					.build();
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"media(page: " + page + ") " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Character character = Character.getCharacterBuilder().media(arguments, connection).build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}

		@Test
		void characterBuilder_All_ReturnCorrectString() {
			//given
			FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.dateOfBirth).allAndBuild();
			int page = 1;
			MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder().page(page).build();
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(PageInfo.getPageInfoBuilder().currentPage().build())
					.build();
			Set<ParameterString> expectedString = TestUtils.getParameterStringSet(
					"id",
					"name {\nfirst\nmiddle\nlast\nfull\nnative\nalternative\nalternativeSpoiler\n}",
					"image {\nlarge\nmedium\n}",
					"description",
					"gender",
					FuzzyDateFieldParameter.dateOfBirth + " " + "{\nyear\nmonth\nday\n}",
					"age",
					"siteUrl",
					"favourites",
					"modNotes",
					"media(page: " + page + ") " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Character character = Character.getCharacterBuilder()
					.id()
					.name()
					.image()
					.description()
					.gender()
					.dateOfBirth(dateField)
					.age()
					.aniListSiteUrl()
					.favourites()
					.modNotes()
					.media(arguments, connection)
					.build();

			//then
			assertThat(character.getCharacterString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterTitle, expectedString)
			));
		}
	}
}