package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.characters.CharacterConnection.CHARACTER_CONNECTION_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CharacterConnectionTest {

	@Test
	void getCharacterConnectionWithoutFieldName__ReturnCorrectString() {
		//given
		PageInfo info = PageInfo.getPageInfoBuilder().total().build();
		Set<ParameterString> expectedConnection = TestUtils.buildFieldParameterStringSet(
				info.getPageInfoString()
		);

		//when
		CharacterConnection actualConnection = CharacterConnection.getCharacterConnectionBuilder()
				.pageInfo(info)
				.build();

		//then
		assertThat(actualConnection.getCharacterConnectionWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedConnection)
		));
	}

	@Test
	void getCharacterConnectionBuilder__ReturnValidBuilder() {
		//given

		//when
		CharacterConnection.CharacterConnectionBuilder builder = CharacterConnection.getCharacterConnectionBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(CharacterConnection.CharacterConnectionBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Character Connection Builder")
	class CharacterConnectionBuilderTest {
		@Test
		void airingScheduleConnectionBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> CharacterConnection.getCharacterConnectionBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void airingScheduleConnectionBuilder_Edge_ReturnCorrectString() {
			//given
			CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder().id().build();
			Set<ParameterString> expectedConnection = TestUtils.buildFieldParameterStringSet(
					"edges " + edge.getCharacterEdgeWithoutFieldName()
			);

			//when
			CharacterConnection actualConnection = CharacterConnection.getCharacterConnectionBuilder()
					.edges(edge)
					.build();

			//then
			assertThat(actualConnection.getCharacterConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(CHARACTER_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void airingScheduleConnectionBuilder_Nodes_ReturnCorrectString() {
			//given
			Character character = Character.getCharacterBuilder().id().build();
			Set<ParameterString> expectedConnection = TestUtils.buildFieldParameterStringSet(
					"nodes " + character.getCharacterStringWithoutFieldName()
			);

			//when
			CharacterConnection actualConnection = CharacterConnection.getCharacterConnectionBuilder()
					.nodes(character)
					.build();

			//then
			assertThat(actualConnection.getCharacterConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(CHARACTER_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void airingScheduleConnectionBuilder_PageInfo_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			Set<ParameterString> expectedConnection = TestUtils.buildFieldParameterStringSet(
					info.getPageInfoString()
			);

			//when
			CharacterConnection actualConnection = CharacterConnection.getCharacterConnectionBuilder()
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getCharacterConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(CHARACTER_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void airingScheduleConnectionBuilder_AllParameters_ReturnCorrectString() {
			//given
			CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder().id().build();
			Character character = Character.getCharacterBuilder().id().build();
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			Set<ParameterString> expectedConnection = TestUtils.buildFieldParameterStringSet(
					"edges " + edge.getCharacterEdgeWithoutFieldName(),
					"nodes " + character.getCharacterStringWithoutFieldName(),
					info.getPageInfoString()
			);

			//when
			CharacterConnection actualConnection = CharacterConnection.getCharacterConnectionBuilder()
					.edges(edge)
					.nodes(character)
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getCharacterConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(CHARACTER_CONNECTION_TITLE, expectedConnection)
			));
		}
	}
}