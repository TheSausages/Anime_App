package anime.app.anilist.request.query.parameters.media;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaTitleTest {

	@Test
	void getMediaTitleBuilder__ReturnValidBuilder() {
		//given

		//when
		MediaTitle.MediaTitleBuilder builder = MediaTitle.getMediaTitleBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(MediaTitle.MediaTitleBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media Title Builder")
	class MediaTitleBuilderTest {
		@Test
		void mediaTitleBuilder_NoLanguageSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> MediaTitle.getMediaTitleBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaTitleBuilder_RomajiLanguage_ReturnValidString() {
			//given
			String expectedTitle = "title {\nromaji\n}";

			//when
			MediaTitle actualTitle = MediaTitle.getMediaTitleBuilder().romajiLanguage().build();

			//then
			assertThat(actualTitle.getTitleLanguages(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedTitle)
			));
		}

		@Test
		void mediaTitleBuilder_RomajiLanguageStylised_ReturnValidString() {
			//given
			String expectedTitle = "title {\nromaji(stylised: true)\n}";

			//when
			MediaTitle actualTitle = MediaTitle.getMediaTitleBuilder().romajiLanguageStylized().build();

			//then
			assertThat(actualTitle.getTitleLanguages(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedTitle)
			));
		}

		@Test
		void mediaTitleBuilder_EnglishLanguage_ReturnValidString() {
			//given
			String expectedTitle = "title {\nenglish\n}";

			//when
			MediaTitle actualTitle = MediaTitle.getMediaTitleBuilder().englishLanguage().build();

			//then
			assertThat(actualTitle.getTitleLanguages(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedTitle)
			));
		}

		@Test
		void mediaTitleBuilder_EnglishLanguageStylised_ReturnValidString() {
			//given
			String expectedTitle = "title {\nenglish(stylised: true)\n}";

			//when
			MediaTitle actualTitle = MediaTitle.getMediaTitleBuilder().englishLanguageStylized().build();

			//then
			assertThat(actualTitle.getTitleLanguages(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedTitle)
			));
		}

		@Test
		void mediaTitleBuilder_NativeLanguage_ReturnValidString() {
			//given
			String expectedTitle = "title {\nnative\n}";

			//when
			MediaTitle actualTitle = MediaTitle.getMediaTitleBuilder().nativeLanguage().build();

			//then
			assertThat(actualTitle.getTitleLanguages(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedTitle)
			));
		}

		@Test
		void mediaTitleBuilder_NativeLanguageStylised_ReturnValidString() {
			//given
			String expectedTitle = "title {\nnative(stylised: true)\n}";

			//when
			MediaTitle actualTitle = MediaTitle.getMediaTitleBuilder().nativeLanguageStylized().build();

			//then
			assertThat(actualTitle.getTitleLanguages(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedTitle)
			));
		}

		@Test
		void mediaTitleBuilder_OverwriteWhenStylizedIsUsedAfterNonStylized_ReturnValidString() {
			//given
			String expectedTitle = "title {\nnative(stylised: true)\n}";

			//when
			MediaTitle actualTitle = MediaTitle.getMediaTitleBuilder()
					.nativeLanguage()
					.nativeLanguageStylized()
					.build();

			//then
			assertThat(actualTitle.getTitleLanguages(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedTitle)
			));
		}

		@Test
		void mediaTitleBuilder_OverwriteWhenNonStylizedIsUsedAfterStylized_ReturnValidString() {
			//given
			String expectedTitle = "title {\nnative\n}";

			//when
			MediaTitle actualTitle = MediaTitle.getMediaTitleBuilder()
					.nativeLanguageStylized()
					.nativeLanguage()
					.build();

			//then
			assertThat(actualTitle.getTitleLanguages(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedTitle)
			));
		}

		@Test
		void mediaTitleBuilder_EnglishAndNativeStylised_ReturnValidString() {
			//given
			String expectedTitle = "title {\nenglish\nnative(stylised: true)\n}";

			//when
			MediaTitle actualTitle = MediaTitle
					.getMediaTitleBuilder()
					.englishLanguage()
					.nativeLanguageStylized()
					.build();

			//then
			assertThat(actualTitle.getTitleLanguages(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedTitle)
			));
		}
	}
}