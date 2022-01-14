package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.staff.StaffConnection.staffConnectionTitle;
import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StaffConnectionTest {

	@Test
	void getStaffConnectionWithoutFieldName__ReturnCorrectString() {
		//given
		PageInfo info = PageInfo.getPageInfoBuilder().total().build();
		Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
				info.getPageInfoString()
		);


		//when
		StaffConnection actualConnection = StaffConnection.getMediaConnectionBuilder()
				.pageInfo(info)
				.build();

		//then
		assertThat(actualConnection.getStaffConnectionWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedConnection)
		));
	}

	@Test
	void getMediaConnectionBuilder__ReturnValidBuilder() {
		//given

		//when
		StaffConnection.StaffConnectionBuilder builder = StaffConnection.getMediaConnectionBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(StaffConnection.StaffConnectionBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Staff Connection Builder")
	class StaffConnectionBuilderTest {
		@Test
		void staffConnectionBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> StaffConnection.getMediaConnectionBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void staffConnectionBuilder_Edge_ReturnCorrectString() {
			//given
			StaffEdge edge = StaffEdge.getStaffEdgeBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"edges " + edge.getStaffEdgeWithoutFieldName()
			);


			//when
			StaffConnection actualConnection = StaffConnection.getMediaConnectionBuilder()
					.edges(edge)
					.build();

			//then
			assertThat(actualConnection.getStaffConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffConnectionTitle, expectedEdge)
			));
		}

		@Test
		void staffConnectionBuilder_Nodes_ReturnCorrectString() {
			//given
			Staff staff = Staff.getStaffBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
					"nodes " + staff.getStaffWithoutFieldName()
			);


			//when
			StaffConnection actualConnection = StaffConnection.getMediaConnectionBuilder()
					.nodes(staff)
					.build();

			//then
			assertThat(actualConnection.getStaffConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffConnectionTitle, expectedEdge)
			));
		}

		@Test
		void staffConnectionBuilder_PageInfo_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
					info.getPageInfoString()
			);


			//when
			StaffConnection actualConnection = StaffConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getStaffConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffConnectionTitle, expectedConnection)
			));
		}

		@Test
		void staffConnectionBuilder_AllParameters_ReturnCorrectString() {
			//given
			StaffEdge edge = StaffEdge.getStaffEdgeBuilder().id().build();
			Staff staff = Staff.getStaffBuilder().id().build();
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
					"edges " + edge.getStaffEdgeWithoutFieldName(),
					"nodes " + staff.getStaffWithoutFieldName(),
					info.getPageInfoString()
			);


			//when
			StaffConnection actualConnection = StaffConnection.getMediaConnectionBuilder()
					.edges(edge)
					.nodes(staff)
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getStaffConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffConnectionTitle, expectedConnection)
			));
		}
	}
}