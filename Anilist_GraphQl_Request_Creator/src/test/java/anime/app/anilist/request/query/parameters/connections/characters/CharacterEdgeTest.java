package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.characters.CharacterEdge.characterEdgeTitle;
import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CharacterEdgeTest {

	@Test
	void getCharacterEdgeBuilder__ReturnValidBuilder() {
		//given

		//when
		CharacterEdge.CharacterEdgeBuilder builder = CharacterEdge.getCharacterEdgeBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(CharacterEdge.CharacterEdgeBuilder.class)
		));
	}

	@Test
	void getCharacterEdgeWithoutFieldName__ReturnCorrectString() {
		//given
		Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
				"id"
		);

		//when
		CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
				.id()
				.build();

		//then
		assertThat(actualEdge.getCharacterEdgeWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedEdge)
		));
	}

	@Nested
	@DisplayName("Test Character Edge Builder")
	class CharacterEdgeBuilderTest {
		@Test
		void characterEdgeBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> CharacterEdge.getCharacterEdgeBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void characterEdgeBuilder_Node_ReturnCorrectString() {
			//given
			Character character = Character.getCharacterBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"node " + character.getCharacterStringWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.id()
					.build();
			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_Edge_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"id"
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.id()
					.build();
			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_Role_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"role"
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.role()
					.build();
			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_Name_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"name"
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.name()
					.build();
			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_FavouriteOrder_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"favouriteOrder"
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.favouriteOrder()
					.build();
			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}
	}
}