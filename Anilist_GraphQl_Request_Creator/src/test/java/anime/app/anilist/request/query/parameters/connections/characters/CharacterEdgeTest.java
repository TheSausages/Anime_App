package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.connections.staff.Staff;
import anime.app.anilist.request.query.parameters.connections.staff.StaffLanguage;
import anime.app.anilist.request.query.parameters.connections.staff.StaffRoleType;
import anime.app.anilist.request.query.parameters.connections.staff.StaffSort;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
					.node(character)
					.build();
			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_Id_ReturnCorrectString() {
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

		@Test
		void characterEdgeBuilder_Media_ReturnCorrectString() {
			//given
			Media media = new Media("media(id: ${id}) {\nformat\n}");
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"media {\nformat\n}"
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.media(media)
					.build();
			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_VoiceActor_ReturnCorrectString() {
			//given
			Staff staff = Staff.getStaffBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"voiceActors " + staff.getStaffWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.voiceActors(staff)
					.build();

			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_VoiceActorWithLanguage_ReturnCorrectString() {
			//given
			StaffLanguage language = StaffLanguage.ENGLISH;
			Staff staff = Staff.getStaffBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"voiceActors(language: " + language.name() + ") " + staff.getStaffWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.voiceActors(staff, language)
					.build();

			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_VoiceActorWithSortSingle_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID};
			Staff staff = Staff.getStaffBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"voiceActors(sort: " + Arrays.toString(sorts) + ") " + staff.getStaffWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.voiceActors(staff, sorts)
					.build();

			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_VoiceActorWithSortMany_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ROLE};
			Staff staff = Staff.getStaffBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"voiceActors(sort: " + Arrays.toString(sorts) + ") " + staff.getStaffWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.voiceActors(staff, sorts)
					.build();

			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_VoiceActorWithSortManyAndLanguage_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ROLE};
			StaffLanguage language = StaffLanguage.ENGLISH;
			Staff staff = Staff.getStaffBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"voiceActors(language: " + language.name() + ", sort: " + Arrays.toString(sorts) + ") " + staff.getStaffWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.voiceActors(staff, language, sorts)
					.build();

			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_VoiceActorsRoles_ReturnCorrectString() {
			//given
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"voiceActorsRoles " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.voiceActorsRoles(type)
					.build();

			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_VoiceActorsRolesWithLanguage_ReturnCorrectString() {
			//given
			StaffLanguage language = StaffLanguage.ENGLISH;
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"voiceActorsRoles(language: " + language.name() + ") " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.voiceActorsRoles(type, language)
					.build();

			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_VoiceActorsRolesWithSortSingle_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID};
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"voiceActorsRoles(sort: " + Arrays.toString(sorts) + ") " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.voiceActorsRoles(type, sorts)
					.build();

			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_VoiceActorsRolesWithSortMany_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ROLE};
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"voiceActorsRoles(sort: " + Arrays.toString(sorts) + ") " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.voiceActorsRoles(type, sorts)
					.build();

			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_VoiceActorsRolesWithSortManyAndLanguage_ReturnCorrectString() {
			//given
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ROLE};
			StaffLanguage language = StaffLanguage.ENGLISH;
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"voiceActorsRoles(language: " + language.name() + ", sort: " + Arrays.toString(sorts) + ") " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.voiceActorsRoles(type, language, sorts)
					.build();

			//then
			assertThat(actualEdge.getCharacterEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(characterEdgeTitle, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_AllParameters_ReturnCorrectString() {
			//given
			Character character = Character.getCharacterBuilder().id().build();
			Staff staff = Staff.getStaffBuilder().id().build();
			StaffSort[] sorts = new StaffSort[] {StaffSort.ID, StaffSort.ROLE};
			StaffLanguage language = StaffLanguage.ENGLISH;
			StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder().roleNotes().build();
			Media media = new Media("media(id: ${id}) {\nformat\n}");
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"node " + character.getCharacterStringWithoutFieldName(),
					"id",
					"role",
					"name",
					"favouriteOrder",
					"media {\nformat\n}",
					"voiceActors(language: " + language.name() + ", sort: " + Arrays.toString(sorts) + ") " + staff.getStaffWithoutFieldName(),
					"voiceActorsRoles(language: " + language.name() + ", sort: " + Arrays.toString(sorts) + ") " + type.getStaffRoleTypeStringWithoutFieldName()
			);

			//when
			CharacterEdge actualEdge = CharacterEdge.getCharacterEdgeBuilder()
					.node(character)
					.id()
					.role()
					.name()
					.favouriteOrder()
					.media(media)
					.voiceActors(staff, language, sorts)
					.voiceActorsRoles(type, language, sorts)
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