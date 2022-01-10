package anime.app.anilist.request.query.parameters.connections.media;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.connections.characters.Character;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.media.MediaEdge.mediaEdgeTitle;
import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaEdgeTest {

	@Test
	void getMediaEdgeWithoutFieldName__ReturnCorrectString() {
		//given
		Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
				"id"
		);

		//when
		MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
				.id()
				.build();

		//then
		assertThat(actualEdge.getMediaEdgeWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedEdge)
		));
	}

	@Test
	void getMediaEdgeBuilder__ReturnValidBuilder() {
		//given

		//when
		MediaEdge.MediaEdgeBuilder builder = MediaEdge.getMediaEdgeBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(MediaEdge.MediaEdgeBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media Edge Builder")
	class MediaEdgeBuilderTest {
		@Test
		void mediaEdgeBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> MediaEdge.getMediaEdgeBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaEdgeBuilder_Node_ReturnCorrectString() {
			//given
			Media media = new Media("media(id: ${id}) {\nformat\n}");
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"node " + media.getRequestedMediaFields()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.node(media)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_Id_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"id"
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.id()
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_RelationTypeWithoutParameter_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"relationType(version: 2)"
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.relationType()
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_RelationTypeWithParameter_ReturnCorrectString() {
			//given
			int version = 1;
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"relationType(version: " + version + ")"
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.relationType(version)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_IsMainStudio_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"isMainStudio"
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.isMainStudio()
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_Characters_ReturnCorrectString() {
			//given
			Character character = Character.getCharacterBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"characters " + character.getCharacterStringWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.characters(character)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_CharacterRole_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"characterRole"
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.characterRole()
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_CharacterName_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"characterName"
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.characterName()
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_RoleNotes_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"roleNotes"
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.roleNotes()
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_DubGroup_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"dubGroup"
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.dubGroup()
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_StaffRole_ReturnCorrectString() {
			//given
			Character character = Character.getCharacterBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"staffRole"
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.staffRole()
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_AllParameters_ReturnCorrectString() {
			//given
			Media media = new Media("media(id: ${id}) {\nformat\n}");
			Character character = Character.getCharacterBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"node " + media.getRequestedMediaFields(),
					"id",
					"relationType(version: 2)",
					"isMainStudio",
					"characters " + character.getCharacterStringWithoutFieldName(),
					"characterRole",
					"characterName",
					"roleNotes",
					"dubGroup",
					"staffRole"
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.node(media)
					.id()
					.relationType()
					.isMainStudio()
					.characters(character)
					.characterRole()
					.characterName()
					.roleNotes()
					.dubGroup()
					.staffRole()
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(mediaEdgeTitle, expectedEdge)
			));
		}
	}
}