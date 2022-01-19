package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.staff.StaffEdge.STAFF_EDGE_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StaffEdgeTest {

	@Test
	void getStaffEdgeBuilder__ReturnValidBuilder() {
		//given

		//when
		StaffEdge.StaffEdgeBuilder builder = StaffEdge.getStaffEdgeBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(StaffEdge.StaffEdgeBuilder.class)
		));
	}

	@Test
	void getStaffEdgeWithoutFieldName__ReturnCorrectString() {
		//given
		Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
				"id"
		);

		//when
		StaffEdge actualEdge = StaffEdge.getStaffEdgeBuilder()
				.id()
				.build();

		//then
		assertThat(actualEdge.getStaffEdgeWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedEdge)
		));
	}

	@Nested
	@DisplayName("Test Staff Edge Builder")
	class StaffEdgeBuilderTest {
		@Test
		void staffEdgeBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> StaffEdge.getStaffEdgeBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void characterEdgeBuilder_Node_ReturnCorrectString() {
			//given
			Staff staff = Staff.getStaffBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"node " + staff.getStaffWithoutFieldName()
			);

			//when
			StaffEdge actualEdge = StaffEdge.getStaffEdgeBuilder()
					.node(staff)
					.build();
			//then
			assertThat(actualEdge.getStaffEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STAFF_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_Id_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"id"
			);

			//when
			StaffEdge actualEdge = StaffEdge.getStaffEdgeBuilder()
					.id()
					.build();
			//then
			assertThat(actualEdge.getStaffEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STAFF_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_Role_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"role"
			);

			//when
			StaffEdge actualEdge = StaffEdge.getStaffEdgeBuilder()
					.role()
					.build();
			//then
			assertThat(actualEdge.getStaffEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STAFF_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_FavouriteOrder_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"favouriteOrder"
			);

			//when
			StaffEdge actualEdge = StaffEdge.getStaffEdgeBuilder()
					.favouriteOrder()
					.build();
			//then
			assertThat(actualEdge.getStaffEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STAFF_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void characterEdgeBuilder_AllParameters_ReturnCorrectString() {
			//given
			Staff staff = Staff.getStaffBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"id",
					"role",
					"favouriteOrder",
					"node " + staff.getStaffWithoutFieldName()
			);

			//when
			StaffEdge actualEdge = StaffEdge.getStaffEdgeBuilder()
					.id()
					.role()
					.favouriteOrder()
					.node(staff)
					.build();
			//then
			assertThat(actualEdge.getStaffEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STAFF_EDGE_TITLE, expectedEdge)
			));
		}
	}
}