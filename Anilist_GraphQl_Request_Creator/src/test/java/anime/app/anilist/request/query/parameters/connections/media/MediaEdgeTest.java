package anime.app.anilist.request.query.parameters.connections.media;

import anime.app.anilist.request.query.media.Field;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.connections.characters.Character;
import anime.app.anilist.request.query.parameters.connections.staff.Staff;
import anime.app.anilist.request.query.parameters.connections.staff.StaffLanguage;
import anime.app.anilist.request.query.parameters.connections.staff.StaffRoleType;
import anime.app.anilist.request.query.parameters.connections.staff.StaffSort;
import anime.app.anilist.request.query.parameters.media.MediaFormat;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static anime.app.anilist.request.query.parameters.connections.media.MediaEdge.MEDIA_EDGE_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaEdgeTest {

	@Test
	void getMediaEdgeWithoutFieldName__ReturnCorrectString() {
		//given
		List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_Id_ReturnCorrectString() {
			//given
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_RelationTypeWithoutParameter_ReturnCorrectString() {
			//given
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_RelationTypeWithParameter_ReturnCorrectString() {
			//given
			int version = 1;
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_IsMainStudio_ReturnCorrectString() {
			//given
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_Characters_ReturnCorrectString() {
			//given
			Character character = Character.getCharacterBuilder().id().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_CharacterRole_ReturnCorrectString() {
			//given
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_CharacterName_ReturnCorrectString() {
			//given
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_RoleNotes_ReturnCorrectString() {
			//given
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_DubGroup_ReturnCorrectString() {
			//given
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_StaffRole_ReturnCorrectString() {
			//given
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
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
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_VoiceActor_ReturnCorrectString() {
			//given
			Staff staff = Staff.getStaffBuilder().id().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"voiceActors " + staff.getStaffWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.voiceActors(staff)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_VoiceActorWithLanguage_ReturnCorrectString() {
			//given
			StaffLanguage language = StaffLanguage.ENGLISH;
			Staff staff = Staff.getStaffBuilder().id().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"voiceActors(language: " + language.name() + ") " + staff.getStaffWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.voiceActors(staff, language)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_VoiceActorWithSortSingle_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID};
			Staff staff = Staff.getStaffBuilder().id().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"voiceActors(sort: " + Arrays.toString(sorts) + ") " + staff.getStaffWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.voiceActors(staff, sorts)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_VoiceActorWithSortMany_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ROLE};
			Staff staff = Staff.getStaffBuilder().id().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"voiceActors(sort: " + Arrays.toString(sorts) + ") " + staff.getStaffWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.voiceActors(staff, sorts)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_VoiceActorWithSortManyAndLanguage_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ROLE};
			StaffLanguage language = StaffLanguage.ENGLISH;
			Staff staff = Staff.getStaffBuilder().id().build();
			List<String>expectedEdge = TestUtils.buildFieldParameterStringSet(
					"voiceActors(language: " + language.name() + ", sort: " + Arrays.toString(sorts) + ") " + staff.getStaffWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.voiceActors(staff, language, sorts)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_VoiceActorsRoles_ReturnCorrectString() {
			//given
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"voiceActorRoles " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.voiceActorsRoles(type)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_VoiceActorsRolesWithLanguage_ReturnCorrectString() {
			//given
			StaffLanguage language = StaffLanguage.ENGLISH;
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"voiceActorRoles(language: " + language.name() + ") " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.voiceActorsRoles(type, language)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_VoiceActorsRolesWithSortSingle_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID};
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"voiceActorRoles(sort: " + Arrays.toString(sorts) + ") " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.voiceActorsRoles(type, sorts)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_VoiceActorsRolesWithSortMany_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ROLE};
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"voiceActorRoles(sort: " + Arrays.toString(sorts) + ") " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.voiceActorsRoles(type, sorts)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_VoiceActorsRolesWithSortManyAndLanguage_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ROLE};
			StaffLanguage language = StaffLanguage.ENGLISH;
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"voiceActorRoles(language: " + language.name() + ", sort: " + Arrays.toString(sorts) + ") " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			MediaEdge actualEdge = MediaEdge.getMediaEdgeBuilder()
					.voiceActorsRoles(type, language, sorts)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void mediaEdgeBuilder_AllParameters_ReturnCorrectString() {
			//given
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ROLE};
			StaffLanguage language = StaffLanguage.ENGLISH;
			Staff staff = Staff.getStaffBuilder().id().build();
			Character character = Character.getCharacterBuilder().id().build();
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"node " + media.getRequestedMediaFields(),
					"id",
					"relationType(version: 2)",
					"isMainStudio",
					"characters " + character.getCharacterStringWithoutFieldName(),
					"characterRole",
					"characterName",
					"roleNotes",
					"dubGroup",
					"staffRole",
					"voiceActors(language: " + language.name() + ", sort: " + Arrays.toString(sorts) + ") " + staff.getStaffWithoutFieldName(),
					"voiceActorRoles(language: " + language.name() + ", sort: " + Arrays.toString(sorts) + ") " + type.getStaffRoleTypeStringWithoutFieldName()
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
					.voiceActors(staff, language, sorts)
					.voiceActorsRoles(type, language, sorts)
					.build();

			//then
			assertThat(actualEdge.getMediaEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_EDGE_TITLE, expectedEdge)
			));
		}
	}
}