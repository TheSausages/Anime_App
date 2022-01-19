package anime.app.anilist.request.query.parameters;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.common.AnilistUser;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.common.AnilistUser.USER_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AnilistUserTest {

	@Test
	void getAnilistUserWithoutFieldName__ReturnCorrectString() {
		//given
		Set<ParameterString> expectedUser = TestUtils.getParameterStringSetField(
				"id"
		);

		//when
		AnilistUser actualUser = AnilistUser.getUserBuilder()
				.id()
				.build();

		//then
		assertThat(actualUser.getAnilistUserWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedUser)
		));
	}

	@Test
	void getAnilistUserBuilder__ReturnValidBuilder() {
		//given

		//when
		AnilistUser.AnilistUserBuilder builder = AnilistUser.getUserBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(AnilistUser.AnilistUserBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Anilist User Builder")
	class AnilistUserBuilderTest {
		@Test
		void anilistUserBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> AnilistUser.getUserBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void anilistUserBuilder_Id_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedUser = TestUtils.getParameterStringSetField(
					"id"
			);

			//when
			AnilistUser actualUser = AnilistUser.getUserBuilder().id().build();

			//then
			assertThat(actualUser.getAnilistUserString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(USER_TITLE, expectedUser)
			));
		}

		@Test
		void anilistUserBuilder_Name_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedUser = TestUtils.getParameterStringSetField(
					"name"
			);

			//when
			AnilistUser actualUser = AnilistUser.getUserBuilder().name().build();

			//then
			assertThat(actualUser.getAnilistUserString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(USER_TITLE, expectedUser)
			));
		}

		@Test
		void anilistUserBuilder_Avatar_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedUser = TestUtils.getParameterStringSetField(
					"avatar {\nlarge\nmedium\n}"
			);

			//when
			AnilistUser actualUser = AnilistUser.getUserBuilder().avatar().build();

			//then
			assertThat(actualUser.getAnilistUserString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(USER_TITLE, expectedUser)
			));
		}

		@Test
		void anilistUserBuilder_AllParameters_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedUser = TestUtils.getParameterStringSetField(
					"id",
					"name",
					"avatar {\nlarge\nmedium\n}"
			);

			//when
			AnilistUser actualUser = AnilistUser.getUserBuilder()
					.id()
					.name()
					.avatar()
					.build();

			//then
			assertThat(actualUser.getAnilistUserString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(USER_TITLE, expectedUser)
			));
		}
	}
}