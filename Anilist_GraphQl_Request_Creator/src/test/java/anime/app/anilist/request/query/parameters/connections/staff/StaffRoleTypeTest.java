package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.query.parameters.connections.staff.StaffRoleType.STAFF_ROLE_TYPE_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StaffRoleTypeTest {

	@Test
	void getStaffRoleTypeStringWithoutFieldName__ReturnCorrectString() {
		//given
		List<String> expectedType = TestUtils.buildFieldParameterStringSet(
				"roleNotes"
		);

		//when
		StaffRoleType actualType = StaffRoleType.getStaffRoleTypeBuilder()
				.roleNotes()
				.build();

		//then
		assertThat(actualType.getStaffRoleTypeStringWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedType)
		));
	}

	@Test
	void getStaffRoleTypeBuilder__ReturnValidBuilder() {
		//given

		//when
		StaffRoleType.StaffRoleTypeBuilder builder = StaffRoleType.getStaffRoleTypeBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(StaffRoleType.StaffRoleTypeBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Staff Role Type Builder")
	class StaffRoleTypeBuilderTest {
		@Test
		void staffRoleTypeBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> StaffRoleType.getStaffRoleTypeBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void staffRoleTypeBuilder_RoleNotes_ReturnCorrectString() {
			//given
			List<String> expectedType = TestUtils.buildFieldParameterStringSet(
					"roleNotes"
			);

			//when
			StaffRoleType actualType = StaffRoleType.getStaffRoleTypeBuilder()
					.roleNotes()
					.build();

			//then
			assertThat(actualType.getStaffRoleTypeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STAFF_ROLE_TYPE_TITLE, expectedType)
			));
		}

		@Test
		void staffRoleTypeBuilder_DubGroups_ReturnCorrectString() {
			//given
			List<String> expectedType = TestUtils.buildFieldParameterStringSet(
					"dubGroup"
			);

			//when
			StaffRoleType actualType = StaffRoleType.getStaffRoleTypeBuilder()
					.dubGroup()
					.build();

			//then
			assertThat(actualType.getStaffRoleTypeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STAFF_ROLE_TYPE_TITLE, expectedType)
			));
		}

		@Test
		void staffRoleTypeBuilder_VoiceActor_ReturnCorrectString() {
			//given
			Staff staff = Staff.getStaffBuilder().id().build();
			List<String> expectedType = TestUtils.buildFieldParameterStringSet(
					"voiceActor " + staff.getStaffWithoutFieldName()
			);

			//when
			StaffRoleType actualType = StaffRoleType.getStaffRoleTypeBuilder()
					.voiceActor(staff)
					.build();

			//then
			assertThat(actualType.getStaffRoleTypeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STAFF_ROLE_TYPE_TITLE, expectedType)
			));
		}

		@Test
		void staffRoleTypeBuilder_AllParameters_ReturnCorrectString() {
			//given
			Staff staff = Staff.getStaffBuilder().id().build();
			List<String> expectedType = TestUtils.buildFieldParameterStringSet(
					"roleNotes",
					"dubGroup",
					"voiceActor " + staff.getStaffWithoutFieldName()
			);

			//when
			StaffRoleType actualType = StaffRoleType.getStaffRoleTypeBuilder()
					.roleNotes()
					.dubGroup()
					.voiceActor(staff)
					.build();

			//then
			assertThat(actualType.getStaffRoleTypeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STAFF_ROLE_TYPE_TITLE, expectedType)
			));
		}
	}
}